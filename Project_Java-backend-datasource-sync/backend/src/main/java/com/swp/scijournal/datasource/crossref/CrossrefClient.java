package com.swp.scijournal.datasource.crossref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp.scijournal.datasource.dto.ExternalPaperRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrossrefClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ExternalPaperRecord> fetchPapers(String keyword, int limit) {
        List<ExternalPaperRecord> records = new ArrayList<>();
        String url = "https://api.crossref.org/works?query=" + keyword + "&rows=" + limit;

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("message").path("items");

            for (JsonNode item : items) {
                List<String> authors = new ArrayList<>();
                if (item.has("author")) {
                    item.path("author").forEach(a -> authors.add(a.path("given").asText("") + " " + a.path("family").asText("")));
                }

                String title = item.path("title").get(0) != null ? item.path("title").get(0).asText() : "Unknown Title";
                String doi = item.path("DOI").asText(null);
                Integer year = item.path("created").path("date-parts").get(0) != null ? item.path("created").path("date-parts").get(0).get(0).asInt() : null;
                String journal = item.path("container-title").get(0) != null ? item.path("container-title").get(0).asText() : "N/A";
                Long citations = item.path("is-referenced-by-count").asLong(0L);

                records.add(new ExternalPaperRecord(title, authors, doi, year, journal, citations, "CROSSREF"));
            }
        } catch (Exception e) {
            System.err.println("CrossRef API Error: " + e.getMessage());
        }
        return records;
    }
}