package com.pathways.controllers;

import com.pathways.models.ApplicationUser;
import com.pathways.payload.request.NewPasswordRequest;
import com.pathways.payload.request.ResetPasswordRequest;
import com.pathways.services.PasswordResetTokenService;
import com.pathways.services.UserService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/reset-password")
public class ResetPasswordController {
    private UserService userService;
    private PasswordResetTokenService passwordResetTokenService;

    public ResetPasswordController(UserService userService, PasswordResetTokenService passwordResetTokenService) {
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @PostMapping("/new-password")
    public ResponseEntity<String> newPasswordRequest(@RequestBody NewPasswordRequest request) {
        try {
            ApplicationUser user = passwordResetTokenService.getUserByValidToken(request.getResetToken());
            if (user != null) {
                this.userService.resetPassword(user, request.getNewPassword());
                return new ResponseEntity<>("Successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping("/request")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody ResetPasswordRequest request) throws MessagingException, UnsupportedEncodingException {
        try {
            var user = this.userService.getUserByEmail(request.getEmail());
            if (user != null) {
                this.userService.initiatePasswordReset(request.getEmail());
                return new ResponseEntity<>("Successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
