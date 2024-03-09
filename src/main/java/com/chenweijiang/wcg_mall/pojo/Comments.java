package com.chenweijiang.wcg_mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    private Long id;
    private Long blogId;
    private Long createUser;
    private LocalDateTime createTime;
    private String comment;
}
