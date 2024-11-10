package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.models.MethodologyEmotion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodologyEmotionRepository extends JpaRepository<MethodologyEmotion, String> {
    @Modifying
    @Transactional
    @Query(value = "delete from methodologies_emotions me where methodology_id = :idMethodology", nativeQuery = true)
    public void deleteByMethodologyId(@Param("idMethodology") String idMethodology);
}
