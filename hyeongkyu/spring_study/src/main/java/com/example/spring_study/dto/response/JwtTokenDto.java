package com.example.spring_study.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;

@Builder
@Schema(name = "JwtTokenDto", description = "JWT 토큰 응답")
public record JwtTokenDto(
        @JsonProperty("accessToken")
        @NotNull(message = "accessToken은 필수값입니다.")
        String accessToken,

        @JsonProperty("refreshToken")
        String refreshToken
) implements Serializable {
    public static JwtTokenDto of(String accessToken, String refreshToken) {
        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
