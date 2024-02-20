package com.chenweijiang.wcg_mall.mapper;

import com.alibaba.fastjson.JSONPatch;
import com.chenweijiang.wcg_mall.annotation.AutoFill;
import com.chenweijiang.wcg_mall.enumeration.OperationType;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.dto.ProductFilterDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> filter(ProductFilterDTO productFilterDTO);

    @Insert("insert into product (create_time,update_time,create_user,update_user,name, category_id,brand_id,price, inventory, image, description,label,status) " +
            "values (#{createTime},#{updateTime},#{createUser},#{updateUser},#{name},#{categoryId},#{brandId},#{price},#{inventory},#{image},#{description},#{label},#{status})")
    @AutoFill(value = OperationType.INSERT)
    int addProduct(Product product);

    List<Product> list();

    @Delete("delete from product where id = #{id}")
    int deleteProductById(Long id);
    @AutoFill(value = OperationType.UPDATE)
    int updateProduct(Product product);

    @Update("update product set status = !status where id = #{id}")
    int stopOrStart(Long id);

    List<Product> userGetList();
    @Select("select * from product where id = #{id} and status = 1 order by id")
    Product getById(Long id);
    @Select("select * from product where id = #{id} order by id")
    Product getByIdNotStatus(Long id);
    @Insert("insert into user_wish_list (user_id,product_id) values (#{userId},#{id})")
    int addToWishList(Long userId, Long id);
}
