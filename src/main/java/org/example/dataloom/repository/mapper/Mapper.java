package org.example.dataloom.repository.mapper;

import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

//    @Mapping(target = "id", ignore = true)    // check if this is needed: causes build problem "shuld be null"
    FruitHarvestEventEntity toEntity(FruitHarvestEventPayloadDto dto);
}
