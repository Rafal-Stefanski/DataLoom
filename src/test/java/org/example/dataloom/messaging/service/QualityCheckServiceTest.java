package org.example.dataloom.messaging.service;

import org.example.dataloom.messaging.dto.QualityCheckEventPayloadDto;
import org.example.dataloom.repository.ArchivedQualityControlEventsRepository;
import org.example.dataloom.repository.entity.QualityControlEntity;
import org.example.dataloom.repository.mapper.DataLoomMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QualityCheckServiceTest {

    @Mock
    private ArchivedQualityControlEventsRepository qualityCheckRepository;
    @Mock
    private DataLoomMapper dataLoomMapperImpl;

    @InjectMocks
    private QualityCheckService underTest;

    @Test
    void shouldSaveQualityCheckEventFromQualityCheckEventPayloadDto() {
        // given
        var qualityCheckEventPayloadDto = new QualityCheckEventPayloadDto();
        var qualityCheckEventEntity = new QualityControlEntity();

        when(dataLoomMapperImpl.toEntity(qualityCheckEventPayloadDto)).thenReturn(qualityCheckEventEntity);

        // when
        underTest.save(qualityCheckEventPayloadDto);

        // then
        assertAll(
                () -> verify(dataLoomMapperImpl).toEntity(qualityCheckEventPayloadDto),
                () -> verify(qualityCheckRepository).save(qualityCheckEventEntity)
        );
    }


}
