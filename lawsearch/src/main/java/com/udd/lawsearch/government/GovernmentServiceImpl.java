package com.udd.lawsearch.government;

import com.udd.lawsearch.shared.filestorage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GovernmentServiceImpl implements GovernmentService {
    private final GovernmentRepository governmentRepository;

    public void create(Government government) {
        governmentRepository.save(government);
    }
}
