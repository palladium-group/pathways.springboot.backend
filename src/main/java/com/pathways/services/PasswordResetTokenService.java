package com.pathways.services;

import com.pathways.models.ApplicationUser;
import com.pathways.models.PasswordResetToken;
import com.pathways.repository.PasswordTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PasswordResetTokenService {
    private PasswordTokenRepository passwordTokenRepository;

    public PasswordResetTokenService(PasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public ApplicationUser getUserByValidToken(String token) {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);

        // Check if the token exists and is not expired
        if (passwordResetToken != null && !passwordResetToken.isTokenExpired()) {
            return passwordResetToken.getUser();
        }

        return null; // Token is invalid or expired
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs at midnight every day
    public void removeExpiredTokens() {
        List<PasswordResetToken> expiredTokens = passwordTokenRepository.findByExpiryDateLessThan(new Date());
        passwordTokenRepository.deleteAll(expiredTokens);
    }
}
