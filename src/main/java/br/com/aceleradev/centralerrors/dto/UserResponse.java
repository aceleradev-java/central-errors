package br.com.aceleradev.centralerrors.dto;

import org.springframework.data.domain.Page;

import br.com.aceleradev.centralerrors.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResponse {
    private Long id;
    private String username;
    private String name;
    private boolean admin;
    
    public static Page<UserResponse> map(Page<User>  users) {
        return users.map(UserResponse::mapUserToUserResponseDTO);
    }
    
    public static UserResponse mapUserToUserResponseDTO(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getName(), user.isAdmin());
    }
}
