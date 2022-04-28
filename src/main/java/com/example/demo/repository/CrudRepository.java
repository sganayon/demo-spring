package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrudRepository<E, I> extends JpaRepository<E, I>, JpaSpecificationExecutor<E> {
}
