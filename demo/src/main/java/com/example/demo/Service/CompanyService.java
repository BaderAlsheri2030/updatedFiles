package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.Model.Company;
import com.example.demo.Repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;


    public List getAllCompanies(){
        return companyRepository.findAll();
    }

    //Register
    public void addCompany(Company company){
        companyRepository.save(company);
    }

    public void updateCompany(Integer id, Company company){
        Company oldCompany=companyRepository.findCompanyById(id);
        if(oldCompany==null){
            throw new ApiException("company id not found");
        }
        oldCompany.setAddress(company.getAddress());
        oldCompany.setBusinessNumber(company.getBusinessNumber());
        oldCompany.setOrders(company.getOrders());
        oldCompany.setCompanyName(company.getCompanyName());
        oldCompany.setEmail(company.getEmail());
        oldCompany.setRfps(company.getRfps());

        companyRepository.save(oldCompany);

    }



    public void deleteCompany(Integer id){
        Company company=companyRepository.findCompanyById(id);
        if(company==null){
            throw new ApiException("company id not found");
        }
        companyRepository.delete(company);
    }

}
