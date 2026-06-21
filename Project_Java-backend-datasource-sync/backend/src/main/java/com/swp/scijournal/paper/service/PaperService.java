package com.swp.scijournal.paper.service;

import com.swp.scijournal.paper.entity.ResearchPaper;
import com.swp.scijournal.paper.repository.ResearchPaperRepository;
import com.swp.scijournal.paper.dto.PaperSummaryResponse;
import com.swp.scijournal.paper.dto.PaperDetailResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;

@Service
public class PaperService {

    private final ResearchPaperRepository researchPaperRepository;

    public PaperService(ResearchPaperRepository researchPaperRepository) {
        this.researchPaperRepository = researchPaperRepository;
    }

    @Transactional(readOnly = true)
    public List<PaperSummaryResponse> search(String title, String author, String journal, Integer year) {
        List<ResearchPaper> papers = researchPaperRepository.findAll();
        List<PaperSummaryResponse> responses = new ArrayList<>();
        
        for (ResearchPaper paper : papers) {
            PaperSummaryResponse res = new PaperSummaryResponse();
            responses.add(res);
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public PaperDetailResponse getDetail(Long id) {
        researchPaperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài báo với ID: " + id));
        
        return new PaperDetailResponse();
    }
}