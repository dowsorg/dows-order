package org.dows.order;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.framework.api.exceptions.BaseException;
import org.dows.goods.api.GoodsApi;
import org.dows.goods.form.GoodsForm;
import org.dows.goods.form.GoodsSpuForm;
import org.dows.order.api.OrderCartApiService;
import org.dows.order.bo.OrderCartAddBo;
import org.dows.order.bo.OrderCartQueryBo;
import org.dows.order.entity.OrderCart;
import org.dows.order.service.OrderCartService;
import org.dows.order.vo.OrderCartInfoVo;
import org.dows.order.vo.OrderCartTotalVo;
import org.dows.utils.AssertUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuhonger
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCatBiz implements OrderCartApiService {

    private final OrderCartService orderCartService;

    private final GoodsApi goodsApi;
    @Override
    public void addOrderCart(OrderCartAddBo orderCartAddBo) {
        Response<List<GoodsForm>> infoByIds = goodsApi.getGoodsInfoByIds(Lists.newArrayList(Long.valueOf(orderCartAddBo.getGoodsSpuId())));
        GoodsForm goodsForm = infoByIds.getData().get(0);
        GoodsSpuForm goodsSpu = goodsForm.getGoodsSpu();
        //如果是加+减-
        OrderCart cart = queryOrderCart(orderCartAddBo);
        if(cart != null){
            handleQuantity(orderCartAddBo, cart);
        }else{
            OrderCart orderCart = new OrderCart();
            orderCart.setGoodsName(goodsSpu.getSpuName());
            orderCart.setGoodsPic(goodsSpu.getPic());
            orderCart.setGoodsSpuId(orderCartAddBo.getGoodsSpuId());
            orderCart.setTableNo(orderCartAddBo.getTableNo());
            orderCart.setStoreId(orderCartAddBo.getStoreId());
            orderCart.setAccountId(orderCartAddBo.getAccountId());
            orderCart.setQuantity(1);
            orderCart.setPrice(goodsSpu.getNormalPrice());
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

    @Override
    public boolean cleanUpOrderCart(OrderCartQueryBo queryBo) {
        List<OrderCart> orderCarts;
        if(StrUtil.isBlank(queryBo.getAccountId())){ //公共购物车
            orderCarts = orderCartService.lambdaQuery()
                    .eq(OrderCart::getTableNo, queryBo.getTableNo())
                    .eq(OrderCart::getStoreId, queryBo.getStoreId()).list();
        }else{
            orderCarts = orderCartService.lambdaQuery()
                    .eq(OrderCart::getAccountId, queryBo.getAccountId())
                    .eq(OrderCart::getStoreId, queryBo.getStoreId()).list();
        }
        List<Long> ids = orderCarts.stream().map(OrderCart::getId).collect(Collectors.toList());
        return orderCartService.removeByIds(ids);
    }


    private void handleQuantity(OrderCartAddBo orderCartAddBo, OrderCart cart) {
        if(orderCartAddBo.getIsAdd().equals(0) && cart.getQuantity().equals(1)){ //如果是-最后一个
            orderCartService.removeById(cart);
        }else{
            orderCartService.lambdaUpdate()
                    .setSql(orderCartAddBo.getIsAdd().equals(1),"quantity = quantity + 1")
                    .setSql(orderCartAddBo.getIsAdd().equals(0),"quantity = quantity - 1")
                    .eq(orderCartAddBo.getAccountId() != null,OrderCart::getAccountId, orderCartAddBo.getAccountId())
                    .eq(StrUtil.isBlank(orderCartAddBo.getTableNo()),OrderCart::getTableNo,orderCartAddBo.getTableNo())
                    .eq(OrderCart::getStoreId, orderCartAddBo.getStoreId())
                    .eq(OrderCart::getGoodsSpuId, orderCartAddBo.getGoodsSpuId()).update();
        }
    }

    private OrderCart queryOrderCart(OrderCartAddBo orderCartAddBo){
        OrderCart cart;
        if(orderCartAddBo.getAccountId() == null){
            //收银台点菜 或者 大店扫桌号点菜
            AssertUtil.isTrue(StrUtil.isBlank(orderCartAddBo.getTableNo()),new BaseException("收银台点菜桌号不能为空!"));
            cart = orderCartService.lambdaQuery()
                    .eq(OrderCart::getStoreId, orderCartAddBo.getStoreId())
                    .eq(OrderCart::getTableNo,orderCartAddBo.getTableNo())
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
    public OrderCartTotalVo getOrderCartInfo(OrderCartQueryBo queryBo) {
        OrderCartTotalVo totalVo = new OrderCartTotalVo();
        List<OrderCartInfoVo> cartInfo = new ArrayList<>();
        List<OrderCart> orderCarts;
        if(StrUtil.isBlank(queryBo.getAccountId())){ //公共购物车
             orderCarts = orderCartService.lambdaQuery()
                    .eq(OrderCart::getTableNo, queryBo.getTableNo())
                    .eq(OrderCart::getStoreId, queryBo.getStoreId()).list();
        }else{
             orderCarts = orderCartService.lambdaQuery()
                    .eq(OrderCart::getAccountId, queryBo.getAccountId())
                    .eq(OrderCart::getStoreId, queryBo.getStoreId()).list();
        }
        BigDecimal bigDecimal = new BigDecimal("0");
        if(!CollUtil.isEmpty(orderCarts)){
            for (OrderCart orderCart : orderCarts) {
                OrderCartInfoVo cartInfoVo = new OrderCartInfoVo();
                cartInfoVo.setOrderCount(10);
                cartInfoVo.setStatus(1);
                cartInfoVo.setNormalDiscount(new BigDecimal("8.8"));
                cartInfoVo.setMemberDiscount(new BigDecimal("9.5"));
                cartInfoVo.setSpecialPrice(new BigDecimal("67.3"));
                cartInfoVo.setMembershipPrice(new BigDecimal("24.6"));
                cartInfoVo.setNormalPrice(new BigDecimal("46.7"));

                cartInfoVo.setGoodSpuId(orderCart.getGoodsSpuId());
                cartInfoVo.setQuantity(orderCart.getQuantity());
                cartInfoVo.setPrice(orderCart.getPrice());
                cartInfoVo.setGoodsPic(orderCart.getGoodsPic());
                cartInfoVo.setGoodName(orderCart.getGoodsName());
                bigDecimal = bigDecimal.add(orderCart.getPrice().multiply(new BigDecimal(orderCart.getQuantity().toString())));
                cartInfo.add(cartInfoVo);
            }
        }
        totalVo.setList(cartInfo);
        totalVo.setTotal(bigDecimal);
        return totalVo;
    }
}
