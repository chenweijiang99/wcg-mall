package com.chenweijiang.wcg_mall.pojo.dto;

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
public class ProductDetailDTO implements Serializable {
    private Long id;
    private String name;
    private String categoryName;
    private String brandName;
    private BigDecimal price;
    private Integer inventory;
    private String image;
    private String detailImages;
    private String description;
    private String descriptionImage;
}
