package br.com.aceleradev.centralerrors.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePassword {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String newPassword;
    @NotNull
    @NotBlank
    private String confirmPassword;
}
