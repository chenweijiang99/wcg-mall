package com.chenweijiang.wcg_mall.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 枳枳
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPageDTO {
    private String[] searchTitles;
    private String[] authorNames;
}
