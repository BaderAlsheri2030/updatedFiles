package com.example.demo.Repository;

import com.example.demo.Model.Offer;
import com.example.demo.Model.Rfp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RfpRepository extends JpaRepository<Rfp,Integer> {
    Rfp findRfpById(Integer id);
}
