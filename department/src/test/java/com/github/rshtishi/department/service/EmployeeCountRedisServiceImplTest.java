package com.github.rshtishi.department.service;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;

import com.github.rshtishi.department.repository.EmployeeCountRedisRepository;

import brave.Span;
import brave.Tracer;
import brave.Tracing;

class EmployeeCountRedisServiceImplTest {
	
	@InjectMocks
	private EmployeeCountRedisServiceImpl employeeCountRedisService;
	@Mock
	private EmployeeCountRedisRepository employeeCountRedisRepository;
	@Mock
	private Tracing tracing;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testFindEmployeeCountFromCache() {
		//setup
		int departmentId = 1;
		Tracer tracer = mock(Tracer.class);
		Span span = mock(Span.class);
		doNothing().when(span).finish();
		when(span.start()).thenReturn(span);
		when(span.tag(anyString(), anyString())).thenReturn(span);
		when(span.name(anyString())).thenReturn(span);
		when(tracer.nextSpan()).thenReturn(span);
		when(tracing.tracer()).thenReturn(tracer);
		long expectedEmployeeCount = 2L;
		when(employeeCountRedisRepository.findEmployeeCount(departmentId)).thenReturn(expectedEmployeeCount);
		//execute
		long actualEmployeeCount = employeeCountRedisService.findEmployeeCountFromCache(departmentId);
		//verify
		Assertions.assertEquals(expectedEmployeeCount,actualEmployeeCount);
	}
	
	@Test
	public void testSaveEmployeeCountInCache() {
		//setup
		int departmentId=1;
		long employeeCount=3L;
		Tracer tracer = mock(Tracer.class);
		Span span = mock(Span.class);
		when(tracer.nextSpan()).thenReturn(span);
		when(span.name(anyString())).thenReturn(span);
		doNothing().when(span).finish();
		doNothing().when(employeeCountRedisRepository).saveEmployeeCount(anyInt(), anyLong());
		//execute
		employeeCountRedisRepository.saveEmployeeCount(departmentId, employeeCount);
		//verify
		verify(employeeCountRedisRepository,times(1)).saveEmployeeCount(departmentId, employeeCount);
	}

}
