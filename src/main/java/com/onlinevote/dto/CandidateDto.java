package com.onlinevote.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CandidateDto {
    private Long id;
    private String candidateId;
    private String name;
    private String voteCount;
    private String status;
}
