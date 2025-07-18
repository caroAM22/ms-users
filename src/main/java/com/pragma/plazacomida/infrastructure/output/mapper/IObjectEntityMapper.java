package com.pragma.plazacomida.infrastructure.output.mapper;

import com.pragma.plazacomida.domain.model.ObjectModel;
import com.pragma.plazacomida.infrastructure.output.entity.ObjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IObjectEntityMapper {
    ObjectEntity toEntity(ObjectModel objectModel);
    ObjectModel toModel(ObjectEntity objectEntity);
    List<ObjectModel> toModelList(List<ObjectEntity> objectEntityList);
} 