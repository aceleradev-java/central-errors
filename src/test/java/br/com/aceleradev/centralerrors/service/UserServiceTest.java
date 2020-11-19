package br.com.aceleradev.centralerrors.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.aceleradev.centralerrors.dto.UpdatePassword;
import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.exception.ActionNotAllowed;
import br.com.aceleradev.centralerrors.exception.EntityNotFound;
import br.com.aceleradev.centralerrors.exception.PasswordNotMatchException;
import br.com.aceleradev.centralerrors.exception.UsernameAlreadyExists;
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
	
	@Test
	void shouldUpdatePassword() {
		//given
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordEncoded = passwordEncoder.encode("123");
		String newPasswordEncoded = passwordEncoder.encode("1234");
		User userFromDataBase = createUserWithId();
		userFromDataBase.setPassword(passwordEncoded);
		User userWithNewPassword = createUser();
		userWithNewPassword.setPassword(newPasswordEncoded);
		UpdatePassword passwordUpdated = UpdatePassword.builder()
								.username("adriano")
								.password("123")
								.newPassword("1234")
								.confirmPassword("1234")
								.build();
		
		given(this.repository.findByUsername(any(String.class))).willReturn(userFromDataBase);
		given(this.repository.save(any(User.class))).willReturn(userWithNewPassword);
		
		//when
		User userWithPasswordUpdated = this.service.updatePassword(passwordUpdated);
		
		//then
		assertThat(true, is(passwordEncoder.matches("1234", userWithPasswordUpdated.getPassword())));
	}

	@Test
	void shouldShowErrorOnSaveWhenUsernameAlreadyExist() {
		//given
		User user = createUser();
		given(this.repository.save(any(User.class)))
			.willThrow(new UsernameAlreadyExists("The username already exists. Choose another username"));
		
		//then
		Assertions.assertThrows(
			UsernameAlreadyExists.class, 
			() -> this.service.save(user)
		);
	}
	
	@Test
	void shouldShowErrorOnUpdateWhenUsernameAlreadyExist() {
		//given
		User user = createUserWithId();
		given(this.repository.findById(any(Long.class))).willReturn(Optional.of(user));
		given(this.repository.save(any(User.class)))
		.willThrow(new UsernameAlreadyExists("The username already exists. Choose another username"));
		
		//then
		Assertions.assertThrows(
			UsernameAlreadyExists.class, 
			() -> this.service.update(user)
		);
	}
	
	@Test
	void shouldShowErrorOnUpdateWhenUserNotFound() {
		//given
		User user = createUserWithId();
		given(this.repository.findById(any(Long.class)))
			.willReturn(Optional.empty());

		//then
		Assertions.assertThrows(EntityNotFound.class, () -> this.service.update(user));
	}
	
	@Test
	void shouldShowErrorOnUpdatePasswordWhenNewPasswordAndConfirmPasswordNotMatch() {
		//given
		UpdatePassword newPassword = UpdatePassword.builder()
										.password("123")
										.newPassword("1234")
										.confirmPassword("12345")
										.username("adriano")
										.build();
		
		//then
		Assertions.assertThrows(
			PasswordNotMatchException.class, 
			() -> this.service.updatePassword(newPassword)
		);
	}
	
	@Test
	void shouldShowErrorOnUpdatePasswordWhenUserNotFound() {
		//given
		UpdatePassword newPassword = UpdatePassword.builder()
				.password("123")
				.newPassword("1234")
				.confirmPassword("1234")
				.username("adriano")
				.build();
		given(this.repository.findByUsername(any(String.class))).willReturn(null);
		
		//then
		Assertions.assertThrows(
			EntityNotFound.class,
			() -> this.service.updatePassword(newPassword)
		);
	}
	
	@Test
	void shouldShowErrorOnUpdatePasswordWhenCurrentPasswordIsWrong() {
		//given
		UpdatePassword newPassword = UpdatePassword.builder()
				.password("wrong password")
				.newPassword("1234")
				.confirmPassword("1234")
				.username("adriano")
				.build();
		User userFromDatabase = createUserWithId();
		userFromDatabase.setPassword(new BCryptPasswordEncoder().encode("123"));
		given(this.repository.findByUsername(any(String.class))).willReturn(userFromDatabase);
		
		//then
		Assertions.assertThrows(
			ActionNotAllowed.class,
			() -> this.service.updatePassword(newPassword)
		);
	}
	
	@Test
	void shouldShowErrorOnFindUserByIdWhenUserNotFound() {
		//given
		User user = createUserWithId();
		given(this.repository.findById(any(Long.class))).willThrow(new EntityNotFound("User not found"));
		
		//then
		Assertions.assertThrows(
			EntityNotFound.class,
			() -> this.service.findById(user.getId())
		);
	}
	
	@Test
	void shouldShowErrorOnDeleteWhenUserNotFound() {
		//given
		User user = createUserWithId();
		given(this.repository.findById(any(Long.class))).willThrow(new EntityNotFound("User not found"));
		
		//then
		Assertions.assertThrows(
			EntityNotFound.class,
			() -> this.service.delete(user.getId())
		);
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
