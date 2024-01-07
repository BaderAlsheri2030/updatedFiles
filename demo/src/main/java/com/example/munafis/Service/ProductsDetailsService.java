package com.example.munafis.Service;


import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.ProductDetailsDTO;
import com.example.munafis.Model.Product;
import com.example.munafis.Model.ProductDetails;
import com.example.munafis.Model.Provider;
import com.example.munafis.Repository.ProductDetailsRepository;
import com.example.munafis.Repository.ProductRepository;
import com.example.munafis.Repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductsDetailsService {

    private final ProductDetailsRepository productsDetailsRepository;
    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;





     //Only provider
    public void addProductsDetails(ProductDetailsDTO productDetalisDTO){
        Product product= productRepository.findProductById(productDetalisDTO.getProduct_id());

        if(product==null){
            throw new ApiException("Product id not found");
        }
        ProductDetails productsDetails = new ProductDetails(null,productDetalisDTO.getQuantity(),null,null);
        productsDetailsRepository.save(productsDetails);
    }


    public void updateProductsDetails(Integer id,ProductDetailsDTO productDetailsDTO){

        Product product = productRepository.findProductById(productDetailsDTO.getProduct_id());
        if(product==null){
            throw new ApiException("Product id not found");
        }

        ProductDetails oldproductDetails = productsDetailsRepository.findProductDetailsById(id);
        if(oldproductDetails==null){
            throw new ApiException("product details id not found");
        }
        oldproductDetails.setQuantity(productDetailsDTO.getQuantity());
        productsDetailsRepository.save(oldproductDetails);

    }



    public void addStock(Integer provider_id, Integer product_id , Integer quantity){
        Provider provider= providerRepository.findProviderById(provider_id);
        Product product = productRepository.findProductById(product_id);

        if(provider==null){
            throw new ApiException("provider id not found");
        }
        if(product==null){
            throw new ApiException("product id not found");
        }

        product.setStock(product.getStock()+quantity);
//        product.getProductsDetails().setQuantity(quantity+product.getProductsDetails().getQuantity());
        productRepository.save(product);
    }


}
