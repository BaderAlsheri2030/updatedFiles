package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.DTO.RfpDTO;
import com.example.demo.Model.Company;
import com.example.demo.Model.Competition;
import com.example.demo.Model.Offer;
import com.example.demo.Model.Rfp;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.CompetitionRepository;
import com.example.demo.Repository.OffersRepository;
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
    private final OffersRepository offersRepository;

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
        Rfp rfp = new Rfp(null,rfpDTO.getDescription(),rfpDTO.getReference_number(),rfpDTO.getCompetition_type(),rfpDTO.getDead_line(),rfpDTO.getContract_length(),rfpDTO.getService_details(),rfpDTO.getTitle(),rfpDTO.isComplete(),rfpDTO.getTime_left(),company,competition,null);
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
        for (Rfp rfp1:company.getRfps()){
            if (rfp1.getId().equals(rfp.getId())){
                rfp.setDescription(rfpDTO.getDescription());
                rfp.setCompetitionType(rfpDTO.getCompetition_type());
                rfp.setContractLength(rfpDTO.getContract_length());
                rfp.setDeadLine(rfpDTO.getDead_line());
                rfp.setServiceDetails(rfpDTO.getService_details());
                rfp.setTimeLeft(rfpDTO.getTime_left());
                rfp.setTitle(rfpDTO.getTitle());
                break;
            }
        }
        rfpRepository.save(rfp);
    }

    public void deleteRfp(Integer rfp_id){
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (rfp == null){
            throw new ApiException("invalid");
        }
        rfpRepository.delete(rfp);
    }

    public void acceptOffer(Integer rfp_id,Integer company_id,Integer offer_id){
        boolean mark = false;
        Company company = companyRepository.findCompanyById(company_id);
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        Offer offer = offersRepository.findOfferById(offer_id);
        for (Rfp rfp1:company.getRfps()){
            if (rfp1.getId().equals(rfp.getId())){
                for (Offer offer1:rfp1.getOffers()){
                    if (offer.getId().equals(offer1.getId())){
                        offer1.setStatus("accepted");
                        offersRepository.save(offer1);
                        mark = true;
                        rfp.setComplete(true);
                        break;
                    }

                }
            }
        }
        if (mark){
            for (Rfp rfp1:company.getRfps()){
                if (rfp1.getId().equals(rfp.getId())){
                    for (Offer offer1:rfp1.getOffers()) {
                        if (offer1.getStatus().equals("pending")){
                            offer1.setStatus("rejected");
                            offersRepository.save(offer1);
                        }
                    }
                }
            }
        }
        rfpRepository.save(rfp);

    }

    //add company authentication for security
    public List<Offer> viewReceivedOffers(Integer rfp_id){
        List<Offer> myOffers = offersRepository.findAllByRfpId(rfp_id);
        if (myOffers == null){
            throw new ApiException("You have no offers for this proposal");
        }
        return myOffers;
    }

    public List<Offer> viewOffersByPrice(Integer rfp_id,double min,double max){
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (rfp == null){
            throw new ApiException("Invalid proposal");
        }
        List<Offer> offers = offersRepository.findOffersByRfpIdAndPriceBetween(rfp_id,min,max);
        return offers;
    }

}
