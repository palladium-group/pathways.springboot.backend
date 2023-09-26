package com.pathways.services;

import com.pathways.models.ApplicationUser;
import com.pathways.models.PasswordResetToken;
import com.pathways.models.UserPermission;
import com.pathways.repository.PasswordTokenRepository;
import com.pathways.repository.UserPermissionRepository;
import com.pathways.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private PasswordEncoder encoder;
    private UserRepository userRepository;
    private UserPermissionRepository userPermissionRepository;
    private PasswordTokenRepository passwordTokenRepository;
    private EmailService emailService;
    private Environment environment;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder encoder,
            UserPermissionRepository userPermissionRepository,
            PasswordTokenRepository passwordTokenRepository, EmailService emailService, Environment environment) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userPermissionRepository = userPermissionRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
        this.environment = environment;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ApplicationUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<String> getUserPermissions(String username) {
        Optional<ApplicationUser> user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<UserPermission> userPermissions = userPermissionRepository.findByUser(user);
        return userPermissions.stream()
                .map(userPermission -> userPermission.getRoute())
                .collect(Collectors.toList());
    }

    public List<String> getUserPermissions(Integer userId) {
        Optional<ApplicationUser> user = userRepository.findById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<UserPermission> userPermissions = userPermissionRepository.findByUser(user);
        return userPermissions.stream()
                .map(userPermission -> userPermission.getRoute())
                .collect(Collectors.toList());
    }

    public void resetPassword(ApplicationUser user, String password) {
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void initiatePasswordReset(String email) throws MessagingException {
        Context context = new Context();
        String host = environment.getProperty("allowed.origins");
        ApplicationUser user = userRepository.findByEmail(email);
        if (user != null) {
            PasswordResetToken token = passwordTokenRepository.findPasswordResetTokenByUser(user);
            if (token == null || token.isTokenExpired()) {
                String resetToken = this.generateResetToken();
                PasswordResetToken myToken = new PasswordResetToken(resetToken, user);
                passwordTokenRepository.save(myToken);
                context.setVariable("message", resetToken);
                context.setVariable("host", host);
                emailService.sendEmailWithHtmlTemplate(user.getEmail(), "Password Reset E-mail", "email-template", context);
            } else {
                context.setVariable("message", token.getToken());
                context.setVariable("host", host);
                emailService.sendEmailWithHtmlTemplate(user.getEmail(), "Password Reset E-mail", "email-template", context);
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void initiateConfirmUserAccount(ApplicationUser user) throws MessagingException {
        Context context = new Context();
        PasswordResetToken token = passwordTokenRepository.findPasswordResetTokenByUser(user);
        if (token == null || token.isTokenExpired()) {
            String resetToken = this.generateResetToken();
            PasswordResetToken myToken = new PasswordResetToken(resetToken, user);
            passwordTokenRepository.save(myToken);
            context.setVariable("message", resetToken);
            context.setVariable("name", user.getFirstName());
            emailService.sendEmailWithHtmlTemplate(user.getEmail(), "Verify Account E-mail", "register-account-template", context);
        } else {
            context.setVariable("message", token.getToken());
            context.setVariable("name", user.getFirstName());
            emailService.sendEmailWithHtmlTemplate(user.getEmail(), "Verify Account E-mail", "register-account-template", context);
        }
    }

    public String generateResetToken() {
        return UUID.randomUUID().toString();
    }
}
