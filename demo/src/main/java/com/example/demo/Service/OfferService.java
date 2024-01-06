package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.DTO.OfferDTO;
import com.example.demo.Model.Offer;
import com.example.demo.Model.Provider;
import com.example.demo.Model.Rfp;
import com.example.demo.Repository.OffersRepository;
import com.example.demo.Repository.ProviderRepository;
import com.example.demo.Repository.RfpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OffersRepository offerRepository;
    private final RfpRepository rfpRepository;
    private final ProviderRepository providerRepository;
    public List<Offer> getAllOffers(){
        return offerRepository.findAll();
    }

    //authenticate provider id
    //add offer if provider is authenticated and there's an existent rfp with valid status
    public void addOffer(OfferDTO offerDTO, Integer rfp_id,Integer provider_id){
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        Provider provider = providerRepository.findProviderById(provider_id);
        if (rfp == null){
            throw new ApiException("Proposal doesn't exist");
        }
        if (rfp.isComplete()){
            throw new ApiException("Sorry you cannot make an offer for the proposal because it's completed");
        }
        Offer offer = new Offer(null,offerDTO.getDescription(),offerDTO.getDead_line(),offerDTO.getPrice(),"pending",offerDTO.getConditions(),rfp,provider);
        offerRepository.save(offer);
    }

    public void updateOffer(Integer offer_id,OfferDTO offerDTO){
        Offer offer = offerRepository.findOfferById(offer_id);
        if (offer == null){
            throw new ApiException("invalid id");
        }
        if (offer.getRfp().isComplete()){
            throw new ApiException("Sorry you cannot updated this offer, the proposal is completed");
        }
        offer.setConditions(offerDTO.getConditions());
        offer.setPrice(offerDTO.getPrice());
        offer.setDeadLine(offerDTO.getDead_line());
        offer.setDescription(offerDTO.getDescription());
        offerRepository.save(offer);
    }

    public void withdrawOffer(Integer id){
        Offer offer = offerRepository.findOfferById(id);
        if (offer == null){
            throw new ApiException("invalid");
        }
        if (offer.getStatus().equals("accepted")){
            throw new ApiException("you can't withdraw the offer, contact the company that received your offer");
        }
        offerRepository.delete(offer);
    }

}
