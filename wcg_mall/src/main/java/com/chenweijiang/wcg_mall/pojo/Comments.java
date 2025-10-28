package com.chenweijiang.wcg_mall.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    private Long id;
    private Long pid;//父评论id
    private Long fid;//关联id
    private String module;//模块
    private Long rootId;//根评论id
    private Long createUser;//评论人id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//评论时间
    private String comment;//评论内容

    private String userName;
    private String userAvatar;
    private String parentUserName;  // 回复的父级的用户名称

    // 根节点下面的所有子评论数组
    private List<Comments> children;

}
