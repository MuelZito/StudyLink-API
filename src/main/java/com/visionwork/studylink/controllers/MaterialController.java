package com.visionwork.studylink.controllers;


import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class MaterialController {

    @Autowired
    MaterialService materialService;

    @PostMapping("/material/{usuarioId}")
    public ResponseEntity<Material> criarMaterial(@PathVariable Long usuarioId, @RequestBody Material material) {
        Material novoMaterial = materialService.adicionarMaterialParaUsuario(usuarioId, material);
        return ResponseEntity.ok(novoMaterial);
    }
}
