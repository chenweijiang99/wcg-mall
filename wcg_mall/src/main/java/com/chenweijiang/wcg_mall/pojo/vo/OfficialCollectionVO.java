package com.chenweijiang.wcg_mall.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficialCollectionVO implements Serializable {
    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
}
