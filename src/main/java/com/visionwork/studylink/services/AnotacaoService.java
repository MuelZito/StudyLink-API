package com.visionwork.studylink.services;

import com.visionwork.studylink.dto.anotacao.AnotacaoDTO;
import com.visionwork.studylink.models.anotacao.Anotacao;
import com.visionwork.studylink.repositories.AnotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnotacaoService {
    @Autowired
    private AnotacaoRepository anotacaoRepository;

    @Autowired
    private ArquivoService arquivoService;

    public AnotacaoDTO criarAnotacao(AnotacaoDTO dto, List<MultipartFile> arquivos) throws IOException {
        Anotacao anotacao = new Anotacao();
        anotacao.setTitulo(dto.getTitulo());
        anotacao.setConteudo(dto.getConteudo());

        if (arquivos != null && !arquivos.isEmpty()) {
            for (MultipartFile arquivo : arquivos) {
                Anotacao.Arquivo arquivoSalvo = arquivoService.salvarArquivo(arquivo);
                anotacao.adicionarArquivo(arquivoSalvo);
            }
        }

        Anotacao anotacaoSalva = anotacaoRepository.save(anotacao);
        return AnotacaoDTO.fromEntity(anotacaoSalva);
    }

    public List<AnotacaoDTO> listarAnotacoes() {
        return anotacaoRepository.findAll().stream()
                .map(AnotacaoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AnotacaoDTO> listarPorMaterial(Long materialId) {
        List<Anotacao> anotacoes = anotacaoRepository.findByMaterialId(materialId);
        return anotacoes.stream()
                .map(AnotacaoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public AnotacaoDTO buscarPorId(Long id) {
        Anotacao anotacao = anotacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anotação não encontrada"));
        return AnotacaoDTO.fromEntity(anotacao);
    }

    public void deletarAnotacao(Long id) {
        anotacaoRepository.deleteById(id);
    }
}