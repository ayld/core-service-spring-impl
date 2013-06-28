package net.ayld.core.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.ayld.core.annotation.BindField;
import net.ayld.core.domain.BaseEntity;
import net.ayld.core.dto.BaseDto;
import net.ayld.core.exception.BindAssemblyException;
import net.ayld.core.persistance.Dao;
import net.ayld.core.persistance.impl.BaseDao;
import net.ayld.core.service.Assembler;
import net.ayld.core.service.CrudService;

import org.apache.commons.lang.WordUtils;
import org.springframework.core.GenericTypeResolver;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public abstract class BaseService<D extends BaseDto<I>, I extends Serializable> implements CrudService<D, I> {

	private Dao<BaseEntity<I>, I> dao;
	private Assembler<D, BaseEntity<I>, I> assembler;
	
	@Override
	@SuppressWarnings("unchecked")
	public D read(I id) {
		assertDao();
		
		if (assembler == null) {
			assembler = new BindAssembler<>(
					GenericTypeResolver.resolveTypeArguments(getClass(), BaseService.class)[0],
					GenericTypeResolver.resolveTypeArguments(dao.getClass(), Dao.class)[0]
			);
		}
		
		final D result = assembler.toDto(dao.read(id));
		
		if (result == null) {
			throw new IllegalArgumentException("entry with id " + id + " does not exist");
		}
		
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public D find(I id) {
		assertDao();
		
		if (assembler == null) {
			assembler = new BindAssembler<>(
					GenericTypeResolver.resolveTypeArguments(getClass(), BaseService.class)[0],
					GenericTypeResolver.resolveTypeArguments(dao.getClass(), BaseDao.class)[0]
			);
		}
		
		return assembler.toDto(dao.find(id));
	}

	@Override
	@SuppressWarnings("unchecked")
	public D update(D dto) {
		assertDao();

		if (assembler == null) {
			assembler = new BindAssembler<>(
					GenericTypeResolver.resolveTypeArguments(getClass(), BaseService.class)[0],
					GenericTypeResolver.resolveTypeArguments(dao.getClass(), BaseDao.class)[0]
			);
		}
		
		final BaseEntity<I> entity = assembler.toEntity(dto);
		
		return assembler.toDto(dao.update(entity));
	}

	@Override
	@SuppressWarnings("unchecked")
	public D create(D dto) {
		assertDao();
		
		if (assembler == null) {
			assembler = new BindAssembler<>(
					GenericTypeResolver.resolveTypeArguments(getClass(), BaseService.class)[0],
					GenericTypeResolver.resolveTypeArguments(dao.getClass(), BaseDao.class)[0]
			);
		}
		
		final BaseEntity<I> entity = assembler.toEntity(dto);
		
		return assembler.toDto(dao.create(entity));
	}

	@Override
	@SuppressWarnings("unchecked")
	public D delete(I id) {
		assertDao();
		
		if (assembler == null) {
			assembler = new BindAssembler<>(
					GenericTypeResolver.resolveTypeArguments(getClass(), BaseService.class)[0],
					GenericTypeResolver.resolveTypeArguments(dao.getClass(), BaseDao.class)[0]
			);
		}
		
		return assembler.toDto(dao.delete(id));
	}

	private void assertDao() {
		if (dao == null) {
			throw new IllegalStateException("can not execure requested operation as a dao is not set to service " + getClass().getName());
		}
	}
	
	private class BindAssembler<E extends BaseEntity<I>> extends BaseAssembler<D, BaseEntity<I>, I> {

		private static final String SETTER_PREFIX = "set";
		private static final String GETTER_PREFIX = "get";
		
		private final Class<D> dtoType;
		private final Class<E> entityType;


		private BindAssembler(Class<D> dtoType, Class<E> entityType) {
			this.dtoType = dtoType;
			this.entityType = entityType;
		}

		@Override
		protected D makeDto(BaseEntity<I> entity) {
			
			D result = null;
			try {
				
				result = dtoType.newInstance();
				
			} catch (InstantiationException | IllegalAccessException e) {
				throw new BindAssemblyException(e);
			}
			
			for (Field dtoField : dtoType.getDeclaredFields()) {
				if (Modifier.isStatic(dtoField.getModifiers())) { // we don't care about static fields
					continue;
				}
				
				final BindField bind = dtoField.getAnnotation(BindField.class);
				
				final String dtoFieldName = dtoField.getName();
				if (bind == null) {
					throw new IllegalStateException("no Bind annotation found on field " + dtoFieldName);
				}
				
				final String bindTo = bind.name();
				if (Strings.isNullOrEmpty(bindTo)) {
					throw new IllegalArgumentException("Found a Bind annotation without a name field");
				}
				
				for (Field entityField : entity.getClass().getDeclaredFields()) {
					
					final String entityFieldName = entityField.getName();
					if (entityFieldName.equals(bindTo)) {
						try {
							
							final String setterName = Joiner.on("").join(SETTER_PREFIX, WordUtils.capitalize(dtoFieldName));
							final Method dtoSetter = result.getClass().getDeclaredMethod(setterName, dtoField.getType());
							
							final String getterName = Joiner.on("").join(GETTER_PREFIX, WordUtils.capitalize(entityFieldName));
							final Method entityGetter = entity.getClass().getDeclaredMethod(getterName, new Class[]{});
							
							dtoSetter.invoke(result, entityGetter.invoke(entity, (Object[]) null));
							
						} catch (SecurityException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
							throw new BindAssemblyException(e);
						}
					}
				}
			}
			return result;
		}

		@Override
		protected E makeEntity(D dto) {
			E result = null;
			try {
				
				result = entityType.newInstance();
				
			} catch (InstantiationException | IllegalAccessException e) {
				throw new BindAssemblyException(e);
			}
			
			for (Field dtoField : dto.getClass().getDeclaredFields()) {
				if (Modifier.isStatic(dtoField.getModifiers())) { // we don't care about static fields
					continue;
				}
				
				final BindField bind = dtoField.getAnnotation(BindField.class);
				
				final String dtoFieldName = dtoField.getName();
				if (bind == null) {
					throw new IllegalStateException("no Bind annotation found on field " + dtoFieldName);
				}
				
				final String bindTo = bind.name();
				if (Strings.isNullOrEmpty(bindTo)) {
					throw new IllegalArgumentException("Found a Bind annotation without a name field");
				}
				
				for (Field entityField : entityType.getDeclaredFields()) {
					
					final String entityFieldName = entityField.getName();
					if (entityFieldName.equals(bindTo)) {
						try {
							
							final String setterName = Joiner.on("").join(SETTER_PREFIX, WordUtils.capitalize(entityFieldName));
							final Method entitySetter = result.getClass().getDeclaredMethod(setterName, entityField.getType());
							
							final String getterName = Joiner.on("").join(GETTER_PREFIX, WordUtils.capitalize(dtoFieldName));
							final Method dtoGetter = dtoType.getDeclaredMethod(getterName, new Class[]{});
							
							entitySetter.invoke(result, dtoGetter.invoke(dto, (Object[]) null));
							
						} catch (SecurityException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
							throw new BindAssemblyException(e);
						}
					}
				}
			}
			return result;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setDao(Dao<? extends BaseEntity<I>, I> dao) {
		this.dao = (Dao<BaseEntity<I>, I>) dao;
	}

	@SuppressWarnings("unchecked")
	public void setAssembler(Assembler<D, ? extends BaseEntity<I>, I> assembler) {
		this.assembler = (Assembler<D, BaseEntity<I>, I>) assembler;
	}
}
