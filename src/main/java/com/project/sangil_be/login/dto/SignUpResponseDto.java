package com.project.sangil_be.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpResponseDto {
    private boolean result;

    public SignUpResponseDto(Boolean result) {
        this.result = result;
    }
}
