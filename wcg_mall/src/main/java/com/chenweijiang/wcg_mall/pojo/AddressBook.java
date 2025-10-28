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
public class AddressBook  implements Serializable{
    public static final Integer DEFAULT = 1;
    public static final Integer NOT_DEFAULT = 0;
    private Long id;
    private Long userId;
    private String consignee;
    private String consigneeAddress;
    private String consigneePhone;
    private Integer Default;
}
