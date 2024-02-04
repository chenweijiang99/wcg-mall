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
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户身份 1:普通用户  2：卖家  3：管理员
     */
    public static final Integer USER = 1;
    public static final Integer SELLER = 2;
    public static final Integer ADMIN = 3;

    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private Integer status;
}
