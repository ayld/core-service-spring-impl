package net.ayld.core.service.impl;

import net.ayld.core.domain.TestEntity;
import net.ayld.core.dto.TestBindDto;
import net.ayld.core.persistance.Dao;
import net.ayld.core.persistance.impl.BaseDao;
import net.ayld.core.service.CrudService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Strings;

public class TestBindAssembly {
	
	private Dao<TestEntity, Integer> dao = new BaseDao<TestEntity, Integer>() {
		
		@Override
		public TestEntity read(Integer id) {
			final TestEntity result = new TestEntity();
			result.setId(1);
			result.setName("Jill");
			return result;
		}

		@Override
		public TestEntity create(TestEntity entity) {
			final TestEntity result = new TestEntity();
			result.setId(1);
			result.setName("Jill");
			return result;
		}

		@Override
		public TestEntity createOrUpdate(TestEntity entity) {
			final TestEntity result = new TestEntity();
			result.setId(1);
			result.setName("Jill");
			return result;
		}

		@Override
		public TestEntity find(Integer id) {
			final TestEntity result = new TestEntity();
			result.setId(1);
			result.setName("Jill");
			return result;
		}

		@Override
		public TestEntity update(TestEntity entity) {
			final TestEntity result = new TestEntity();
			result.setId(2);
			result.setName("Jull");
			return result;
		}

		@Override
		public TestEntity delete(Integer id) {
			final TestEntity result = new TestEntity();
			result.setId(1);
			result.setName("Jill");
			return result;
		}

		private static final long serialVersionUID = -5208642168650079251L;
	};
	
	private CrudService<TestBindDto, Integer> service = new BaseService<TestBindDto, Integer>() {
	};
	
	@Before
	public void init() {
		((BaseService<TestBindDto, Integer>) service).setDao(dao);
	}
	
	@Test
	public void testRead() {
		final TestBindDto result = service.read(1);
		
		Assert.assertTrue(result != null);
		
		Assert.assertTrue(!Strings.isNullOrEmpty(result.getUsername()));
		Assert.assertTrue(result.getUsername().equals("Jill"));
		
		Assert.assertTrue(result.getId() != null);
		Assert.assertTrue(result.getId().equals(Integer.valueOf(1)));
	}
	
	@Test
	public void testUpdate() {
		final TestBindDto test = new TestBindDto();
		test.setId(1);
		test.setNum(1);
		test.setUsername("Jill");
		
		final TestBindDto result = service.update(test);
		
		Assert.assertTrue(result != null);
		
		Assert.assertTrue(!Strings.isNullOrEmpty(result.getUsername()));
		Assert.assertTrue(result.getUsername().equals("Jull"));
		
		Assert.assertTrue(result.getId() != null);
		Assert.assertTrue(result.getId().equals(Integer.valueOf(2)));
	}
}
