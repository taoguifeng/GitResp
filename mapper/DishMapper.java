package com.happysnaker.mapper;

<<<<<<< HEAD
import com.happysnaker.pojo.Classification;
=======
>>>>>>> 06e8e2d0dc2bb8bcc0a6daa57c6b2f3e8eb0a21d
import com.happysnaker.pojo.Dish;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD

@Mapper
@Repository
public interface DishMapper {

    @Select("SELECT id FROM `dms_dish` WHERE name = #{name}")
    int getIdByName(String name);

    /**
     * 向 dms_dish 表中插入信息
     * @param dish
     * @return
     */
    int insertDish(Dish dish);

    int insertDishTag(@Param("dishId") int dishId, @Param("tag") String tag);

    int insertDishClassification(@Param("dishId") int dishId,@Param("cId") int cId);

    @Delete("DELETE FROM `dms_dish` WHERE id = #{dishId}")
    int deleteDish(int dishId);

    @Delete("DELETE FROM `dms_dish_tag` WHERE dish_id = #{dishId}")
    int deleteDishTag(int dishId);

    @Delete("DELETE FROM `dms_dish_classification` WHERE dish_id = #{dishId}")
    int deleteDishClassification(int dishId);

    @Delete("DELETE FROM `dms_dish_stock` WHERE dish_id = #{dishId}")
    int deleteDishStock(int dishId);
    /**
     * 查询菜品分类信息
     * @return
     */
    @Select("SELECT * FROM `dms_classification` WHERE id > 2")
    List<Classification> getClassificationList();

    @Select("SELECT id FROM dms_dish UNION SELECT id FROM combo")
    List<Integer> queryAllDishId();

    List<Dish> queryDishes();

    /**
     * 查询 菜品数目
     * @return
     */
    @Select("SELECT count(*) FROM `dms_dish`")
    int queryDishTotalSize();

    /**
     * 查询分页的菜品信息
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return
     */
    List<Dish> queryDishByPagination(@Param("offset") int offset, @Param("num") int num, @Param("keyword") String keyword);
    /**
     * 查询分页的折扣菜品信息
     * @return
     */
    List<Dish> getDiscountDishListByPagination(@Param("offset") int offset, @Param("num") int num, @Param("keyword") String keyword, @Param("type") Integer discountType);
=======
/**
 * @author Happysnaker
 * @description
 * @date 2021/10/22
 * @email happysnaker@foxmail.com
 */
@Mapper
@Repository
public interface DishMapper {
    /**
     * 获取所有菜品 ID，包括套餐
     * @return
     */
    @Select("SELECT id FROM dms_dish UNION SELECT id FROM dms_combo")
    List<Integer> queryAllDishId();

    /**
     * 查询 dish 表中所有的信息
     * @return
     */
    List<Dish> queryDishes();
>>>>>>> 06e8e2d0dc2bb8bcc0a6daa57c6b2f3e8eb0a21d

    /**
     * 查询指定菜品的信息，这个函数仅仅只会读取 dish 表中的信息
     * @param id 菜品ID
     * @return
     */
    Dish queryDishById(@Param("id") int id);

    /**
     * 获取数据库中 dish表 的全部菜品信息, 并连接 dish_tag, discount, dish_stock 表
     * @param storeId 店铺ID，查询库存需要指定店铺
     * @return 以链表的形式封装
     */
    List<Dish> queryDishInfo(@Param("storeId") int storeId);

    /**
<<<<<<< HEAD
=======
     * 获取热销榜单中的菜品信息，通过这个方法返回的 Dish 类只包括 dish 表中的全部信息 和 关联的 tag，不包含 discount 与 dish_stock 信息
     * @param n 表示获取前几的菜品，例如当 n = 10 时，则返回销售量前十的菜品
     * @return 以链表的形式封装
     */
    List<Dish> queryHotSaleDish(@Param("n") int n);

    /**
>>>>>>> 06e8e2d0dc2bb8bcc0a6daa57c6b2f3e8eb0a21d
     * 获取新品推荐榜单中的菜品信息，通过这个方法返回的 Dish 类只包括 dish 表中的全部信息 和 关联的 tag，不包含 discount 与 dish_stock 信息
     * @param n 表示获取前几的菜品，例如当 n = 10 时，则返回销售量前十的菜品
     * @return 以链表的形式封装
     */
    List<Dish> queryNewDish();

