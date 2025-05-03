package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private Long id;
    private Long customerId;        // foreign key reference to Customer
    private String sector;
    private String projectName;
    private String response;
    private Double evaluation;
    private String generalDescription;
    private String objectives;
    private String problems;
    private String typeOfServices;
    private String expectedFeatures;
    private String preferredTechnologies;
    private String restrictionsOrRequests;
    private BigDecimal budget;
    private LocalDate deadline;
    private String additionalComments;
}
