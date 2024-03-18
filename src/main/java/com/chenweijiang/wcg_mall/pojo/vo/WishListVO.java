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
public class WishListVO implements Serializable {
    private Long id;
    private Long productId;
    private Long userId;
    private String productName;
    private BigDecimal productPrice;
    private String productImage;

}
