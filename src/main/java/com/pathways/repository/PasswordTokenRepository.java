package com.pathways.repository;

import com.pathways.models.ApplicationUser;
import com.pathways.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    List<PasswordResetToken> findByExpiryDateLessThan(Date currentDate);

    PasswordResetToken findByToken(String token);
    ApplicationUser findUserByToken(String token);

    @Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.expiryDate <= :currentDate")
    void deleteExpiredTokens(@Param("currentDate") Date currentDate);

    PasswordResetToken findPasswordResetTokenByUser(ApplicationUser user);
}
