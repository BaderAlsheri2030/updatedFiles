package com.example.munafis.Service;

import com.example.munafis.API.ApiException;
import com.example.munafis.Model.Competition;
import com.example.munafis.Model.Rfp;
import com.example.munafis.Repository.CompetitionRepository;
import com.example.munafis.Repository.RfpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        if (!competitionRepository.findAll().isEmpty() ){
            throw new ApiException("there can be only one competition at a time");
        }
        competitionRepository.save(competition);
    }

    //admin
    public void updateCompetition(Integer id){
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
