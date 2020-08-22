package br.com.aceleradev.centralerrors;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.aceleradev.centralerrors.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}
