package br.com.aceleradev.centralerrors.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.aceleradev.centralerrors.entity.User;

public interface UserServiceInterface {
    User save(User user);
    User update(User user);
    User findById(Long id);
    Page<User> findAll(Pageable pageable);
    void delete(Long id);
}
