package br.com.aceleradev.centralerrors.endpoint;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.aceleradev.centralerrors.entity.User;
import br.com.aceleradev.centralerrors.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService service;

	@Test
	void findById() throws Exception {
		User user = new User("adriano", "123", "Adriano dos Santos", true);
		this.service.save(user);
		
		String str = "{\"username\":\"admin\",\"password\":\"123\"}";
		MvcResult authenticationresult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(str))
				.andReturn();
		String token = authenticationresult.getResponse().getContentAsString();
		String id = user.getId().toString();
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/v1/admin/users/{id}", id)
						.header("Authorization", token))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		Assertions.assertThat(result.getResponse().getContentAsString())
		.contains("\"id\":3")
		.contains("\"username\":\"adriano\"")
		.contains("\"name\":\"Adriano dos Santos\"")
		.contains("\"admin\":true");
	}

}
