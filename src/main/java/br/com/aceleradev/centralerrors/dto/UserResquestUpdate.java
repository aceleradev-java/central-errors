package br.com.aceleradev.centralerrors.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.aceleradev.centralerrors.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResquestUpdate {
    @NotNull
    private Long id;
    @Column(unique = true)
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
        return new User(
                this.id,
                this.username,
                this.password,
                this.name,
                this.admin
        );
    }
}
