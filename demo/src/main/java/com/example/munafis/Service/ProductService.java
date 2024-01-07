package com.example.munafis.Service;

import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.ProductDTO;
import com.example.munafis.Model.Product;
import com.example.munafis.Model.Provider;
import com.example.munafis.Repository.ProductRepository;
import com.example.munafis.Repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;


    //ALL
    public List getAllProducts(){

        return productRepository.findAll();
    }



    //Only provider
//    public List getMyProducts(){
//
//    }


    //Only provider
    public void addProduct(ProductDTO productDTO){
        Provider provider = providerRepository.findProviderById(productDTO.getProvider_id());
        if(provider==null){
            throw new ApiException("provider Id not found");
        }

        Product product = new Product(null,productDTO.getName(),productDTO.getPrice(),productDTO.getStock(),provider,null);
        productRepository.save(product);
    }




    //Only provider
    public void updateProduct(Integer id,ProductDTO productDTO){

        Product oldProduct = productRepository.findProductById(id);
        if(oldProduct==null){
            throw new ApiException("Product Id not found");
        }

        oldProduct.setName(productDTO.getName());
        oldProduct.setPrice(productDTO.getPrice());
        productRepository.save(oldProduct);
    }




    //Only provider
    public void deleteProduct(Integer id){
        Product product=productRepository.findProductById(id);
        if(product==null){
            throw new ApiException("product id  not found");
        }
        productRepository.delete(product);
    }



    //ALL
    public List getProductsByName(String name){
        List<Product> products =productRepository.findProductsByName(name);
        if(products.isEmpty()){
            throw new ApiException("no products same this name");
        }
        return products;
    }



    //All
    public Product displayProductInfo(Integer id){
        Product product=productRepository.findProductById(id);
        if(product==null){
            throw new ApiException("product id not found");
        }
        return product;
    }



    //All
    public List getAllByOrderByPrice(){
        List<Product> products =productRepository.findAllByOrderByPrice();
        if(products==null){
            throw new ApiException("no products");
        }
        return products;
    }





}
