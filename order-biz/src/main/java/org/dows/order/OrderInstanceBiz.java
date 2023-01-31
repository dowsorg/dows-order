package org.dows.order;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.order.api.OrderInstanceBizApiService;
import org.dows.order.bo.*;
import org.dows.order.entity.OrderInstance;
import org.dows.order.entity.OrderItem;
import org.dows.order.enums.OrderInstanceTypeEnum;
import org.dows.order.enums.OrderItemFlagEnum;
import org.dows.order.enums.OrderTableStatusEnum;
import org.dows.order.form.OrderInstanceTenantForm;
import org.dows.order.mapper.OrderInstanceMapper;
import org.dows.order.service.OrderInstanceService;
import org.dows.order.service.OrderItemService;
import org.dows.order.vo.OrderInstanceTenantOpVo;
import org.dows.order.vo.OrderInstanceTenantVo;
import org.dows.order.vo.OrderInstanceInfoVo;
import org.dows.sequence.api.IdGenerator;
import org.dows.sequence.api.IdKey;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author liuhonger
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderInstanceBiz implements OrderInstanceBizApiService {

    private final OrderInstanceService orderInstanceService;

    private final OrderItemService orderItemService;

    private final IdGenerator idGenerator;

    private final OrderInstanceMapper orderInstanceMapper;

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
                instanceInfoVo.setAccountName("张三");
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
                instanceInfoVo.setCreateDate(orderInstance.getDt());
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

    @Override
    public boolean applyRefund(OrderApplyRefundBo refundBo) {
        //TODO 调支付系统退款

        return ChainWrappers.lambdaUpdateChain(orderInstanceMapper)
                .eq(OrderInstance::getOrderId,refundBo.getOrderId())
                .set(OrderInstance::getRefund,refundBo.getType())
                .set(OrderInstance::getRefundRemark,refundBo.getApplyRemark()).update();
    }

    @Override
    public IPage<OrderInstanceTenantVo> selectOrderInstancePage(OrderInstanceTenantForm adminForm) {
        Page<OrderInstanceTenantForm> paging = new Page(adminForm.getCurrent(),adminForm.getSize());
        IPage<OrderInstanceTenantVo> adminVoIPage = orderInstanceMapper.selectOrderInstancePage(paging, adminForm);
        if(!CollUtil.isEmpty(adminVoIPage.getRecords())){
            for (OrderInstanceTenantVo record : adminVoIPage.getRecords()) {
                record.setUserName("张三");
                record.setTypeStr("堂食");
                record.setBrand("海底捞");
                record.setStoreRegion("徐家汇");
                record.setStoreType(1);
                record.setStoreName("普通面馆");
                record.setFoodNum(2);
            }
        }
        return adminVoIPage;
    }

    @Override
    public IPage<OrderInstanceTenantOpVo> selectOrderInstanceRePage(OrderInstanceTenantForm adminForm) {
        Page<OrderInstanceTenantForm> paging = new Page(adminForm.getCurrent(),adminForm.getSize());
        IPage<OrderInstanceTenantOpVo> adminVoIPage = orderInstanceMapper.selectOrderInstanceRePage(paging, adminForm);
        if(!CollUtil.isEmpty(adminVoIPage.getRecords())){
            for (OrderInstanceTenantOpVo record : adminVoIPage.getRecords()) {
                record.setFood("小炒肉");
                record.setNum(1);
                record.setReason("不好吃");
                record.setReAmount(new BigDecimal("4.6"));
                record.setDateTime(new Date());
                record.setOperator("小猪");
                record.setUserName("张三");
                record.setTypeStr("堂食");
                record.setBrand("海底捞");
                record.setStoreRegion("徐家汇");
                record.setStoreType(1);
                record.setStoreName("普通面馆");
                record.setFoodNum(2);
            }
        }
        return adminVoIPage;
    }
}
