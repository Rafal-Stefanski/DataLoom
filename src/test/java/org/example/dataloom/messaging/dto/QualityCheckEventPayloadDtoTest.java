package org.example.dataloom.messaging.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dataloom.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QualityCheckEventPayloadDtoTest {

    @Test
    void shouldBeDeserializableFromJsonWithMapper() throws JsonProcessingException {
        // Given
        String payload = TestUtil.readJsonFile("quality_check_event.json");

        // When
        var result = TestUtil.getObjectMapper().readValue(payload, QualityCheckEventPayloadDto.class);

        // Then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("HARV-APPLE-001", result.getBatchId()),
                () -> assertEquals(FruitType.Apple, result.getFruitType()),
                () -> assertEquals(QualityGrade.A_PLUS, result.getQuality()),
                () -> assertEquals("insp001", result.getInspectorId()),
                () -> assertEquals("2024-10-20T08:30:00Z", result.getInspectionDate()),
                () -> assertEquals("Excellent condition", result.getRemarks())
        );
    }

}
