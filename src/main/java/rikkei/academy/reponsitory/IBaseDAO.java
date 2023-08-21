package rikkei.academy.reponsitory;

import org.springframework.stereotype.Component;

import java.util.List;


public interface IBaseDAO<T,E> {
	List<T> findAll();
	void save(T t);
	void delete(E id);
	T findById(E id);
}
