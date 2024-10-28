package org.example.dataloom.repository.mapper;

import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FruitHarvestMapper {

    @Mapping(target = "id", ignore = true)
    FruitHarvestEventEntity toEntity(FruitHarvestEventPayloadDto dto);
}
