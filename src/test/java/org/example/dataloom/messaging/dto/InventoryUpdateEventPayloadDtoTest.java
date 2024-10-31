package org.example.dataloom.messaging.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dataloom.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryUpdateEventPayloadDtoTest {

    @Test
    void shouldBeDeserializableFromJsonWithMapper() throws JsonProcessingException {
        // Given
        String payload = TestUtil.readJsonFile("inventory_update_event.json");

        // When
        var result = TestUtil.getObjectMapper().readValue(payload, InventoryUpdateEventPayloadDto.class);

        // Then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(FruitType.Apple, result.getFruitType()),
                () -> assertEquals("HARV-APPLE-001", result.getBatchId()),
                () -> assertEquals("Warehouse A", result.getLocation()),
                () -> assertEquals(450, result.getAvailableKg()),
                () -> assertEquals("Stored", result.getStatus())
        );
    }

}
