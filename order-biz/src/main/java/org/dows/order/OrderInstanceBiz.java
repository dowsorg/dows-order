package org.dows.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.account.biz.AccountBiz;
import org.dows.framework.api.Response;
import org.dows.goods.api.GoodsApi;
import org.dows.goods.form.GoodsForm;
import org.dows.goods.form.GoodsSpuForm;
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
import org.dows.order.vo.OrderInstanceInfoVo;
import org.dows.order.vo.OrderInstanceTenantOpVo;
import org.dows.order.vo.OrderInstanceTenantVo;
import org.dows.sequence.api.IdGenerator;
import org.dows.sequence.api.IdKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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

    private final AccountBiz accountBiz;

    private final GoodsApi goodsApi;

    @Override
    public String createOrderInstance(OrderInstancePaymentBo paymentBo) {
        return null;
    }

    @Override
    @Transactional
    public void creatOrderInstance(OrderInstanceCreateBo createBo) {
        OrderInstance orderInstance = new OrderInstance();
        String timePrefix = idGenerator.getTimePrefix(IdKey.OMS_ORDER_ID);
        orderInstance.setOrderNo(timePrefix);
        orderInstance.setTableNo(createBo.getTableNo());
        orderInstance.setAccountId(createBo.getAccountId());
        orderInstance.setOrderSource(createBo.getOrderSource());
        orderInstance.setOrderStore(1);
        orderInstance.setStoreId(createBo.getStoreId());
        orderInstance.setAppId("");
        orderInstance.setTenantId("");
        orderInstance.setRemark(createBo.getRemark());
        orderInstance.setPeoples(createBo.getPeoples());
        orderInstance.setType(0);
        orderInstance.setStatus(0);
        BigDecimal amoutPrice = new BigDecimal("0");
        List<Long> spuIds = createBo.getGoodSpuInfoList().stream().map(OrderInstanceCreateBo.GoodSpuInfo::getGoodSpuId).collect(Collectors.toList());
        Response<List<GoodsForm>> goodsList = goodsApi.getGoodsInfoByIds(spuIds);
        List<GoodsForm> data = goodsList.getData();
        List<GoodsSpuForm> spuInfoList = data.stream().map(GoodsForm::getGoodsSpu).collect(Collectors.toList());
        Map<Long, GoodsSpuForm> spuFormMap = CollStreamUtil.toMap(spuInfoList, GoodsSpuForm::getId, Function.identity());
        for (OrderInstanceCreateBo.GoodSpuInfo goodSpuInfo : createBo.getGoodSpuInfoList()) {
            amoutPrice = amoutPrice.add(spuFormMap.get(goodSpuInfo.getGoodSpuId()).getNormalPrice().multiply(BigDecimal.valueOf(goodSpuInfo.getQuantity())));
        }
        orderInstance.setAmount(amoutPrice);
        orderInstanceService.save(orderInstance);
        List<OrderItem> orderItems = Lists.newArrayList();
        for (OrderInstanceCreateBo.GoodSpuInfo goodSpuInfo : createBo.getGoodSpuInfoList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderInstance.getId());
            orderItem.setTableNo(orderInstance.getTableNo());
            orderItem.setAccountId(orderInstance.getAccountId());
            orderItem.setSpuId(goodSpuInfo.getGoodSpuId());
            if(spuFormMap.containsKey(goodSpuInfo.getGoodSpuId())){
                GoodsSpuForm goodsSpuForm = spuFormMap.get(goodSpuInfo.getGoodSpuId());
                orderItem.setSpuName(goodsSpuForm.getSpuName());
                orderItem.setPrice(goodsSpuForm.getNormalPrice());
            }
            orderItem.setQuantity(goodSpuInfo.getQuantity());
            orderItem.setFlag(0);
            orderItem.setRemark(goodSpuInfo.getRemark());
            orderItems.add(orderItem);
        }
        orderItemService.saveBatch(orderItems);
    }



    @Override
    public List<OrderInstanceInfoVo> queryOrderInfo(OrderInstanceQueryBo queryBo) {
        List<OrderInstanceInfoVo> infoVos = Lists.newArrayList();
        List<OrderInstance> orderList = orderInstanceService.lambdaQuery()
                .eq(!StrUtil.isBlank(queryBo.getAccountId()),OrderInstance::getAccountId, queryBo.getAccountId())
                .eq(!StrUtil.isBlank(queryBo.getStoreId()),OrderInstance::getStoreId,queryBo.getStoreId())
                .eq(!StrUtil.isBlank(queryBo.getTableNo()),OrderInstance::getTableNo,queryBo.getTableNo())
                .list();
        if(!CollUtil.isEmpty(orderList)){
            List<Long> orderIds = orderList.stream().map(OrderInstance::getId).collect(Collectors.toList());
            Map<Long, List<OrderInstanceInfoVo.GoodSpuInfo>> orderGoodSpuInfoMap = orderItemService.lambdaQuery().in(OrderItem::getOrderId, orderIds)
                    .list().stream().collect(Collectors.groupingBy(OrderItem::getOrderId, Collectors.collectingAndThen(Collectors.toList(), a -> {
                        List<OrderInstanceInfoVo.GoodSpuInfo> list = Lists.newArrayList();
                        for (OrderItem orderItem : a) {
                            OrderInstanceInfoVo.GoodSpuInfo goodSpuInfo = new OrderInstanceInfoVo.GoodSpuInfo();
                            goodSpuInfo.setOrderItemId(orderItem.getId());
                            goodSpuInfo.setFlag(orderItem.getFlag());
                            if(Integer.valueOf(2).equals(orderItem.getFlag())){
                                OrderItemMoreBo moreBo = JSONUtil.toBean(orderItem.getMore(),OrderItemMoreBo.class);
                                goodSpuInfo.setRefundNum(Optional.ofNullable(moreBo).map(OrderItemMoreBo::getFoodNum).orElse(0));
                            }
                            goodSpuInfo.setGoodName(orderItem.getSpuName());
                            goodSpuInfo.setQuantity(orderItem.getQuantity());
                            goodSpuInfo.setPrice(orderItem.getPrice());
                            goodSpuInfo.setRemark(orderItem.getRemark());
                            list.add(goodSpuInfo);
                        }
                        return list;
                    })));
            for (OrderInstance orderInstance : orderList) {
                OrderInstanceInfoVo instanceInfoVo = new OrderInstanceInfoVo();
                instanceInfoVo.setOrderId(orderInstance.getId());
                instanceInfoVo.setOrderNo(orderInstance.getOrderNo());
                if(Integer.valueOf(1).equals(orderInstance.getType())){ //自营外面才有 手机号和姓名
                    instanceInfoVo.setPhone("13554700856");
                    instanceInfoVo.setAccountName("张三");
                    instanceInfoVo.setAddress("上海市");
                    instanceInfoVo.setAccountOrderNum(92);
                }
                if(Integer.valueOf(0).equals(orderInstance.getType())){ //堂食
                    instanceInfoVo.setTableNo(orderInstance.getTableNo());
                    instanceInfoVo.setPeoples(orderInstance.getPeoples());
                    instanceInfoVo.setTodayNum(3);
                    instanceInfoVo.setMenuMin(DateUtil.formatBetween(orderInstance.getDt(),
                            DateUtil.date(), BetweenFormatter.Level.MINUTE));
                }else{
                    //Response byAccountId = accountBiz.getInfoByAccountId();
                    OrderInstanceInfoVo.UserInfo userInfo = new OrderInstanceInfoVo.UserInfo();
                    userInfo.setHeadUrl("https://c-ssl.duitang.com/uploads/blog/202103/31/20210331160001_9a852.jpg");
                    userInfo.setName("李四");
                    userInfo.setSex("男");
                    userInfo.setBirthday("1992.09.09");
                    userInfo.setPhone("12345786789696");
                    userInfo.setCreateDate(DateUtil.date());
                    userInfo.setOrderNum(23423);
                    userInfo.setAmount(new BigDecimal("343.567"));
                    userInfo.setDateOf("今天");
                    instanceInfoVo.setUserInfo(userInfo);
                }
                if(Integer.valueOf(1).equals(orderInstance.getPayState())){
                    instanceInfoVo.setPayChannel(orderInstance.getPayChannel());
                }

                if(orderGoodSpuInfoMap.containsKey(orderInstance.getId())){
                    List<OrderInstanceInfoVo.GoodSpuInfo> goodSpuInfos = orderGoodSpuInfoMap.get(orderInstance.getId());
                    instanceInfoVo.setGoodSpuInfoList(goodSpuInfos);
                    BigDecimal decimal = goodSpuInfos.stream().map(OrderInstanceInfoVo.GoodSpuInfo::getPrice).reduce((x, y) -> x.add(y)).orElse(BigDecimal.ZERO);
                    instanceInfoVo.setSubtotal(decimal);
                    instanceInfoVo.setTotalAmount(decimal);
                    long count = goodSpuInfos.stream().map(OrderInstanceInfoVo.GoodSpuInfo::getQuantity).count();
                    instanceInfoVo.setSpuCount(Long.valueOf(count));
                    instanceInfoVo.setSpuCategory(goodSpuInfos.size());
                }
                if(orderInstance.getDiningTime() != null){
                    instanceInfoVo.setDiningTime(DateUtil.format(orderInstance.getDiningTime(), "HH:mm"));
                }else{
                    instanceInfoVo.setDiningTime(DateUtil.formatBetween(orderInstance.getDt(),
                            Optional.ofNullable(orderInstance.getDiningTime()).orElse(DateUtil.date()), BetweenFormatter.Level.MINUTE));
                }
                instanceInfoVo.setRemark(orderInstance.getRemark());
                instanceInfoVo.setStatus(0);
                instanceInfoVo.setDt(orderInstance.getDt());
                instanceInfoVo.setType(orderInstance.getType());
                instanceInfoVo.setOrderSource(orderInstance.getOrderSource());
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
                .eq(OrderInstance::getId,statusBo.getOrderId()).update();
    }


    @Override
    public List<OrderTableInfoBo> getOrderTableInfo(Long storeId, List<String> tableNos) {
        List<OrderTableInfoBo> tableInfoBoList = Lists.newArrayList();
        List<OrderInstance> list = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getStoreId, storeId)
                .in(OrderInstance::getTableNo, tableNos).list();
        if(!CollUtil.isEmpty(list)){
            List<Long> orderIds = list.stream().map(OrderInstance::getId).collect(Collectors.toList());
            Map<Long, List<OrderItem>> orderItemMap = orderItemService.lambdaQuery()
                    .in(OrderItem::getOrderId, orderIds).list().stream().collect(Collectors.groupingBy(OrderItem::getOrderId));
            for (OrderInstance order : list) {
                OrderTableInfoBo infoBo = new OrderTableInfoBo();
                infoBo.setTableNo(order.getTableNo());
                infoBo.setPeoples(order.getPeoples());
                infoBo.setAmount(order.getAmount());
                infoBo.setMinute(((Long)DateUtil.between(order.getDt(), DateUtil.date(), DateUnit.MINUTE)).intValue());
                infoBo.setTableStatus(OrderTableStatusEnum.menu_in.getCode());
                if(orderItemMap.containsKey(order.getId())){
                    List<OrderItem> orderItems =  orderItemMap.get(order.getId());
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
                .eq(OrderInstance::getId,refundBo.getOrderId())
                .set(OrderInstance::getRefund,refundBo.getType())
                .set(OrderInstance::getRefundRemark,refundBo.getApplyRemark()).update();
    }

    @Override
    public boolean diningOrder(Long orderId) {
        return ChainWrappers.lambdaUpdateChain(orderInstanceMapper)
                .set(OrderInstance::getDiningTime,DateUtil.date())
                .set(OrderInstance::getStatus,1)
                .eq(OrderInstance::getId,orderId).update();

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
                OrderItemMoreBo moreBo = BeanUtil.toBean(record.getMore(),OrderItemMoreBo.class);
                if(moreBo != null){
                    record.setNum(moreBo.getFoodNum());
                    record.setRemarks(moreBo.getRemarks());
                    record.setReAmount(moreBo.getLossReporting());
                    record.setDateTime(moreBo.getOpt());
                    record.setOperator(moreBo.getUserName());
                }
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
