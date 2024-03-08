package com.goalracha.controller;

import com.goalracha.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // userId를 @PathVariable에서 추출하는 부분은 올바르나, 요청 본문 처리를 위한 수정이 필요
    @PutMapping("/{userId}/change-password")
    public String changePassword(@PathVariable String userId, @RequestBody PasswordChangeRequest request) {
        // System.out.println("Received newPassword: " + request.getNewPassword()); // 로깅 추가
        log.info(request.getNewPassword());
        adminService.changePassword(userId, request.getNewPassword());
        return "Password changed successfully";
    }

    // 요청 본문을 위한 DTO
    public static class PasswordChangeRequest {
        private String newPassword;

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
