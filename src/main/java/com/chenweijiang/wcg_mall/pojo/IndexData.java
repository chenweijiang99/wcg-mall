package com.chenweijiang.wcg_mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexData {
    private  Long id;
    private String carouselImages;
    private String newProductImages;
    private String discountProductImages;
    private String officialCollection;
    private String newBlog;
}
