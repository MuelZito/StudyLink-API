package com.visionwork.studylink.services;

import com.visionwork.studylink.dto.material.MaterialCreateDTO;
import com.visionwork.studylink.dto.material.MaterialReadDTO;
import com.visionwork.studylink.dto.material.MaterialSearchDTO;
import com.visionwork.studylink.dto.material.MaterialUpdateDTO;
import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.models.material.Visibilidade;
import com.visionwork.studylink.models.tarefa.Tarefa;
import com.visionwork.studylink.models.usuario.Usuario;
import com.visionwork.studylink.repositories.MaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Transactional
    public MaterialReadDTO criarMaterial(MaterialCreateDTO materialCreateDTO) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Material material = new Material.Builder()
                .titulo(materialCreateDTO.titulo())
                .areaConhecimento(materialCreateDTO.areaConhecimento())
                .imagemBanner(materialCreateDTO.imagemBanner())
                .visibilidade(materialCreateDTO.visibilidade())
                .usuario(principal)
                .build();
        material = materialRepository.save(material);

        return new MaterialReadDTO(material);
    }

    @Transactional
    public MaterialReadDTO atualizarMaterial(Long id, MaterialUpdateDTO materialUpdateDTO) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Material material = materialRepository.findByIdAndUsuario(id, principal).
                orElseThrow(() -> new AccessDeniedException("Você não tem permissão para alterar esse Material"));
        material.update(materialUpdateDTO);
        return new MaterialReadDTO(material);
    }

    @Transactional
    public List<MaterialSearchDTO> pesquisarMaterial(String titulo) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Material> materials = materialRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .filter(material -> material.getVisibilidade() == Visibilidade.PUBLICO)
                .collect(Collectors.toList());
        return materials.stream()
                .map(MaterialSearchDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id){
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Material material = materialRepository.findByIdAndUsuario(id, principal).
                orElseThrow(() -> new AccessDeniedException("Você não tem permissão pra deletar essa tarefa"));
        materialRepository.delete(material);
    }

}
