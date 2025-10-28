package com.chenweijiang.wcg_mall.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order{

    /**
     *  订单状态  1待付款 2待发货 3已发货 4已完成  5已取消
     */
    public static final Integer PENDING = 1;
    public static final Integer UN_DELIVERED = 2;
    public static final Integer DELIVERED = 3;
    public static final Integer COMPLETED = 4;
    public static final Integer CANCELED = 5;
    public static final Integer REFUNDED = 6;

    /**
     * 支付状态 0未支付 1已支付 2退款
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private Long id;
    private String orderNumber;
    private Integer status;
    private Long userId;
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
}
