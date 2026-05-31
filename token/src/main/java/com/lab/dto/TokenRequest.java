package com.lab.dto;

public record TokenRequest(
    String clientId,
    String clientSecret
) {}
