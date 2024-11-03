package org.example.dataloom.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataLoomDto {
    private String eventId;
    private String batchId;
    private String farmLocation;
    private String harvestDate;
    private int quantityKg;
    private String inspectorId;
    private String inspectionDate;
    private String remarks;
    private String fruitType;
    private String quality;
}
