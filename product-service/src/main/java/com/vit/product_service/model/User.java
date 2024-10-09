package com.vit.product_service.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    private String id;
    private String name;
    private String phone;
    private String password;
}
