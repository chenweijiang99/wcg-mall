package com.chenweijiang.wcg_mall.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailProductList implements Serializable {
    private Long id;
    private String productName;
    private Integer productNumber;
    private BigDecimal productPrice;
    private String productImage;
}
