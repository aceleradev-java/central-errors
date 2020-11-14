package br.com.aceleradev.centralerrors.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@MockBean
	private UserRepository repository;
	
	@Autowired
	private UserService service;

	@Test
	void shouldSaveAnUser() {
		//given
		User user = createUser();
		given(this.repository.save(any(User.class))).willReturn(createUserWithId());
		
		//when
		User userSaved = this.service.save(user);
		
		//then
		assertThat(1l, is(userSaved.getId()));
	}
	
	@Test
	void shouldUpdateAnUser() {
		//given
		User userSaved = createUserWithId();
		String nameUpdated = "Adriano Update";
		userSaved.setName(nameUpdated);
		given(this.repository.findById(any(Long.class))).willReturn(Optional.of(new User()));
		given(this.repository.save(any(User.class))).willReturn(userSaved);
		
		//when
		User userUpdated = this.service.update(userSaved);
		
		//then
		assertThat(1l, is(userUpdated.getId()));
		assertThat(nameUpdated, is(userUpdated.getName()));
	}
	
	@Test
	void shouldDeleteAnUser() {
		//given
		User user = createUserWithId();
		given(this.repository.findById(any(long.class))).willReturn(Optional.of(user));
		
		//when
		this.service.delete(user.getId());
		
		//then
		Mockito.verify(this.repository, Mockito.times(1)).delete(user);
	}

	private User createUser() {
		return new User("adriano", "123", "Adriano dos Santos", true);
	}
	
	private User createUserWithId() {
		User userWithId = createUser();
		userWithId.setId(1l);
		return userWithId;
	}
}
