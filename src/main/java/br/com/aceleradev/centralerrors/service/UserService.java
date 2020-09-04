package br.com.aceleradev.centralerrors.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.aceleradev.centralerrors.config.AuthenticationFacadeInterface;
import br.com.aceleradev.centralerrors.dto.UpdatePassword;
import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.exception.ActionNotAllowed;
import br.com.aceleradev.centralerrors.exception.EntityNotFound;
import br.com.aceleradev.centralerrors.exception.PasswordNotMatchException;
import br.com.aceleradev.centralerrors.exception.UsernameAlreadyExists;
import br.com.aceleradev.centralerrors.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserServiceInterface {

    private static final String USER_NOT_FOUND = "User not found";
    private UserRepository repository;
    private AuthenticationFacadeInterface authenticationFacade;
    
    @Override
    public User save(User user) {
        checkUsernameAvailable(user);
        String password = encoderPassword(user.getPassword());
        user.setPassword(password);
        return repository.save(user);
    }

    private void checkUsernameAvailable(User user) {
        User userFound = repository.findByUsername(user.getUsername());
        if (userFound != null && !user.equals(userFound)) {
            throw new UsernameAlreadyExists("The username already exists. Choose another username");
        }
    }

    private String encoderPassword(String pasword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(pasword);
    }

    @Override
    public User update(User user) {
        if (isValidUser(user)) return save(user);
        throw new EntityNotFound(USER_NOT_FOUND);
    }

    private boolean isValidUser(User user) {
        return repository.findById(user.getId()).isPresent();
    }
    
    @Override
    public User updatePassword(UpdatePassword user) {
        if (!user.getNewPassword().equals(user.getConfirmPassword())) {
            throw new PasswordNotMatchException("The new password and confirm password not match");
        }
        
        User userFound = repository.findByUsername(user.getUsername());
        if (userFound == null) throw new EntityNotFound(USER_NOT_FOUND);
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!userFound.getUsername().equals(userDetails.getUsername())) {
            throw new ActionNotAllowed("You don't have permission to update this user");
        }
        
        userFound.setPassword(user.getNewPassword());
        return save(userFound);
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
