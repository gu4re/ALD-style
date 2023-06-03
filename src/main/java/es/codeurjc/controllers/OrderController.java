package es.codeurjc.controllers;

import es.codeurjc.exceptions.UnsupportedExportException;
import es.codeurjc.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controls the mapping of the cart page and all related to
 * add and keep the control of products, in this case, shoes
 * @author gu4re
 * @version 1.6
 */
@Controller
public class OrderController {

    @Autowired
    @SuppressWarnings(value = "unused")
    private OrderService orderService;

    @GetMapping("/order")
    public ResponseEntity<String> export() {
        try{
            return ResponseEntity.ok(orderService.export().toString());
        } catch (UnsupportedExportException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
