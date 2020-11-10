package br.com.aceleradev.centralerrors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.repository.UserRepository;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Test
	void shouldPersisteNewValidUser() {
		User user = new User("adriano", "123", "Adriano Avelino", false);
		this.repository.save(user);
		assertThat(user.getUsername(), is("adriano"));
		assertThat(user.getName(), is("Adriano Avelino"));
		assertThat(user.getPassword(), is("123"));
		assertThat(user.isAdmin(), is(false));
	}

}
