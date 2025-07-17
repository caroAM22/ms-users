package com.pragma.plazacomida.infrastructure.output.adapter;

import com.pragma.plazacomida.domain.model.RolModel;
import com.pragma.plazacomida.domain.spi.IRolPersistencePort;
import com.pragma.plazacomida.infrastructure.output.entity.RolEntity;
import com.pragma.plazacomida.infrastructure.output.mapper.IRolEntityMapper;
import com.pragma.plazacomida.infrastructure.output.repository.IRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class RolJpaAdapter implements IRolPersistencePort {
    
    private final IRolRepository rolRepository;
    private final IRolEntityMapper rolEntityMapper;
    
    @Override
    public RolModel saveRol(RolModel rolModel) {
        RolEntity rolEntity = rolEntityMapper.toRolEntity(rolModel);
        RolEntity savedEntity = rolRepository.save(rolEntity);
        return rolEntityMapper.toRolModel(savedEntity);
    }
    
    @Override
    public List<RolModel> getAllRoles() {
        return rolRepository.findAll().stream()
                .map(rolEntityMapper::toRolModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<RolModel> getRolById(Long id) {
        return rolRepository.findById(id)
                .map(rolEntityMapper::toRolModel);
    }
    
    @Override
    public Optional<RolModel> getRolByNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .map(rolEntityMapper::toRolModel);
    }
    
    @Override
    public void deleteRolById(Long id) {
        rolRepository.deleteById(id);
    }
} 