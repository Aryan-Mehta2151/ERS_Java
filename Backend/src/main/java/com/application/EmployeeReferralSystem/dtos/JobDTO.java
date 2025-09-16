package com.application.EmployeeReferralSystem.dtos;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private String id;
    private int reqEmp;
    private List<String> skills;
    private String jobRole;
    private int minAts;
}
