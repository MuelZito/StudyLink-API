package com.visionwork.studylink.services;

import com.visionwork.studylink.models.arquivo.Arquivo;
import com.visionwork.studylink.models.anotacao.Anotacao;
import com.visionwork.studylink.repositories.ArquivoRepository;
import com.visionwork.studylink.repositories.AnotacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArquivoService {
    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private AnotacaoRepository anotacaoRepository;

    @Transactional
    public Arquivo criarArquivo(String conteudo, Long anotacaoId) {
        // Busca a anotação
        Anotacao anotacao = anotacaoRepository.findById(anotacaoId)
                .orElseThrow(() -> new RuntimeException("Anotação não encontrada"));

        // Verifica se já existe um arquivo para esta anotação
        List<Arquivo> arquivosExistentes = arquivoRepository.findByAnotacaoId(anotacaoId);

        Arquivo arquivo;
        if (!arquivosExistentes.isEmpty()) {
            // Se já existe, atualiza o conteúdo do primeiro arquivo
            arquivo = arquivosExistentes.get(0);
            arquivo.setConteudo(conteudo);
        } else {
            // Se não existe, cria um novo
            arquivo = new Arquivo();
            arquivo.setConteudo(conteudo);
            arquivo.setAnotacao(anotacao);
        }

        return arquivoRepository.save(arquivo);
    }

    @Transactional
    public Arquivo atualizarArquivo(Long id, String novoConteudo) {
        Arquivo arquivo = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo não encontrado"));

        arquivo.setConteudo(novoConteudo);
        return arquivoRepository.save(arquivo);
    }

    @Transactional(readOnly = true)
    public Optional<Arquivo> buscarArquivoPorId(Long id) {
        return arquivoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Arquivo> buscarArquivosPorAnotacao(Long anotacaoId) {
        return arquivoRepository.findByAnotacaoId(anotacaoId);  // Retorna a lista de arquivos associados à anotação
    }


    @Transactional
    public void deletarArquivo(Long id) {
        Arquivo arquivo = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo não encontrado"));

        arquivoRepository.delete(arquivo);
    }
}