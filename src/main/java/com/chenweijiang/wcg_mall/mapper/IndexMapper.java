package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.OfficialCollection;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.vo.OfficialCollectionVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IndexMapper {
    @Insert("insert into official_collection(product_id) values (#{id})")
    void setProductAsOfficialCollection(Long id);
    @Delete("delete from official_collection where product_id = #{id}")
    void unSetProductAsOfficialCollection(Long id);
    @Select("select * from official_collection")
    List<OfficialCollection> getOL();

    @Select("select * from official_collection where product_id = #{id}")
    OfficialCollection getOLById(Long id);
    @Select("select o.id as id," +
            "p.id as productId," +
            "p.name as productName," +
            "p.image as productImage" +
            " from official_collection as o,product as p where o.product_id = p.id")
    List<OfficialCollectionVO> userGetOL();

    @Select("select * from product where status = 1 order by create_time limit 6")
    List<Product> getNewProduct();
}
