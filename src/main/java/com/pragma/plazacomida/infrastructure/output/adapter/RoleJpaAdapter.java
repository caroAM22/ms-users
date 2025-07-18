package com.pragma.plazacomida.infrastructure.output.adapter;

import com.pragma.plazacomida.domain.model.RoleModel;
import com.pragma.plazacomida.domain.spi.IRolePersistencePort;
import com.pragma.plazacomida.infrastructure.output.entity.RoleEntity;
import com.pragma.plazacomida.infrastructure.output.mapper.IRoleEntityMapper;
import com.pragma.plazacomida.infrastructure.output.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {
    
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    
    @Override
    public Optional<RoleModel> findByName(String name) {
        return roleRepository.findByName(name)
                .map(roleEntityMapper::toRoleModel);
    }
    
    @Override
    public Optional<Long> findIdByName(String name) {
        return roleRepository.findIdByName(name);
    }
} 