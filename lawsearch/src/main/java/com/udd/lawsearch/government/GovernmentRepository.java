package com.udd.lawsearch.government;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentRepository extends JpaRepository<Government, Long> {
}
