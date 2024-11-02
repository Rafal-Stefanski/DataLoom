package org.example.dataloom.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FruitHarvestHandler {

    private final FruitHarvestService fruitHarvestService;

    public void handle(FruitHarvestEventPayloadDto fruitHarvestEventPayloadDto) {
        fruitHarvestService.save(fruitHarvestEventPayloadDto);
    }
}
