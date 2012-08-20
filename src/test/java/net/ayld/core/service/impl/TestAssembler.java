package net.ayld.core.service.impl;

import net.ayld.core.domain.TestEntity;
import net.ayld.core.dto.TestDto;

public class TestAssembler extends BaseAssembler<TestDto, TestEntity, Integer>{

	@Override
	protected TestDto makeDto(TestEntity entity) {
		final TestDto result = new TestDto();
		result.setName(entity.getName());
		
		return result;
	}

	@Override
	protected TestEntity makeEntity(TestDto dto) {
		final TestEntity result = new TestEntity();
		result.setName(dto.getName());
		
		return result;
	}
	
	private static final long serialVersionUID = 1L;
}
