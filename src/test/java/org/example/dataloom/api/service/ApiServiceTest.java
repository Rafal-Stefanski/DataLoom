package org.example.dataloom.api.service;

import org.example.dataloom.messaging.fixtures.DataLoomCatalogueEntityFixture;
import org.example.dataloom.repository.DataLoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiServiceTest {

    @Mock
    private DataLoomRepository dataLoomRepository;

    @InjectMocks
    private ApiService underTest;

    @Test
    void shouldGetListOfAvailableFruitsFromDataLoomRepository() {
        // given
        var dataLoomEntity = DataLoomCatalogueEntityFixture.dataLoomCatalogueEntity;
        when(dataLoomRepository.findAll()).thenReturn(new ArrayList<>(List.of(dataLoomEntity)));

        String fruit = "Available: Fruit Type: %s, Quality Grade: %s, Quantity: %d"
                .formatted(dataLoomEntity.getFruitType(),
                        dataLoomEntity.getQuality(),
                        dataLoomEntity.getAvailableKg());
        List<String> expected = new ArrayList<>(List.of(fruit));

        // when
        underTest.getAvailableFruits();

        // then
        assertAll(
                () -> assertEquals(expected, underTest.getAvailableFruits())
        );
    }

    @Test
    void shouldFindDataLoomCatalogueEntityByFruitTypeAndQualityGrade() {
        // given
        var dataLoomEntity = DataLoomCatalogueEntityFixture.dataLoomCatalogueEntity;
        when(dataLoomRepository.findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(
                dataLoomEntity.getFruitType(),
                dataLoomEntity.getQuality()))
                .thenReturn(java.util.Optional.of(dataLoomEntity));

        // when
        underTest.findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(
                dataLoomEntity.getFruitType().toString(),
                dataLoomEntity.getQuality().toString());

        // then
        assertAll(
                () -> assertEquals(java.util.Optional.of(dataLoomEntity), underTest.findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(
                        dataLoomEntity.getFruitType().toString(),
                        dataLoomEntity.getQuality().toString()))
        );
    }

}
