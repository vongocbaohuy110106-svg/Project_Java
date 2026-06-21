package com.swp.scijournal.datasource.dto;

import java.util.List;

public class ExternalPaperRecord {
    private String title;
    private List<String> authors;
    private String doi;
    private Integer publicationYear;
    private String journalName;
    private Long citationCount;
    private String sourcePlatform;

    public ExternalPaperRecord() {}

    public ExternalPaperRecord(String title, List<String> authors, String doi, Integer publicationYear, String journalName, Long citationCount, String sourcePlatform) {
        this.title = title;
        this.authors = authors;
        this.doi = doi;
        this.publicationYear = publicationYear;
        this.journalName = journalName;
        this.citationCount = citationCount;
        this.sourcePlatform = sourcePlatform;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> authors) { this.authors = authors; }
    public String getDoi() { return doi; }
    public void setDoi(String doi) { this.doi = doi; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public String getJournalName() { return journalName; }
    public void setJournalName(String journalName) { this.journalName = journalName; }
    public Long getCitationCount() { return citationCount; }
    public void setCitationCount(Long citationCount) { this.citationCount = citationCount; }
    public String getSourcePlatform() { return sourcePlatform; }
    public void setSourcePlatform(String sourcePlatform) { this.sourcePlatform = sourcePlatform; }
}