    /**
     * 通过dishID获取该菜品的标签信息
     * @param id 菜品的ID
     * @return 菜品的标签信息
     */
    List<String> queryDishTag(@Param("id") int id);

<<<<<<< HEAD
=======
    /**
     * 获取指定店铺中所有菜品的库存
     * @param storeId 店铺的ID
     * @return 返回一个Map，key代表菜品id，val是一个哈希表，其中可以通过 get("stock") 得到对应菜品id的库存量
     */
    @MapKey("dish_id")
    Map<Integer, Map> queryDishInventory(@Param("storeId") int storeId);

>>>>>>> 06e8e2d0dc2bb8bcc0a6daa57c6b2f3e8eb0a21d

    /**
     * 获取 queryClassification 表中的全部信息
     * @return key = 表列名, val = 值
     */
    List<Map<String, String>> queryClassification();


    /**
     * 获取指定店铺中指定菜品的库存量
     * @param dishId 菜品ID
     * @param storeId 店铺OD
     * @return 返回对应的库存量
     */
    Integer getTheDishInventory(@Param("storeId") int storeId, @Param("dishId") int dishId);

    /**
     * 查询店长推荐的菜品，通过这个方法返回的 Dish 类，除了 dish 表中的信息，还应该包括推荐指数和推荐理由、标签等信息
     * @return 菜品链表
     */
    List<Dish> queryRecommendedDish();


    /**
     * 增加或减少 dish 表中 指定菜品的销量
     * @param dishId 菜品ID
     * @param val 增加或者减少的值，当这个值为负时表示减少，所有的更改都是在原值上修改的
     * @return 影响的记录数，如果成功，则为1，否则为0
     */
<<<<<<< HEAD
    @Update("UPDATE dish SET sale = sale + #{val}")
=======
    @Update("UPDATE dms_dish SET sale = sale + #{val}")
>>>>>>> 06e8e2d0dc2bb8bcc0a6daa57c6b2f3e8eb0a21d
    int updateDishSale(@Param("dishId") int dishId, @Param("val") int val);

    /**
     * 向 dish_sale 表中插入一条日志记录
     * @param dishId 菜品ID
     * @param data 当前日期
     * @param storeId 店铺ID
     * @param val 单日销量
     * @return
     */
<<<<<<< HEAD
    @Insert("INSERT INTO dish_sale SELECT #{dishId}, #{data}, #{storeId}, #{val}")
    int insertDishSaleLog(@Param("dishId") int dishId, @Param("data")Timestamp data, @Param("storeId") int storeId, @Param("val") int val);
=======
    @Insert("INSERT INTO dms_dish_sale SELECT #{data}, #{storeId}, #{val}")
    int insertSaleLog(@Param("data")Timestamp data, @Param("storeId") int storeId, @Param("val") int val);
>>>>>>> 06e8e2d0dc2bb8bcc0a6daa57c6b2f3e8eb0a21d

    /**
     * 增加或减少指定店铺中指定菜品的喜欢数量
     * @param dishId 菜品ID
     * @param val 增加或者减少的值，当这个值为负时表示减少，所有的更改都是在原值上修改的
     * @return 影响的记录数，如果成功，则为1，否则为0
     */
    int updateDishFavoriteNum(@Param("dishId") int dishId, @Param("val") int val);

    /**
     * 增加或减少指定店铺中指定菜品的库存
     * @param storeId 店铺ID
     * @param dishId 菜品ID
<<<<<<< HEAD
     * @param newVal 新的值
     * @return 影响的记录数，如果成功，则为1，否则为0
     */
    int updateDishInventory(@Param("storeId") int storeId, @Param("dishId") int dishId, @Param("val") int newVal);
=======
     * @param val 增加或者减少的值，当这个值为负时表示减少，所有的更改都是在原值上修改的
     * @return 影响的记录数，如果成功，则为1，否则为0
     */
    int updateDishInventory(@Param("storeId") int storeId, @Param("dishId") int dishId, @Param("val") int val);

    /**
     * 乐观的扣减库存的方法
     * @param storeId
     * @param dishId
     * @param val 注意，此值必须是个正数，表示要扣减的数目
     * @return
     */
    int optimisticDeductInventory(@Param("storeId") int storeId, @Param("dishId") int dishId, @Param("val") int val);
>>>>>>> 06e8e2d0dc2bb8bcc0a6daa57c6b2f3e8eb0a21d

    /**
     * 当updateDishInventory方法调用失败时，此时数据库没有该记录，需要插入一条记录
     * @param storeId 店铺ID
     * @param dishId 菜品ID
     * @param val 初始值
     * @return 影响的记录数，如果成功，则为1，否则为0
     */
    int insertDishInventory(@Param("storeId") int storeId, @Param("dishId") int dishId, @Param("val") int val);
<<<<<<< HEAD

=======
>>>>>>> 06e8e2d0dc2bb8bcc0a6daa57c6b2f3e8eb0a21d
}
