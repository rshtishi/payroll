package com.github.rshtishi.payroll.employee.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import org.junit.jupiter.api.Assertions;
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
import com.github.rshtishi.payroll.employee.entity.Employee;
import com.github.rshtishi.payroll.employee.helper.EmployeeHelper;
import com.github.rshtishi.payroll.employee.service.EmployeeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class EmployeeRestControllerUnitTest {

	@InjectMocks
	private EmployeeRestController employeeRestController;
	@Mock
	private EmployeeService employeeService;
	@Mock
	private EmployeeHelper employeeHelper;

	private MockMvc mockMvc;
	private Employee employee;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(employeeRestController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
		employee = new Employee();
		employee.setId(1);
		employee.setFirstname("Rando");
		employee.setDepartmentId(1);
	}

	@Test
	public void testFindAll() throws Exception {
		// setup
		Page<Employee> page = Page.empty();
		when(employeeService.findAll(any())).thenReturn(page);
		// execute
		ResultActions result = mockMvc
				.perform(get("/employees").param("page", "5").param("size", "10").param("sort", "id,desc"));
		// verify
		result.andExpect(status().isOk());
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.content").isArray());
	}

	@Test
	public void testFindById() throws Exception {
		// setup
		when(employeeHelper.verifyEmployeeExistance(any(), anyInt())).thenReturn(employee);
		// execute
		ResultActions result = mockMvc.perform(get("/employees/1"));
		// verify
		result.andExpect(status().isOk());
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.id", is(1)));
		result.andExpect(jsonPath("$.firstname", is("Rando")));
	}

	@Test
	public void testCountByDepartmentId() throws Exception {
		// setup
		long expected = 1L;
		when(employeeService.countByDepartmentId(anyInt())).thenReturn(expected);
		// execute
		ResultActions result = mockMvc.perform(get("/employees/1/count"));
		// verify
		result.andExpect(status().isOk());
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		Assertions.assertEquals(expected, Long.parseLong(result.andReturn().getResponse().getContentAsString()));
	}

	@Test
	public void testCreate() throws Exception {
		// setup
		when(employeeService.createEmployee(any())).thenReturn(employee);
		String payload = new ObjectMapper().writeValueAsString(employee);
		// execute
		ResultActions result = mockMvc
				.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(payload));
		// verify
		result.andExpect(status().isCreated());
		result.andExpect(header().string("Location", "http://localhost/employees/1"));
		result.andExpect(jsonPath("$.id", is(1)));
		result.andExpect(jsonPath("$.firstname", is("Rando")));
	}

	@Test
	public void testUpdate() throws Exception {
		// setup
		when(employeeHelper.verifyEmployeeExistance(any(), anyInt())).thenReturn(employee);
		when(employeeService.updateEmployee(employee)).thenReturn(employee);
		String payload = new ObjectMapper().writeValueAsString(employee);
		// execute
		ResultActions result = mockMvc
				.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON).content(payload));
		// verify
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id", is(1)));
		result.andExpect(jsonPath("$.firstname", is("Rando")));
	}
	
	@Test
	public void testDelete() throws Exception {
		//setup
		when(employeeHelper.verifyEmployeeExistance(any(), anyInt())).thenReturn(employee);
		doNothing().when(employeeService).deleteEmployee(Mockito.anyInt());
		//execute
		ResultActions result = mockMvc.perform(delete("/employees/1"));
		//verify
		verify(employeeService,times(1)).deleteEmployee(1);
		result.andExpect(status().isOk());
	}

}
