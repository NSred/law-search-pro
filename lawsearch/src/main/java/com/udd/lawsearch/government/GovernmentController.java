package com.udd.lawsearch.government;

import com.udd.lawsearch.elastic.contract.ContractIndexService;
import com.udd.lawsearch.elastic.law.LawIndexService;
import com.udd.lawsearch.exceptions.EntityNotFoundException;
import com.udd.lawsearch.government.dto.GovernmentDto;
import com.udd.lawsearch.governmentType.GovernmentType;
import com.udd.lawsearch.governmentType.GovernmentTypeService;
import com.udd.lawsearch.shared.GeoLocation;
import com.udd.lawsearch.shared.filestorage.FileStorageService;
import com.udd.lawsearch.shared.location.GeoCodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/government")
public class GovernmentController {
    private final GovernmentService governmentService;
    private final GovernmentTypeService governmentTypeService;
    private final FileStorageService fileStorageService;
    private final ContractIndexService contractIndexService;
    private final GeoCodingService geoCodingService;
    private final LawIndexService lawIndexService;

    @PostMapping
    public ResponseEntity<Void> createGovernment(@ModelAttribute GovernmentDto dto) throws Exception {
        Government gov = mapDtoToGovernment(dto);
        governmentService.create(gov);
        fileStorageService.uploadFile(dto.getContract());
        for (MultipartFile file : dto.getLaws()) {
            fileStorageService.uploadFile(file);
        }
        contractIndexService.create(dto.getContract().getOriginalFilename());
        lawIndexService.create(dto.getLaws());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private Government mapDtoToGovernment(GovernmentDto dto) throws EntityNotFoundException {
        Government government = new Government();
        GovernmentType govType = governmentTypeService.findById(dto.getGovernmentTypeId());
        String address = dto.getNumber() + "," + dto.getStreet() + "," + dto.getCity() + "," + dto.getCountry();
        var coordinates = geoCodingService.getCoordinates(address);
        GeoLocation location = new GeoLocation(dto.getCountry(), dto.getCity(), dto.getStreet(), dto.getNumber(), coordinates[0], coordinates[1]);
        government.setName(dto.getName());
        government.setGovernmentType(govType);
        government.setEmail(dto.getEmail());
        government.setPhoneNumber(dto.getPhoneNumber());
        government.setLocation(location);
        return government;
    }
}
