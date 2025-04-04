package com.project.jwt.service;

import com.project.jwt.DTO.DashBoardResponse;
import com.project.jwt.entity.User;
import com.project.jwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardService {
    private final UserRepository userRepository;

    public DashBoardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public DashBoardResponse common(String name) {
        User user = userRepository.findByUserName(name);
        DashBoardResponse response = new DashBoardResponse(user.getUserName(),user.getAge(),user.getHeight(),user.getEmail());
        return response;
    }

    public List<DashBoardResponse> admin() {
        List<User> user = userRepository.findAll();
        List<DashBoardResponse> response = user.stream().map(u -> new DashBoardResponse(u.getUserName(),u.getAge(),u.getHeight(),u.getEmail())).toList();
        return response;
    }

}
