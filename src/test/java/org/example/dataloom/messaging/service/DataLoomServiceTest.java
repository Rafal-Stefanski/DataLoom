package org.example.dataloom.messaging.service;

import nl.altindag.log.LogCaptor;
import org.example.dataloom.messaging.fixtures.DataLoomCatalogueEntityFixture;
import org.example.dataloom.messaging.fixtures.FruitHarvestEventEntityFixture;
import org.example.dataloom.messaging.fixtures.InventoryUpdateEntityFixture;
import org.example.dataloom.messaging.fixtures.QualityControlEntityFixture;
import org.example.dataloom.repository.ArchivedFruitHarvestEventsRepository;
import org.example.dataloom.repository.ArchivedInventoryUpdateEventsRepository;
import org.example.dataloom.repository.ArchivedQualityControlEventsRepository;
import org.example.dataloom.repository.DataLoomRepository;
import org.example.dataloom.repository.entity.DataLoomCatalogueEntity;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.example.dataloom.repository.entity.InventoryUpdateEntity;
import org.example.dataloom.repository.entity.QualityControlEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataLoomServiceTest {

    @Mock
    private DataLoomRepository dataLoomRepository;
    @Mock
    private ArchivedFruitHarvestEventsRepository archivedFruitHarvestEventsRepository;
    @Mock
    private ArchivedQualityControlEventsRepository archivedQualityControlEventsRepository;
    @Mock
    private ArchivedInventoryUpdateEventsRepository archivedInventoryUpdateEventsRepository;
    @Mock
    private LogCaptor logCaptor;

    @InjectMocks
    private DataLoomService underTest;

    @BeforeEach
    void setUp() {
        logCaptor = LogCaptor.forClass(DataLoomService.class);
        logCaptor.clearLogs();
    }

    @Test
    void shouldSaveFruitHarvestEvent() {
        // given
        FruitHarvestEventEntity fruitHarvestEvent = FruitHarvestEventEntityFixture.fruitHarvestEventEntity;
        QualityControlEntity quantityCheckEvent = QualityControlEntityFixture.qualityControlEntity;
        InventoryUpdateEntity inventoryUpdateEvent = InventoryUpdateEntityFixture.inventoryUpdateEntity;
        DataLoomCatalogueEntity dataLoomCatalogueEntity = DataLoomCatalogueEntityFixture.dataLoomCatalogueEntity;

        when(archivedFruitHarvestEventsRepository.findAll()).thenReturn(List.of(fruitHarvestEvent));
        when(archivedQualityControlEventsRepository.findAll()).thenReturn(List.of(quantityCheckEvent));
        when(archivedInventoryUpdateEventsRepository.findAll()).thenReturn(List.of(inventoryUpdateEvent));

        // when
        underTest.runDataLoomCatalogueUpdate();

        // then
        assertAll(
                () -> verify(archivedFruitHarvestEventsRepository).findAll(),
                () -> verify(archivedQualityControlEventsRepository).findAll(),
                () -> verify(archivedInventoryUpdateEventsRepository).findAll(),
                () -> verify(dataLoomRepository).save(dataLoomCatalogueEntity),
                () -> assertTrue(logCaptor.getInfoLogs().contains("Created new document in DataLoomCatalogue for: Apple, A_PLUS")),
                () -> assertTrue(logCaptor.getInfoLogs().contains("Deleted archived documents for batchId: APPLE-BATCH-001"))
        );
    }

    @Test
    void shouldUpdateFruitHarvestEvent() {
        // given
        FruitHarvestEventEntity fruitHarvestEvent = FruitHarvestEventEntityFixture.fruitHarvestEventEntity;
        QualityControlEntity quantityCheckEvent = QualityControlEntityFixture.qualityControlEntity;
        InventoryUpdateEntity inventoryUpdateEvent = InventoryUpdateEntityFixture.inventoryUpdateEntity;
        DataLoomCatalogueEntity dataLoomCatalogueEntity = DataLoomCatalogueEntityFixture.dataLoomCatalogueEntity;

        when(archivedFruitHarvestEventsRepository.findAll()).thenReturn(List.of(fruitHarvestEvent));
        when(archivedQualityControlEventsRepository.findAll()).thenReturn(List.of(quantityCheckEvent));
        when(archivedInventoryUpdateEventsRepository.findAll()).thenReturn(List.of(inventoryUpdateEvent));
        when(dataLoomRepository.findDataLoomCatalogueEntityByFruitTypeAndQualityGrade(fruitHarvestEvent.getFruitType(), quantityCheckEvent.getQuality())).thenReturn(java.util.Optional.of(dataLoomCatalogueEntity));

        // when
        underTest.runDataLoomCatalogueUpdate();

        // then
        assertAll(
                () -> verify(archivedFruitHarvestEventsRepository).findAll(),
                () -> verify(archivedQualityControlEventsRepository).findAll(),
                () -> verify(archivedInventoryUpdateEventsRepository).findAll(),
                () -> verify(dataLoomRepository).save(dataLoomCatalogueEntity),
                () -> assertEquals(200, dataLoomCatalogueEntity.getAvailableKg()),
                () -> assertTrue(logCaptor.getInfoLogs().contains("Updating Apple, A_PLUS in DataLoomCatalogue.")),
                () -> assertTrue(logCaptor.getInfoLogs().contains("Updated document in DataLoomCatalogue for: Apple, A_PLUS, added 100 kg, to: warehouse A")),
                () -> assertTrue(logCaptor.getInfoLogs().contains("Deleted archived documents for batchId: APPLE-BATCH-001"))
        );
    }

    @Test
    void shouldNotSaveFruitHarvestEvent() {
        // given

        // when
        underTest.runDataLoomCatalogueUpdate();

        // then
        assertAll(
                () -> verify(archivedFruitHarvestEventsRepository).findAll(),
                () -> verify(archivedQualityControlEventsRepository).findAll(),
                () -> verify(archivedInventoryUpdateEventsRepository).findAll(),
                () -> verifyNoInteractions(dataLoomRepository)
        );
    }

}
