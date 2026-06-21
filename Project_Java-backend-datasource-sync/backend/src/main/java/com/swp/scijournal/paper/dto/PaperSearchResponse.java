package com.swp.scijournal.paper.dto;

public class PaperSearchResponse {
    private String title;
    private String doi;

    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }

    public void setDoi(String doi) { this.doi = doi; }
    public String getDoi() { return doi; }
}