package com.violetfreesia.mybatisplusplus;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.property.PropertyCopier;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author violetfreesia
 * @date 2021-02-26
 */
public class IllogicalMethods {
    /**
     * 修改表信息中的逻辑删除模式
     *
     * @param tableInfo 原表信息对象
     * @return 返回修改后的新的表信息对象
     */
    private static TableInfo setWithLogicDeleteToFalse(TableInfo tableInfo) {
        Class<TableInfo> tableInfoClass = TableInfo.class;
        TableInfo target = new TableInfo(tableInfo.getEntityType());
        PropertyCopier.copyBeanProperties(tableInfoClass, tableInfo, target);
        try {
            Field field = tableInfoClass.getDeclaredField("withLogicDelete");
            field.setAccessible(true);
            field.set(target, false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static IllogicalDeleteMethod deleteByIdIllogicalMethod() {
        return new IllogicalDeleteMethod(IllogicalSqlMethod.DELETE_BY_ID_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        tableInfo.getTableName(), tableInfo.getKeyColumn(),
                        tableInfo.getKeyProperty()), Object.class);
    }

    public static IllogicalDeleteMethod deleteByMapIllogicalMethod() {
        return new IllogicalDeleteMethod(IllogicalSqlMethod.DELETE_BY_MAP_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        tableInfo.getTableName(), illogicalMethod.sqlWhereByMap(tableInfo)),
                Map.class);
    }

    public static IllogicalDeleteMethod deleteIllogicalMethod() {
        return new IllogicalDeleteMethod(IllogicalSqlMethod.DELETE_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        tableInfo.getTableName(), illogicalMethod.sqlWhereEntityWrapper(true, tableInfo),
                        illogicalMethod.sqlComment()));
    }

