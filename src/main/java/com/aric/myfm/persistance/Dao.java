package com.aric.myfm.persistance;

import java.util.List;

import com.aric.myfm.domain.Entity;


public interface Dao<T extends Entity, I> {

	List<T> findAll();


	T find(I id);


	T save(T entry);


	void delete(I id);

}
