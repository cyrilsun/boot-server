package com.mrsun.bootserver.service.mapper;

import java.util.List;

/**
 * base entity mapper
 * @param <D>
 * @param <E>
 */
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
