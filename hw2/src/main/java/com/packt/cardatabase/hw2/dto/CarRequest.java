// Gabriel J 9/25/26 ~ 1330

package com.packt.cardatabase.hw2.dto;

// Standardized CarRequest utilized by Controller
public record CarRequest(
        String brand,
        String model,
        String color,
        String registrationNumber,
        int modelYear,
        int price,
        Long ownerId
) {}
