package com.example.demo.Repository;

import com.example.demo.Model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<Offer,Integer> {
    Offer findOfferById(Integer id);
    List<Offer> findAllByRfpId(Integer id);
    List<Offer> findOffersByRfpIdAndPriceBetween(Integer id,double min,double max);
}
