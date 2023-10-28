package com.review.servicemanagement.models;


import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Address extends BaseModel{
    private String streetName;
    private String houseNumber;
    private String location;
    private int pincode;
}
