package org.example.dataloom.messaging.service;


import org.example.dataloom.messaging.dto.QualityCheckEventPayloadDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QualityCheckHandlerTest {

    @Mock
    private QualityCheckService qualityCheckService;

    @InjectMocks
    private QualityCheckHandler underTest;

    @Test
    void shouldHandle() {
        // given
        var message = new QualityCheckEventPayloadDto();

        // when
        underTest.handle(message);

        // then
        verify(qualityCheckService).save(message);
    }


}
