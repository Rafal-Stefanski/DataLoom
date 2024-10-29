package org.example.dataloom.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dataloom.messaging.dto.QualityCheckEventPayloadDto;
import org.example.dataloom.repository.ArchivedQualityControlEventsRepository;
import org.example.dataloom.repository.entity.QualityControlEntity;
import org.example.dataloom.repository.mapper.DataLoomMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QualityCheckService {

    private final ArchivedQualityControlEventsRepository qualityCheckRepository;
    private final DataLoomMapper dataLoomMapperImpl;

    public void save(QualityCheckEventPayloadDto qualityCheckEventPayloadDto) {
        var qualityCheckEventEntity = dataLoomMapperImpl.toEntity(qualityCheckEventPayloadDto);
        qualityCheckRepository.save(qualityCheckEventEntity);
    }

    public void delete(QualityControlEntity qualityCheckEventEntity) {
        qualityCheckRepository.delete(qualityCheckEventEntity);
    }
}
