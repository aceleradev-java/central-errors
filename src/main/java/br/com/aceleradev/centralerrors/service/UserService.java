package br.com.aceleradev.centralerrors.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.aceleradev.centralerrors.config.AuthenticationFacadeInterface;
import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.exception.ActionNotAllowed;
import br.com.aceleradev.centralerrors.exception.EntityNotFound;
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
        User userFound = repository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFound(USER_NOT_FOUND));
        
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal(); 

        if(userDetails.getAuthorities().toString().contains("ROLE_ADMIN")){
            userFound.setName(user.getName());
            userFound.setUsername(user.getUsername());
            userFound.setAdmin(user.isAdmin());
            userFound.setPassword(user.getPassword());
            return save(user);
        }
        
        if (repository.findByUsername(principal.getUsername()).getUsername().equals(user.getUsername())) {
            userFound.setName(user.getName());
            userFound.setUsername(user.getUsername());
            userFound.setPassword(user.getPassword());
            return save(user);
        }

        throw new ActionNotAllowed("You don't have permission to update this user");
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
