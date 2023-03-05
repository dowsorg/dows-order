package org.dows.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.account.api.AccountUserApi;
import org.dows.account.vo.AccountVo;
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
import org.dows.order.enums.OrderTypeEnum;
import org.dows.order.form.*;
import org.dows.order.mapper.OrderInstanceMapper;
import org.dows.order.service.OrderInstanceService;
import org.dows.order.service.OrderItemService;
import org.dows.order.vo.*;
import org.dows.sequence.api.IdGenerator;
import org.dows.sequence.api.IdKey;
import org.dows.store.api.StoreInstanceApi;
import org.dows.store.api.response.StoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author liuhonger
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Component
public class OrderInstanceBiz implements OrderInstanceBizApiService {

    private final OrderInstanceService orderInstanceService;

    private final OrderItemService orderItemService;

    private final IdGenerator idGenerator;

    private final OrderInstanceMapper orderInstanceMapper;

    @Lazy
    @Autowired
    private AccountUserApi accountUserApi;

    private final GoodsApi goodsApi;

    @Lazy
    @Autowired
    private StoreInstanceApi storeInstanceApi;

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
        List<GoodsForm> data = goodsApi.getGoodsInfoByIds(spuIds);
        List<GoodsSpuForm> spuInfoList = Optional.ofNullable(data).orElseGet(ArrayList::new).stream().map(GoodsForm::getGoodsSpu).collect(Collectors.toList());
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

