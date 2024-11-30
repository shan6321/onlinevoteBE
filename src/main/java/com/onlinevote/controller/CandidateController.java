package com.onlinevote.controller;

import com.onlinevote.dto.CandidateDto;
import com.onlinevote.dto.StudentDto;
import com.onlinevote.entity.ApiResponse;
import com.onlinevote.entity.Candidate;
import com.onlinevote.entity.Student;
import com.onlinevote.service.CandidateService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/admin")
public class CandidateController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/displayCandidate")
    public ResponseEntity<ApiResponse<List<CandidateDto>>> displayCandidate(){
        log.info("========Inside Candidate controller of displayCandidate Method============");
        List<CandidateDto> candidateDtoList = candidateService.fetchCandidate();
        ApiResponse<List<CandidateDto>> response = new ApiResponse<>(candidateDtoList, "Display All Candidate List",true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/applyVote/{id}")
    public ResponseEntity<ApiResponse<CandidateDto>> applyVote(@PathVariable Long id){
        log.info("========Inside Candidate controller of applyVote Method============");
        Candidate candidatetEntity = candidateService.findByCandidateId(id);
        CandidateDto candidateDtoRes = modelMapper.map(candidatetEntity, CandidateDto.class);
        ApiResponse<CandidateDto> response = new ApiResponse<>(candidateDtoRes, "You have Successfully Voted for "+candidateDtoRes.getName(),true);
        return ResponseEntity.ok(response);
    }
}
