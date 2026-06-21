package com.swp.scijournal.paper.repository;

import com.swp.scijournal.paper.entity.ResearchPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchPaperRepository extends JpaRepository<ResearchPaper, Long> {
    boolean existsByDoi(String doi);
}