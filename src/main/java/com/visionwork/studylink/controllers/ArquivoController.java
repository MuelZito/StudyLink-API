package com.visionwork.studylink.controllers;

import com.visionwork.studylink.models.arquivo.Arquivo;
import com.visionwork.studylink.services.ArquivoService;
import com.visionwork.studylink.dto.arquivo.ArquivoCreateDTO;
import com.visionwork.studylink.dto.arquivo.ArquivoReadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/arquivo")
public class ArquivoController {
    @Autowired
    private ArquivoService arquivoService;

    // Método para criar um arquivo
    @PostMapping("/criar")
    public ResponseEntity<Arquivo> criarArquivo(@RequestBody ArquivoCreateDTO arquivoDTO) {
        try {
            System.out.println("DTO recebido: " + arquivoDTO);

            // Criar o arquivo associando ao anotacaoId
            Arquivo arquivo = arquivoService.criarArquivo(
                    arquivoDTO.getConteudo(),
                    arquivoDTO.getAnotacaoId() // Garantir que o anotaionId seja associado corretamente
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(arquivo); // Retorna 201 Created
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Método para atualizar o conteúdo de um arquivo
    @PutMapping("/atualizar")
    public ResponseEntity<Arquivo> atualizarArquivo(@RequestBody AtualizarArquivoRequest request) {
        try {
            Arquivo arquivo = arquivoService.atualizarArquivo(request.getId(), request.getConteudo());
            return ResponseEntity.ok(arquivo); // Retorna 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/anotacao/{anotacaoId}")
    public ResponseEntity<List<ArquivoReadDTO>> buscarArquivosPorAnotacao(@PathVariable Long anotacaoId) {
        List<Arquivo> arquivos = arquivoService.buscarArquivosPorAnotacao(anotacaoId);

        // Se não houver arquivos, retorne uma lista vazia com status 200 OK
        if (arquivos.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // Retorna 200 OK com uma lista vazia
        }

        // Convertendo os arquivos para ArquivoReadDTO
        List<ArquivoReadDTO> arquivosDTO = arquivos.stream()
                .map(arquivo -> new ArquivoReadDTO(
                        arquivo.getId(),
                        arquivo.getConteudo(),
                        arquivo.getAnotacao().getId()))
                .toList();

        return ResponseEntity.ok(arquivosDTO); // Retorna 200 OK com a lista de arquivos
    }


    // DTO para atualização de arquivo
    public static class AtualizarArquivoRequest {
        private Long id;
        private String conteudo;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getConteudo() {
            return conteudo;
        }

        public void setConteudo(String conteudo) {
            this.conteudo = conteudo;
        }
    }
}
