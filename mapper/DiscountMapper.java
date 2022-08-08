package com.happysnaker.mapper;

import com.happysnaker.pojo.Discount;
<<<<<<< HEAD
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface DiscountMapper {
    @Insert(" INSERT INTO `mms_discount`(`dish_id`,`type`,`val`,`count`,`unit`) " +
            "SELECT #{dishId},#{type},#{val},#{count},#{unit}")
    int insertDiscount(Discount discount);

    @Delete("DELETE FROM `mms_discount` WHERE dish_id = #{dishId}")
    int deleteDiscount(int dishId);

    @Select("SELECT count(*) FROM `mms_discount`")
    int querySize();
=======
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Happysnaker
 * @description
 * @date 2021/10/22
 * @email happysnaker@foxmail.com
 */
@Mapper
public interface DiscountMapper {
    /**
     * 查询 discout 表中的所有信息
     * @return 将 Discount 类封装成链表
     */
    List<Discount> queryDiscountInfo();
>>>>>>> 06e8e2d0dc2bb8bcc0a6daa57c6b2f3e8eb0a21d
}
