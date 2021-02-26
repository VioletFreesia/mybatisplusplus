package com.violetfreesia.mybatisplusplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

/**
 * @author violetfreesia
 * @date 2021-02-26
 */
public class IllogicalBaseServiceImpl<M extends IllogicalBaseMapper<T>, T>
        extends ServiceImpl<M, T> implements IllogicalBaseService<T> {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatchByIdIllogical(Collection<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(IllogicalMethods.IllogicalSqlMethod.UPDATE_BY_ID_ILLOGICAL);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    @Override
    public T getOneIllogical(Wrapper<T> queryWrapper, boolean throwEx) {
        if (throwEx) {
            return baseMapper.selectOneIllogical(queryWrapper);
        }
        return SqlHelper.getObject(log, baseMapper.selectListIllogical(queryWrapper));
    }

    @Override
    public Map<String, Object> getMapIllogical(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(log, baseMapper.selectMapsIllogical(queryWrapper));
    }


    /**
     * 获取mapperStatementId
     *
     * @param sqlMethod 方法名
     * @return 命名id
     * @since 3.4.0
     */
    protected String getSqlStatement(IllogicalMethods.IllogicalSqlMethod sqlMethod) {
        return mapperClass.getName() + StringPool.DOT + sqlMethod.getMethod();
    }
}
