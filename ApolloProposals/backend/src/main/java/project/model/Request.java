package project.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    private String sector;
    private String projectName;

    @Column(columnDefinition = "TEXT")
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