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
public class ProductNew implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDateTime createTime;
    private Long createUser;
    private Long productId;
}
