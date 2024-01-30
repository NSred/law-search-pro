package com.udd.lawsearch.governmentType;

import com.udd.lawsearch.exceptions.EntityNotFoundException;

public interface GovernmentTypeService {
    GovernmentType findById(Long id) throws EntityNotFoundException;
}
