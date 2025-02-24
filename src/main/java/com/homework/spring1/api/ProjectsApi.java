package com.homework.spring1.api;

import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectDTO;
import com.homework.spring1.model.ProjectStatusInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Проекты", description = "API для управления проектами")
@RequestMapping("/api/v1/projects")
public interface ProjectsApi {

    @Operation(summary = "Получить список всех проектов")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список проектов успешно получен",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class)))
    })
    @GetMapping
    ResponseEntity<List<Project>> getAllProjects();

    @Operation(summary = "Получить проект по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Проект успешно найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
        @ApiResponse(responseCode = "404", description = "Проект с указанным ID не найден")
    })
    @GetMapping("/{id}")
    ResponseEntity<Project> getProjectById(
        @Parameter(description = "ID проекта") 
        @PathVariable Long id
    );

    @Operation(summary = "Создать новый проект")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Проект успешно создан",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
        @ApiResponse(responseCode = "400", description = """
            Некорректные данные проекта. Возможные причины:
            - Название проекта отсутствует или короче 3 символов или длиннее 100 символов
            - Описание длиннее 1000 символов
            - Статус проекта отсутствует или некорректный
            - Дата дедлайна не в будущем
            - Бюджет отрицательный
            - Список сотрудников пуст
            - Приоритет проекта отсутствует
            - Один или несколько сотрудников не найдены
            """)
    })
    @PostMapping
    ResponseEntity<Project> createProject(
        @Parameter(description = "Данные нового проекта") 
        @Valid @RequestBody Project project
    );

    @Operation(summary = "Обновить данные проекта")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Данные проекта успешно обновлены",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
        @ApiResponse(responseCode = "404", description = "Проект с указанным ID не найден"),
        @ApiResponse(responseCode = "400", description = """
            Некорректные данные проекта. Возможные причины:
            - Название проекта отсутствует или короче 3 символов или длиннее 100 символов
            - Описание длиннее 1000 символов
            - Статус проекта отсутствует или некорректный
            - Дата дедлайна не в будущем
            - Бюджет отрицательный
            - Список сотрудников пуст
            - Приоритет проекта отсутствует
            - Один или несколько сотрудников не найдены
            """)
    })
    @PutMapping("/{id}")
    ResponseEntity<Project> updateProject(
        @Parameter(description = "ID проекта") 
        @PathVariable Long id,
        @Parameter(description = "Обновленные данные проекта") 
        @Valid @RequestBody Project project
    );

    @Operation(summary = "Частично обновить данные проекта")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Данные проекта успешно обновлены",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
        @ApiResponse(responseCode = "404", description = "Проект с указанным ID не найден"),
        @ApiResponse(responseCode = "400", description = """
            Некорректные данные проекта. Возможные причины:
            - Название проекта короче 3 символов или длиннее 100 символов
            - Описание длиннее 1000 символов
            - Статус проекта некорректный
            - Дата дедлайна не в будущем
            - Бюджет отрицательный
            - Список сотрудников пуст
            - Один или несколько сотрудников не найдены
            """)
    })
    @PatchMapping("/{id}")
    ResponseEntity<Project> patchProject(
        @Parameter(description = "ID проекта") 
        @PathVariable Long id,
        @Parameter(description = "Частично обновленные данные проекта") 
        @Valid @RequestBody ProjectDTO projectDTO
    );

    @Operation(summary = "Удалить проект")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Проект успешно удален"),
        @ApiResponse(responseCode = "404", description = "Проект с указанным ID не найден")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProject(
        @Parameter(description = "ID проекта") 
        @PathVariable Long id
    );

    @Operation(summary = "Получить проекты по статусу")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список проектов успешно получен",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class)))
    })
    @GetMapping("/status/{status}")
    ResponseEntity<List<Project>> getProjectsByStatus(
        @Parameter(description = "Статус проекта") 
        @PathVariable String status
    );

    @Operation(summary = "Получить список всех возможных статусов проекта")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список статусов проекта успешно получен",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectStatusInfo.class)))
    })
    @GetMapping("/statuses")
    ResponseEntity<List<ProjectStatusInfo>> getAllProjectStatuses();
} 