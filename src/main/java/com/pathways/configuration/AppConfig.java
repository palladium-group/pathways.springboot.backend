package com.pathways.configuration;

import com.pathways.models.ApplicationUser;
import com.pathways.models.Permission;
import com.pathways.models.Role;
import com.pathways.repository.PermissionRepository;
import com.pathways.repository.RoleRepository;
import com.pathways.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {
    private final Environment environment;

    public AppConfig(Environment environment) {
        this.environment = environment;
    }


    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            ApplicationUser admin = new ApplicationUser(1, environment.getProperty("DEFAULT_USER_USERNAME"), environment.getProperty("DEFAULT_USER_EMAIL"), passwordEncoder.encode(environment.getProperty("DEFAULT_USER_PASSWORD")), "Admin", "Admin", roles);

            userRepository.save(admin);
        };
    }

    @Bean
    CommandLineRunner initializePermissions(PermissionRepository permissionRepository) {
        return args -> {
            if (permissionRepository.findByPermissionName("home") != null) return;
            permissionRepository.save(new Permission("home", "/"));
            permissionRepository.save(new Permission("pathways", "/pathways"));
            permissionRepository.save(new Permission("pathways", "/pathways/finance"));
            permissionRepository.save(new Permission("pathways", "/pathways/monitoring-evaluation"));
            permissionRepository.save(new Permission("inovasi", "/inovasi"));
            permissionRepository.save(new Permission("inovasi", "/inovasi/finance"));
            permissionRepository.save(new Permission("inovasi", "/inovasi/monitoring-evaluation"));
            permissionRepository.save(new Permission("nauru", "/nauru"));
            permissionRepository.save(new Permission("nauru", "/nauru/finance"));
            permissionRepository.save(new Permission("nauru", "/nauru/monitoring-evaluation"));
            permissionRepository.save(new Permission("map-tnc", "/map-tnc"));
            permissionRepository.save(new Permission("map-tnc", "/map-tnc/finance"));
            permissionRepository.save(new Permission("map-tnc", "/map-tnc/monitoring-evaluation"));
            permissionRepository.save(new Permission("tautua", "/tautua"));
            permissionRepository.save(new Permission("tautua", "/tautua/finance"));
            permissionRepository.save(new Permission("tautua", "/tautua/monitoring-evaluation"));
        };
    }
}
