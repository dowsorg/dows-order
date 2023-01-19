package org.dows.order;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.operators.relational.Between;
import org.dows.order.api.OrderInstanceBizApiService;
import org.dows.order.bo.*;
import org.dows.order.entity.OrderInstance;
import org.dows.order.entity.OrderItem;
import org.dows.order.enums.OrderInstanceTypeEnum;
import org.dows.order.enums.OrderItemFlagEnum;
import org.dows.order.enums.OrderTableStatusEnum;
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
    public String createOrderInstance(OrderInstancePaymentBo paymentBo) {

        return null;
    }
    @Override
    public void creatOrderInstance(OrderInstanceCreateBo createBo) {
        //TODO 获取goods 信息 店铺桌号信息
        OrderInstance orderInstance = new OrderInstance();
        String timePrefix = idGenerator.getTimePrefix(IdKey.OMS_ORDER_ID);
        orderInstance.setOrderId(timePrefix);
        orderInstance.setTableId(createBo.getTableId());
        orderInstance.setTableNo(null);
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
                .eq(!StrUtil.isBlank(queryBo.getAccountId()),OrderInstance::getAccountId, queryBo.getAccountId())
                .eq(OrderInstance::getStoreId,queryBo.getStoreId())
                .eq(!StrUtil.isBlank(queryBo.getTableNo()),OrderInstance::getTableNo,queryBo.getTableNo())
                .list();
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
                instanceInfoVo.setPayChannel(orderInstance.getPayChannel());
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
    public List<OrderTableInfoBo> getOrderTableInfo(String storeId, List<String> tableNos) {
        List<OrderTableInfoBo> tableInfoBoList = Lists.newArrayList();
        List<OrderInstance> list = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getStoreId, storeId)
                .in(OrderInstance::getTableNo, tableNos).list();
        if(!CollUtil.isEmpty(list)){
            List<String> orderIds = list.stream().map(OrderInstance::getOrderId).collect(Collectors.toList());
            Map<String, List<OrderItem>> orderItemMap = orderItemService.lambdaQuery()
                    .in(OrderItem::getOrderId, orderIds).list().stream().collect(Collectors.groupingBy(OrderItem::getOrderId));
            for (OrderInstance order : list) {
                OrderTableInfoBo infoBo = new OrderTableInfoBo();
                infoBo.setTableNo(order.getTableNo());
                infoBo.setPeoples(order.getPeoples());
                infoBo.setAmount(order.getAmount());
                infoBo.setMinute(((Long)DateUtil.between(order.getDt(), DateUtil.date(), DateUnit.MINUTE)).intValue());
                infoBo.setTableStatus(OrderTableStatusEnum.menu_in.getCode());
                if(orderItemMap.containsKey(order.getOrderId())){
                    List<OrderItem> orderItems =  orderItemMap.get(order.getOrderId());
                    Integer count = orderItems.stream().filter(e -> e.getFlag().equals(1)).collect(Collectors.toList()).size();
                    infoBo.setFinish(Integer.valueOf(count));
                    infoBo.setTotal(orderItems.size());
                    if(orderItems.stream().allMatch(e->e.getFlag().equals(OrderItemFlagEnum.stroke_menu.getCode()))){
                        infoBo.setTableStatus(OrderTableStatusEnum.finish_menu.getCode());
                    }
                }
                if(true){ // TODO 已超时
                    infoBo.setTableStatus(OrderTableStatusEnum.time_out.getCode());
                }
                if(OrderInstanceTypeEnum.over.getCode().equals(order.getStatus())){
                    infoBo.setTableStatus(OrderTableStatusEnum.closed.getCode());
                }
                tableInfoBoList.add(infoBo);
            }
        }
        return tableInfoBoList;
    }
}
