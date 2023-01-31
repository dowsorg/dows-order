package org.dows.order.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.dows.order.bo.*;
import org.dows.order.form.OrderInstanceTenantForm;
import org.dows.order.vo.OrderInstanceTenantOpVo;
import org.dows.order.vo.OrderInstanceTenantVo;
import org.dows.order.vo.OrderInstanceInfoVo;

import java.util.Arrays;
import java.util.List;

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
        return infoVoList.isEmpty()?null:infoVoList.get(0);
    }

    /**
     * 支付成功修改订单状态
     * @return
     */
    boolean updateOrderInstance(OrderInstanceStatusBo statusBo);


    /**
     * 返回桌台的订单信息 催万乐用
     * @param storeId
     * @param tableIds
     * @return
     */
    List<OrderTableInfoBo> getOrderTableInfo(String storeId,List<String> tableIds);

    /**
     * websocket 推送到桌台通知 已超时超时 其他状态
     * @param storeId
     * @param tableId
     * @return
     */
    default OrderTableInfoBo getOrderTableInfoOne(String storeId,String tableId) {
        List<OrderTableInfoBo> orderTableInfoList = getOrderTableInfo(storeId, Arrays.asList(tableId));
        return orderTableInfoList.isEmpty()?null:orderTableInfoList.get(0);
    }

    /**
     * 申请退款 接口
     * @param refundBo
     * @return
     */
    boolean applyRefund(OrderApplyRefundBo refundBo);

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


}
