package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.models.Methodology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodologyRepository extends JpaRepository<Methodology, String> {
}
