package com.swp.scijournal.datasource.openalex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp.scijournal.datasource.dto.ExternalPaperRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OpenAlexClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ExternalPaperRecord> fetchPapers(String keyword, int limit) {
        List<ExternalPaperRecord> records = new ArrayList<>();
        String url = "https://api.openalex.org/works?search=" + keyword + "&per_page=" + limit;

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.path("results");

            for (JsonNode item : results) {
                List<String> authors = new ArrayList<>();
                item.path("authorships").forEach(a -> authors.add(a.path("author").path("display_name").asText()));

                String title = item.path("display_name").asText("Unknown Title");
                String doi = item.path("doi").asText(null);
                Integer year = item.path("publication_year").asInt();
                String journal = item.path("primary_location").path("source").path("display_name").asText("N/A");
                Long citations = item.path("cited_by_count").asLong(0L);

                records.add(new ExternalPaperRecord(title, authors, doi, year, journal, citations, "OPENALEX"));
            }
        } catch (Exception e) {
            System.err.println("OpenAlex API Error: " + e.getMessage());
        }
        return records;
    }
}