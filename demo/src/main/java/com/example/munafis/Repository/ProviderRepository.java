package com.example.munafis.Repository;

import com.example.munafis.Model.Offer;
import com.example.munafis.Model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Integer> {
    Provider findProviderById(Integer id);


    Provider findByCompanyName(String companyName);
}
