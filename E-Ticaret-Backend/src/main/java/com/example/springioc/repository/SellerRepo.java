package com.example.springioc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Seller;

public interface SellerRepo extends JpaRepository<Seller, Long> {
    Optional<Seller> findByUser_Id(Long userId);

    @Query("SELECT s.user FROM Seller s WHERE s.id = :sellerID")
    MyUser findUserBySellerID(@Param("sellerID") Long sellerID);
}
