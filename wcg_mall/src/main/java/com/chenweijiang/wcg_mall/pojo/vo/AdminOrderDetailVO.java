package com.chenweijiang.wcg_mall.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminOrderDetailVO {
    private Long id;
    private String orderNumber;
    private Long productId;
    private Integer productNumber;
    private String productName;
    private BigDecimal productPrice;
    private String productImage;
}
