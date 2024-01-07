package com.example.munafis.Service;

import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.OrderDTO;
import com.example.munafis.Model.Company;
import com.example.munafis.Model.Orderr;
import com.example.munafis.Model.Product;
import com.example.munafis.Model.ProductDetails;
import com.example.munafis.Repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ServiceRepository serviceRepository;
    private final CompanyRepository companyRepository;
    private final ProductDetailsRepository productsDetailsRepository;


    //Admin
    public List getAllOrders() {

        return orderRepository.findAll();
    }


    public void addOrder(OrderDTO orderDTO) {
        double totalPriceProduct = 0;
        double totalPriceService = 0;
        double totalPrice = 0;
        double price = 0;


        Company company = companyRepository.findCompanyById(orderDTO.getCompany_id());

        if(company==null){
            throw new ApiException("company id nit found");
        }

        Set<ProductDetails> productsDetails = new HashSet<>();
        Set<com.example.munafis.Model.Service> serviceList = new HashSet<>();

//     Set<Product> products = new HashSet<>();
//     Set<com.example.munafis.Model.Service> services = new HashSet<>();


        for (ProductDetails productsDetails1 : orderDTO.getProductsDetails()) {

            Product product = productRepository.findProductById(productsDetails1.getId());
            if (product == null) {
                throw new ApiException("product id not found");
            }
            Product foundProduct = productRepository.findProductById(product.getId());

            if (foundProduct == null) {
                throw new ApiException("Product not found for id: " + product.getId());
            }
            price += product.getPrice() * productsDetails1.getQuantity();
            ProductDetails productsDetails2 = new ProductDetails(null, productsDetails1.getQuantity(),foundProduct,null);
            totalPriceProduct += price;
            productsDetailsRepository.save(productsDetails2);
            productsDetails.add(productsDetails2);
        }

        for (com.example.munafis.Model.Service s : orderDTO.getServices()) {

            com.example.munafis.Model.Service service = serviceRepository.findServiceById(s.getId());
            if (service == null) {
                throw new ApiException("service not found");
            }
            totalPriceService += s.getPrice();
            serviceList.add(s);
        }
        totalPrice = totalPriceProduct + totalPriceService;
        Orderr orderr = new Orderr(null, "pending", totalPrice, serviceList, productsDetails, company);
        orderRepository.save(orderr);


        for (ProductDetails productsDetails1: orderr.getProductsDetails()){
            productsDetails1.setOrder(orderr);
            productsDetailsRepository.save(productsDetails1);
        }
        for(com.example.munafis.Model.Service service: orderr.getServices()){
            service.setOrder(orderr);
            serviceRepository.save(service);
        }


    }
}
