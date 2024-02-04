package com.chenweijiang.wcg_mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDiscount implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long createUser;
    private Long productId;
    private BigDecimal discountedPrice;
}
