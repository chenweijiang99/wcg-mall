package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.annotation.AutoFill;
import com.chenweijiang.wcg_mall.enumeration.OperationType;
import com.chenweijiang.wcg_mall.pojo.ProductCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<ProductCategory> list();

    @AutoFill(value = OperationType.UPDATE)
    int update( ProductCategory productCategory);

    @Insert("insert into product_category (name,create_time,update_time,create_user,update_user) values (#{name},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    int add(ProductCategory productCategory);

    @Delete("delete from product_category where id = #{id}")
    int deleteById(Long id);
}
