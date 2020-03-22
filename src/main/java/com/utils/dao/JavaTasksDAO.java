package com.utils.dao;

import java.util.List;

public interface JavaTasksDAO<T> {

    boolean create(T model);       // create

    T findById(int id);            // read

    List<T> findAll();             // read

    boolean update(T model);       // update

    boolean delete(T model);       // delete
}
