package org.example.dataloom.messaging.service;

import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

class FruitHarvestHandlerTest {

    @Mock
    private FruitHarvestService fruitHarvestservice;

    @InjectMocks
    private FruitHarvestHandler underTest;

    @BeforeEach
    @ExtendWith(MockitoExtension.class)
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldHandle() {
        // given
        FruitHarvestEventPayloadDto message = new FruitHarvestEventPayloadDto();

        // when
        underTest.handle(message);

        // then
        verify(fruitHarvestservice).save(message);
    }
}
