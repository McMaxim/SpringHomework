package app.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniversityDto {
    private Long id;
    private String name;
}
