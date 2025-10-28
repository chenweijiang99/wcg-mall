package com.chenweijiang.wcg_mall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class User  {
    /**
     * 默认密码：123456
     */
    public static final String DEFAULT_PASSWORD = "123456";
    /**
     * 状态 1：已激活 0：待激活
     */
    public static final Integer ACTIVATED = 1;
    public static final Integer UN_ACTIVATED = 0;
    /**
     * 用户身份 1:普通用户  2：卖家  3：管理员
     */
    public static final Integer USER = 1;
    public static final Integer SELLER = 2;
    public static final Integer ADMIN = 3;

    private Long id;
    private String socialId;
    private String type;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String username;
    private String nickName;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private Integer status;
    private Integer state;
}
