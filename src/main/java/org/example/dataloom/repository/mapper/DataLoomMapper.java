package org.example.dataloom.repository.mapper;

import org.example.dataloom.messaging.dto.FruitHarvestEventPayloadDto;
import org.example.dataloom.messaging.dto.InventoryUpdateEventPayloadDto;
import org.example.dataloom.messaging.dto.QualityCheckEventPayloadDto;
import org.example.dataloom.repository.entity.FruitHarvestEventEntity;
import org.example.dataloom.repository.entity.InventoryUpdateEntity;
import org.example.dataloom.repository.entity.QualityControlEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataLoomMapper {

    @Mapping(target = "id", ignore = true)
    FruitHarvestEventEntity toEntity(FruitHarvestEventPayloadDto dto);

    @Mapping(target = "id", ignore = true)
    QualityControlEntity toEntity(QualityCheckEventPayloadDto dto);

    @Mapping(target = "id", ignore = true)
    InventoryUpdateEntity toEntity(InventoryUpdateEventPayloadDto dto);

}
