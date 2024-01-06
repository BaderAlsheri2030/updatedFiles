package com.example.demo.Controller;

import com.example.demo.DTO.RfpDTO;
import com.example.demo.Service.RfpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/proposal")
@RequiredArgsConstructor
public class RfpController {

    private final RfpService rfpService;

    @GetMapping("get-all-proposals")
    public ResponseEntity getAllProposals(){
        return ResponseEntity.status(200).body(rfpService.getAll());
    }

    //create proposal using the company id and the competition id
    @PostMapping("{company_id}/create-proposal/{comp_id}")
    public ResponseEntity addProposal(@PathVariable Integer company_id,@PathVariable Integer comp_id, @Valid @RequestBody RfpDTO rfpDTO){
        rfpService.addRfp(rfpDTO,company_id,comp_id);
        return ResponseEntity.status(200).body("Proposal created");
    }

    @PutMapping("{company_id}/update-proposal/{rfp_id}")
    public ResponseEntity updateProposal(@PathVariable Integer rfp_id,@PathVariable Integer company_id,@Valid @RequestBody RfpDTO rfpDTO){
        rfpService.updateRfp(rfp_id,rfpDTO,company_id);
        return ResponseEntity.status(200).body("Proposal updated");
    }

    @DeleteMapping("delete-proposal/{rfp_id}")
    public ResponseEntity deleteProposal(@PathVariable Integer rfp_id){
        rfpService.deleteRfp(rfp_id);
        return ResponseEntity.status(200).body("Proposal deleted");
    }


}
