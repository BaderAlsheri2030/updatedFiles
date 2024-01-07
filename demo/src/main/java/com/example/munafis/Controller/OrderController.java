package com.example.munafis.Controller;


import com.example.munafis.DTO.OrderDTO;
import com.example.munafis.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;




    @GetMapping("/get")
    public ResponseEntity getAllOrder(){
        return ResponseEntity.status(200).body(orderService.getAllOrders());
    }
    @PostMapping("/add")
    public ResponseEntity addOrder(@Valid @RequestBody OrderDTO orderDTO){
        orderService.addOrder(orderDTO);
        return ResponseEntity.status(200).body("order added");
    }
}
