package com.example.demo.service;

import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

/**
 * Interface de base pour les services proposant diverse méthodes CRUD
 * @param <E> le type de l'entité
 * @param <I> le type de l'ID
 */
public interface CrudBaseService<E, I> {
    Page<E> getEntities(Integer pageNumber, Integer pageSize, String sortedElement, Boolean sortedAsc, Specification<E> specification);
    void deleteOneById(I id);
    E save(E entity);
    @Named("findOneById") // Peut être utilisé par Mapstruct pour fetch une entité avec un id via (@Mapping(qualifiedByName="findOneById"))
    E findOneById(I id);
}
