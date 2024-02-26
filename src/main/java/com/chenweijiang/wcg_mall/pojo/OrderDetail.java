package com.chenweijiang.wcg_mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail{


    private Long id;
    private String orderNumber;
    private Long productId;
    private Integer productNumber;
}
