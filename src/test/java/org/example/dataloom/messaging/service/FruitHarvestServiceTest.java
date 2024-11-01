package org.example.dataloom.messaging.service;

import org.example.dataloom.messaging.fixtures.FruitHarvestEventPayloadDtoFixture;
import org.example.dataloom.repository.ArchivedFruitHarvestEventsRepository;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.example.dataloom.repository.mapper.DataLoomMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FruitHarvestServiceTest {

    @Mock
    private ArchivedFruitHarvestEventsRepository fruitHarvestRepository;
    @Mock
    private DataLoomMapper dataLoomMapperImpl;

    @InjectMocks
    private FruitHarvestService underTest;

    @Test
    void shouldSaveFruitHarvestEventFromFruitHarvestEventPayloadDto() {
        // given
        var fruitHarvestEventPayloadDto = FruitHarvestEventPayloadDtoFixture.fruitHarvestEventPayloadDto;
        var fruitHarvestEventEntity = new FruitHarvestEventEntity();

        when(dataLoomMapperImpl.toEntity(fruitHarvestEventPayloadDto)).thenReturn(fruitHarvestEventEntity);

        // when
        underTest.save(fruitHarvestEventPayloadDto);

        // then
        assertAll(
                () -> verify(dataLoomMapperImpl).toEntity(fruitHarvestEventPayloadDto),
                () -> verify(fruitHarvestRepository).save(fruitHarvestEventEntity)
        );
    }

    @Test
    void shouldDeleteFruitHarvestEventFromFruitHarvestEventEntity() {
        // given
        var fruitHarvestEventEntity = new FruitHarvestEventEntity();

        // when
        underTest.delete(fruitHarvestEventEntity);

        // then
        verify(fruitHarvestRepository).delete(fruitHarvestEventEntity);
    }
}
