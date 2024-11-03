package org.example.dataloom.api.service;

import lombok.RequiredArgsConstructor;
import org.example.dataloom.messaging.dto.FruitType;
import org.example.dataloom.messaging.dto.QualityGrade;
import org.example.dataloom.repository.DataLoomRepository;
import org.example.dataloom.repository.entity.DataLoomCatalogueEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final DataLoomRepository dataLoomRepository;

    public List<String> getAvailableFruits() {
    return dataLoomRepository.findAll().stream()
            .map(dataLoomCatalogueEntity -> String.format("Available: Fruit Type: %s, Quality Grade: %s, Quantity: %d",
                    dataLoomCatalogueEntity.getFruitType(),
                    dataLoomCatalogueEntity.getQuality(),
                    dataLoomCatalogueEntity.getAvailableKg()))
            .distinct()
            .toList();
    }

    public Optional<DataLoomCatalogueEntity> findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(
            String fruitType, String qualityGrade) {
        return dataLoomRepository.findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(
                FruitType.valueOf(fruitType), QualityGrade.valueOf(qualityGrade));
    }

}
