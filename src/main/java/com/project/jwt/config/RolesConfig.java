package com.project.jwt.config;

import com.project.jwt.entity.Role;
import com.project.jwt.entity.User;
import com.project.jwt.repository.RoleRepository;
import com.project.jwt.repository.UserRepository;
import com.project.jwt.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RolesConfig implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public RolesConfig(RoleRepository roleRepository,
                       UserRepository userRepository,
                       AuthService authService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!roleRepository.existsByName(Role.Values.ADMIN.name())) {
            Role role = new Role();
            role.setName(Role.Values.ADMIN.name());
            roleRepository.save(role);
        }
        if (!roleRepository.existsByName(Role.Values.COMMON.name())) {
            Role role = new Role();
            role.setName(Role.Values.COMMON.name());
            roleRepository.save(role);
        }
        if (!userRepository.existsByUserName("ADMIN")) {
            authService.register(new com.project.jwt.DTO.RequestAuthParams("ADMIN", "ADMIN", "ADM@email.com", 0, 0));
            User user = userRepository.findByUserName("ADMIN");
            user.getRoles().add(roleRepository.findByName(Role.Values.ADMIN.name()));
            userRepository.save(user);
        }
    }
}
