package com.chenweijiang.wcg_mall.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO  {
    private String consignee;
    private String consigneeAddress;
    private String consigneePhone;
    private Integer payMethod;
    private BigDecimal amount;
}
