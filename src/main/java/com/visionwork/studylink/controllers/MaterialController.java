package com.visionwork.studylink.controllers;


import com.visionwork.studylink.dto.material.MaterialCreateDTO;
import com.visionwork.studylink.dto.material.MaterialReadDTO;
import com.visionwork.studylink.dto.material.MaterialSearchDTO;
import com.visionwork.studylink.dto.material.MaterialUpdateDTO;
import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.services.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MaterialController {

    @Autowired
    MaterialService materialService;
    private Material material;

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
    public ResponseEntity<List<MaterialSearchDTO>> pesquisarMaterial(@RequestParam String titulo) {

        List<MaterialSearchDTO> material = materialService.pesquisarMaterial(titulo);
        return ResponseEntity.ok(material);
    }

    @DeleteMapping("/materiais/{id}")
    public ResponseEntity<String> deletarMaterial(@PathVariable Long id){
        materialService.deleteById(id);
        return ResponseEntity.ok("Material deletado com sucesso!");
    }

}
