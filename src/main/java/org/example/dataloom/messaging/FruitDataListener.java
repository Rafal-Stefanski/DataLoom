package org.example.dataloom.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.example.dataloom.messaging.dto.InventoryUpdateEventPayloadDto;
import org.example.dataloom.messaging.dto.QualityCheckEventPayloadDto;
import org.example.dataloom.messaging.model.EventType;
import org.example.dataloom.messaging.model.KafkaMessage;
import org.example.dataloom.messaging.service.FruitHarvestHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FruitDataListener {

    private final ObjectMapper objectMapper;
    private final FruitHarvestHandler fruitHarvestHandler;

    @Timed(value = "listenAndHandleMessage", histogram = true, percentiles = 0.98) // for app metrics
    @KafkaListener(topics = "${kafka.consumer.fruit-data.topic}",
            groupId = "${kafka.consumer.fruit-data.group-id}",
            concurrency = "${kafka.consumer.fruit-data.concurrency}",
            autoStartup = "${kafka.consumer.fruit-data.enabled}")
    public void listen(String message) {
        log.info("Received Kafka Fruit message: {}", message);
        try {
            KafkaMessage kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);
            EventType eventType = kafkaMessage.getMetaData().getEventType();
            switch (eventType) {
                case FRUIT_HARVEST:
                    log.info("Received Fruit Harvest Event: {}", message);
                    FruitHarvestEventPayloadDto fruitHarvestEventPayloadDto =
                            objectMapper.readValue(kafkaMessage.getPayload(), FruitHarvestEventPayloadDto.class);
                    fruitHarvestEventPayloadDto.setEventId(kafkaMessage.getMetaData().getEventId());
                    fruitHarvestHandler.handle(fruitHarvestEventPayloadDto);
                    break;
                case QUALITY_INSPECTION:
                    log.info("Received Quality Inspection Event: {}", message);
                    QualityCheckEventPayloadDto qualityCheckEventPayloadDto =
                            objectMapper.readValue(kafkaMessage.getPayload(), QualityCheckEventPayloadDto.class);
                    qualityCheckEventPayloadDto.setEventId(kafkaMessage.getMetaData().getEventId());
                    // TODO: handle quality check event
                    break;
                case INVENTORY_UPDATE:
                    log.info("Received Inventory Update Event: {}", message);
                    InventoryUpdateEventPayloadDto inventoryUpdateEventPayloadDto =
                            objectMapper.readValue(kafkaMessage.getPayload(), InventoryUpdateEventPayloadDto.class);
                    inventoryUpdateEventPayloadDto.setEventId(kafkaMessage.getMetaData().getEventId());
                    // TODO: handle inventory update event
                    break;
                default:
                    log.warn("Unknown event type: {}", eventType);
                    break;
            }

        } catch (JsonProcessingException e) {
            log.error("Error processing message: {}", message, e);
        }
    }

}
