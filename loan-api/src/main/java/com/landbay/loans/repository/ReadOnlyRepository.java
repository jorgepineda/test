package com.landbay.loans.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID> {

    T findOne(final ID id);

    List<T> findAll();

    boolean exists(final ID id);

    long count();
}
