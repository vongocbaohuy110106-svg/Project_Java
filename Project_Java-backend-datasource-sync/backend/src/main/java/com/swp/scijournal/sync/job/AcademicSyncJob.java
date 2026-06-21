package com.swp.scijournal.sync.job;

import com.swp.scijournal.sync.service.SyncService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AcademicSyncJob {

    private final SyncService syncService;

    public AcademicSyncJob(SyncService syncService) {
        this.syncService = syncService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void cronSyncJob() {
        System.out.println("--- Bắt đầu chạy Job tự động đồng bộ bài báo định kỳ ---");
        String[] defaultKeywords = {"Artificial Intelligence", "Machine Learning", "Data Science"};
        
        for (String keyword : defaultKeywords) {
            try {
                syncService.syncDataByKeyword(keyword, 20);
            } catch (Exception e) {
                System.err.println("Lỗi khi chạy Sync Job cho từ khóa: " + keyword + " - " + e.getMessage());
            }
        }
        System.out.println("--- Hoàn thành Job đồng bộ tự động ---");
    }
}