package br.com.aceleradev.centralerrors.endpoint;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.aceleradev.centralerrors.dto.UpdatePassword;
import br.com.aceleradev.centralerrors.dto.UserResquestUpdate;
import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService service;
	
    @Autowired
    private ObjectMapper objectMapper;

	
	private HttpHeaders getAdminHeaders() throws Exception {
    	String authenticationCredential = "{\"username\":\"admin\",\"password\":\"123\"}";
		MvcResult authenticationResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(authenticationCredential))
				.andReturn();
		String authorizationValue = authenticationResult.getResponse().getContentAsString();
    	HttpHeaders adminHeader = new HttpHeaders();
		adminHeader.setBearerAuth(authorizationValue);
    	return adminHeader;
    }
	
	private HttpHeaders getDefaultUser() throws Exception {
		String authenticationCredential = "{\"username\":\"user\",\"password\":\"123\"}";
		MvcResult authenticationResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(authenticationCredential))
				.andReturn();
		String authorizationValue = authenticationResult.getResponse().getContentAsString();
		HttpHeaders adminHeader = new HttpHeaders();
		adminHeader.setBearerAuth(authorizationValue);
		return adminHeader;
	}
	
	@Test
	void shouldSaveAnUser() throws Exception {
		User user = new User("carlos", "123", "Carlos dos Santos", true);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user))
						.headers(this.getAdminHeaders()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("\"id\":")
		.contains("\"username\":")
		.contains("\"name\":")
		.contains("\"admin\":");
	}
	
	@Test
	void shoulFindAllUsers() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/admin/users")
						.headers(getAdminHeaders()))
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("\"id\":")
		.contains("\"username\":")
		.contains("\"name\":")
		.contains("\"admin\":");
	}
    
	@Test
	void shouldFindUserById() throws Exception {
		User user = new User("adriano", "123", "Adriano dos Santos", true);
		this.service.save(user);
		String id = user.getId().toString();
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/v1/admin/users/{id}", id)
						.headers(this.getAdminHeaders()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("\"id\":")
		.contains("\"username\":")
		.contains("\"name\":")
		.contains("\"admin\":");
	}
	
	@Test
	void shouldUpdateAnUser() throws Exception, Exception {
		User user = new User("jose", "123", "Jose da Silva", true);
		this.service.save(user);
		UserResquestUpdate userUpdated = UserResquestUpdate.builder()
											.id(user.getId())
											.username("joseAtualizado")
											.password("1324")
											.name("Jose Ferreira")
											.admin(false)
											.build();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/admin/users")
						.headers(this.getAdminHeaders())
						.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userUpdated)))
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();

		Assertions.assertThat(result.getResponse().getContentAsString())
			.contains("\"id\":")
			.contains("\"username\":")
			.contains("\"name\":")
			.contains("\"admin\":");
	}
	
	@Test
	void shouldUpdateUserPassword() throws Exception {
		User user = new User("gabriel", "123", "Gabriel Antos", true);
		this.service.save(user);
		
		UpdatePassword userWithNewPassword = UpdatePassword.builder()
												.username("gabriel")
												.password("123")
												.newPassword("1234")
												.confirmPassword("1234")
												.build();
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/protected/updatepassword")
						.headers(getAdminHeaders())
						.content(objectMapper.writeValueAsString(userWithNewPassword))
						.contentType(MediaType.APPLICATION_JSON))
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();
		
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("\"id\":")
		.contains("\"username\":")
		.contains("\"name\":")
		.contains("\"admin\":");
	}
	
	@Test
	void shouldDeleteAnUser() throws Exception {
		User user = new User("alex", "123", "Alex Silva", true);
		this.service.save(user);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/admin/users/" + user.getId())
						   		.headers(getAdminHeaders()))
							.andDo(MockMvcResultHandlers.print())
							.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void shouldShowErrorOnSaveAnUserWhenTheUsernameAlreadyExist() throws Exception {
		User user = new User("admin", "123", "Maria dos Santos", true);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(user)))
				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("\"status\":")
		.contains("\"date\":")
		.contains("\"title\":");
	}
	
	@Test
	void shouldShowErrorOnUpdateUserWhenTheUsernameAlreadyExist() throws Exception {
		User user = new User(1l,"user", "123", "Maria dos Santos", true);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/admin/users")
						.headers(this.getAdminHeaders())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
					.andExpect(MockMvcResultMatchers.status().isConflict())
					.andDo(MockMvcResultHandlers.print())
					.andReturn();
		
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("\"status\":")
		.contains("\"date\":")
		.contains("\"title\":");
	}
	
	@Test
	void shouldShowErrorOnUpdateUserWhenTheUserNotFound() throws Exception {
		User user = new User(100l,"domingos", "123", "Domingos dos Santos", true);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/admin/users")
								.headers(this.getAdminHeaders())
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(user)))
							.andExpect(MockMvcResultMatchers.status().isNotFound())
							.andDo(MockMvcResultHandlers.print())
							.andReturn();
		
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("\"status\":")
		.contains("\"date\":")
		.contains("\"title\":");
	}
	
	@Test
	void shouldShowErrorOnUpdateWhenActionNotAllowedByDefaultUser() throws Exception {
		User user = new User(2l,"nazare", "123", "Maria nazaré", true);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/v1/admin/users")
					.headers(this.getDefaultUser())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(user)))
				.andExpect(MockMvcResultMatchers.status().isForbidden())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	void shouldShowErrorOnUpdatePasswordWhenNewPasswordAndConfirmNewPasswordAreDiferent() throws Exception {
		UpdatePassword newPassword = UpdatePassword.builder()
		.username("user")
		.password("123")
		.newPassword("1234")
		.confirmPassword("12345")
		.build();
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/protected/updatepassword")
					.headers(this.getAdminHeaders())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newPassword)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("status")
		.contains("date")
		.contains("title");
	}
	
	@Test
	void shouldShowErrorOnUpdatePasswordWhenUserNotFound() throws Exception {
		UpdatePassword newPassword = UpdatePassword.builder()
													.username("notfound")
													.password("123")
													.newPassword("1234")
													.confirmPassword("1234")
													.build();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/protected/updatepassword")
								.headers(this.getAdminHeaders())
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(newPassword)))
							.andDo(MockMvcResultHandlers.print())
							.andExpect(MockMvcResultMatchers.status().isNotFound())
							.andReturn();
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("status")
		.contains("date")
		.contains("title");
	}
	
	@Test
	void shouldShowErrorOnUpdatePasswordWhenActionIsNotAllowedWithWrongPassword() throws Exception {
		UpdatePassword newPassword = UpdatePassword.builder()
				.username("user")
				.password("wrongPassword")
				.newPassword("1234")
				.confirmPassword("1234")
				.build();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/protected/updatepassword")
				.headers(this.getAdminHeaders())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newPassword)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isForbidden())
				.andReturn();
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("status")
		.contains("date")
		.contains("title");
	}

}
