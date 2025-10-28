package com.chenweijiang.wcg_mall.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private String name;
    private Long categoryId;
    private Long brandId;
    private BigDecimal price;
    private Integer inventory;
    private String image;
    private String detailImages;
    private String description;
    private String descriptionImage;
    private String label;
    private Integer status;

    //非数据库字段
    private String[] searchNames;
    private Integer[] category;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer[] brand;
}
