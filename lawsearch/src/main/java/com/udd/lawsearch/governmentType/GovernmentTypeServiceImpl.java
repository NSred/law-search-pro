package com.udd.lawsearch.governmentType;

import com.udd.lawsearch.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GovernmentTypeServiceImpl implements GovernmentTypeService{
    private final GovernmentTypeRepository governmentTypeRepository;
    private final String entityName = "Government type";
    public GovernmentType findById(Long id) throws EntityNotFoundException {
        return governmentTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }
}
