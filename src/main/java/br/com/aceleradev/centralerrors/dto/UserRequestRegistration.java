package br.com.aceleradev.centralerrors.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.aceleradev.centralerrors.entity.User;
import lombok.Getter;

@Getter
public class UserRequestRegistration {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String name;

    private boolean admin;
    
    public User mapToUser() {
        return new User(username, password, name, admin);
    }
}
