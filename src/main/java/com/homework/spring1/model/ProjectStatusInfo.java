package com.homework.spring1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о статусе проекта")
public class ProjectStatusInfo {
    @Schema(description = "Код статуса")
    private String code;

    @Schema(description = "Описание статуса")
    private String description;
} 