package org.dows.order;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.order.api.OrderItemApiService;
import org.dows.order.bo.OrderItemFlagBo;
import org.dows.order.bo.OrderItemMoreBo;
import org.dows.order.entity.OrderItem;
import org.dows.order.enums.OrderItemFlagEnum;
import org.dows.order.service.OrderItemService;
import org.springframework.stereotype.Service;


@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class OrderItemBiz implements OrderItemApiService {


    private final OrderItemService orderItemService;

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
}
