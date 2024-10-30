package org.example.dataloom.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.InventoryUpdateEventPayloadDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryUpdateHandler {

    private final InventoryUpdateService inventoryUpdateService;

    public void handle(InventoryUpdateEventPayloadDto inventoryUpdateEvent) {
        log.info("Received Inventory Update Event: {}", inventoryUpdateEvent);
        inventoryUpdateService.save(inventoryUpdateEvent);
    }
}
