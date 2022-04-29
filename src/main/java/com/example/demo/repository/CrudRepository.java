package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface internmédiaire pour les opération CRUD avec spécification
 * On ne veux pas d'implémentation (car elle est générique)
 * C'est l'utilité de @NoRepositoryBean qui indique à Spring de ne pas créer l'instance de cette interface
 * @param <E> le type de l'entité
 * @param <I> le type de l'ID
 */
@NoRepositoryBean
public interface CrudRepository<E, I> extends JpaRepository<E, I>, JpaSpecificationExecutor<E> {
}
