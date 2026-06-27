package com.swp.scijournal.admin.service;

import com.swp.scijournal.sync.service.SyncService;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final SyncService syncService;

    public AdminService(SyncService syncService) {
        this.syncService = syncService;
    }

    public void runSync() {
        String[] keywords = {"Artificial Intelligence", "Machine Learning", "Data Science"};
        
        for (String keyword : keywords) {
            syncService.syncDataByKeyword(keyword, 10);
        }
    }
}