package br.com.aceleradev.centralerrors.endpoint;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.aceleradev.centralerrors.dto.UpdatePassword;
import br.com.aceleradev.centralerrors.dto.UserRequestRegistration;
import br.com.aceleradev.centralerrors.dto.UserResponse;
import br.com.aceleradev.centralerrors.dto.UserResquestUpdate;
import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.service.UserServiceInterface;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "v1")
public class UserController {
    private UserServiceInterface service;
    
    @PostMapping(path = "users")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Registra um novo usuário")
    public UserResponse save(@Valid @RequestBody UserRequestRegistration userDTO) {
        User user = userDTO.mapToUser();
        return UserResponse.mapUserToUserResponseDTO(service.save(user));
    }
    
    @GetMapping(path = "admin/users")
    @ApiOperation(value = "Lista todos os usuários")
    public Page<UserResponse> findAll(Pageable pageable) {
        return UserResponse.map(service.findAll(pageable));
    }
    
    @GetMapping(path = "admin/users/{id}")
    @ApiOperation(value = "Procura usuário por id")
    public User findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }
    
    @PutMapping(path = "admin/users")
    @ApiOperation(value = "Atualiza um usuário")
    public UserResponse update(@Valid @RequestBody UserResquestUpdate userDto) {
        User user = service.update(userDto.mapToUser());
        return UserResponse.mapUserToUserResponseDTO(user);
    }
    
    @PutMapping(path = "protected/updatepassword")
    @ApiOperation(value = "Atualiza senha do usuário")
    public UserResponse updatePassword(@Valid @RequestBody UpdatePassword userWithNewPassword) {
        User user = service.updatePassword(userWithNewPassword);
        return UserResponse.mapUserToUserResponseDTO(user);
    }
    
    @DeleteMapping(path = "admin/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove um usuário")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
