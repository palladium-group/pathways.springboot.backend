package com.pathways.controllers;

import com.pathways.payload.request.ResetPasswordRequest;
import com.pathways.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reset-password")
public class ResetPasswordController {
    private UserService userService;

    public ResetPasswordController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        var user = this.userService.getUserByEmail(request.getEmail());
        if (user != null) {
            this.userService.resetPassword(request.getEmail(), request.getNewPassword());
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);
        }
    }
}
