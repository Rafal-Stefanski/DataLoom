package org.example.dataloom.messaging.dto;

import org.example.dataloom.util.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FruitHarvestEventPayloadDtoTest {

    @Test
    void shouldBeDeserializableFromJsonWithMapper() throws JsonProcessingException {
        // Given
        String payload = TestUtil.readJsonFile("fruit_harvest_event.json");

        // When
        var result = TestUtil.getObjectMapper().readValue(payload, FruitHarvestEventPayloadDto.class);

        // Then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("HARV-APPLE-001", result.getBatchId()),
                () -> assertEquals(FruitType.Apple, result.getFruitType()),
                () -> assertEquals(500, result.getQuantityKg()),
                () -> assertEquals("Farm A", result.getFarmLocation()),
                () -> assertEquals("2024-10-19T07:30:00Z", result.getHarvestDate())
        );
    }

}
