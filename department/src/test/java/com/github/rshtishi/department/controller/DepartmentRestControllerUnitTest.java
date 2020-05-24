package com.github.rshtishi.department.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.rshtishi.department.DepartmentApplication;
import com.github.rshtishi.department.entity.Department;
import com.github.rshtishi.department.service.DepartmentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DepartmentApplication.class)
@ContextConfiguration(classes = MockServletContext.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
public class DepartmentRestControllerUnitTest {

	@InjectMocks
	private DepartmentRestController departmentRestController;
	@Mock
	private DepartmentService departmentService;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(departmentRestController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
	}

	@Test
	public void testfindAll() throws Exception {
		// setup
		Page<Department> page = Page.empty();
		when(departmentService.findAll(Mockito.any())).thenReturn(page);
		// execute
		ResultActions result = mockMvc
				.perform(get("/departments").param("page", "5").param("size", "10").param("sort", "id,desc"));
		// verify
		result.andExpect(status().isOk());
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.content").isArray());
	}
}
