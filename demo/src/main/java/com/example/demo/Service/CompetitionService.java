package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.Model.Competition;
import com.example.demo.Model.Rfp;
import com.example.demo.Repository.CompetitionRepository;
import com.example.demo.Repository.RfpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final RfpRepository rfpRepository;
    public List<Competition> getCompetitions(){
        return competitionRepository.findAll();
    }

    public void addCompetition(Competition competition){
        competitionRepository.save(competition);
    }

    public void updateCompetition(Integer id,Competition competition){
        Competition competition1 = competitionRepository.findCompetitionById(id);
        Set<Rfp> rfps = new HashSet<>();
        if (competition1 == null){
            throw new ApiException("invalid");
        }
        for (Rfp rfp:rfpRepository.findAll()){
            if (!rfp.isComplete()){
                rfps.add(rfp);
            }
        }
        competition1.setRfps(rfps);
    }
    public void deleteCompetition(Integer id){
        Competition competition = competitionRepository.findCompetitionById(id);
        if (competition == null){
            throw new ApiException("invalid id");
        }
        for (Rfp rfp:competition.getRfps()){
            if (!rfp.isComplete()){
                throw new ApiException("cannot delete since there's an incomplete proposals in the competition");
            }
        }
        competitionRepository.delete(competition);
    }


}
