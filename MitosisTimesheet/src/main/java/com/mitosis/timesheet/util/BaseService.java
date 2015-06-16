package com.mitosis.timesheet.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public abstract class BaseService<T> {

	private static final String PERSISTENCE_UNIT_NAME = "mitosisTimesheet";
	private EntityManagerFactory factory;
	
	protected EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		entityManager = factory.createEntityManager();
		return entityManager;
	}

	protected EntityTransaction getTransaction() {
		getEntityManager();
		return entityManager.getTransaction();
	}

	protected void begin() {
		getEntityManager();

		entityManager.getTransaction().begin();
	}

	protected <T> TypedQuery<T> createQuery(String query, Class<T> clazz) {
		return entityManager.createQuery(query, clazz);
	}

	protected Object persist(Object object) {
		entityManager.persist(object);
		return object;
	}

	protected Object merge(Object object) {
		entityManager.merge(object);
		return object;
	}

	protected void commit() {
		entityManager.getTransaction().commit();
	}

	protected void remove(Object entity) {
		entityManager.remove(entity);
	}

	protected void flush() {
		entityManager.flush();
	}

	protected void clear() {
		entityManager.clear();
	}

	protected void close() {
		System.out.println("Connection Start :"+entityManager.isOpen());
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		System.out.println("Connection End :"+entityManager.isOpen());

	}

}