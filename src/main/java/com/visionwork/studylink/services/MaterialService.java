package com.visionwork.studylink.services;

import com.visionwork.studylink.dto.material.*;
import com.visionwork.studylink.models.material.Anotacao;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Transactional
    public List<MaterialReadDTO> listarMateriaisPublicos() {
        // Supondo que você tenha um repositório de Material
        List<Material> materiais = materialRepository.findAll();

        // Filtra apenas os materiais com visibilidade PUBLICO
        return materiais.stream()
                .filter(material -> material.getVisibilidade() == Visibilidade.PUBLICO)
                .map(material -> new MaterialReadDTO(material))
                .collect(Collectors.toList());
    }


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
    public List<MaterialReadDTO> listarMateriais() {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Busca todos os materiais criados pelo usuário autenticado
        List<Material> materiais = materialRepository.findByUsuario(principal);

        // Converte os materiais para DTOs e retorna a lista
        return materiais.stream()
                .map(MaterialReadDTO::new)
                .collect(Collectors.toList());
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
    public List<MaterialSearchDTO> pesquisarMaterial(String termoPesquisa) {
        if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
            return new ArrayList<>();
        }

        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Material> materials = materialRepository.findByTituloContainingIgnoreCaseOrAreaConhecimentoContainingIgnoreCase(
                termoPesquisa.trim(),
                termoPesquisa.trim()
        );

        return materials.stream()
                .filter(material -> material.getVisibilidade() == Visibilidade.PUBLICO)
                .map(MaterialSearchDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Material material = materialRepository.findByIdAndUsuario(id, principal).
                orElseThrow(() -> new AccessDeniedException("Você não tem permissão pra deletar essa tarefa"));
        materialRepository.delete(material);
    }

    @Transactional
    public AnotacaoReadDTO adicionarAnotacao(Long materialId, AnotacaoCreateDTO anotacaoCreateDTO) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Material material = materialRepository.findByIdAndUsuario(materialId, principal).
                orElseThrow(() -> new IllegalArgumentException("Você não pode alterar esse material"));

        Anotacao anotacao = new Anotacao.Builder()
                .titulo(anotacaoCreateDTO.titulo())
                .conteudo(anotacaoCreateDTO.conteudo())
                .material(material)
                .build();

        material.setAnotacao(anotacao);
        materialRepository.save(material);

        return new AnotacaoReadDTO(anotacao);
    }

    @Transactional
    public AnotacaoReadDTO buscarAnotacao(Long materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material não encontrado."));
        if (material.getAnotacao() == null) {
            throw new IllegalArgumentException("Nenhuma anotação associada a este material.");
        }
        return new AnotacaoReadDTO(material.getAnotacao());
    }


}
