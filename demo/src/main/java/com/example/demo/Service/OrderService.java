package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.Model.Company;
import com.example.demo.Model.OrderModel;
import com.example.demo.Model.Product;
import com.example.demo.Repository.*;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
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


        Set<Product> productList;
        Set<com.example.demo.Model.Service> serviceList;

        Set<Product> products = new HashSet<>();
        Set<com.example.demo.Model.Service> services = new HashSet<>();

        productList = orderDTO.getProducts();
        serviceList = orderDTO.getServices();

        for (Product product : productList) {
            Product product1 = productRepository.findProductById(product.getId());
            if (product1 != null) {
                products.add(product);
            } else throw new ApiException("invalid product input");
        }

        for (com.example.demo.Model.Service service : serviceList) {
            com.example.demo.Model.Service service1 = serviceRepository.findServiceById(service.getId());
            if (service1 != null) {
                services.add(service);
            } else throw new ApiException("invalid service input");
        }

        Company company = companyRepository.findCompanyById(orderDTO.getCompany_id());
        if (company == null) {
            throw new ApiException("company not found");
        }
        double totalPriceProduct = 0;
        double totalPriceService = 0;
        double totalPrice = 0;


        for (Product product:products) {
            //stock not quantity
            if (product.getProductsDetails().getQuantity() <= 0) {
                throw new ApiException("product " + product.getName() + " is not available now");
            } else {

                //Calculate the total price
                totalPriceProduct += product.getPrice() * product.getProductsDetails().getQuantity();

                //product.getProductsDetails().setQuantity(product.getProductsDetails().getQuantity() - orderDTO.getQuantity());
                productsDetailsRepository.save(product.getProductsDetails());
            }
        }
                for (com.example.demo.Model.Service service:services){
                totalPriceService += service.getPrice();

        }

        totalPrice = totalPriceProduct + totalPriceService;

        OrderModel orderModel = new OrderModel(null, orderDTO.getStatus(), totalPrice, services, products, company);
        orderRepository.save(orderModel);
    }
}