package cn.zqmy.monkeytool.common.core;

import java.util.List;
import java.util.Map;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public interface IService<T> {
    /**
     * 保存
     *
     * @param model
     * @return boolean 保存是否成功结果：true：成功、false：失败
     */
    boolean save(T model);

    /**
     * 批量保存
     *
     * @param models
     * @return boolean 批量保存插入是否成功结果 true：成功、false：失败
     */
    boolean saveBatch(List<T> models);


    /**
     * 更新
     *
     * @param model
     * @return boolean 修改是否成功的结果 true：成功、false：失败
     */
    boolean update(T model);

    /**
     * 批量更新
     *
     * @param models
     * @return 批量更新是否成功的结果 true：成功、false：失败
     */
    boolean updateBatch(List<T> models);

    /**
     * 根据ID查询
     *
     * @param id
     * @return T
     */
    T getById(Long id);

    /**
     * 根据ID集合批量查询
     *
     * @param ids
     * @return
     */
    List<T> findByIds(List<Long> ids);

    /**
     * 通过 Model 条件查询
     *
     * @param t 指定泛型数据对象
     * @return List<T> 指定泛型数据对象集合
     */
    List<T> find(T t);

    /**
     * 通过 Model 条件查询
     *
     * @param t 指定泛型数据对象
     * @return T 指定单个泛型数据对象
     */
    T findOne(T t);

    /**
     * 根据Map条件查询
     *
     * @param map 查看条件与值得 Map 信息
     * @return List<T> 指定泛型数据对象集合
     */
    List<T> findByMap(Map<String, Object> map);

    /**
     * 查询所有
     *
     * @return List<T> 指定泛型数据对象集合
     */
    List<T> find();

    /**
     * 通过主键删除
     *
     * @param id
     * @return boolean 删除是否成功的结果 true：成功、false：失败
     */
    boolean deleteById(Long id);

    /**
     * 通过主键集合批量刪除
     *
     * @param ids
     * @return boolean 批量删除是否成功的结果 true：成功、false：失败
     */
    boolean deleteByIds(List<Long> ids);

}
