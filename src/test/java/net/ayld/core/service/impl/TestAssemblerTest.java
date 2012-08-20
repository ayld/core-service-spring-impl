package net.ayld.core.service.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
		
		assertEquals(resultArrayList instanceof ArrayList, true);
		
		// test LinkedList compatibility
		final List<TestEntity> entitiesLinkedList = new LinkedList<TestEntity>();
		for (int i = 0; i < capacity; i++) {
			
			entitiesLinkedList.add(newTestEntity(i, EXPECTED_NAME + i));
		}
		final List<TestDto> resultLinkedList = assembler.toDtos(entitiesLinkedList);
		
		assertEquals(resultLinkedList instanceof LinkedList, true);
		
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
	
	private class UnsupportedList<T extends Serializable> implements List<T> {

		@Override
		public boolean add(T e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int index, T element) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(Collection<? extends T> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int index, Collection<? extends T> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public T get(int index) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Iterator<T> iterator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int lastIndexOf(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ListIterator<T> listIterator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public ListIterator<T> listIterator(int index) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public T remove(int index) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public T set(int index, T element) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int size() {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<T> subList(int fromIndex, int toIndex) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object[] toArray() {
			throw new UnsupportedOperationException();
		}

		@Override
		public <A> A[] toArray(A[] a) {
			throw new UnsupportedOperationException();
		}
	}
}
