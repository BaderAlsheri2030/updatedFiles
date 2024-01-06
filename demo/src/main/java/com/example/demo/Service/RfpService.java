package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.DTO.RfpDTO;
import com.example.demo.Model.Company;
import com.example.demo.Model.Competition;
import com.example.demo.Model.Rfp;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.CompetitionRepository;
import com.example.demo.Repository.RfpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RfpService {
    private final RfpRepository rfpRepository;
    private final CompetitionRepository competitionRepository;
    private final CompanyRepository companyRepository;

    public List<Rfp> getAll(){
        return rfpRepository.findAll();
    }

    public void addRfp(RfpDTO rfpDTO,Integer company_id,Integer comp_id){
        Competition competition = competitionRepository.findCompetitionById(comp_id);
        Company company = companyRepository.findCompanyById(company_id);
        if (competition == null){
            throw new ApiException("there's no competition");
        }
        if (company == null){
            throw new ApiException("Invalid company");
        }
        Rfp rfp = new Rfp(null,rfpDTO.getDescription(),rfpDTO.getReference_number(),rfpDTO.getCompetition_type(),rfpDTO.getDead_line(),rfpDTO.getContract_length(),rfpDTO.getService_details(),rfpDTO.getTitle(),rfpDTO.isComplete(),rfpDTO.getTime_left(),company,competition);
        rfpRepository.save(rfp);
    }

    public void updateRfp(Integer rfp_id,RfpDTO rfpDTO,Integer company_id){
        Company company = companyRepository.findCompanyById(company_id);
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (company == null){
            throw new ApiException("Invalid company");
        }
        if (rfp == null){
            throw new ApiException("Invalid proposal");
        }
        rfp.setDescription(rfpDTO.getDescription());
        rfp.setCompetitionType(rfpDTO.getCompetition_type());
        rfp.setContractLength(rfpDTO.getContract_length());
        rfp.setDeadLine(rfpDTO.getDead_line());
        rfp.setServiceDetails(rfpDTO.getService_details());
        rfp.setTimeLeft(rfpDTO.getTime_left());
        rfp.setTitle(rfpDTO.getTitle());
        rfpRepository.save(rfp);
    }
    public void deleteRfp(Integer rfp_id){
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (rfp == null){
            throw new ApiException("invalid");
        }

    }

}
