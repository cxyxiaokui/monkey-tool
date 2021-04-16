package cn.zqmy.monkeytool.mybatis.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.zqmy.monkeytool.common.core.IMapper;
import cn.zqmy.monkeytool.common.core.IService;

import java.util.List;
import java.util.Map;

/**
 * 服务层的基础方法抽象
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public abstract class BaseService<T> implements IService<T> {

    /**
     * 具体的Service实现并指定具体执行的DAO
     *
     * @return 具体实现 Mybaties DAO
     */
    public abstract IMapper<T> getMapper();

    /**
     * 查询所有
     *
     * @return List<T> 指定泛型数据对象集合
     */
    @Override
    public List<T> find() {
        return this.getMapper().find();
    }

    /**
     * 通过 Model 条件查询
     *
     * @param t 指定泛型数据对象
     * @return List<T> 指定泛型数据对象集合
     */
    @Override
    public List<T> find(T t) {
        Map<String, Object> map = BeanUtil.beanToMap(t);
        return this.getMapper().findByMap(map);
    }

    /**
     * 通过 Model 条件查询
     *
     * @param t 指定泛型数据对象
     * @return T 指定单个泛型数据对象
     */
    @Override
    public T findOne(T t) {
        List<T> list = this.find(t);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 根据Map条件查询
     *
     * @param map 查看条件与值得 Map 信息
     * @return List<T> 指定泛型数据对象集合
     */
    @Override
    public List<T> findByMap(Map<String, Object> map) {
        return this.getMapper().findByMap(map);
    }


    /**
     * 根据ID查询
     *
     * @param id
     * @return T
     */
    @Override
    public T getById(Long id) {
        return this.getMapper().getById(id);
    }

    /**
     * 根据ID集合批量查询
     *
     * @param ids
     * @return
     */
    @Override
    public List<T> findByIds(List<Long> ids) {
        return this.getMapper().findByIds(ids);
    }

    /**
     * 保存
     *
     * @param t
     * @return boolean 保存是否成功结果：true：成功、false：失败
     */
    @Override
    public boolean save(T t) {
        return this.getMapper().save(t) > 0;
    }

    /**
     * 批量保存
     *
     * @param list
     * @return boolean 批量保存插入是否成功结果 true：成功、false：失败
     */
    @Override
    public boolean saveBatch(List<T> list) {
        return this.getMapper().saveBatch(list) > 0;
    }

    /**
     * 更新
     *
     * @param t
     * @return boolean 修改是否成功的结果 true：成功、false：失败
     */
    @Override
    public boolean update(T t) {
        return this.getMapper().update(t) > 0;
    }

    /**
     * 通过主键删除
     *
     * @param id
     * @return boolean 删除是否成功的结果 true：成功、false：失败
     */
    @Override
    public boolean deleteById(Long id) {
        return this.getMapper().deleteById(id) > 0;
    }

    /**
     * 通过主键集合批量刪除
     *
     * @param ids
     * @return boolean 批量删除是否成功的结果 true：成功、false：失败
     */
    @Override
    public boolean deleteByIds(List<Long> ids) {
        return this.getMapper().deleteByIds(ids) > 0;
    }

    /**
     * 批量更新
     *
     * @param list
     * @return 批量更新是否成功的结果 true：成功、false：失败
     */
    @Override
    public boolean updateBatch(List<T> list) {
        return this.getMapper().updateBatch(list) > 0;
    }
}
