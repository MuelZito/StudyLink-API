package com.visionwork.studylink.controllers;

import com.visionwork.studylink.dto.anotacao.AnotacaoDTO;
import com.visionwork.studylink.services.AnotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class AnotacaoController {
    @Autowired
    private AnotacaoService anotacaoService;

    @PostMapping("/add/atividades")
    public ResponseEntity<AnotacaoDTO> criarAnotacao(
            @RequestParam("titulo") String titulo,
            @RequestParam("conteudo") String conteudo,
            @RequestParam(value = "arquivos", required = false) List<MultipartFile> arquivos
    ) throws IOException {
        AnotacaoDTO anotacaoDTO = new AnotacaoDTO();
        anotacaoDTO.setTitulo (titulo);
        anotacaoDTO.setConteudo(conteudo);

        AnotacaoDTO anotacaoCriada = anotacaoService.criarAnotacao(anotacaoDTO, arquivos);
        return ResponseEntity.ok(anotacaoCriada);
    }

    @GetMapping("/atividades")
    public ResponseEntity<List<AnotacaoDTO>> listarAnotacoes() {
        List<AnotacaoDTO> anotacoes = anotacaoService.listarAnotacoes();
        return ResponseEntity.ok(anotacoes);
    }

    @GetMapping("/atividades/{id}")
    public ResponseEntity<AnotacaoDTO> buscarAnotacao(@PathVariable Long id) {
        AnotacaoDTO anotacao = anotacaoService.buscarPorId(id);
        return ResponseEntity.ok(anotacao);
    }

    @DeleteMapping("/atividades/{id}")
    public ResponseEntity<Void> deletarAnotacao(@PathVariable Long id) {
        anotacaoService.deletarAnotacao(id);
        return ResponseEntity.noContent().build();
    }
}