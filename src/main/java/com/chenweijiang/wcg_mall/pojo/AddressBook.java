package com.chenweijiang.wcg_mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook  {
    private Long id;
    private LocalDateTime createTime;
    private  LocalDateTime updateTime;
    private Long userId;
    private String consignee;
    private String consigneeAddress;
    private String consigneePhone;
}
