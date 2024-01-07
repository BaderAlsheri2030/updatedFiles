package com.example.munafis.Controller;


import com.example.munafis.Model.Company;
import com.example.munafis.Model.Provider;
import com.example.munafis.Service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("get")
    public ResponseEntity getAllCompanies(){
        return ResponseEntity.status(200).body(companyService.getAllCompanies());
    }


    @PostMapping("/add")
    public ResponseEntity addCompany(@Valid @RequestBody Company company ){
        companyService.addCompany(company);
        return ResponseEntity.status(200).body("company added");
    }



    @PutMapping("/update/{id}")
    public ResponseEntity updateCompany(@PathVariable Integer id,@Valid @RequestBody Company company){
        companyService.updateCompany(id, company);
        return ResponseEntity.status(200).body("company updated");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity companyService(@PathVariable Integer id){
        companyService.deleteCompany(id);
        return ResponseEntity.status(200).body("company deleted");
    }
}
