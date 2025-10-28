package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface AddressMapper {
    @Select("select * from address_book where user_id = #{userId} order by id")
    List<AddressBook> listByUserId(Long userId);
    @Insert("insert into address_book (user_id,consignee,consignee_address,consignee_phone,`default`) values (#{userId},#{consignee},#{consigneeAddress},#{consigneePhone},#{default})")
    void add(AddressBook addressBook);
    @Update("update address_book set consignee = #{consignee},consignee_address = #{consigneeAddress},consignee_phone = #{consigneePhone},`default` = #{default} where id = #{id}")
    void update(AddressBook addressBook);
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);
}
