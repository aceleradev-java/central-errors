package br.com.aceleradev.centralerrors.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.exception.EntityNotFound;
import br.com.aceleradev.centralerrors.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserServiceInterface {

    private static final String USER_NOT_FOUND = "User not found";
    private UserRepository repository;
    
    @Override
    public User save(User user) {
        String password = encoderPassword(user.getPassword());
        user.setPassword(password);
        return repository.save(user);
    }

    private String encoderPassword(String pasword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(pasword);
    }

    @Override
    public User update(User user) {
        User userFound = repository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFound(USER_NOT_FOUND));
        userFound.setName(user.getName());
        userFound.setUsername(user.getUsername());
        userFound.setAdmin(user.isAdmin());
        String password = encoderPassword(user.getPassword());
        userFound.setPassword(password);
        return save(user);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFound(USER_NOT_FOUND));
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFound(USER_NOT_FOUND));
        repository.delete(user);
    }

}
