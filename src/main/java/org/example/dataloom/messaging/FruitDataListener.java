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
import org.example.dataloom.messaging.service.InventoryUpdateHandler;
import org.example.dataloom.messaging.service.QualityCheckHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FruitDataListener {

    private final ObjectMapper objectMapper;
    private final FruitHarvestHandler fruitHarvestHandler;
    private final QualityCheckHandler qualityCheckHandler;
    private final InventoryUpdateHandler inventoryUpdateHandler;

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

            if (eventType == null) {
                log.warn("Event type is null for message: {}", message);
                return;
            }

            switch (eventType) {
                case FRUIT_HARVEST:
                    log.info("Received Fruit Harvest Event, eventId: {}", kafkaMessage.getMetaData().getEventId());
                    FruitHarvestEventPayloadDto fruitHarvestEventPayloadDto =
                            objectMapper.readValue(kafkaMessage.getPayload(), FruitHarvestEventPayloadDto.class);
                    fruitHarvestHandler.handle(fruitHarvestEventPayloadDto);
                    break;
                case QUALITY_INSPECTION:
                    log.info("Received Quality Inspection Event, eventId: {}", kafkaMessage.getMetaData().getEventId());
                    QualityCheckEventPayloadDto qualityCheckEventPayloadDto =
                            objectMapper.readValue(kafkaMessage.getPayload(), QualityCheckEventPayloadDto.class);
                    qualityCheckHandler.handle(qualityCheckEventPayloadDto);
                    break;
                case INVENTORY_UPDATE:
                    log.info("Received Inventory Update Event eventId: {}", kafkaMessage.getMetaData().getEventId());
                    InventoryUpdateEventPayloadDto inventoryUpdateEventPayloadDto =
                            objectMapper.readValue(kafkaMessage.getPayload(), InventoryUpdateEventPayloadDto.class);
                    inventoryUpdateHandler.handle(inventoryUpdateEventPayloadDto);
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
