package com.github.rshtishi.department.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rshtishi.department.entity.Department;
import com.github.rshtishi.department.helper.DepartmentHelper;
import com.github.rshtishi.department.service.DepartmentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class DepartmentRestControllerUnitTest {

	@InjectMocks
	private DepartmentRestController departmentRestController;
	@Mock
	private DepartmentService departmentService;
	@Mock
	private DepartmentHelper departmentHelper;

	private MockMvc mockMvc;
	private Department department;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(departmentRestController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
		department = new Department();
		department.setId(1);
		department.setName("Finance");
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

	@Test
	public void testFindById() throws Exception {
		// setup
		when(departmentHelper.verifyDeparmentExistence(Mockito.any(), Mockito.anyInt())).thenReturn(department);
		// execute
		ResultActions result = mockMvc.perform(get("/departments/1"));
		// verify
		result.andExpect(status().isOk());
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.id", is(1)));
		result.andExpect(jsonPath("$.name", is("Finance")));
	}

	@Test
	public void testCreate() throws Exception {
		// setup
		when(departmentService.createDepartment(Mockito.any())).thenReturn(department);
		String payload = new ObjectMapper().writeValueAsString(department);
		// execute
		ResultActions result = mockMvc
				.perform(post("/departments").contentType(MediaType.APPLICATION_JSON).content(payload));
		// verify
		result.andExpect(status().isCreated());
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		result.andExpect(header().string("Location", "http://localhost/departments/1"));
		result.andExpect(jsonPath("$.id", is(1)));
		result.andExpect(jsonPath("$.name", is("Finance")));
		
		
	}

	@Test
	public void testUpdate() throws Exception {
		// setup
		when(departmentHelper.verifyDeparmentExistence(Mockito.any(), Mockito.anyInt())).thenReturn(department);
		when(departmentService.updateDepartment(Mockito.any(Department.class))).thenReturn(department);
		String payload = new ObjectMapper().writeValueAsString(department);
		// execute
		ResultActions result = mockMvc
				.perform(put("/departments/1").contentType(MediaType.APPLICATION_JSON).content(payload));
		// verify
		result.andExpect(status().isOk());
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.id", is(1)));
		result.andExpect(jsonPath("$.name", is("Finance")));
	}
	
	@Test
	public void testDelete() throws Exception {
		//setup
		when(departmentHelper.verifyDeparmentExistence(Mockito.any(), Mockito.anyInt())).thenReturn(department);
		doNothing().when(departmentService).deleteDepartment(Mockito.anyInt());
		//execute
		ResultActions result = mockMvc.perform(delete("/departments/1"));
		//verify
		verify(departmentService,times(1)).deleteDepartment(1);
		result.andExpect(status().isOk());
	}
}
