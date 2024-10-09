package com.visionwork.studylink.services;

import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.models.usuario.Usuario;
import com.visionwork.studylink.repositories.MaterialRepository;
import com.visionwork.studylink.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Material adicionarMaterial(Long usuarioId, Material material) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        material.setUsuario(usuario);
        return materialRepository.save(material);
    }
}
