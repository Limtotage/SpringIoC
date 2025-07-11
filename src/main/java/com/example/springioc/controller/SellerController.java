package com.example.springioc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.SellerDTO;
import com.example.springioc.service.SellerService;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
    @PostMapping
    public ResponseEntity<SellerDTO> CreateSeller(@RequestBody SellerDTO sellerDTO) {
        return ResponseEntity.ok(sellerService.CreateSeller(sellerDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> GetAllSellers() {
        return ResponseEntity.ok(sellerService.GetAllSellers());
    }

    @PutMapping("/{id}")
    @PreAuthorize(" hasAnyRole('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<SellerDTO> UpdateSeller(@PathVariable Long id, @RequestBody SellerDTO sellerDTO) {
        return ResponseEntity.ok(sellerService.UpdateSeller(id, sellerDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<String> DeleteSeller(@PathVariable Long id) {
        sellerService.DeleteSeller(id);
        return ResponseEntity.ok("Seller deleted successfully");
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> DeleteSellerAdmin(@PathVariable Long id) {
        sellerService.DeleteSellerAdmin(id);
        return ResponseEntity.ok("Seller deleted successfully");
    }
}
