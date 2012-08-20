package net.ayld.core.service.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.ayld.core.domain.TestEntity;
import net.ayld.core.dto.TestDto;
import net.ayld.core.exception.UnsupportedCollectionException;

import org.junit.Test;


public class TestAssemblerTest {
	private static final String EXPECTED_NAME = "test";
	private static final Integer EXPECTED_ID = 1;

	private final TestAssembler assembler = new TestAssembler();
	
	@Test
	public void testToDto() {
		
		final TestDto assembledDto = assembler.toDto(newTestEntity(EXPECTED_ID, EXPECTED_NAME));
		
		assertEquals(assembledDto.getName(), EXPECTED_NAME);
		assertEquals(assembledDto.getId(), EXPECTED_ID);
	}

	
	@Test
	public void testToEntity() {
		
		final TestEntity assembledEntity = assembler.toEntity(newTestDto(EXPECTED_ID, EXPECTED_NAME));
		
		assertEquals(assembledEntity.getId(), EXPECTED_ID);
		assertEquals(assembledEntity.getName(), EXPECTED_NAME);
	}

	@Test
	public void testToDtosList() {
		
		// test ArrayList compatibility
		final int capacity = 5;
		final List<TestEntity> entitiesArrayList = new ArrayList<TestEntity>(capacity);
		for (int i = 0; i < capacity; i++) {
			
			entitiesArrayList.add(newTestEntity(i, EXPECTED_NAME + i));
		}
		final List<TestDto> resultArrayList = assembler.toDtos(entitiesArrayList);
		
		assertTrue(resultArrayList instanceof ArrayList);
		
		checkContents(resultArrayList);
		
		// test LinkedList compatibility
		final List<TestEntity> entitiesLinkedList = new LinkedList<TestEntity>();
		for (int i = 0; i < capacity; i++) {
			
			entitiesLinkedList.add(newTestEntity(i, EXPECTED_NAME + i));
		}
		final List<TestDto> resultLinkedList = assembler.toDtos(entitiesLinkedList);
		
		assertTrue(resultLinkedList instanceof LinkedList);
		
		checkContents(resultLinkedList);
		
		// test UnsupportedCollectionException
		final List<TestEntity> entitiesUnsupportedList = new UnsupportedList<TestEntity>();
		
		boolean exceptionThrown = false;
		try {
			
			assembler.toDtos(entitiesUnsupportedList);
		} catch (UnsupportedCollectionException e) {
			
			// exception is thrown, everything is fine
			exceptionThrown = true;
		}
		if (!exceptionThrown) {
			
			fail();
		}
	}

	private void checkContents(final List<TestDto> resultList) {
		Integer count = 0;
		for (TestDto dto : resultList) {
			
			assertEquals(dto.getId(), count);
			assertEquals(dto.getName(), EXPECTED_NAME + count);
			
			count++;
		}
	}
	
	private TestEntity newTestEntity(int id, String name) {
		final TestEntity result = new TestEntity();
		result.setId(id);
		result.setName(name);
		
		return result;
	}
	
	private TestDto newTestDto(int id, String name) {
		final TestDto result = new TestDto();
		result.setId(id);
		result.setName(name);
		
		return result;
	}
}
