package com.example.munafis.Repository;

import com.example.munafis.DTO.RfpDTO;
import com.example.munafis.Model.Rfp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface RfpRepository extends JpaRepository<Rfp,Integer> {
    Rfp findRfpById(Integer id);
    Set<Rfp> findAllByName(String name);
    Set<Rfp> findAllByLocationEqualsIgnoreCase(String city);
    Set<Rfp> findAllByDeadLineBefore(LocalDate date);
}
