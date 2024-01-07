package com.example.munafis.Repository;

import com.example.munafis.Model.Product;
import com.example.munafis.Model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails,Integer> {

    ProductDetails findProductDetailsById(Integer id);

}