    public static IllogicalDeleteMethod deleteBatchIdsIllogicalMethod() {
        return new IllogicalDeleteMethod(IllogicalSqlMethod.DELETE_BATCH_BY_IDS_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        tableInfo.getTableName(), tableInfo.getKeyColumn(), SqlScriptUtils
                                .convertForeach("#{item}", Constants.COLLECTION,
                                        null, "item", Constants.COMMA)), Object.class);
    }

    public static IllogicalUpdateMethod updateByIdIllogicalMethod() {
        return new IllogicalUpdateMethod(IllogicalSqlMethod.UPDATE_BY_ID_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> {
                    final String additional = illogicalMethod.optlockVersion(tableInfo) +
                            tableInfo.getLogicDeleteSql(true, true);
                    return String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                            illogicalMethod.sqlSet(tableInfo.isWithLogicDelete(), false,
                                    tableInfo, false, Constants.ENTITY, Constants.ENTITY_DOT),
                            tableInfo.getKeyColumn(), Constants.ENTITY_DOT + tableInfo.getKeyProperty(), additional);
                });
    }

    public static IllogicalUpdateMethod updateIllogicalMethod() {
        return new IllogicalUpdateMethod(IllogicalSqlMethod.UPDATE_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                        illogicalMethod.sqlSet(true, true, tableInfo,
                                true, Constants.ENTITY, Constants.ENTITY_DOT),
                        illogicalMethod.sqlWhereEntityWrapper(true, tableInfo), illogicalMethod.sqlComment()));
    }

    public static IllogicalSelectForTableMethod selectByIdIllogicalMethod() {
        return new IllogicalSelectForTableMethod(IllogicalSqlMethod.SELECT_BY_ID_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        illogicalMethod.sqlSelectColumns(tableInfo, false),
                        tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty(),
                        tableInfo.getLogicDeleteSql(true, true)), Object.class);
    }

    public static IllogicalSelectForTableMethod selectByMapIllogicalMethod() {
        return new IllogicalSelectForTableMethod(IllogicalSqlMethod.SELECT_BY_MAP_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        illogicalMethod.sqlSelectColumns(tableInfo, false),
                        tableInfo.getTableName(), illogicalMethod.sqlWhereByMap(tableInfo)), Map.class);
    }

    public static IllogicalSelectForTableMethod selectBatchIdsIllogicalMethod() {
        return new IllogicalSelectForTableMethod(IllogicalSqlMethod.SELECT_BATCH_BY_IDS_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        illogicalMethod.sqlSelectColumns(tableInfo, false),
                        tableInfo.getTableName(), tableInfo.getKeyColumn(),
                        SqlScriptUtils.convertForeach("#{item}", Constants.COLLECTION,
                                null, "item", Constants.COMMA),
                        tableInfo.getLogicDeleteSql(true, true)), Object.class);
    }

    public static IllogicalSelectForTableMethod selectOneIllogicalMethod() {
        return new IllogicalSelectForTableMethod(IllogicalSqlMethod.SELECT_ONE_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        illogicalMethod.sqlFirst(), illogicalMethod.sqlSelectColumns(tableInfo, true),
                        tableInfo.getTableName(), illogicalMethod.sqlWhereEntityWrapper(true, tableInfo),
                        illogicalMethod.sqlComment()));
    }

    public static IllogicalSelectForTableMethod selectListIllogicalMethod() {
        return new IllogicalSelectForTableMethod(IllogicalSqlMethod.SELECT_LIST_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(), illogicalMethod.sqlFirst(),
                        illogicalMethod.sqlSelectColumns(tableInfo, true), tableInfo.getTableName(),
                        illogicalMethod.sqlWhereEntityWrapper(true, tableInfo),
                        illogicalMethod.sqlComment()));
    }

    public static IllogicalSelectForTableMethod selectPageIllogicalMethod() {
        return new IllogicalSelectForTableMethod(IllogicalSqlMethod.SELECT_PAGE_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        illogicalMethod.sqlFirst(), illogicalMethod.sqlSelectColumns(tableInfo, true),
                        tableInfo.getTableName(), illogicalMethod.sqlWhereEntityWrapper(true, tableInfo),
                        illogicalMethod.sqlComment()));
    }

    public static IllogicalSelectForOtherMethod selectCountIllogicalMethod() {
        return new IllogicalSelectForOtherMethod(IllogicalSqlMethod.SELECT_COUNT_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(), illogicalMethod.sqlFirst(),
                        illogicalMethod.sqlCount(), tableInfo.getTableName(),
                        illogicalMethod.sqlWhereEntityWrapper(true, tableInfo),
                        illogicalMethod.sqlComment()), Integer.class);
    }

    public static IllogicalSelectForOtherMethod selectMapsIllogicalMethod() {
        return new IllogicalSelectForOtherMethod(IllogicalSqlMethod.SELECT_MAPS_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(), illogicalMethod.sqlFirst(),
                        illogicalMethod.sqlSelectColumns(tableInfo, true), tableInfo.getTableName(),
                        illogicalMethod.sqlWhereEntityWrapper(true, tableInfo),
                        illogicalMethod.sqlComment()), Map.class);
    }

    public static IllogicalSelectForOtherMethod selectMapsPageIllogicalMethod() {
        return new IllogicalSelectForOtherMethod(IllogicalSqlMethod.SELECT_MAPS_PAGE_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        illogicalMethod.sqlFirst(), illogicalMethod.sqlSelectColumns(tableInfo, true),
                        tableInfo.getTableName(), illogicalMethod.sqlWhereEntityWrapper(true, tableInfo),
                        illogicalMethod.sqlComment()), Map.class);
    }

    public static IllogicalSelectForOtherMethod selectObjsIllogicalMethod() {
        return new IllogicalSelectForOtherMethod(IllogicalSqlMethod.SELECT_OBJS_ILLOGICAL,
                (tableInfo, sqlMethod, illogicalMethod) -> String.format(sqlMethod.getSql(),
                        illogicalMethod.sqlFirst(), illogicalMethod.sqlSelectObjsColumns(tableInfo),
                        tableInfo.getTableName(), illogicalMethod.sqlWhereEntityWrapper(true, tableInfo),
                        illogicalMethod.sqlComment()), Object.class);
    }

    @FunctionalInterface
    private interface SqlBuilder {
        /**
         * 构建sql
         *
         * @param tableInfo       表信息
         * @param sqlMethod       sql模板
         * @param illogicalMethod 非逻辑删除方法对象
         * @return 构建好的sql
         */
        String build(TableInfo tableInfo, IllogicalSqlMethod sqlMethod, IllogicalAbstractMethod illogicalMethod);
    }

    private abstract static class IllogicalAbstractMethod extends AbstractMethod {

        protected IllogicalSqlMethod sqlMethod;

        protected SqlBuilder getSql;

        protected Class<?> sqlSourceParameterType;

        private IllogicalAbstractMethod(IllogicalSqlMethod sqlMethod, SqlBuilder getSql) {
            this.sqlMethod = sqlMethod;
            this.getSql = getSql;
        }

        private IllogicalAbstractMethod(IllogicalSqlMethod sqlMethod, SqlBuilder getSql, Class<?> sqlSourceParameterType) {
            this.sqlMethod = sqlMethod;
            this.getSql = getSql;
            this.sqlSourceParameterType = sqlSourceParameterType;
        }

        public Class<?> getSqlSourceParameterType(Class<?> modelClass) {
            return sqlSourceParameterType == null ? modelClass : sqlSourceParameterType;
        }

        @Override
        protected String sqlLogicSet(TableInfo table) {
            return super.sqlLogicSet(table);
        }

        @Override
        protected String sqlSet(boolean logic, boolean ew, TableInfo table, boolean judgeAliasNull, String alias, String prefix) {
            return super.sqlSet(logic, ew, table, judgeAliasNull, alias, prefix);
        }

        @Override
        protected String sqlComment() {
            return super.sqlComment();
        }

        @Override
        protected String sqlFirst() {
            return super.sqlFirst();
        }

        @Override
        protected String sqlSelectColumns(TableInfo table, boolean queryWrapper) {
            return super.sqlSelectColumns(table, queryWrapper);
        }

        @Override
        protected String sqlCount() {
            return super.sqlCount();
        }

        @Override
        protected String sqlSelectObjsColumns(TableInfo table) {
            return super.sqlSelectObjsColumns(table);
        }

        @Override
        protected String sqlWhereByMap(TableInfo table) {
            return super.sqlWhereByMap(table);
        }

        @Override
        protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
            return super.sqlWhereEntityWrapper(newLine, table);
        }

        @Override
        protected String filterTableFieldInfo(List<TableFieldInfo> fieldList, Predicate<TableFieldInfo> predicate, Function<TableFieldInfo, String> function, String joiningVal) {
            return super.filterTableFieldInfo(fieldList, predicate, function, joiningVal);
        }

        @Override
        protected String optlockVersion(TableInfo tableInfo) {
            return super.optlockVersion(tableInfo);
        }
    }

    private static class IllogicalDeleteMethod extends IllogicalAbstractMethod {

        public IllogicalDeleteMethod(IllogicalSqlMethod sqlMethod, SqlBuilder getSql) {
            super(sqlMethod, getSql);
        }

        public IllogicalDeleteMethod(IllogicalSqlMethod sqlMethod, SqlBuilder getSql,
                                     Class<?> sqlSourceParameterType) {
            super(sqlMethod, getSql, sqlSourceParameterType);
        }


        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            tableInfo = setWithLogicDeleteToFalse(tableInfo);
            String sql = getSql.build(tableInfo, sqlMethod, this);
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql,
                    getSqlSourceParameterType(modelClass));
            return this.addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
        }
    }

    private static class IllogicalUpdateMethod extends IllogicalAbstractMethod {
        public IllogicalUpdateMethod(IllogicalSqlMethod sqlMethod, SqlBuilder getSql) {
            super(sqlMethod, getSql);
        }

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            tableInfo = setWithLogicDeleteToFalse(tableInfo);
            String sql = getSql.build(tableInfo, sqlMethod, this);
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql,
                    getSqlSourceParameterType(modelClass));
            return this.addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
        }
    }

    private static class IllogicalSelectForTableMethod extends IllogicalAbstractMethod {
        public IllogicalSelectForTableMethod(IllogicalSqlMethod sqlMethod, SqlBuilder getSql) {
            super(sqlMethod, getSql);
        }

        public IllogicalSelectForTableMethod(IllogicalSqlMethod sqlMethod, SqlBuilder getSql, Class<?> sqlSourceParameterType) {
            super(sqlMethod, getSql, sqlSourceParameterType);
        }

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            tableInfo = setWithLogicDeleteToFalse(tableInfo);
            String sql = getSql.build(tableInfo, sqlMethod, this);
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql,
                    getSqlSourceParameterType(modelClass));
            return this.addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(),
                    sqlSource, tableInfo);
        }
    }

    private static class IllogicalSelectForOtherMethod extends IllogicalAbstractMethod {
        private final Class<?> resultType;

        public IllogicalSelectForOtherMethod(IllogicalSqlMethod sqlMethod, SqlBuilder getSql, Class<?> resultType) {
            super(sqlMethod, getSql);
            this.resultType = resultType;

        }

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            tableInfo = setWithLogicDeleteToFalse(tableInfo);
            String sql = getSql.build(tableInfo, sqlMethod, this);
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql,
                    getSqlSourceParameterType(modelClass));
            return this.addSelectMappedStatementForOther(mapperClass, sqlMethod.getMethod(),
                    sqlSource, resultType);
        }
    }

    public enum IllogicalSqlMethod {
        /**
         * 删除
         */
        DELETE_BY_ID_ILLOGICAL("deleteByIdIllogical", SqlMethod.DELETE_BY_ID),
        DELETE_BY_MAP_ILLOGICAL("deleteByMapIllogical", SqlMethod.DELETE_BY_MAP),
        DELETE_ILLOGICAL("deleteIllogical", SqlMethod.DELETE),
        DELETE_BATCH_BY_IDS_ILLOGICAL("deleteBatchIdsIllogical", SqlMethod.DELETE_BATCH_BY_IDS),

        /**
         * 修改
         */
        UPDATE_BY_ID_ILLOGICAL("updateByIdIllogical", SqlMethod.UPDATE_BY_ID),
        UPDATE_ILLOGICAL("updateIllogical", SqlMethod.UPDATE),


        /**
         * 查询
         */
        SELECT_BY_ID_ILLOGICAL("selectByIdIllogical", SqlMethod.SELECT_BY_ID),
        SELECT_BY_MAP_ILLOGICAL("selectByMapIllogical", SqlMethod.SELECT_BY_MAP),
        SELECT_BATCH_BY_IDS_ILLOGICAL("selectBatchIdsIllogical", SqlMethod.SELECT_BATCH_BY_IDS),
        SELECT_ONE_ILLOGICAL("selectOneIllogical", SqlMethod.SELECT_ONE),
        SELECT_COUNT_ILLOGICAL("selectCountIllogical", SqlMethod.SELECT_COUNT),
        SELECT_LIST_ILLOGICAL("selectListIllogical", SqlMethod.SELECT_LIST),
        SELECT_PAGE_ILLOGICAL("selectPageIllogical", SqlMethod.SELECT_PAGE),
        SELECT_MAPS_ILLOGICAL("selectMapsIllogical", SqlMethod.SELECT_MAPS),
        SELECT_MAPS_PAGE_ILLOGICAL("selectMapsPageIllogical", SqlMethod.SELECT_MAPS_PAGE),
        SELECT_OBJS_ILLOGICAL("selectObjsIllogical", SqlMethod.SELECT_OBJS);

        private final String method;

        private final SqlMethod relatedMethod;

        IllogicalSqlMethod(String method, SqlMethod sqlMethod) {
            this.method = method;
            this.relatedMethod = sqlMethod;
        }

        public String getMethod() {
            return method;
        }

        public String getSql() {
            return relatedMethod.getSql();
        }
    }
}
