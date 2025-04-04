package com.project.jwt.DTO;

public record RequestAuthParams(String userName, String password, String email, int age, float height) {
}
