package com.swp.scijournal.admin.controller;

import com.swp.scijournal.admin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/sync")
    public ResponseEntity<?> triggerSync() {
        adminService.runSync();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Kích hoạt đồng bộ dữ liệu thành công!");
        
        return ResponseEntity.ok(response);
    }
}