    /**
     * 根据订单ids 获取订单详情
     * @param orderIds
     * @return
     */
    private Map<Long, List<GoodSpuInfoVo>> getGoodSpuInfoVo(List<Long> orderIds){
        Map<Long, List<GoodSpuInfoVo>> orderGoodSpuInfoMap = orderItemService.lambdaQuery().in(OrderItem::getOrderId, orderIds)
                .list().stream().collect(Collectors.groupingBy(OrderItem::getOrderId, Collectors.collectingAndThen(Collectors.toList(), a -> {
                    List<GoodSpuInfoVo> list = Lists.newArrayList();
                    for (OrderItem orderItem : a) {
                        GoodSpuInfoVo goodSpuInfo = new GoodSpuInfoVo();
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
        return orderGoodSpuInfoMap;
    }



    @Override
    public List<OrderInstanceInfoVo> queryOrderInfo(OrderInstanceQueryBo queryBo) {
        List<OrderInstanceInfoVo> infoVos = Lists.newArrayList();
        List<OrderInstance> orderList = orderInstanceService.lambdaQuery()
                .eq(!StrUtil.isBlank(queryBo.getAccountId()),OrderInstance::getAccountId, queryBo.getAccountId())
                .eq(!StrUtil.isBlank(queryBo.getStoreId()),OrderInstance::getStoreId,queryBo.getStoreId())
                .eq(!StrUtil.isBlank(queryBo.getTableNo()),OrderInstance::getTableNo,queryBo.getTableNo())
                .eq(!StrUtil.isBlank(queryBo.getOrderNo()),OrderInstance::getOrderNo,queryBo.getOrderNo())
                .list();
        if(!CollUtil.isEmpty(orderList)){
            List<Long> orderIds = orderList.stream().map(OrderInstance::getId).collect(Collectors.toList());
            Map<Long, List<GoodSpuInfoVo>> orderGoodSpuInfoMap = getGoodSpuInfoVo(orderIds);
            for (OrderInstance orderInstance : orderList) {
                OrderInstanceInfoVo instanceInfoVo = new OrderInstanceInfoVo();
                instanceInfoVo.setOrderId(orderInstance.getId());
                instanceInfoVo.setOrderNo(orderInstance.getOrderNo());
                instanceInfoVo.setRefund(orderInstance.getRefund());
                instanceInfoVo.setApplyImageUrl(StrUtil.split(orderInstance.getApplyImageUrl(),","));
                instanceInfoVo.setApplyRefundRemark(orderInstance.getApplyRefundRemark());
                if(Integer.valueOf(1).equals(orderInstance.getType())){ //自营外面才有 手机号和姓名
                    AccountVo accountVo = accountUserApi.getInfoByAccountId(orderInstance.getAccountId());
                    instanceInfoVo.setPhone(accountVo.getPhone());
                    instanceInfoVo.setAccountName(accountVo.getAccountName());
                    instanceInfoVo.setAddress(StrUtil.concat(true,accountVo.getProvince(),accountVo.getCity(),accountVo.getDistrict()));
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
                    instanceInfoVo.setUserInfo(getUserInfo(orderInstance.getAccountId()));
                }
                if(Integer.valueOf(1).equals(orderInstance.getPayState())){
                    instanceInfoVo.setPayChannel(orderInstance.getPayChannel());
                }

                if(orderGoodSpuInfoMap.containsKey(orderInstance.getId())){
                    List<GoodSpuInfoVo> goodSpuInfos = orderGoodSpuInfoMap.get(orderInstance.getId());
                    instanceInfoVo.setGoodSpuInfoList(goodSpuInfos);
                    BigDecimal decimal = goodSpuInfos.stream().map(GoodSpuInfoVo::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    instanceInfoVo.setSubtotal(decimal);
                    instanceInfoVo.setTotalAmount(orderInstance.getAmount());
                    Integer count = goodSpuInfos.stream().map(GoodSpuInfoVo::getQuantity).reduce(Integer::sum).orElse(0);
                    instanceInfoVo.setSpuCount(count);
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

    /**
     * 获取用户信息
     * @param accountId
     * @return
     */
    private UserInfoVo getUserInfo(String accountId){
        AccountVo accountVo = accountUserApi.getInfoByAccountId(accountId);
        if(!Objects.isNull(accountVo)){
            UserInfoVo userInfo = new UserInfoVo();
            userInfo.setHeadUrl(accountVo.getAvatar());
            userInfo.setName(accountVo.getAccountName());
            userInfo.setSex(Integer.valueOf(1).equals(accountVo.getSex())?"男":"女");
            userInfo.setBirthday(accountVo.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            userInfo.setPhone(accountVo.getPhone());
            userInfo.setCreateDate(accountVo.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            userInfo.setOrderNum(23423);
            userInfo.setAmount(new BigDecimal("343.567"));
            userInfo.setDateOf("今天");
            return userInfo;
        }
        return null;
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
    public List<OrderTableInfoBo> getOrderTableInfo(String storeId, List<String> tableNos) {
        List<OrderTableInfoBo> tableInfoBoList = Lists.newArrayList();
        List<OrderInstance> list = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getStoreId, storeId)
                .in(OrderInstance::getTableNo, tableNos)
                .list();
        Map<String, OrderInstance> orderInstanceMap = list.stream().collect(Collectors.groupingBy(OrderInstance::getTableNo,
                Collectors.collectingAndThen(Collectors.toList(),
                        a -> a.stream().sorted(Comparator.comparing(OrderInstance::getId).reversed()).findFirst().orElse(null))));
        if(!CollUtil.isEmpty(list)){
            List<Long> orderIds = list.stream().map(OrderInstance::getId).collect(Collectors.toList());
            Map<Long, List<OrderItem>> orderItemMap = orderItemService.lambdaQuery()
                    .in(OrderItem::getOrderId, orderIds).list().stream().collect(Collectors.groupingBy(OrderItem::getOrderId));
            List<OrderInstance> orderInstanceList = orderInstanceService.lambdaQuery()
                    .in(OrderInstance::getTableNo, tableNos)
                    .eq(OrderInstance::getStoreId, storeId)
                    .apply("date_format(dt,'%Y-%m-%d') = {0}", DateUtil.formatDate(DateUtil.date()))
                    .list();
            Map<String, Integer> orderTableNoMap = CollStreamUtil.groupBy(orderInstanceList, OrderInstance::getTableNo, Collectors.collectingAndThen(Collectors.toList(), e -> e.size()));
            orderInstanceMap.forEach((k,order)->{
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
               if(OrderInstanceTypeEnum.over.getCode().equals(order.getStatus())){
                    infoBo.setTableStatus(OrderTableStatusEnum.closed.getCode());
                }else if(OrderInstanceTypeEnum.make.getCode().equals(order.getStatus())){
                    infoBo.setTableStatus(OrderTableStatusEnum.menu_in.getCode());
                   long between = DateUtil.between(order.getDt(), DateUtil.date(), DateUnit.MINUTE);
                   if(between > 20){ //
                        infoBo.setTableStatus(OrderTableStatusEnum.time_out.getCode());
                    }
                }else if(OrderInstanceTypeEnum.make_finish.getCode().equals(order.getStatus())){
                    infoBo.setTableStatus(OrderTableStatusEnum.finish_menu.getCode());
                }
                infoBo.setTodayNum(orderTableNoMap.getOrDefault(k,0));
                tableInfoBoList.add(infoBo);
            });
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
    public boolean customerApplyRefund(OrderRefundForm refundForm) {
        List<String> imageUrls = refundForm.getImages();
        return orderInstanceService.lambdaUpdate()
                .set(OrderInstance::getRefund,2)
                .set(!CollUtil.isEmpty(imageUrls),OrderInstance::getApplyImageUrl,imageUrls.stream().collect(Collectors.joining(",")))
                .set(!StrUtil.isBlank(refundForm.getRemark()),OrderInstance::getApplyRefundRemark,refundForm.getRemark())
                .eq(OrderInstance::getId,refundForm.getOrderId()).update();
    }

    @Override
    public IPage<OrderInstanceTenantVo> selectOrderInstancePage(OrderInstanceTenantForm adminForm) {
        Page<OrderInstanceTenantForm> paging = new Page(adminForm.getCurrent(),adminForm.getSize());
        IPage<OrderInstanceTenantVo> adminVoIPage = orderInstanceMapper.selectOrderInstancePage(paging, adminForm);
        if(!CollUtil.isEmpty(adminVoIPage.getRecords())){
            List<String> storeIds = adminVoIPage.getRecords().stream().map(OrderInstanceTenantVo::getStoreId).collect(Collectors.toList());
            List<String> accountIds = adminVoIPage.getRecords().stream().map(OrderInstanceTenantVo::getAccountId).collect(Collectors.toList());
            List<AccountVo> accountVoList = accountUserApi.getInfoByAccountIds(accountIds);
            Map<String, String> accountVoMap = CollStreamUtil.toMap(accountVoList, AccountVo::getAccountId, AccountVo::getAccountName);
//            List<StoreResponse> storeList = storeInstanceApi.getStoresByIds(storeIds);
//            Map<String, StoreResponse> storeMap = CollStreamUtil.toMap(storeList, StoreResponse::getStoreId, Function.identity());
            for (OrderInstanceTenantVo record : adminVoIPage.getRecords()) {
                record.setUserName(accountVoMap.get(record.getAccountId()));
                record.setTypeStr(EnumUtil.likeValueOf(OrderTypeEnum.class,record.getType()).getName());
//                if(storeMap.containsKey(record.getStoreId())){
//                    StoreResponse storeResponse = storeMap.get(record.getStoreId());
//                    record.setBrand(storeResponse.getStoreBrand());
//                    record.setStoreDistrict(storeResponse.getDistrict());
//                    record.setStorePattern(storeResponse.getStorePattern());
//                    record.setStoreName(storeResponse.getName());
//                }
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
//            List<String> accountIds = adminVoIPage.getRecords().stream().map(OrderInstanceTenantVo::getAccountId).collect(Collectors.toList());
//            List<AccountVo> accountVoList = accountUserApi.getInfoByAccountIds(accountIds);
//            Map<String, String> accountVoMap = CollStreamUtil.toMap(accountVoList, AccountVo::getAccountId, AccountVo::getAccountName);
            List<String> storeIds = adminVoIPage.getRecords().stream().map(OrderInstanceTenantVo::getStoreId).collect(Collectors.toList());
            List<StoreResponse> storeList = storeInstanceApi.getStoresByIds(storeIds);
            Map<String, StoreResponse> storeMap = CollStreamUtil.toMap(storeList, StoreResponse::getStoreId, Function.identity());
            for (OrderInstanceTenantOpVo record : adminVoIPage.getRecords()) {
                OrderItemMoreBo moreBo = BeanUtil.toBean(record.getMore(),OrderItemMoreBo.class);
                if(moreBo != null){
                    record.setNum(moreBo.getFoodNum());
                    record.setRemarks(moreBo.getRemarks());
                    record.setReAmount(moreBo.getLossReporting());
                    record.setDateTime(moreBo.getOpt());
                    record.setOperator(moreBo.getUserName());
                }
                record.setTypeStr(EnumUtil.likeValueOf(OrderTypeEnum.class,record.getType()).getName());
                if(storeMap.containsKey(record.getStoreId())){
                    StoreResponse storeResponse = storeMap.get(record.getStoreId());
                    record.setBrand(storeResponse.getStoreBrand());
                    record.setStoreDistrict(storeResponse.getDistrict());
                    record.setStorePattern(storeResponse.getStorePattern());
                    record.setStoreName(storeResponse.getName());
                }
                record.setFoodNum(2);
            }
        }
        return adminVoIPage;
    }

    @Override
    public OrderDetailPcVo getOrderDetailPcVo(Long orderId) {
        OrderInstance orderInstance = orderInstanceMapper.selectById(orderId);
        Map<Long, List<GoodSpuInfoVo>> orderGoodSpuInfoMap = getGoodSpuInfoVo(Lists.newArrayList(orderInstance.getId()));
        OrderDetailPcVo detailPcVo = new OrderDetailPcVo();
        detailPcVo.setOrderId(orderInstance.getId());
        detailPcVo.setOrderNo(orderInstance.getOrderNo());
        detailPcVo.setType(orderInstance.getType());
        detailPcVo.setPeoples(orderInstance.getPeoples());
        detailPcVo.setTableNo(orderInstance.getTableNo());
        AccountVo accountVo = accountUserApi.getInfoByAccountId(orderInstance.getAccountId());
        detailPcVo.setPhone(accountVo.getPhone());
        detailPcVo.setAccountName(accountVo.getAccountName());
        detailPcVo.setAddress(StrUtil.concat(true,accountVo.getProvince(),accountVo.getCity(),accountVo.getDistrict()));
        detailPcVo.setOrderSource(orderInstance.getOrderSource());
        detailPcVo.setRemark(orderInstance.getRemark());
        if(orderGoodSpuInfoMap.containsKey(orderInstance.getId())){
            List<GoodSpuInfoVo> spuInfoVoList = orderGoodSpuInfoMap.get(orderInstance.getId());
            BigDecimal decimal = spuInfoVoList.stream().map(GoodSpuInfoVo::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            detailPcVo.setSubtotal(decimal);
            detailPcVo.setGoodSpuInfoList(spuInfoVoList);
        }
        detailPcVo.setTotalAmount(orderInstance.getAmount());
        detailPcVo.setUserInfo(getUserInfo(orderInstance.getAccountId()));
        detailPcVo.setStatus(1);
        if(orderInstance.getDiningTime() != null){
            detailPcVo.setDiningTime(DateUtil.format(orderInstance.getDiningTime(), "HH:mm"));
        }else{
            detailPcVo.setDiningTime(DateUtil.formatBetween(orderInstance.getDt(),
                    Optional.ofNullable(orderInstance.getDiningTime()).orElse(DateUtil.date()), BetweenFormatter.Level.MINUTE));
        }
        OperationLogVo logVo = new OperationLogVo().setDt(DateUtil.date()).setRemark("A01开台点单").setOperator("王五");
        detailPcVo.setOperationLogList(Lists.newArrayList(logVo));
        detailPcVo.setRiderName("李四");
        detailPcVo.setRiderPhone("156786734543");
        detailPcVo.setDt(orderInstance.getDt());
        return detailPcVo;
    }

    @Override
    public OrderInstanceDetailVo getOrderDetailInfo(Long orderId) {
        OrderInstanceDetailVo detailVo = new OrderInstanceDetailVo();
        OrderInstance instance = orderInstanceService.getById(orderId);
        List<OrderItem> orderItems = orderItemService.lambdaQuery()
                .in(OrderItem::getOrderId,instance.getId()).list();
        detailVo.setOrderId(instance.getId());
        detailVo.setPayStatus(instance.getStatus());
        detailVo.setOrderStatus(1);
        detailVo.setTableNo(instance.getTableNo());
        if(Integer.valueOf(2).equals(instance.getRefund())){
            detailVo.setRefundStatus(1);
        }else if(Integer.valueOf(1).equals(instance.getRefund())){
            detailVo.setRefundStatus(3);
            detailVo.setRefundAmount(instance.getRefundAmout());
            detailVo.setRefundReason(instance.getRefundRemark());
        }else if(Integer.valueOf(0).equals(instance.getRefund())){
            detailVo.setRefundStatus(2);
            detailVo.setRefundReason(instance.getRefundRemark());
        }
        AccountVo accountVo = accountUserApi.getInfoByAccountId(instance.getAccountId());
        detailVo.setAccountName(accountVo.getAccountName());
        detailVo.setAccountPhone(accountVo.getPhone());
        detailVo.setAccountAddress(StrUtil.concat(true,accountVo.getProvince(),accountVo.getCity(),accountVo.getDistrict()));
        StoreResponse storeResponse = storeInstanceApi.getStoreById(instance.getStoreId());
       if(storeResponse != null){
           detailVo.setStoreName(storeResponse.getName());
           detailVo.setStoreAddress(storeResponse.getAddress());
           detailVo.setStorePhone(storeResponse.getContactPhone());
       }
        List<OrderInstanceDetailVo.GoodInfo> list = Lists.newArrayList();
        List<Long> spuIds = orderItems.stream().map(OrderItem::getSpuId).collect(Collectors.toList());
        List<GoodsForm> data = goodsApi.getGoodsInfoByIds(spuIds);
        List<GoodsSpuForm> spuInfoList = Optional.ofNullable(data).orElseGet(ArrayList::new).stream().map(GoodsForm::getGoodsSpu).collect(Collectors.toList());
        Map<Long, GoodsSpuForm> spuFormMap = CollStreamUtil.toMap(spuInfoList, GoodsSpuForm::getId, Function.identity());
        for (OrderItem item : orderItems) {
            if(spuFormMap.containsKey(item.getSpuId())){
                OrderInstanceDetailVo.GoodInfo goodInfo = new OrderInstanceDetailVo.GoodInfo();
                GoodsSpuForm spuForm = spuFormMap.get(item.getSpuId());
                goodInfo.setImage(spuForm.getPic());
                goodInfo.setSpuName(spuForm.getSpuName());
                goodInfo.setSubSpuName(spuForm.getDescr());
                goodInfo.setNum(item.getQuantity());
                goodInfo.setPrice(item.getPrice());
                list.add(goodInfo);
            }
        }
        detailVo.setGoodInfoList(list);
        BigDecimal decimal = orderItems.stream()
                .map(e -> new BigDecimal(e.getQuantity()).multiply(e.getPrice()))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        detailVo.setTotalAmount(decimal);
        detailVo.setPackingPrice(new BigDecimal("3.5"));
        detailVo.setGivePrice(new BigDecimal("10.5"));
        detailVo.setDeductionPrice(new BigDecimal("10.5"));
        detailVo.setIntegralPrice(200);
        detailVo.setTotalPrice(new BigDecimal("134.5"));
        detailVo.setRemark(instance.getRemark());
        detailVo.setTableWare(1);
        OrderInstanceDetailVo.OrderInfo orderInfo = new OrderInstanceDetailVo.OrderInfo();
        orderInfo.setOrderNo(instance.getOrderNo());
        orderInfo.setDiningMode(instance.getType());
        orderInfo.setDt(instance.getDt());
        orderInfo.setIntegral(200);
        orderInfo.setAcceptIntegral("本人");
        detailVo.setOrderInfo(orderInfo);
        return detailVo;
    }

    @Override
    public OrderTableTotalVo getOrderInstanceTableInfo(String storeId, String tableNo) {
        List<OrderInstance> orderInstanceList = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getTableNo, tableNo)
                .eq(OrderInstance::getStoreId, storeId)
                .apply("date_format(dt,'%Y-%m-%d') = {0}", DateUtil.formatDate(DateUtil.date()))
                .orderByDesc(OrderInstance::getDt)
                .list();
        if(CollUtil.isEmpty(orderInstanceList)){
            return null;
        }
        OrderTableTotalVo tableTotalVo = new OrderTableTotalVo();
        OrderInstance instanceFirstOrder = CollUtil.getFirst(orderInstanceList);
        tableTotalVo.setStatus(1);
        tableTotalVo.setTableNo(instanceFirstOrder.getTableNo());
        tableTotalVo.setTodayNum(orderInstanceList.size());
        tableTotalVo.setMenuMin("36分钟");
        tableTotalVo.setPeoples(instanceFirstOrder.getPeoples());
        List<OrderTableInfoVo> tableInfoVos = Lists.newArrayList();
        Set<Long> orderIds = orderInstanceList.stream().map(OrderInstance::getId).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemService.lambdaQuery()
                .in(OrderItem::getOrderId,orderIds).list();
        Map<Long, List<OrderItem>> orderItemMap = CollStreamUtil.groupKeyValue(orderItemList, OrderItem::getOrderId, Function.identity());
        BigDecimal amount = new BigDecimal("0");
        for (OrderInstance instance : orderInstanceList) {
            amount = amount.add(instance.getAmount());
            List<OrderItem> orderItems = Optional.ofNullable(orderItemMap.get(instance.getId())).filter(CollUtil::isNotEmpty).orElseGet(ArrayList::new);
            OrderTableInfoVo infoVo = new OrderTableInfoVo();
            infoVo.setOrderId(instance.getId());
            infoVo.setOrderNo(instance.getOrderNo());
            List<GoodSpuInfoVo> list = Lists.newArrayList();
            List<Long> spuIds = orderItems.stream().map(OrderItem::getSpuId).collect(Collectors.toList());
            List<GoodsForm> data = goodsApi.getGoodsInfoByIds(spuIds);
            List<GoodsSpuForm> spuInfoList = Optional.ofNullable(data).orElseGet(ArrayList::new).stream().map(GoodsForm::getGoodsSpu).collect(Collectors.toList());
            Map<Long, GoodsSpuForm> spuFormMap = CollStreamUtil.toMap(spuInfoList, GoodsSpuForm::getId, Function.identity());
            for (OrderItem item : orderItems) {
                GoodSpuInfoVo goodInfo = new GoodSpuInfoVo();
                goodInfo.setOrderItemId(item.getId());
                goodInfo.setQuantity(item.getQuantity());
                if (spuFormMap.containsKey(item.getSpuId())) {
                    GoodsSpuForm spuForm = spuFormMap.get(item.getSpuId());
                    goodInfo.setGoodName(spuForm.getSpuName());
                    goodInfo.setPrice(item.getPrice());
                    if (Integer.valueOf(2).equals(item.getFlag())) {
                        OrderItemMoreBo moreBo = JSONUtil.toBean(item.getMore(), OrderItemMoreBo.class);
                        goodInfo.setRefundNum(moreBo == null ? moreBo.getFoodNum() : 0);
                    }
                }
                goodInfo.setFlag(item.getFlag());
                goodInfo.setRemark(item.getRemark());
                list.add(goodInfo);
            }
            Integer nums = orderItems.stream().map(OrderItem::getQuantity).reduce((a, b) -> a + b).orElse(0);
            infoVo.setSpuCount(nums);
            infoVo.setSpuCategory(orderItems.size());
            infoVo.setGoodSpuInfoList(list);
            infoVo.setRemark(instance.getRemark());
            infoVo.setDt(instance.getDt());
            infoVo.setSubtotal(instance.getAmount());
            tableInfoVos.add(infoVo);
        }
        tableTotalVo.setTableInfoList(tableInfoVos);
        if(Integer.valueOf(4).equals(tableTotalVo.getStatus())){
            tableTotalVo.setAmount(amount);
        }else{
            tableTotalVo.setAmount(instanceFirstOrder.getAmount());
        }
        return tableTotalVo;
    }

    @Override
    public List<OrderMyInstanceInfoVo> getMyOrderInstance(OrderMyForm myForm) {
        List<OrderMyInstanceInfoVo> infoVoList = Lists.newArrayList();
        List<OrderInstance> instanceList = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getAccountId, myForm.getAccountId())
                .gt(!ObjectUtil.isNull(myForm.getOrderId()),OrderInstance::getId, myForm.getOrderId())
                .eq(myForm.getType() != null,OrderInstance::getType,myForm.getType())
                .orderByDesc(OrderInstance::getId)
                .list();
        if(!CollUtil.isEmpty(instanceList)){
            List<Long> orderIds = instanceList.stream().map(OrderInstance::getId).collect(Collectors.toList());
            List<OrderItem> orderItems = orderItemService.lambdaQuery().in(OrderItem::getOrderId, orderIds).list();
            Map<Long, List<OrderItem>> orderedItemMap = CollStreamUtil.groupByKey(orderItems, OrderItem::getOrderId, false);

            List<String> storeIds = instanceList.stream().map(OrderInstance::getStoreId).collect(Collectors.toList());
            List<StoreResponse> storeList = storeInstanceApi.getStoresByIds(storeIds);
            Map<String, String> storeMap = CollStreamUtil.toMap(storeList, StoreResponse::getStoreId, StoreResponse::getName);
            for (OrderInstance orderInst : instanceList) {
                OrderMyInstanceInfoVo instanceInfoVo = new OrderMyInstanceInfoVo();
                instanceInfoVo.setOrderId(orderInst.getId());
                instanceInfoVo.setStoreName(storeMap.get(orderInst.getStoreId()));
                instanceInfoVo.setTableNo(orderInst.getTableNo());
                instanceInfoVo.setOrderStatus(1);
                instanceInfoVo.setType(orderInst.getType());
                if(orderedItemMap.containsKey(orderInst.getId())){
                    List<OrderItem> itemList  = orderedItemMap.get(orderInst.getId());
                    List<Long> spuIds = itemList.stream().map(OrderItem::getSpuId).collect(Collectors.toList());
                    List<GoodsForm> data = goodsApi.getGoodsInfoByIds(spuIds);
                    List<String> images = Optional.ofNullable(data).orElseGet(ArrayList::new).stream().map(GoodsForm::getGoodsSpu).map(GoodsSpuForm::getPic).collect(Collectors.toList());
                    instanceInfoVo.setGoodsUrl(images);
                    Integer nums = itemList.stream().map(OrderItem::getQuantity).reduce(Integer::sum).orElse(0);
                    instanceInfoVo.setGoodsNum(nums);
//                    BigDecimal decimal = itemList.stream()
//                            .map(e -> new BigDecimal(e.getQuantity()).multiply(e.getPrice()))
//                            .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    instanceInfoVo.setTotal(orderInst.getAmount());
                }
                instanceInfoVo.setDt(orderInst.getDt());
                infoVoList.add(instanceInfoVo);
            }
        }
        return infoVoList;
    }

    @Override
    public OrderTaVo getTaOrderStat(OrderTaTypeForm typeForm) {
        List<OrderInstance> instanceList = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getStoreId, typeForm.getStoreId())
                .eq(OrderInstance::getType, typeForm.getType())
                .eq(OrderInstance::getAccountId, typeForm.getAccountId()).list();
        OrderTaVo orderTaVo = new OrderTaVo();
        int size = instanceList.size();
        orderTaVo.setOrderCount(size);
        BigDecimal totalAmount = instanceList.stream().map(OrderInstance::getAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        orderTaVo.setOrderAmount(totalAmount);
        orderTaVo.setOrderConsumeNum(size);
        return orderTaVo;
    }


    @Override
    public List<Object> getTaOrderAll(OrderTaPageForm pageForm) {
        List list = new ArrayList();
        if(Integer.valueOf(0).equals(pageForm.getType())){
            list = getTaOrderTablePage(pageForm);
        }
        if(Integer.valueOf(2).equals(pageForm.getType())){
            list = getTaOrderPackPage(pageForm);
        }
        if(Integer.valueOf(1).equals(pageForm.getType())){
            list = getTaOrderTakeOutPage(pageForm);
        }
        return list;
    }

    @Override
    public List<OrderTaTableVo> getTaOrderTablePage(OrderTaPageForm pageForm) {
        List<OrderTaTableVo> tableVoList = CollUtil.newArrayList();
        List<OrderInstance> instanceList = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getStoreId, pageForm.getStoreId())
                .eq(OrderInstance::getType, 0)
                .lt(pageForm.getOrderId() != null,OrderInstance::getId, pageForm.getOrderId())
                .orderByDesc(OrderInstance::getId)
                .eq(OrderInstance::getAccountId, pageForm.getAccountId()).last("limit 10").list();
        for (OrderInstance orderInstance : instanceList) {
            OrderTaTableVo taTableVo = new OrderTaTableVo();
            taTableVo.setOrderId(orderInstance.getId());
            taTableVo.setTableNo(orderInstance.getTableNo());
            taTableVo.setDt(orderInstance.getDt());
            taTableVo.setMenuNum(3);
            taTableVo.setPeople(orderInstance.getPeoples());
            taTableVo.setPayChannel(orderInstance.getPayChannel());
            taTableVo.setPeopleAug(orderInstance.getAmount().divide(new BigDecimal(orderInstance.getPeoples()),2, RoundingMode.HALF_UP));
            taTableVo.setAmountTotal(orderInstance.getAmount());
            tableVoList.add(taTableVo);
        }
        return tableVoList;
    }

    @Override
    public List<OrderTaPackVo> getTaOrderPackPage(OrderTaPageForm pageForm) {
        List<OrderTaPackVo> packVoList = CollUtil.newArrayList();
        List<OrderInstance> instanceList = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getStoreId, pageForm.getStoreId())
                .eq(OrderInstance::getType, 2)
                .lt(pageForm.getOrderId() != null,OrderInstance::getId, pageForm.getOrderId())
                .orderByDesc(OrderInstance::getId)
                .eq(OrderInstance::getAccountId, pageForm.getAccountId()).last("limit 10").list();
        for (OrderInstance orderInstance : instanceList) {
            OrderTaPackVo packVo = new OrderTaPackVo();
            packVo.setOrderId(orderInstance.getId());
            packVo.setDt(orderInstance.getDt());
            packVo.setMenuNum(4);
            packVo.setPayChannel(orderInstance.getPayChannel());
            packVo.setAmountTotal(orderInstance.getAmount());
            packVoList.add(packVo);
        }
        return packVoList;
    }

    @Override
    public List<OrderTaTakeOutVo> getTaOrderTakeOutPage(OrderTaPageForm pageForm) {
        List<OrderTaTakeOutVo> taTakeOutList = CollUtil.newArrayList();
        List<OrderInstance> instanceList = orderInstanceService.lambdaQuery()
                .eq(OrderInstance::getStoreId, pageForm.getStoreId())
                .eq(OrderInstance::getType, 1)
                .lt(pageForm.getOrderId() != null,OrderInstance::getId, pageForm.getOrderId())
                .orderByDesc(OrderInstance::getId)
                .eq(OrderInstance::getAccountId, pageForm.getAccountId()).last("limit 10").list();
        for (OrderInstance orderInstance : instanceList) {
            OrderTaTakeOutVo takeOutVo = new OrderTaTakeOutVo();
            takeOutVo.setOrderId(orderInstance.getId());
            takeOutVo.setDt(orderInstance.getDt());
            takeOutVo.setMenuNum(3);
            takeOutVo.setTakeOut(1);
            takeOutVo.setPayChannel(orderInstance.getPayChannel());
            takeOutVo.setAmountTotal(orderInstance.getAmount());
            taTakeOutList.add(takeOutVo);
        }
        return taTakeOutList;
    }

    @Override
    public boolean updateTableNo(OrderChangeTableNoForm tableNoForm) {
        return  orderInstanceService.lambdaUpdate()
                .set(OrderInstance::getTableNo,tableNoForm.getTableNo())
                .eq(OrderInstance::getOrderNo,tableNoForm.getOrderNo()).update();

    }

    @Override
    public OrderMergeTableNoVo mergeTableNo(OrderMergeTableNoForm mergeTableNoForm) {
        OrderMergeTableNoVo tableNoVo = new OrderMergeTableNoVo();
        List<OrderInstance> instanceList = orderInstanceService.lambdaQuery()
                .in(OrderInstance::getOrderNo, mergeTableNoForm.getOrderNoList())
                .list();
        List<OrderMergeTableNoVo.TableNoAmount> list = CollUtil.newArrayList();
        BigDecimal bigDecimal = new BigDecimal("0");
        for (OrderInstance orderInstance : instanceList) {
            OrderMergeTableNoVo.TableNoAmount tableNoAmount = new OrderMergeTableNoVo.TableNoAmount();
            tableNoAmount.setTableNo(orderInstance.getTableNo());
            tableNoAmount.setTotalAmount(orderInstance.getAmount());
            bigDecimal = bigDecimal.add(orderInstance.getAmount());
            list.add(tableNoAmount);
        }
        tableNoVo.setTableNoAmountList(list);
        tableNoVo.setAmount(bigDecimal);
        return tableNoVo;
    }

    @Override
    public OrderTableInfoVo orderSettleAccounts(String orderNo) {
        OrderInstance instance = orderInstanceService.lambdaQuery().eq(OrderInstance::getOrderNo, orderNo).one();
        OrderTableInfoVo tableInfoVo = new OrderTableInfoVo();
        tableInfoVo.setOrderId(instance.getId());
        tableInfoVo.setOrderNo(instance.getOrderNo());
        tableInfoVo.setDt(instance.getDt());
        tableInfoVo.setSubtotal(instance.getAmount());
        List<OrderItem> orderItems = orderItemService.lambdaQuery()
                .eq(OrderItem::getOrderId,instance.getId()).list();
        List<Long> spuIds = orderItems.stream().map(OrderItem::getSpuId).collect(Collectors.toList());
        List<GoodsForm> data = goodsApi.getGoodsInfoByIds(spuIds);
        List<GoodsSpuForm> spuInfoList = Optional.ofNullable(data).orElseGet(ArrayList::new).stream().map(GoodsForm::getGoodsSpu).collect(Collectors.toList());
        Map<Long, GoodsSpuForm> spuFormMap = CollStreamUtil.toMap(spuInfoList, GoodsSpuForm::getId, Function.identity());
        List<GoodSpuInfoVo> list = Lists.newArrayList();
        for (OrderItem item : orderItems) {
            GoodSpuInfoVo goodInfo = new GoodSpuInfoVo();
            goodInfo.setOrderItemId(item.getId());
            goodInfo.setQuantity(item.getQuantity());
            goodInfo.setPrice(item.getPrice());
            if (spuFormMap.containsKey(item.getSpuId())) {
                GoodsSpuForm spuForm = spuFormMap.get(item.getSpuId());
                goodInfo.setGoodName(spuForm.getSpuName());
                if (Integer.valueOf(2).equals(item.getFlag())) {
                    OrderItemMoreBo moreBo = JSONUtil.toBean(item.getMore(), OrderItemMoreBo.class);
                    goodInfo.setRefundNum(moreBo == null ? moreBo.getFoodNum() : 0);
                }
            }
            goodInfo.setFlag(item.getFlag());
            goodInfo.setRemark(item.getRemark());
            goodInfo.setAmount(goodInfo.getPrice().multiply(new BigDecimal(goodInfo.getQuantity())));
            list.add(goodInfo);
        }
        tableInfoVo.setGoodSpuInfoList(list);
        return tableInfoVo;
    }


}
