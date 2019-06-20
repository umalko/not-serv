package com.example.notificationservice.util;

import java.util.List;
import java.util.stream.Collectors;

public interface TransformBeanUtil<M, D> {

    D transformModelToDto(M model);

    M transformDtoToModel(D dto);

    default List<D> transformModelsToDtos(List<M> models) {
        return models.stream().map(this::transformModelToDto).collect(Collectors.toList());
    }

    default List<M> transformDtosToModels(List<D> dtos) {
        return dtos.stream().map(this::transformDtoToModel).collect(Collectors.toList());
    }
}
