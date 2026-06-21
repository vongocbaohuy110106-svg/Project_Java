package com.swp.scijournal.datasource.semanticscholar;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp.scijournal.datasource.dto.ExternalPaperRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SemanticScholarClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ExternalPaperRecord> fetchPapers(String keyword, int limit) {
        List<ExternalPaperRecord> records = new ArrayList<>();
        String url = "https://api.semanticscholar.org/graph/v1/paper/search?query=" + keyword + "&limit=" + limit + "&fields=title,authors,externalIds,year,venue,citationCount";

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode data = root.path("data");

            for (JsonNode item : data) {
                List<String> authors = new ArrayList<>();
                item.path("authors").forEach(a -> authors.add(a.path("name").asText()));

                String title = item.path("title").asText("Unknown Title");
                String doi = item.path("externalIds").path("DOI").asText(null);
                Integer year = item.path("year").isNull() ? null : item.path("year").asInt();
                String journal = item.path("venue").asText("N/A");
                Long citations = item.path("citationCount").asLong(0L);

                records.add(new ExternalPaperRecord(title, authors, doi, year, journal, citations, "SEMANTIC_SCHOLAR"));
            }
        } catch (Exception e) {
            System.err.println("Semantic Scholar API Error: " + e.getMessage());
        }
        return records;
    }
}