package com.example.demo.Controller;

import com.example.demo.DTO.OfferDTO;
import com.example.demo.Service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/offer")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping("get-all-offers")
    public ResponseEntity getAllOffers(){
        return ResponseEntity.status(200).body(offerService.getAllOffers());
    }

    @PostMapping("{provider_id}/create-offer/{rfp_id}")
    public ResponseEntity createOffer(@PathVariable Integer rfp_id,@PathVariable Integer  provider_id, @Valid @RequestBody OfferDTO offerDTO){
        offerService.addOffer(offerDTO,rfp_id,provider_id);
        return ResponseEntity.status(200).body("Offer created");
    }

    @PutMapping("/update-offer/{offer_id}")
    public ResponseEntity updateOffer(@PathVariable Integer offer_id, @Valid @RequestBody OfferDTO offerDTO){
        offerService.updateOffer(offer_id,offerDTO);
        return ResponseEntity.status(200).body("Offer updated");
    }

    @DeleteMapping("/delete-offer/{offer_id}")
    public ResponseEntity withdrawOffer(@PathVariable Integer offer_id){
        offerService.withdrawOffer(offer_id);
        return ResponseEntity.status(200).body("Offer withdrawn");
    }
}
