package com.udd.lawsearch.government;

import com.udd.lawsearch.governmentType.GovernmentType;
import com.udd.lawsearch.shared.GeoLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Government implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    @ManyToOne
    private GovernmentType governmentType;
    @Embedded
    private GeoLocation location;
}
