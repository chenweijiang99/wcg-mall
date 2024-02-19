package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.annotation.AutoFill;
import com.chenweijiang.wcg_mall.enumeration.OperationType;
import com.chenweijiang.wcg_mall.pojo.ProductBrand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<ProductBrand> list();

    @AutoFill(value = OperationType.UPDATE)
    @Update("update product_brand set name = #{name},update_time = #{updateTime},update_user=#{updateUser} where id = #{id}")
    int update(ProductBrand productBrand);
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into product_brand (create_time,update_time,create_user,update_user,name) values (#{createTime},#{updateTime},#{createUser},#{updateUser},#{name})")
    int add(ProductBrand productBrand);
    @Delete("delete from product_brand where id = #{id}")
    int  deleteById(Long id);
}
