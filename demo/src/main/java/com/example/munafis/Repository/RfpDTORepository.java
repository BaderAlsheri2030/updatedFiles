package com.example.munafis.Repository;

import com.example.munafis.DTO.RfpDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RfpDTORepository extends JpaRepository<RfpDTO,Integer> {
    List<RfpDTO> findAllByName(String name);

    @Query("SELECT r FROM Rfr WHERE r.deadLine < :date")
    List<RfpDTO> findAllByDeadLineBefore(@Param("date") LocalDate date);
    List<RfpDTO> findAllByLocationEqualsIgnoreCase(String city);
}
