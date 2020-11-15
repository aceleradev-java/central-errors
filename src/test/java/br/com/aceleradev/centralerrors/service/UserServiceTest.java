package br.com.aceleradev.centralerrors.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
	
	@Test
	void shouldFindUserById() {
		//given
		User user = createUserWithId();
		given(this.repository.findById(any(Long.class))).willReturn(Optional.of(user));
		
		//when
		User userFound = this.service.findById(user.getId());
		
		//then
		assertThat(user.getId(), is(userFound.getId()));
	}
	
	@Test
	void shouldFindAllUsers() {
		//given
		User user1 = new User(1l, "adriano", "123", "Adriano dos Santos", true);
		User user2 = new User(2l, "alex", "123", "Alex dos Anjos", true);
		User user3 = new User(3l, "matheus", "123", "Matheus da Silva", true);

		List<User> usersList = new ArrayList<>();
		usersList.add(user1);
		usersList.add(user2);
		usersList.add(user3);
		
		Pageable firstPageWithThreeElements = PageRequest.of(0, 3);
		Page<User> users3 = new PageImpl<>(usersList, firstPageWithThreeElements, 3l);
		given(this.repository.findAll(firstPageWithThreeElements)).willReturn(users3);

		//when
		Page<User> usersReturned = this.repository.findAll(firstPageWithThreeElements);
		
		//then
		assertThat(3l, is(usersReturned.getTotalElements()));
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
