package com.taguz91.api_serena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taguz91.api_serena.models.Register;
import org.springframework.stereotype.Repository;

@Repository

public interface RegisterRepository extends JpaRepository<Register, Object>{
    
}
