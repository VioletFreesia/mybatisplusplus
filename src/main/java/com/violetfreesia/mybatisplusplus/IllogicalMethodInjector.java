package com.violetfreesia.mybatisplusplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.Arrays;
import java.util.List;

/**
 * 全局配置逻辑删除的情况下
 * 非逻辑删除操作方法注入
 *
 * @author violetfreesia
 * @date 2021-02-25
 */
public class IllogicalMethodInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        if (Arrays.asList(mapperClass.getInterfaces()).contains(IllogicalBaseMapper.class)) {
            methodList.addAll(newMethodList());
        }
        return methodList;
    }

    private List<AbstractMethod> newMethodList() {
        return Arrays.asList(
                IllogicalMethods.deleteIllogicalMethod(),
                IllogicalMethods.deleteByIdIllogicalMethod(),
                IllogicalMethods.deleteBatchIdsIllogicalMethod(),
                IllogicalMethods.deleteByMapIllogicalMethod(),
                IllogicalMethods.updateIllogicalMethod(),
                IllogicalMethods.updateByIdIllogicalMethod(),
                IllogicalMethods.selectBatchIdsIllogicalMethod(),
                IllogicalMethods.selectByIdIllogicalMethod(),
                IllogicalMethods.selectByMapIllogicalMethod(),
                IllogicalMethods.selectCountIllogicalMethod(),
                IllogicalMethods.selectListIllogicalMethod(),
                IllogicalMethods.selectMapsIllogicalMethod(),
                IllogicalMethods.selectMapsPageIllogicalMethod(),
                IllogicalMethods.selectOneIllogicalMethod(),
                IllogicalMethods.selectObjsIllogicalMethod(),
                IllogicalMethods.selectPageIllogicalMethod());
    }
}
