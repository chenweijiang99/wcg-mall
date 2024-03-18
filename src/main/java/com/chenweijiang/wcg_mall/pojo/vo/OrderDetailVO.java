package com.chenweijiang.wcg_mall.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailVO implements Serializable {
    private String orderNumber;
    private Integer status;
    private String consignee;
    private String consigneeAddress;
    private String consigneePhone;
    private String email;
    private Integer payMethod;
    private Integer payStatus;
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkoutTime;
    private List<OrderDetailProductList> productLists;
}
