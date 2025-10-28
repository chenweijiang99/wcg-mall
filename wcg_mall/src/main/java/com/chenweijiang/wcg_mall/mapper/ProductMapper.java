package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.annotation.AutoFill;
import com.chenweijiang.wcg_mall.enumeration.OperationType;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.dto.ProductDetailDTO;
import com.chenweijiang.wcg_mall.pojo.dto.ProductFilterDTO;
import com.chenweijiang.wcg_mall.pojo.dto.ProductPageDTO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> filter(ProductFilterDTO productFilterDTO);


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
    @Select("select p.id as id," +
            "p.name as name," +
            "pc.name as categoryName," +
            "pb.name as brandName," +
            "p.price as price," +
            "p.inventory as inventory," +
            "p.image as image," +
            "p.detail_images as detailImages," +
            "p.description  as description, " +
            "p.description_image  as descriptionImage " +
            "from product as p,product_category as pc,product_brand as pb where p.id = #{id} and p.status = 1 and p.category_id = pc.id and p.brand_id = pb.id order by id")
    ProductDetailDTO getById(Long id);
    @Select("select * from product where id = #{id} order by id")
    Product getByIdNotStatus(Long id);
    @Insert("insert into user_wish_list (user_id,product_id) values (#{userId},#{id})")
    int addToWishList(Long userId, Long id);
    @Select("select * from product where id = #{id}")
    Product getProductById(Long id);

    @Update("update product set inventory = inventory - #{productNumber} where id = #{id}")
    void decreaseProductInventory(Long id, Integer productNumber);
    @Select("SELECT * FROM product WHERE name LIKE #{searchQuery}")
    List<Product> search(String searchQuery);

    List<Product> usergetListPage(String searchQuery);

//    @Select("select * from product where status = 1")
    List<Product> selectAll(ProductPageDTO productPageDTO);
}
