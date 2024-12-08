package com.visionwork.studylink.controllers;

import com.visionwork.studylink.dto.anotacao.AnotacaoCreateDTO;
import com.visionwork.studylink.dto.anotacao.AnotacaoReadDTO;
import com.visionwork.studylink.services.AnotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class AnotacaoController {

    @Autowired
    private AnotacaoService anotacaoService;

    @PostMapping("/atividades")
    public ResponseEntity<?> criarAnotacao(
            @RequestBody AnotacaoCreateDTO anotacaoCreateDTO
    ) {
        try {
            // Validate input
            if (anotacaoCreateDTO.titulo() == null || anotacaoCreateDTO.idMaterial() == null) {
                return ResponseEntity.badRequest().body("Título e ID do material são obrigatórios");
            }

            AnotacaoReadDTO anotacaoCriada = anotacaoService.criarAnotacao(anotacaoCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(anotacaoCriada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar anotação");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAnotacao(
            @PathVariable Long id,
            @RequestBody AnotacaoCreateDTO dto) {
        try {
            AnotacaoReadDTO anotacaoAtualizada = anotacaoService.atualizarAnotacao(id, dto);
            return ResponseEntity.ok(anotacaoAtualizada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar anotação");
        }
    }

    @GetMapping("/materiais/{materialId}/atividades")
    public ResponseEntity<?> listarAnotacoesPorMaterial(@PathVariable Long materialId) {
        try {
            List<AnotacaoReadDTO> anotacoes = anotacaoService.listarAnotacoesPorMaterial(materialId);
            return ResponseEntity.ok(anotacoes);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar anotações");
        }
    }

    @DeleteMapping("atividades/{id}")
    public ResponseEntity<?> deletarAnotacao(@PathVariable Long id) {
        try {
            anotacaoService.deletarAnotacao(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar anotação");
        }
    }
}