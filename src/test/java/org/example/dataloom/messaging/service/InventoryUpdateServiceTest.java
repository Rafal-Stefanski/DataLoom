package org.example.dataloom.messaging.service;

import org.example.dataloom.messaging.fixtures.InventoryUpdateEventPayloadDtoFixture;
import org.example.dataloom.repository.ArchivedInventoryUpdateEventsRepository;
import org.example.dataloom.repository.entity.InventoryUpdateEntity;
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
class InventoryUpdateServiceTest {

    @Mock
    private ArchivedInventoryUpdateEventsRepository inventoryUpdateRepository;

    @Mock
    private DataLoomMapper dataLoomMapperImpl;

    @InjectMocks
    private InventoryUpdateService underTest;

    @Test
    void shouldSaveInventoryUpdateEventFromInventoryUpdateEventPayloadDto() {
        // given
        var inventoryUpdateEventPayloadDto = InventoryUpdateEventPayloadDtoFixture.inventoryUpdateEventPayloadDto;
        var inventoryUpdateEntity = new InventoryUpdateEntity();

        when(dataLoomMapperImpl.toEntity(inventoryUpdateEventPayloadDto)).thenReturn(inventoryUpdateEntity);

        // when
        underTest.save(inventoryUpdateEventPayloadDto);

        // then
        assertAll(
                () -> verify(dataLoomMapperImpl).toEntity(inventoryUpdateEventPayloadDto),
                () -> verify(inventoryUpdateRepository).save(inventoryUpdateEntity)
        );
    }

}
