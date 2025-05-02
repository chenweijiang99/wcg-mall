package com.chenweijiang.wcg_mall.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CommentsVO implements Serializable {
    private Long id;
    private Long pid;//父评论id
    private Long fid;//关联id
    private String module;//模块
    private Long rootId;//根评论id
    private Long createUser;//评论人id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//评论时间
    private String comment;//评论内容
    private String createUserName;
    private String userImage;

}
