package com.homework.spring1.api;

import com.homework.spring1.model.Employee;
import com.homework.spring1.model.EmployeeDTO;
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

@Tag(name = "Сотрудники", description = "API для управления сотрудниками")
@RequestMapping("/api/v1/employees")
public interface EmployeesApi {

    @Operation(summary = "Получить список всех сотрудников")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список сотрудников успешно получен",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)))
    })
    @GetMapping
    ResponseEntity<List<Employee>> getAllEmployees();

    @Operation(summary = "Получить сотрудника по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Сотрудник успешно найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
        @ApiResponse(responseCode = "404", description = "Сотрудник с указанным ID не найден")
    })
    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(
        @Parameter(description = "ID сотрудника") 
        @PathVariable Long id
    );

    @Operation(summary = "Создать нового сотрудника")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Сотрудник успешно создан",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
        @ApiResponse(responseCode = "400", description = """
            Некорректные данные сотрудника. Возможные причины:
            - Имя сотрудника отсутствует или короче 2 символов или длиннее 50 символов
            - Email отсутствует или имеет некорректный формат
            - Должность отсутствует или строка короче 2 символов или длиннее 50 символов
            - Отдел отсутствует или строка короче 2 символов или длиннее 50 символов
            - Опыт работы отрицательный
            """)
    })
    @PostMapping
    ResponseEntity<Employee> createEmployee(
        @Parameter(description = "Данные нового сотрудника") 
        @Valid @RequestBody Employee employee
    );

    @Operation(summary = "Обновить данные сотрудника")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Данные сотрудника успешно обновлены",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
        @ApiResponse(responseCode = "404", description = "Сотрудник с указанным ID не найден"),
        @ApiResponse(responseCode = "400", description = """
            Некорректные данные сотрудника. Возможные причины:
            - Имя сотрудника отсутствует или короче 2 символов или длиннее 50 символов
            - Email отсутствует или имеет некорректный формат
            - Должность отсутствует или строка короче 2 символов или длиннее 50 символов
            - Отдел отсутствует или строка короче 2 символов или длиннее 50 символов
            - Опыт работы отрицательный
            """)
    })
    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(
        @Parameter(description = "ID сотрудника") 
        @PathVariable Long id,
        @Parameter(description = "Обновленные данные сотрудника") 
        @Valid @RequestBody Employee employee
    );

    @Operation(summary = "Частично обновить данные сотрудника")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Данные сотрудника успешно обновлены",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
        @ApiResponse(responseCode = "404", description = "Сотрудник с указанным ID не найден"),
        @ApiResponse(responseCode = "400", description = """
            Некорректные данные сотрудника. Возможные причины:
            - Имя сотрудника короче 2 символов или длиннее 50 символов
            - Email имеет некорректный формат
            - Должность короче 2 символов или длиннее 50 символов
            - Отдел короче 2 символов или длиннее 50 символов
            - Опыт работы отрицательный
            """)
    })
    @PatchMapping("/{id}")
    ResponseEntity<Employee> patchEmployee(
        @Parameter(description = "ID сотрудника") 
        @PathVariable Long id,
        @Parameter(description = "Частично обновленные данные сотрудника") 
        @Valid @RequestBody EmployeeDTO employeeDTO
    );

    @Operation(summary = "Удалить сотрудника")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Сотрудник успешно удален"),
        @ApiResponse(responseCode = "404", description = "Сотрудник с указанным ID не найден")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEmployee(
        @Parameter(description = "ID сотрудника") 
        @PathVariable Long id
    );
} 