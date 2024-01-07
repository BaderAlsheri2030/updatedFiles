package com.example.munafis.Service;

import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.RfpDTO;
import com.example.munafis.Model.Company;
import com.example.munafis.Model.Competition;
import com.example.munafis.Model.Offer;
import com.example.munafis.Model.Rfp;
import com.example.munafis.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RfpService {
    private final RfpRepository rfpRepository;
    private final CompetitionRepository competitionRepository;
    private final CompanyRepository companyRepository;
    private final OffersRepository offersRepository;
    private final RfpDTORepository rfpDTORepository;

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
        Rfp rfp = new Rfp(null,rfpDTO.getDescription(),rfpDTO.getReference_number(),rfpDTO.getCompetition_type(),rfpDTO.getDead_line(),rfpDTO.getLocation(),rfpDTO.getStartDate(),rfpDTO.getContract_length(),rfpDTO.getService_details(),rfpDTO.getTitle(),rfpDTO.isComplete(),rfpDTO.getName(),rfpDTO.getTime_left(),company,competition,null);
        rfpRepository.save(rfp);
        rfpDTORepository.save(rfpDTO);
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
        rfpDTORepository.save(rfpDTO);
    }

    public void deleteRfp(Integer rfp_id){
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (rfp == null){
            throw new ApiException("invalid");
        }
        rfpRepository.delete(rfp);
    }

    //accept one offer and reject all offers by default
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
    //company view rfp received offers
    public List<Offer> viewReceivedOffers(Integer rfp_id){
        List<Offer> myOffers = offersRepository.findAllByRfpId(rfp_id);
        if (myOffers == null){
            throw new ApiException("You have no offers for this proposal");
        }
        return myOffers;
    }

    //company view
    public List<Offer> viewOffersByPrice(Integer rfp_id,double min,double max){
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (rfp == null){
            throw new ApiException("Invalid proposal");
        }
        List<Offer> offers = offersRepository.findOffersByRfpIdAndPriceBetween(rfp_id,min,max);
        return offers;
    }

    //provider view
    // must hide offers from providers
    public Set<Rfp> findRfpsBydeadlineBefore(LocalDate date){

        Set<Rfp> rfps = rfpRepository.findAllByDeadLineBefore(date);
        for (Rfp rfp:rfps){
            if (rfp.isComplete()){
                rfps.remove(rfp);
            }
        }
        if (rfps.isEmpty()){
            throw new ApiException("There is no proposals before this date");
        }
        return rfps;
    }

    //providerView
    // must hide offers from providers
    public Set<Rfp> findProposalsByLocation(String location){
        Set<Rfp> rfps = rfpRepository.findAllByLocationEqualsIgnoreCase(location);
        if (rfps.isEmpty()){
            throw new ApiException("There is no proposals in this city");
        }
        return rfps;
    }

    public Set<Rfp> findProposalsByCompanyName(String name){
        Set<Rfp> rfps = rfpRepository.findAllByName(name);
        if (rfps.isEmpty()){
            throw new ApiException("There is no proposals in this city");
        }
        return rfps;
    }



}
