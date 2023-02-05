package org.dows.order;
import java.math.BigDecimal;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.order.api.OrderItemApiService;
import org.dows.order.bo.OrderItemFlagBo;
import org.dows.order.bo.OrderItemMoreBo;
import org.dows.order.entity.OrderInstance;
import org.dows.order.entity.OrderItem;
import org.dows.order.enums.OrderItemFlagEnum;
import org.dows.order.service.OrderInstanceService;
import org.dows.order.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.BiFunction;


@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class OrderItemBiz implements OrderItemApiService {


    private final OrderItemService orderItemService;

    private final OrderInstanceService orderInstanceService;

    private static final Map<OrderItemFlagEnum, BiFunction<OrderItemFlagBo,OrderItem,Boolean>> optMenuMap = Maps.newConcurrentMap();

    @Override
    public boolean updateOrderItem(OrderItemFlagBo flagBo) {
        OrderItem orderItem = orderItemService.getBaseMapper().selectById(flagBo.getOrderItemId());
        return optMenuMap.get(flagBo.getFlag()).apply(flagBo,orderItem);
    }

    /**
     * 加菜
     * @param flagBo
     * @param orderItem
     * @return
     */
    public boolean addMenuItem(OrderItemFlagBo flagBo,OrderItem orderItem){
        OrderItem oldItem = new OrderItem();
        oldItem.setId(orderItem.getId());
        oldItem.setFlag(OrderItemFlagEnum.add_menu.getCode());
        OrderInstance instance = orderInstanceService.lambdaQuery().eq(OrderInstance::getId, orderItem.getOrderId()).one();
        OrderItem item  = new OrderItem();
        item.setTableNo(instance.getTableNo());
        item.setAccountId(instance.getAccountId());
        item.setSpuId(orderItem.getSpuId());
        item.setSpuName(orderItem.getSpuName());
        item.setQuantity(1);
        item.setPrice(orderItem.getPrice());
        item.setFlag(OrderItemFlagEnum.add_menu.getCode());
        oldItem.setMore(JSONUtil.toJsonStr(item));
        return orderItemService.updateById(oldItem);
    }

    /**
     * 划菜
     * @param flagBo
     * @param orderItem
     * @return
     */
    public boolean strokeMenu(OrderItemFlagBo flagBo,OrderItem orderItem){
        OrderItem item = new OrderItem();
        item.setId(orderItem.getId());
        item.setFlag(OrderItemFlagEnum.stroke_menu.getCode());
        return orderItemService.updateById(item);
    }

    /**
     * 赠送
     * @param flagBo
     * @param orderItem
     * @return
     */
    public boolean give(OrderItemFlagBo flagBo,OrderItem orderItem){
        //TODO 修改订单总金额
        OrderItemMoreBo moreBo = new OrderItemMoreBo();
        moreBo.setFoodNum(flagBo.getNum());
        moreBo.setRemarks(flagBo.getRemark());
        moreBo.setOpt(DateUtil.date());
        moreBo.setUserName(null);
        OrderItem item = new OrderItem();
        item.setId(orderItem.getId());
        item.setFlag(OrderItemFlagEnum.give.getCode());
        item.setMore(JSONUtil.toJsonStr(moreBo));
        item.setQuantity(orderItem.getQuantity()+1);
        return orderItemService.updateById(item);
    }

    /**
     * 退菜
     * @param flagBo
     * @param orderItem
     * @return
     */
    public boolean returnMenu(OrderItemFlagBo flagBo,OrderItem orderItem){
        //TODO 修改订单总金额
        OrderItemMoreBo moreBo = new OrderItemMoreBo();
        moreBo.setFoodNum(flagBo.getNum());
        moreBo.setRemarks(flagBo.getRemark());
        moreBo.setOpt(DateUtil.date());
        moreBo.setUserName(String.valueOf(flagBo.getUserId()));
        OrderItem item  = new OrderItem();
        item.setId(orderItem.getId());
        item.setFlag(OrderItemFlagEnum.return_menu.getCode());
        item.setQuantity(orderItem.getQuantity() - flagBo.getNum());
        item.setMore(JSONUtil.toJsonStr(moreBo));
        return orderItemService.updateById(item);
    }

    /**
     * 报损
     * @param flagBo
     * @param orderItem
     * @return
     */
    public boolean reporting(OrderItemFlagBo flagBo,OrderItem orderItem){
        //TODO 修改订单总金额
        OrderItemMoreBo moreBo = new OrderItemMoreBo();
        moreBo.setLossReporting(flagBo.getReporting());
        moreBo.setRemarks(flagBo.getRemark());
        moreBo.setOpt(DateUtil.date());
        moreBo.setUserName(null);
        OrderItem item  = new OrderItem();
        item.setId(orderItem.getId());
        item.setFlag(OrderItemFlagEnum.reporting.getCode());
        item.setMore(JSONUtil.toJsonStr(moreBo));
        item.setQuantity(orderItem.getQuantity()-1);
        return orderItemService.updateById(item);
    }


    @PostConstruct
    public void optMenuItem(){
        optMenuMap.putIfAbsent(OrderItemFlagEnum.stroke_menu,this::strokeMenu);
        optMenuMap.putIfAbsent(OrderItemFlagEnum.return_menu,this::returnMenu);
        optMenuMap.putIfAbsent(OrderItemFlagEnum.give,this::give);
        optMenuMap.putIfAbsent(OrderItemFlagEnum.reporting,this::reporting);
        optMenuMap.putIfAbsent(OrderItemFlagEnum.add_menu,this::addMenuItem);
    }
}
