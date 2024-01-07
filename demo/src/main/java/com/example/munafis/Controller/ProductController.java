package com.example.munafis.Controller;


import com.example.munafis.DTO.ProductDTO;
import com.example.munafis.Model.Product;
import com.example.munafis.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;



    //ALL
    @GetMapping("/get")
    public ResponseEntity getAllProducts(){
        return ResponseEntity.status(200).body(productService.getAllProducts());

    }


    //Only provider
    @PostMapping("/add")
    public ResponseEntity addProduct(@Valid @RequestBody ProductDTO productDTO){
        productService.addProduct(productDTO);
        return  ResponseEntity.status(200).body("product added");
    }


    //Only provider
    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id ,@Valid @RequestBody ProductDTO productDTO){

        productService.updateProduct(id,productDTO);
        return  ResponseEntity.status(200).body("product updated");
    }



    //Only provider
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
        return  ResponseEntity.status(200).body("product deleted");
    }




    //Only provider
    @GetMapping("/getProductInfo/{id}")
    public ResponseEntity getProductInfo(@PathVariable Integer id){
       Product product = productService.displayProductInfo(id);
        return  ResponseEntity.status(200).body(product);

    }

    @GetMapping("/getAllByOrderByPrice")
    public ResponseEntity getAllByOrderByPrice(){
        List<Product> products = productService.getAllByOrderByPrice();
        return  ResponseEntity.status(200).body(products);
    }




    @GetMapping("/getProductsByName/{name}")
    public ResponseEntity getProductsByName(@PathVariable String name){
        List<Product> products = productService.getProductsByName(name);
        return  ResponseEntity.status(200).body(products);
    }



//ALL
//    @GetMapping("/getAllProductsByProvider/{companyName}")
//    private ResponseEntity getAllProductsByProvider(@PathVariable String companyName){
//        return ResponseEntity.status(200).body(productService.getProductsByCompanyName(companyName));
//    }

}
