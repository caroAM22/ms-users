package com.pragma.plazacomida.infrastructure.output.adapter;

import com.pragma.plazacomida.domain.model.ObjectModel;
import com.pragma.plazacomida.domain.spi.IObjectPersistencePort;
import com.pragma.plazacomida.infrastructure.output.entity.ObjectEntity;
import com.pragma.plazacomida.infrastructure.output.mapper.IObjectEntityMapper;
import com.pragma.plazacomida.infrastructure.output.repository.IObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ObjectJpaAdapter implements IObjectPersistencePort {
    
    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;
    
    @Override
    public ObjectModel saveObject(ObjectModel objectModel) {
        ObjectEntity objectEntity = objectEntityMapper.toEntity(objectModel);
        ObjectEntity savedEntity = objectRepository.save(objectEntity);
        return objectEntityMapper.toModel(savedEntity);
    }
    
    @Override
    public List<ObjectModel> getAllObjects() {
        List<ObjectEntity> objectEntities = objectRepository.findAll();
        return objectEntityMapper.toModelList(objectEntities);
    }
    

} 