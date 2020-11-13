package br.com.aceleradev.centralerrors.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
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

	private User createUser() {
		return new User("adriano", "123", "Adriano dos Santos", true);
	}
	
	private User createUserWithId() {
		User userWithId = createUser();
		userWithId.setId(1l);
		return userWithId;
	}
}
