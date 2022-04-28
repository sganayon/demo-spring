package com.example.demo.service.impl;

import com.example.demo.repository.CrudRepository;
import com.example.demo.service.CrudBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class CrudBaseServiceImpl<E, I> implements CrudBaseService<E, I> {
    protected final CrudRepository<E, I> repository;
    protected final String defaultElementSort;

    @Override
    public Page<E> getMembers(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<E> specification) {
        int pageNumberArg = Optional.ofNullable(pageNumber).orElse(0);
        int pageSizeArg = Optional.ofNullable(pageSize).orElse(50);
        String sortedElementArg = Optional.ofNullable(sortedElement).orElse(defaultElementSort);
        boolean sortedAscArg = Optional.ofNullable(sortedAsc).orElse(true);

        Sort sortType = sortedAscArg ? Sort.by(sortedElementArg).ascending() : Sort.by(sortedElementArg).descending(); // Tri ASC ou DESC ?
        Pageable page = PageRequest.of(pageNumberArg, pageSizeArg, sortType);
        return repository.findAll(specification, page);
    }

    @Override
    public E findOneById(I id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No member with id: "+id));
    }

    @Override
    public void deleteOneById(I id) {
        repository.deleteById(id);
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }
}
