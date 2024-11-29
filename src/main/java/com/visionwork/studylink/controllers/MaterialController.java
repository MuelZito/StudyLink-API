package com.visionwork.studylink.controllers;


import com.visionwork.studylink.dto.material.*;
import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.services.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MaterialController {

    @Autowired
    MaterialService materialService;
    @GetMapping( value = "materiais/descobrir")
    public ResponseEntity<List<MaterialReadDTO>> listarMateriaisPublicos() {
        List<MaterialReadDTO> materiaispublicos = materialService.listarMateriaisPublicos();
        System.out.println("publicos " + materiaispublicos);
        return ResponseEntity.ok(materiaispublicos);
    }

    @GetMapping(value = "/materiais")
    public ResponseEntity<List<MaterialReadDTO>> listarMateriais() {
        List<MaterialReadDTO> materiais = materialService.listarMateriais();
        return ResponseEntity.ok(materiais);
    }

    @PostMapping(value = "/materiais/add")
    public ResponseEntity<MaterialReadDTO> criarMaterial(@RequestBody MaterialCreateDTO material) {
        MaterialReadDTO criarMaterial = materialService.criarMaterial(material);
        return ResponseEntity.ok(criarMaterial);
    }

    @PutMapping(value = "/materiais/{id}")
    public ResponseEntity<MaterialReadDTO> atualizarMaterial(@PathVariable Long id, @RequestBody MaterialUpdateDTO materialUpdateDTO) {
        MaterialReadDTO materialAtualizado = materialService.atualizarMaterial(id, materialUpdateDTO);
        return ResponseEntity.ok(materialAtualizado);
    }
    @GetMapping("/materiais/pesquisar")
    public ResponseEntity<List<MaterialSearchDTO>> pesquisarMaterial(
            @RequestParam(required = false) String termoPesquisa) {

        List<MaterialSearchDTO> materiais = materialService.pesquisarMaterial(termoPesquisa);
        return ResponseEntity.ok(materiais);
    }

    @DeleteMapping("/materiais/{id}")
    public ResponseEntity<String> deletarMaterial(@PathVariable Long id){
        materialService.deleteById(id);
        return ResponseEntity.ok("Material deletado com sucesso!");
    }

    @PostMapping("/{material_id}/anotacao")
    public ResponseEntity<AnotacaoReadDTO> adicionarAnotacao(@PathVariable("material_id") Long materialId,@RequestBody AnotacaoCreateDTO anotacaoCreateDTO){
        AnotacaoReadDTO anotacao = materialService.adicionarAnotacao(materialId, anotacaoCreateDTO);
        return ResponseEntity.ok(anotacao);
    }

    @GetMapping("/{materialId}/anotacao")
    public ResponseEntity<AnotacaoReadDTO> buscarAnotacao(@PathVariable("material_id") Long materialId) {
        AnotacaoReadDTO anotacao = materialService.buscarAnotacao(materialId);
        return ResponseEntity.ok(anotacao);
    }


}
