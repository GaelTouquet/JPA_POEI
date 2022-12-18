package org.example.interfaces;

import java.util.List;

public interface IDao<T> {

    void begin();

    boolean create(T o);

    boolean update(T o);

    boolean delete(T o);

    T findById(int id);

    void envoie();

    void close();

    //Exercice 2
    List<T> findAll();

}