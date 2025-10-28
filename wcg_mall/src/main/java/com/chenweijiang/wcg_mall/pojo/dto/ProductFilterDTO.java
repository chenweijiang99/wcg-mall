package com.chenweijiang.wcg_mall.pojo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFilterDTO {

    private String[] category;
    private Integer minPrice;
    private Integer maxPrice;
    private String[] brand;

}
