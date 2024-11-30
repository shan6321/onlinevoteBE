package com.onlinevote.service;

import com.onlinevote.dto.CandidateDto;
import com.onlinevote.entity.Candidate;
import com.onlinevote.entity.Student;
import com.onlinevote.exception.RecordNotInserted;
import com.onlinevote.exception.UserNotFound;
import com.onlinevote.repository.CandidateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<CandidateDto> fetchCandidate() {
        try{
            List<Candidate> candidateList = candidateRepository.findAll();
            if (!candidateList.isEmpty()){
                return candidateList.stream().map((Candidate) -> modelMapper.map(Candidate, CandidateDto.class))
                        .collect(Collectors.toList());
            }
            else {
                throw new RecordNotInserted("Record not Found");
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    public Candidate findByCandidateId(Long id) {
        try{
            //Candidate candidate = modelMapper.map(candidateDto, Candidate.class);
            Optional<Candidate> candidateData = candidateRepository.findById(id);
            if(candidateData.isPresent()) {
                int count = 1;
                Candidate candidateUpdate = candidateData.get();
                int updateVoteCount = Integer.parseInt(candidateUpdate.getVoteCount()) + count;
                candidateUpdate.setVoteCount(String.valueOf(updateVoteCount));
                return candidateRepository.save(candidateUpdate);
            }
            else {
                throw new UserNotFound("User Not Found for given id : "+ id.toString());
            }

        }
        catch (Exception e){
            throw e;
        }
    }
}
