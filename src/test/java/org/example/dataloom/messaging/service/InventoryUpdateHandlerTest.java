package org.example.dataloom.messaging.service;

import org.example.dataloom.messaging.dto.InventoryUpdateEventPayloadDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InventoryUpdateHandlerTest {

    @Mock
    private InventoryUpdateService inventoryUpdateService;

    @InjectMocks
    private InventoryUpdateHandler underTest;

    @Test
    void shouldHandle() {
        // given
        InventoryUpdateEventPayloadDto message = new InventoryUpdateEventPayloadDto();

        // when
        underTest.handle(message);

        // then
        verify(inventoryUpdateService).save(message);
    }

}
