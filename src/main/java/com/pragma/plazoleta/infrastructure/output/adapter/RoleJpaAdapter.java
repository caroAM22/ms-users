package com.pragma.plazoleta.infrastructure.output.adapter;

import com.pragma.plazoleta.domain.exception.RoleNotFoundException;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.infrastructure.output.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {
    
    private final IRoleRepository roleRepository;
    
    @Override
    public UUID findIdByName(String roleName) {
        return roleRepository.findIdByName(roleName)
                .map(UUID::fromString)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
    }
    

} 