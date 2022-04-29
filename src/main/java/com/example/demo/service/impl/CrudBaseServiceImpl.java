package com.example.demo.service.impl;

import com.example.demo.repository.CrudRepository;
import com.example.demo.service.CrudBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;

/**
 * Implémentation de base pour les services offrant des opérations CRUD
 * @param <E> le type de l'entité
 * @param <I> le type de l'ID
 * @param <R> le type du répository (permet aux classes de bases d'avoir accès aux méthodes spécifiques du répository sans cast
 */
@Slf4j
@RequiredArgsConstructor
public abstract class CrudBaseServiceImpl<E, I, R extends CrudRepository<E, I>> implements CrudBaseService<E, I> {
    protected final R repository;
    protected final String defaultElementSort;

    @Override
    public Page<E> getEntities(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<E> specification) {
        int pageNumberArg = Optional.ofNullable(pageNumber).orElse(0);
        int pageSizeArg = Optional.ofNullable(pageSize).orElse(50);
        String sortedElementArg = Optional.ofNullable(sortedElement).orElse(defaultElementSort);
        boolean sortedAscArg = Optional.ofNullable(sortedAsc).orElse(true);

        Sort sortType = sortedAscArg ? Sort.by(sortedElementArg).ascending() : Sort.by(sortedElementArg).descending(); // Tri ASC ou DESC ?
        Pageable page = PageRequest.of(pageNumberArg, pageSizeArg, sortType);
        return repository.findAll(specification, page);
    }

    @Override
    public void deleteOneById(I id) {
        repository.deleteById(id);
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    /**
     * Pour une IHM, une méthode qui lance des exceptions avec un message serait plus adapté
     * En plus la gestion du null s'arreterait ici
     * @param id l'id de l'entité souhaité
     * @return l'entité ou null si non trouvé
     */
    @Override
    public E findOneById(I id) {
        if(id == null){ return null; }
        return repository.findById(id).orElse(null);
    }
}
