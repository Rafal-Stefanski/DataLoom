package org.example.dataloom.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.api.service.ApiService;
import org.example.dataloom.repository.entity.DataLoomCatalogueEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/fruits")
    public ResponseEntity<List<String>> getAvailableFruits() {

        log.info("Received request for available fruits.");
        var fruits = apiService.getAvailableFruits();
        return ResponseEntity.status(200).body(fruits);
    }

    @GetMapping("/fruitqualityquantity")
    public Optional<DataLoomCatalogueEntity> getDataLoomCatalogueEntityByFruitTypeAndQualityGrade(
            String fruitType, String qualityGrade) {

        log.info("Received request for fruitType: {} and qualityGrade: {}", fruitType, qualityGrade);
        return apiService.findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(fruitType, qualityGrade);
    }

}
