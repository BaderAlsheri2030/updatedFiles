package com.example.munafis.Repository;

import com.example.munafis.Model.Orderr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orderr,Integer> {
    List<Orderr> findAllByStatus(String Status);
    List<Orderr> findAllByStatusEqualsAndCompanyId(String Status,Integer company_id);




}
