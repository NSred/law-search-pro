package com.udd.lawsearch.shared;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
//@Entity
public class GeoLocation implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String country;
    private String city;
    private String street;
    private String number;
    private double latitude;
    private double longitude;
}
