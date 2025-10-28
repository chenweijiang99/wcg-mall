package com.chenweijiang.wcg_mall.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyPairs  {
    private Long id;
    private Long userId;
    private String publicKey;
    private String privateKey;
}
