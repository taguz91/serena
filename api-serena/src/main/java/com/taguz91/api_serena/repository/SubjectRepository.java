package com.taguz91.api_serena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taguz91.api_serena.models.Subject;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

}
