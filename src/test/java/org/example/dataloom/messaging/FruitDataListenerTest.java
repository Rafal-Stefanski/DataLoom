package org.example.dataloom.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.example.dataloom.messaging.dto.InventoryUpdateEventPayloadDto;
import org.example.dataloom.messaging.dto.QualityCheckEventPayloadDto;
import org.example.dataloom.messaging.model.EventType;
import org.example.dataloom.messaging.model.KafkaMessage;
import org.example.dataloom.messaging.model.MetaData;
import org.example.dataloom.messaging.service.FruitHarvestHandler;
import org.example.dataloom.messaging.service.InventoryUpdateHandler;
import org.example.dataloom.messaging.service.QualityCheckHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.example.dataloom.util.TestUtil.readJsonFile;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
class FruitDataListenerTest {

    private final KafkaMessage kafkaMessage = new KafkaMessage();
    private final String message = readJsonFile("fruit_harvest_event.json");

    @Mock
    private FruitHarvestHandler fruitHarvestHandler;
    @Mock
    private QualityCheckHandler qualityCheckHandler;
    @Mock
    private InventoryUpdateHandler inventoryUpdateHandler;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private FruitDataListener underTest;

    @Test
    void shouldConsumeFruitHarvestEvent() throws Exception {
        // given
        MetaData metaData = MetaData.builder().eventType(EventType.FRUIT_HARVEST).build();
        kafkaMessage.setMetaData(metaData);
        kafkaMessage.setPayload(message);

        when(objectMapper.readValue(message, KafkaMessage.class)).thenReturn(kafkaMessage);
        when(objectMapper.readValue(message, FruitHarvestEventPayloadDto.class)).thenReturn(new FruitHarvestEventPayloadDto());

        // when
        underTest.listen(message);

        // then
        assertAll(
                () -> verify(fruitHarvestHandler).handle(any(FruitHarvestEventPayloadDto.class)),
                () -> verify(qualityCheckHandler, never()).handle(any()),
                () -> verify(inventoryUpdateHandler, never()).handle(any())
        );
    }

    @Test
    void shouldConsumeQualityCheckEvent() throws Exception {
        // given
        MetaData metaData = MetaData.builder().eventType(EventType.QUALITY_INSPECTION).build();
        kafkaMessage.setMetaData(metaData);
        kafkaMessage.setPayload(message);

        when(objectMapper.readValue(message, KafkaMessage.class)).thenReturn(kafkaMessage);
        when(objectMapper.readValue(message, QualityCheckEventPayloadDto.class)).thenReturn(new QualityCheckEventPayloadDto());

        // when
        underTest.listen(message);

        // then
        assertAll(
                () -> verify(fruitHarvestHandler, never()).handle(any()),
                () -> verify(qualityCheckHandler).handle(any(QualityCheckEventPayloadDto.class)),
                () -> verify(inventoryUpdateHandler, never()).handle(any())
        );
    }

    @Test
    void shouldConsumeInventoryUpdateEvent() throws Exception {
        // given
        MetaData metaData = MetaData.builder().eventType(EventType.INVENTORY_UPDATE).build();
        kafkaMessage.setMetaData(metaData);
        kafkaMessage.setPayload(message);

        when(objectMapper.readValue(message, KafkaMessage.class)).thenReturn(kafkaMessage);
        when(objectMapper.readValue(message, InventoryUpdateEventPayloadDto.class)).thenReturn(new InventoryUpdateEventPayloadDto());

        // when
        underTest.listen(message);

        // then
        assertAll(
                () -> verify(fruitHarvestHandler, never()).handle(any()),
                () -> verify(qualityCheckHandler, never()).handle(any()),
                () -> verify(inventoryUpdateHandler).handle(any())
        );
    }

    @Test
    void shouldLogWarningForUnknownEventType() throws Exception {
        // given
        MetaData metaData = MetaData.builder().build();
        kafkaMessage.setMetaData(metaData);
        kafkaMessage.setPayload(message);

        when(objectMapper.readValue(message, KafkaMessage.class)).thenReturn(kafkaMessage);

        // when
        underTest.listen(message);

        // then
        assertAll(
                () -> verify(fruitHarvestHandler, never()).handle(any()),
                () -> verify(qualityCheckHandler, never()).handle(any()),
                () -> verify(inventoryUpdateHandler, never()).handle(any())
        );
    }

}
