package com.pragma.plazacomida.domain.usecase;

import com.pragma.plazacomida.domain.api.IUsuarioServicePort;
import com.pragma.plazacomida.domain.model.UsuarioModel;
import com.pragma.plazacomida.domain.spi.IUsuarioPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UsuarioUseCase implements IUsuarioServicePort {
    
    private final IUsuarioPersistencePort usuarioPersistencePort;
    
    @Override
    public UsuarioModel saveUsuario(UsuarioModel usuarioModel) {
        return usuarioPersistencePort.saveUsuario(usuarioModel);
    }
    
    @Override
    public List<UsuarioModel> getAllUsuarios() {
        return usuarioPersistencePort.getAllUsuarios();
    }
    
    @Override
    public Optional<UsuarioModel> getUsuarioById(Long id) {
        return usuarioPersistencePort.getUsuarioById(id);
    }
    
    @Override
    public Optional<UsuarioModel> getUsuarioByCorreo(String correo) {
        return usuarioPersistencePort.getUsuarioByCorreo(correo);
    }
    
    @Override
    public Optional<UsuarioModel> getUsuarioByNumeroDocumento(String numeroDocumento) {
        return usuarioPersistencePort.getUsuarioByNumeroDocumento(numeroDocumento);
    }
    
    @Override
    public void deleteUsuarioById(Long id) {
        usuarioPersistencePort.deleteUsuarioById(id);
    }
    
    @Override
    public boolean existsByCorreo(String correo) {
        return usuarioPersistencePort.existsByCorreo(correo);
    }
    
    @Override
    public boolean existsByNumeroDocumento(String numeroDocumento) {
        return usuarioPersistencePort.existsByNumeroDocumento(numeroDocumento);
    }
} 