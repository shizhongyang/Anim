package com.bo.dblibrary.db;

/**
 * Created by TT on 2017-11-09.
 */

public interface IBaseDao<T> {

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    Long insert(T entity);

    /**
     * 修改数据
     *
     * @param entity 条件
     * @param where  数值
     * @return
     */
    int update(T entity, T where);

}
