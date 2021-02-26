package com.violetfreesia.mybatisplusplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author violetfreesia
 * @date 2021-02-26
 */
public interface IllogicalBaseService<T> extends IService<T> {

    /**
     * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     *
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    default boolean updateIllogical(Wrapper<T> updateWrapper) {
        return updateIllogical(null, updateWrapper);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    default boolean updateIllogical(T entity, Wrapper<T> updateWrapper) {
        return SqlHelper.retBool(getBaseMapper().updateIllogical(entity, updateWrapper));
    }

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    default boolean updateByIdIllogical(T entity) {
        return SqlHelper.retBool(getBaseMapper().updateByIdIllogical(entity));
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean updateBatchByIdIllogical(Collection<T> entityList) {
        return updateBatchByIdIllogical(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    boolean updateBatchByIdIllogical(Collection<T> entityList, int batchSize);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    default T getByIdIllogical(Serializable id) {
        return getBaseMapper().selectByIdIllogical(id);
    }

    /**
     * 根据 Wrapper，查询一条记录 <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default T getOneIllogical(Wrapper<T> queryWrapper) {
        return getOneIllogical(queryWrapper, true);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    T getOneIllogical(Wrapper<T> queryWrapper, boolean throwEx);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    Map<String, Object> getMapIllogical(Wrapper<T> queryWrapper);


    /**
     * 查询所有
     *
     * @see Wrappers#emptyWrapper()
     */
    default List<T> listIllogical() {
        return listIllogical(Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default List<T> listIllogical(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectListIllogical(queryWrapper);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    default List<T> listByIdsIllogical(Collection<? extends Serializable> idList) {
        return getBaseMapper().selectBatchIdsIllogical(idList);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    default List<T> listByMapIllogical(Map<String, Object> columnMap) {
        return getBaseMapper().selectByMapIllogical(columnMap);
    }

    /**
     * 查询所有列表
     *
     * @see Wrappers#emptyWrapper()
     */
    default List<Map<String, Object>> listMapsIllogical() {
        return listMapsIllogical(Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default List<Map<String, Object>> listMapsIllogical(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectMapsIllogical(queryWrapper);
    }

    /**
     * 查询全部记录
     */
    default List<Object> listObjsIllogical() {
        return listObjsIllogical(Function.identity());
    }

    /**
     * 查询全部记录
     *
     * @param mapper 转换函数
     */
    default <V> List<V> listObjsIllogical(Function<? super Object, V> mapper) {
        return listObjsIllogical(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default List<Object> listObjsIllogical(Wrapper<T> queryWrapper) {
        return listObjsIllogical(queryWrapper, Function.identity());
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param mapper       转换函数
     */
    default <V> List<V> listObjsIllogical(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return getBaseMapper().selectObjsIllogical(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default <E extends IPage<T>> E pageIllogical(E page, Wrapper<T> queryWrapper) {
        return getBaseMapper().selectPageIllogical(page, queryWrapper);
    }

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default <E extends IPage<T>> E pageIllogical(E page) {
        return pageIllogical(page, Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default <E extends IPage<Map<String, Object>>> E pageMapsIllogical(E page, Wrapper<T> queryWrapper) {
        return getBaseMapper().selectMapsPageIllogical(page, queryWrapper);
    }

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default <E extends IPage<Map<String, Object>>> E pageMapsIllogical(E page) {
        return pageMapsIllogical(page, Wrappers.emptyWrapper());
    }

    /**
     * 查询总记录数
     *
     * @see Wrappers#emptyWrapper()
     */
    default int countIllogical() {
        return countIllogical(Wrappers.emptyWrapper());
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default int countIllogical(Wrapper<T> queryWrapper) {
        return SqlHelper.retCount(getBaseMapper().selectCountIllogical(queryWrapper));
    }


    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default boolean removeIllogical(Wrapper<T> queryWrapper) {
        return SqlHelper.retBool(getBaseMapper().deleteIllogical(queryWrapper));
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    default boolean removeByIdIllogical(Serializable id) {
        return SqlHelper.retBool(getBaseMapper().deleteByIdIllogical(id));
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    default boolean removeByMapIllogical(Map<String, Object> columnMap) {
        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
        return SqlHelper.retBool(getBaseMapper().deleteByMapIllogical(columnMap));
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    default boolean removeByIdsIllogical(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteBatchIdsIllogical(idList));
    }


    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    @Override
    IllogicalBaseMapper<T> getBaseMapper();
}
