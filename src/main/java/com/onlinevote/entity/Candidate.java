package com.onlinevote.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "candidatelist")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "candidate_id")
    private String candidateId;

    @Column(name = "name")
    private String name;

    @Column(name = "vote_count")
    private String voteCount;

    @Column(name = "status")
    private String status;
}
