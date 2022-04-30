package com.example.demo.service.impl;

import com.example.demo.dto.DefaultPagination;
import com.example.demo.repository.CrudRepository;
import com.example.demo.service.CrudBaseService;
import lombok.NoArgsConstructor;
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
 */
@Slf4j
@NoArgsConstructor
public abstract class CrudBaseServiceImpl<E, I> implements CrudBaseService<E, I> {

    protected abstract CrudRepository<E, I> getRepository();
    protected abstract DefaultPagination getDefaultPagination();

    @Override
    public Page<E> getEntities(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<E> specification) {
        DefaultPagination defaultPagination = getDefaultPagination();
        int pageNumberArg = Optional.ofNullable(pageNumber).orElse(defaultPagination.getPageNumber());
        int pageSizeArg = Optional.ofNullable(pageSize).orElse(defaultPagination.getPageSize());
        String sortedElementArg = Optional.ofNullable(sortedElement).orElse(defaultPagination.getSortElement());
        boolean sortedAscArg = Optional.ofNullable(sortedAsc).orElse(defaultPagination.isAsc());

        Sort sortType = sortedAscArg ? Sort.by(sortedElementArg).ascending() : Sort.by(sortedElementArg).descending(); // Tri ASC ou DESC ?
        Pageable page = PageRequest.of(pageNumberArg, pageSizeArg, sortType);
        return getRepository().findAll(specification, page);
    }

    @Override
    public void deleteOneById(I id) {
        getRepository().deleteById(id);
    }

    @Override
    public E save(E entity) {
        return getRepository().save(entity);
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
        return getRepository().findById(id).orElse(null);
    }
}
