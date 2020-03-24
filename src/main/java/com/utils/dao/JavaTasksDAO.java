package com.utils.dao;

import java.util.List;

/**
 * An interface to work with data access
 *
 * @param <T> Type of model to work with
 */
public interface JavaTasksDAO<T> {

    /**
     * @param model Object to be inserted into database
     * @return Return <code>true</code> is successfully created; <code>false</code> if couldn't create the object
     */
    boolean create(T model);       // create


    /**
     * @param id Id of searched object
     * @return Object found by id or null if could not found
     */
    T findById(int id);            // read

    /**
     * @return List of all items found
     */
    List<T> findAll();             // read

    /**
     * @param model Model to be updated
     * @return <code>true</code> if successfully updated; <code>false</code> if couldn't update the object
     */
    boolean update(T model);       // update

    /**
     * @param model Model to be deleted
     * @return <code>true</code> if successfully deleted; <code>false</code> if couldn't delete the object
     */
    boolean delete(T model);       // delete
}
