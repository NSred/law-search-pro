package com.udd.lawsearch.government.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GovernmentDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String country;
    private String city;
    private String street;
    private String number;
    private Long governmentTypeId;
    private MultipartFile contract;
    private List<MultipartFile> laws;
}
