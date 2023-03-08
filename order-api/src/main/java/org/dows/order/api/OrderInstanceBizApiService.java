package org.dows.order.api;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.dows.order.bo.*;
import org.dows.order.form.*;
import org.dows.order.vo.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface OrderInstanceBizApiService {

    /**
     * 创建订单 不支付的场景
     * @param createBo
     */
    void creatOrderInstance(OrderInstanceCreateBo createBo);

    /**
     * 创建订单 支付场景使用
     * @param paymentBo
     * @return
     */
    String createOrderInstance(OrderInstancePaymentBo paymentBo);

    /**
     * 查询订单信息多个
     * @param queryBo
     * @return
     */
    List<OrderInstanceInfoVo> queryOrderInfo(OrderInstanceQueryBo queryBo);




    /**
     * 查询单个订单信息
     * @return
     */
    default OrderInstanceInfoVo queryOrderInfoOne(OrderInstanceQueryBo queryBo){
        List<OrderInstanceInfoVo> infoVoList = queryOrderInfo(queryBo);
        return Optional.ofNullable(infoVoList)
                .filter(CollUtil::isNotEmpty)
                .map(CollUtil::getFirst)
                .orElse(null);
    }

    /**
     * 支付成功修改订单状态
     * @return
     */
    boolean updateOrderInstance(OrderInstanceStatusBo statusBo);


    /**
     * 返回桌台的订单信息 吴笑笑用
     * @param storeId
     * @param tableNos
     * @return
     */
    List<OrderTableInfoBo> getOrderTableInfo(String storeId,List<String> tableNos);

    /**
     * websocket 推送到桌台通知 已超时超时 其他状态
     * @param storeId
     * @param tableNo
     * @return
     */
    default OrderTableInfoBo getOrderTableInfoOne(String storeId,String tableNo) {
        List<OrderTableInfoBo> orderTableInfoList = getOrderTableInfo(storeId, Arrays.asList(tableNo));
        return Optional.ofNullable(orderTableInfoList)
                .filter(CollUtil::isNotEmpty)
                .map(CollUtil::getFirst)
                .orElse(null);
    }

    /**
     * 申请退款 接口
     * @param refundBo
     * @return
     */
    boolean applyRefund(OrderApplyRefundBo refundBo);

    /**
     * 确认出餐
     * @param orderId
     * @return
     */
    boolean diningOrder(Long orderId);

    /**
     * 申请退款
     * @param refundForm
     * @return
     */
    boolean customerApplyRefund(OrderRefundForm refundForm);

    /**
     * 订单分页列表
     * @param adminForm
     * @return
     */
    IPage<OrderInstanceTenantVo> selectOrderInstancePage(OrderInstanceTenantForm adminForm);

    /**
     * 订单 退菜 赠菜 破损通用
     * @param adminForm
     * @return
     */
    IPage<OrderInstanceTenantOpVo> selectOrderInstanceRePage(OrderInstanceTenantForm adminForm);

    /**
     * pc 订单详情
     * @return
     */
    OrderDetailPcVo getOrderDetailPcVo(Long orderId);

    /**
     * c端用户的订单详情
     * @param orderId
     * @return
     */
    OrderInstanceDetailVo getOrderDetailInfo(Long orderId);


    /**
     * 桌台订单信息
     * @param storeId
     * @param tableNo
     * @return
     */
    OrderTableTotalVo getOrderInstanceTableInfo(String storeId,String tableNo);


    /**
     * c端我的订单
     * @param myForm
     * @return
     */
    List<OrderMyInstanceInfoVo> getMyOrderInstance(OrderMyForm myForm);

    /**
     * 获取Ta的订单统计信息
     * @param typeForm
     * @return
     */
    OrderTaVo getTaOrderStat(OrderTaTypeForm typeForm);




    <T> List<T> getTaOrderAll(OrderTaPageForm pageForm);
    /**
     * Ta订单 堂食列表
     * @return
     */
    List<OrderTaTableVo> getTaOrderTablePage(OrderTaPageForm pageForm);

    /**
     * Ta订单 打包列表
     * @return
     */
    List<OrderTaPackVo> getTaOrderPackPage(OrderTaPageForm pageForm);

    /**
     * Ta订单 外卖列表
     * @return
     */
    List<OrderTaTakeOutVo> getTaOrderTakeOutPage(OrderTaPageForm pageForm);

    /**
     * 换台
     * @return
     */
    boolean updateTableNo(OrderChangeTableNoForm tableNoForm);

    /**
     * 合并账单
     * @return
     */
    List<OrderTableTotalVo> mergeTableNo(OrderMergeTableNoForm mergeTableNoForm);

    /**
     * 结账 (合并的结账)
     * @return
     */
    OrderMergeTableNoVo orderSettleAccounts(List<String> orderNo);

    /**
     * 单个桌台结账
     * @return
     */
    OrderTableNoVo orderSettleAccounts(String orderNo);


}
