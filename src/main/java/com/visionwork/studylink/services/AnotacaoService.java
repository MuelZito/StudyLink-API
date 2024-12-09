        package com.visionwork.studylink.services;

        import com.visionwork.studylink.dto.anotacao.AnotacaoCreateDTO;
        import com.visionwork.studylink.dto.anotacao.AnotacaoReadDTO;
        import com.visionwork.studylink.models.anotacao.Anotacao;
        import com.visionwork.studylink.models.material.Material;
        import com.visionwork.studylink.repositories.AnotacaoRepository;
        import com.visionwork.studylink.repositories.MaterialRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;
        import java.util.NoSuchElementException;
        import java.util.stream.Collectors;

        @Service
        public class AnotacaoService {

            @Autowired
            private AnotacaoRepository anotacaoRepository;

            @Autowired
            private MaterialRepository materialRepository;

            @Transactional
            public AnotacaoReadDTO criarAnotacao(AnotacaoCreateDTO dto) {
                Material material = materialRepository.findById(dto.materialId())
                        .orElseThrow(() -> new NoSuchElementException("Material não encontrado"));

                Anotacao anotacao = new Anotacao.Builder()
                        .material(material)
                        .titulo(dto.titulo())
                        .dataUltimaAlteracao(dto.dataUltimaAlteracao())
                        .build();

                Anotacao anotacaoSalva = anotacaoRepository.save(anotacao);

                return new AnotacaoReadDTO(anotacaoSalva);
            }

            @Transactional
            public AnotacaoReadDTO atualizarAnotacao(Long id, AnotacaoCreateDTO dto) {
                Anotacao anotacao = anotacaoRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Anotação não encontrada"));

                // Atualizar apenas título e data de última alteração
                anotacao.setTitulo(dto.titulo());
                anotacao.setDataUltimaAlteracao(dto.dataUltimaAlteracao());

                return new AnotacaoReadDTO(anotacaoRepository.save(anotacao));
            }

            @Transactional(readOnly = true)
            public List<AnotacaoReadDTO> listarAnotacoesPorMaterial(Long materialId) {
                materialRepository.findById(materialId)
                        .orElseThrow(() -> new NoSuchElementException("Material não encontrado"));

                return anotacaoRepository.findByMaterialId(materialId).stream()
                        .map(AnotacaoReadDTO::new)
                        .collect(Collectors.toList());
            }

            @Transactional
            public void deletarAnotacao(Long id) {
                Anotacao anotacao = anotacaoRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Anotação não encontrada"));

                anotacaoRepository.delete(anotacao);
            }
        }