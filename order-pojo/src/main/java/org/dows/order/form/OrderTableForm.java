package org.dows.order.form;

import lombok.Data;

import java.util.List;

@Data
public class OrderTableForm {

    private String soreId;

    private List<String> tableNos;

}
