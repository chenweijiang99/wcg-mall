package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.KeyPairs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KeyPairsMapper {
    @Insert("insert into key_pairs (user_id, public_key, private_key) values (#{userId}, #{publicKey}, #{privateKey})")
    void addKeyPairs(KeyPairs keyPairs);
    @Select("select private_key from key_pairs where user_id = #{userId}")
    String getPrivateKey(Long userId);
     @Select("select public_key from key_pairs where user_id = #{userId}")
    String getPublicKey(Long userId);
}
