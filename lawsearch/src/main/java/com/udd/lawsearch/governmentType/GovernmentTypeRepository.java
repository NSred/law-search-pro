package com.udd.lawsearch.governmentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentTypeRepository extends JpaRepository<GovernmentType, Long> {
}
