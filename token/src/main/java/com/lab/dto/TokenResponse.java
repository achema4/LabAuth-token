package com.lab.dto;

public record TokenResponse(
    String access_token,
    long expires_in
) {}
