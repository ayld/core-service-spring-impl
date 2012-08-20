package net.ayld.core.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.ayld.core.domain.BaseEntity;
import net.ayld.core.dto.BaseDto;
import net.ayld.core.service.Assembler;
import net.ayld.core.service.impl.factory.manager.CollectionFactoryManager;

public abstract class BaseAssembler<D extends BaseDto<I>, E extends BaseEntity<I>, I extends Serializable> implements Assembler<D, E, I>, Serializable{

	private final CollectionFactoryManager<D, E, I> collectionFactoryManager = new CollectionFactoryManager<D, E, I>();
	
	@Override
	public D toDto(E entity) {
		final D result = makeDto(entity);
		
		if (result == null) {
			
			throw new IllegalStateException("makeDto method must not return null");
		}
		if (result.getId() != null) {
			
			// TODO: LOG warning
		}
		result.setId(entity.getId());
		
		return result;
	}

	@Override
	public E toEntity(D dto) {
		final E result = makeEntity(dto);
		
		if (result == null) {
			
			throw new IllegalStateException("makeEntity method must not return null");
		}
		result.setId(dto.getId());
		
		return result;
	}

	@Override
	public List<D> toDtos(List<E> entities) {
		final List<D> result = collectionFactoryManager.newDtoList(entities.getClass());
		for (E entity : entities) {
			
			result.add(toDto(entity));
		}
		return result;
	}

	@Override
	public List<E> toEntities(List<D> dtos) {
		final List<E> result = collectionFactoryManager.newEntityList(dtos.getClass());
		for (D dto : dtos) {
			
			result.add(toEntity(dto));
		}
		return result;
	}

	@Override
	public Set<D> toDtos(Set<E> entities) {
		final Set<D> result = collectionFactoryManager.newDtoSet(entities.getClass());
		for (E entity : entities) {
			
			result.add(toDto(entity));
		}
		return result;
	}

	@Override
	public Set<E> toEntities(Set<D> dtos) {
		final Set<E> result = collectionFactoryManager.newEntitySet(dtos.getClass());
		for (D dto : dtos) {
			
			result.add(toEntity(dto));
		}
		return result;
	}
	
	public List<E> typeUnawareToEntities(Collection<D> dtos) {
		final List<E> result = new ArrayList<E>(dtos.size());
		for (D dto : dtos) {
			
			result.add(toEntity(dto));
		}
		return result;
	}
	
	public List<D> typeUnawareToDtos(Collection<E> entities) {
		final List<D> result = new ArrayList<D>(entities.size());
		for (E entity : entities) {
			
			result.add(toDto(entity));
		}
		return result;
	}
	
	protected abstract D makeDto(E entity);
	protected abstract E makeEntity(D dto);
	
	private static final long serialVersionUID = 1L;
}
