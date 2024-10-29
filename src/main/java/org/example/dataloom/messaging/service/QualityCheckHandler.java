package org.example.dataloom.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.QualityCheckEventPayloadDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QualityCheckHandler {

    private final QualityCheckService qualityCheckService;

    public void handle(QualityCheckEventPayloadDto message) {
        log.info("Received Quality Check Event: {}", message);
        qualityCheckService.save(message);
    }

}
