package org.dows.order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.exceptions.BaseException;
import org.dows.order.api.OrderCartApiService;
import org.dows.order.bo.OrderCartAddBo;
import org.dows.order.bo.OrderCartQueryBo;
import org.dows.order.entity.OrderCart;
import org.dows.order.service.OrderCartService;
import org.dows.order.vo.OrderCartInfoVo;
import org.dows.utils.AssertUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author liuhonger
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCatBiz implements OrderCartApiService {

    private final OrderCartService orderCartService;

    //private final Goods
    @Override
    public void addOrderCart(OrderCartAddBo orderCartAddBo) {
        //如果是加+减-
        OrderCart cart = queryOrderCart(orderCartAddBo);
        if(cart != null){
            handleQuantity(orderCartAddBo, cart);
        }else{
            OrderCart orderCart = new OrderCart();
            orderCart.setGoodsName("");
            orderCart.setGoodsSubTitle("");
            orderCart.setGoodsPic("");
            orderCart.setGoodsSkuId("");
            orderCart.setGoodsSpuId(orderCartAddBo.getGoodsSpuId());
            orderCart.setGoodsCategoryId("");
            orderCart.setTableId(orderCartAddBo.getTableId());
            orderCart.setStoreId(orderCartAddBo.getStoreId());
            orderCart.setAccountId(orderCartAddBo.getAccountId());
            orderCart.setAppId("");
            orderCart.setQuantity(1);
            orderCart.setPrice(new BigDecimal("0"));
            orderCart.setAmount(new BigDecimal("0"));
            orderCart.setState(0);
            try {
                orderCartService.save(orderCart);
            } catch (DuplicateKeyException e) {
                log.warn("addOrderCart DuplicateKeyException is error",e);
                OrderCart duplicateCart = queryOrderCart(orderCartAddBo);
                if(duplicateCart != null){
                    handleQuantity(orderCartAddBo, duplicateCart);
                }
            }
        }
    }

    private void handleQuantity(OrderCartAddBo orderCartAddBo, OrderCart cart) {
        if(orderCartAddBo.getIsAdd().equals(0) && cart.getQuantity().equals(1)){ //如果是-最后一个
            orderCartService.removeById(cart);
        }else{
            orderCartService.lambdaUpdate()
                    .setSql(orderCartAddBo.getIsAdd().equals(1),"quantity = quantity + 1")
                    .setSql(orderCartAddBo.getIsAdd().equals(0),"quantity = quantity - 1")
                    .eq(!StrUtil.isBlank(orderCartAddBo.getAccountId()),OrderCart::getAccountId, orderCartAddBo.getAccountId())
                    .eq(StrUtil.isBlank(orderCartAddBo.getAccountId()),OrderCart::getTableId,orderCartAddBo.getTableId())
                    .eq(OrderCart::getStoreId, orderCartAddBo.getStoreId())
                    .eq(OrderCart::getGoodsSpuId, orderCartAddBo.getGoodsSpuId()).update();
        }
    }

    private OrderCart queryOrderCart(OrderCartAddBo orderCartAddBo){
        OrderCart cart;
        if(StrUtil.isBlank(orderCartAddBo.getAccountId())){
            //收银台点菜 或者 大店扫桌号点菜
            AssertUtil.isTrue(StrUtil.isBlank(orderCartAddBo.getTableId()),new BaseException("收银台点菜桌号不能为空!"));
            cart = orderCartService.lambdaQuery()
                    .eq(OrderCart::getStoreId, orderCartAddBo.getStoreId())
                    .eq(OrderCart::getTableId,orderCartAddBo.getTableId())
                    .eq(OrderCart::getGoodsSpuId, orderCartAddBo.getGoodsSpuId())
                    .last("limit 1").one();
        }else{
            //个人点菜
            cart = orderCartService.lambdaQuery()
                    .eq(OrderCart::getAccountId, orderCartAddBo.getAccountId())
                    .eq(OrderCart::getStoreId,orderCartAddBo.getStoreId())
                    .eq(OrderCart::getGoodsSpuId, orderCartAddBo.getGoodsSpuId()).last("limit 1").one();
        }
        return cart;
    }

    @Override
    public List<OrderCartInfoVo> getOrderCartInfo(OrderCartQueryBo queryBo) {
        List<OrderCartInfoVo> cartInfo = new ArrayList<>();
        List<OrderCart> orderCarts;
        if(StrUtil.isBlank(queryBo.getAccountId())){ //公共购物车
             orderCarts = orderCartService.lambdaQuery()
                    .eq(OrderCart::getTableId, queryBo.getTableId())
                    .eq(OrderCart::getStoreId, queryBo.getStoreId()).list();
        }else{
             orderCarts = orderCartService.lambdaQuery()
                    .eq(OrderCart::getAccountId, queryBo.getAccountId())
                    .eq(OrderCart::getStoreId, queryBo.getStoreId()).list();
        }
        if(!CollUtil.isEmpty(orderCarts)){
            for (OrderCart orderCart : orderCarts) {
                OrderCartInfoVo cartInfoVo = new OrderCartInfoVo();
                cartInfoVo.setGoodSpuId(orderCart.getGoodsSpuId());
                cartInfoVo.setQuantity(orderCart.getQuantity());
                cartInfoVo.setPrice(orderCart.getPrice());
                cartInfoVo.setGoodsPic(orderCart.getGoodsPic());
                cartInfoVo.setGoodName(orderCart.getGoodsName());
                cartInfo.add(cartInfoVo);
            }
        }
        return cartInfo;
    }
}
