package br.com.aceleradev.centralerrors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.repository.UserRepository;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Test
	void shouldPersisteNewValidUser() {
		User user = createUser();
		
		this.repository.save(user);
		
		assertThat(user.getUsername(), is("adriano"));
		assertThat(user.getName(), is("Adriano dos Santos"));
		assertThat(user.getPassword(), is("123"));
		assertThat(user.isAdmin(), is(true));
	}
	
	@Test
	void shouldDeleteAnUser() {
		User user = createUser();
		this.repository.save(user);
		assertThat(3l, is(this.repository.count()));
		
		this.repository.delete(user);
		
		assertThat(2l, is(this.repository.count()));
	}
	
	@Test
	void shouldUpdateAnUser() {
		User user = createUser();
		this.repository.save(user);
		
		user.setUsername("usuário alterado");
		user.setPassword("1234");
		user.setName("nome alterado");
		user.setAdmin(false);
		this.repository.save(user);
		
		assertThat("usuário alterado", is(user.getUsername()));
		assertThat("1234", is(user.getPassword()));
		assertThat("nome alterado", is(user.getName()));
		assertThat(false, is(user.isAdmin()));
	}
	
	@Test
	void shouldFindUserById() {
		User user = createUser();
		this.repository.save(user);
		
		User userFound = this.repository.findById(user.getId()).orElse(null);
		
		Assertions.assertNotNull(userFound);
		assertThat("adriano", is(userFound.getUsername()));
	}
	
	@Test
	void shouldFinduserByUsername() {
		User user = createUser();
		this.repository.save(user);
		
		User userFound = this.repository.findByUsername("adriano");
		
		Assertions.assertNotNull(userFound);
		assertThat("adriano", is(userFound.getUsername()));
	}
	
	@Test
	void shouldFindAllUsers() {
		User user = createUser();
		this.repository.save(user);
		
		Pageable firstPageWithThreeElements = PageRequest.of(0, 3);
		Page<User> users = this.repository.findAll(firstPageWithThreeElements);
		
		assertThat(3, is(users.getSize()));
	}

	private User createUser() {
		return new User("adriano", "123", "Adriano dos Santos", true);
	}

}
