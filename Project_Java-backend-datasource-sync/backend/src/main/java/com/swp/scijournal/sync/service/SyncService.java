package com.swp.scijournal.sync.service;

import com.swp.scijournal.datasource.crossref.CrossrefClient;
import com.swp.scijournal.datasource.openalex.OpenAlexClient;
import com.swp.scijournal.datasource.semanticscholar.SemanticScholarClient;
import com.swp.scijournal.datasource.dto.ExternalPaperRecord;
import com.swp.scijournal.datasource.repository.ApiDataSourceRepository;
import com.swp.scijournal.paper.entity.ResearchPaper;
import com.swp.scijournal.paper.repository.ResearchPaperRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class SyncService {

    private final CrossrefClient crossrefClient;
    private final OpenAlexClient openAlexClient;
    private final SemanticScholarClient semanticScholarClient;
    private final ApiDataSourceRepository apiDataSourceRepository;
    private final ResearchPaperRepository researchPaperRepository;

    public SyncService(CrossrefClient crossrefClient, OpenAlexClient openAlexClient, 
                       SemanticScholarClient semanticScholarClient, ApiDataSourceRepository apiDataSourceRepository,
                       ResearchPaperRepository researchPaperRepository) {
        this.crossrefClient = crossrefClient;
        this.openAlexClient = openAlexClient;
        this.semanticScholarClient = semanticScholarClient;
        this.apiDataSourceRepository = apiDataSourceRepository;
        this.researchPaperRepository = researchPaperRepository;
    }

    @Transactional
    public void syncDataByKeyword(String keyword, int limit) {
        List<ExternalPaperRecord> rawRecords = new ArrayList<>();

        apiDataSourceRepository.findBySourceName("CROSSREF").ifPresent(source -> {
            if (source.isActive()) {
                rawRecords.addAll(crossrefClient.fetchPapers(keyword, limit));
                source.setLastSyncAt(Instant.now());
                apiDataSourceRepository.save(source);
            }
        });

        apiDataSourceRepository.findBySourceName("OPENALEX").ifPresent(source -> {
            if (source.isActive()) {
                rawRecords.addAll(openAlexClient.fetchPapers(keyword, limit));
                source.setLastSyncAt(Instant.now());
                apiDataSourceRepository.save(source);
            }
        });

        apiDataSourceRepository.findBySourceName("SEMANTIC_SCHOLAR").ifPresent(source -> {
            if (source.isActive()) {
                rawRecords.addAll(semanticScholarClient.fetchPapers(keyword, limit));
                source.setLastSyncAt(Instant.now());
                apiDataSourceRepository.save(source);
            }
        });

        for (ExternalPaperRecord record : rawRecords) {
            if (record.getDoi() == null || researchPaperRepository.existsByDoi(record.getDoi())) {
                continue; 
            }

            ResearchPaper paper = new ResearchPaper();
            paper.setTitle(record.getTitle());
            paper.setDoi(record.getDoi());
            researchPaperRepository.save(paper);
        }
    }
}