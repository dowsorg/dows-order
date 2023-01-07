package org.dows.order;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.order.api.OrderInstanceBizApiService;
import org.dows.order.bo.*;
import org.dows.order.entity.OrderInstance;
import org.dows.order.entity.OrderItem;
import org.dows.order.enums.OrderItemFlagEnum;
import org.dows.order.service.OrderInstanceService;
import org.dows.order.service.OrderItemService;
import org.dows.order.vo.OrderInstanceInfoVo;
import org.dows.sequence.api.IdGenerator;
import org.dows.sequence.api.IdKey;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderInstanceBiz implements OrderInstanceBizApiService {

    private final OrderInstanceService orderInstanceService;

    private final OrderItemService orderItemService;

    private final IdGenerator idGenerator;
    @Override
    public void creatOrderInstance(OrderInstanceCreateBo createBo) {
        //TODO 获取goods 信息
        OrderInstance orderInstance = new OrderInstance();
        String timePrefix = idGenerator.getTimePrefix(IdKey.OMS_ORDER_ID);
        orderInstance.setOrderId(timePrefix);
        orderInstance.setTableId(createBo.getTableId());
        orderInstance.setAccountId(createBo.getAccountId());
        orderInstance.setStoreId(createBo.getStoreId());
        orderInstance.setAppId("");
        orderInstance.setTenantId("");
        orderInstance.setRemark(createBo.getRemark());
        orderInstance.setPeoples(createBo.getPeoples());
        orderInstance.setType(createBo.getType());
        if(createBo.getOrderStore().equals(1) && createBo.getType().equals(0)){
            orderInstance.setStatus(0);
        }
        BigDecimal amoutPrice = new BigDecimal("0");
        List<OrderItem> orderItems = Lists.newArrayList();
        for (OrderInstanceCreateBo.GoodSpuInfo goodSpuInfo : createBo.getGoodSpuInfoList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(timePrefix);
            orderItem.setTableId(orderInstance.getTableId());
            orderItem.setAccountId(orderInstance.getAccountId());
            orderItem.setSpuId(goodSpuInfo.getGoodSpuId());
            orderItem.setSpuName("");
            orderItem.setFlag(0);
            orderItem.setQuantity(goodSpuInfo.getQuantity());
            orderItem.setPrice(new BigDecimal(""));
            amoutPrice = amoutPrice.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            orderItems.add(orderItem);
        }
        orderInstance.setAmount(amoutPrice);
        orderInstanceService.save(orderInstance);
        orderItemService.saveBatch(orderItems);
    }

    @Override
    public List<OrderInstanceInfoVo> queryOrderInfo(OrderInstanceQueryBo queryBo) {
        List<OrderInstanceInfoVo> infoVos = Lists.newArrayList();
        List<OrderInstance> orderList = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getAccountId, queryBo.getAccountId()).list();
        if(!CollUtil.isEmpty(orderList)){
            List<String> orderIds = orderList.stream().map(OrderInstance::getOrderId).collect(Collectors.toList());
            Map<String, List<OrderInstanceInfoVo.GoodSpuInfo>> orderGoodSpuInfoMap = orderItemService.lambdaQuery().in(OrderItem::getOrderId, orderIds)
                    .list().stream().collect(Collectors.groupingBy(OrderItem::getOrderId, Collectors.collectingAndThen(Collectors.toList(), e -> {
                        List<OrderInstanceInfoVo.GoodSpuInfo> list = Lists.newArrayList();
                        for (OrderItem orderItem : e) {
                            OrderInstanceInfoVo.GoodSpuInfo goodSpuInfo = new OrderInstanceInfoVo.GoodSpuInfo();
                            goodSpuInfo.setOrderItemId(orderItem.getId());
                            goodSpuInfo.setFlag(orderItem.getFlag());
                            goodSpuInfo.setGoodName(orderItem.getSpuName());
                            goodSpuInfo.setQuantity(orderItem.getQuantity());
                            goodSpuInfo.setPrice(orderItem.getPrice());
                            list.add(goodSpuInfo);
                        }
                        return list;
                    })));
            for (OrderInstance orderInstance : orderList) {
                OrderInstanceInfoVo instanceInfoVo = new OrderInstanceInfoVo();
                instanceInfoVo.setAccountName("");
                instanceInfoVo.setTableId(orderInstance.getTableId());
                instanceInfoVo.setPeoples(orderInstance.getPeoples());
                instanceInfoVo.setPayChannel(0);
                if(orderGoodSpuInfoMap.containsKey(orderInstance.getOrderId())){
                    List<OrderInstanceInfoVo.GoodSpuInfo> goodSpuInfos = orderGoodSpuInfoMap.get(orderInstance.getOrderId());
                    instanceInfoVo.setGoodSpuInfoList(goodSpuInfos);
                    BigDecimal decimal = goodSpuInfos.stream().map(OrderInstanceInfoVo.GoodSpuInfo::getPrice).reduce((x, y) -> x.add(y)).orElse(BigDecimal.ZERO);
                    instanceInfoVo.setSubtotal(decimal);
                    instanceInfoVo.setTotalAmount(decimal);
                }
                instanceInfoVo.setRemark(orderInstance.getRemark());
                instanceInfoVo.setStatus(0);
                infoVos.add(instanceInfoVo);
            }
        }
        return infoVos;
    }

    @Override
    public boolean updateOrderInstance(OrderInstanceStatusBo statusBo) {
        return orderInstanceService.lambdaUpdate()
                .set(OrderInstance::getStatus,statusBo.getStatus())
                .set(OrderInstance::getPayTime,statusBo.getPayTime())
                .set(OrderInstance::getPayChannel,statusBo.getPayChannel())
                .eq(OrderInstance::getOrderId,statusBo.getOrderId()).update();
    }

    @Override
    public boolean updateOrderItem(OrderItemFlagBo flagBo) {
        OrderItemMoreBo moreBo = new OrderItemMoreBo();
        OrderItem orderItem = orderItemService.getBaseMapper().selectById(flagBo.getOrderItemId());
        OrderItem item  = new OrderItem();
        item.setId(orderItem.getId());
        if(OrderItemFlagEnum.stroke_menu.getCode().equals(flagBo.getFlag())){ //划菜
            item.setFlag(OrderItemFlagEnum.stroke_menu.getCode());
            return orderItemService.updateById(item);
        }else if(OrderItemFlagEnum.return_menu.getCode().equals(flagBo.getFlag())){ //退菜
            //TODO 修改订单总金额
            item.setFlag(OrderItemFlagEnum.return_menu.getCode());
            item.setQuantity(orderItem.getQuantity() - flagBo.getReturning());
            moreBo.setKey(StrUtil.toString(flagBo.getReturning()));
            moreBo.setVal(flagBo.getRemark());
            item.setMore(JSONUtil.toJsonStr(moreBo));
            return orderItemService.updateById(item);
        }else if(OrderItemFlagEnum.give.getCode().equals(flagBo.getFlag())){ //赠送
            //TODO 修改订单总金额
            item.setFlag(OrderItemFlagEnum.give.getCode());
            moreBo.setKey(StrUtil.toString(orderItem.getPrice()));
            moreBo.setVal(flagBo.getRemark());
            item.setMore(JSONUtil.toJsonStr(moreBo));
            item.setQuantity(orderItem.getQuantity()+1);
            return orderItemService.updateById(item);
        }else if(OrderItemFlagEnum.reporting.getCode().equals(flagBo.getFlag())){
            //TODO 修改订单总金额
            item.setFlag(OrderItemFlagEnum.reporting.getCode());
            moreBo.setKey(StrUtil.toString(flagBo.getReporting()));
            moreBo.setVal(flagBo.getRemark());
            item.setMore(JSONUtil.toJsonStr(moreBo));
            item.setQuantity(orderItem.getQuantity()-1);
            return orderItemService.updateById(item);
        }
        return false;
    }

    @Override
    public List<OrderTableInfoBo> getOrderTableInfo(String storeId, List<String> tableIds) {
        //orderInstanceService.
        return null;
    }
}
