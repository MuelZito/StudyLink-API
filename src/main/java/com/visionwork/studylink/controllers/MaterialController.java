package com.visionwork.studylink.controllers;


import com.visionwork.studylink.dto.material.MaterialCreateDTO;
import com.visionwork.studylink.dto.material.MaterialReadDTO;
import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.services.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class MaterialController {

    @Autowired
    MaterialService materialService;


    @PostMapping(value = "/materiais")
    public ResponseEntity<MaterialReadDTO> criarMaterial(@RequestBody MaterialCreateDTO material){
        MaterialReadDTO criarMaterial = materialService.criarMaterial(material);
        return ResponseEntity.ok(criarMaterial);
    }
}
