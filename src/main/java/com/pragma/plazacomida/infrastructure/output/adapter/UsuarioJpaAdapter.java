package com.pragma.plazacomida.infrastructure.output.adapter;

import com.pragma.plazacomida.domain.model.UsuarioModel;
import com.pragma.plazacomida.domain.spi.IUsuarioPersistencePort;
import com.pragma.plazacomida.infrastructure.output.entity.UsuarioEntity;
import com.pragma.plazacomida.infrastructure.output.mapper.IUsuarioEntityMapper;
import com.pragma.plazacomida.infrastructure.output.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {
    
    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;
    
    @Override
    public UsuarioModel saveUsuario(UsuarioModel usuarioModel) {
        UsuarioEntity usuarioEntity = usuarioEntityMapper.toUsuarioEntity(usuarioModel);
        UsuarioEntity savedEntity = usuarioRepository.save(usuarioEntity);
        return usuarioEntityMapper.toUsuarioModel(savedEntity);
    }
    
    @Override
    public List<UsuarioModel> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuarioEntityMapper::toUsuarioModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<UsuarioModel> getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioEntityMapper::toUsuarioModel);
    }
    
    @Override
    public Optional<UsuarioModel> getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(usuarioEntityMapper::toUsuarioModel);
    }
    
    @Override
    public Optional<UsuarioModel> getUsuarioByNumeroDocumento(String numeroDocumento) {
        return usuarioRepository.findByNumeroDocumento(numeroDocumento)
                .map(usuarioEntityMapper::toUsuarioModel);
    }
    
    @Override
    public void deleteUsuarioById(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }
    
    @Override
    public boolean existsByNumeroDocumento(String numeroDocumento) {
        return usuarioRepository.existsByNumeroDocumento(numeroDocumento);
    }
} 