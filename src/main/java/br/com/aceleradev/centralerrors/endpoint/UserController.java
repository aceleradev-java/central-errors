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

import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.service.UserServiceInterface;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "v1")
public class UserController {
    private UserServiceInterface service;
    
    @PostMapping(path = "admin/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@Valid @RequestBody User user) {
        return service.save(user);
    }
    
    @GetMapping(path = "admin/users")
    public Page<User> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }
    
    @GetMapping(path = "admin/users/{id}")
    public User findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }
    
    @PutMapping(path = "admin/users")
    public User update(@Valid @RequestBody User user) {
        return service.update(user);
    }
    
    @DeleteMapping(path = "admin/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
