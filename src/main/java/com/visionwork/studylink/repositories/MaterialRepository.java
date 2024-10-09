package com.visionwork.studylink.repositories;

import com.visionwork.studylink.models.material.Anotacao;
import com.visionwork.studylink.models.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
