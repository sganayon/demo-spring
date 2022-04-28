package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

public interface CrudBaseService<E, I> {
    Page<E> getMembers(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<E> specification);
    E findOneById(I id);
    void deleteOneById(I id);
    E save(E entity);
}
