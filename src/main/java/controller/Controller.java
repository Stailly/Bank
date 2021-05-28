package controller;

import java.util.List;

public interface Controller<T> {

    T get(int id);

    List<T> getAll();

    void save(T t);

    void delete(T t);
}
