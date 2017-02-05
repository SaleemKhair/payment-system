package repositories.loader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import util.DataBaseSettings;

public class EntityManagerLoader {

	private static EntityManagerLoader loader;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	private EntityManagerLoader() {
		entityManagerFactory = Persistence.createEntityManagerFactory("induction-payment-jpa", prepareDBProperties());
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	public EntityManagerLoader(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public static EntityManager getEntityManger() {
		if (Objects.isNull(loader)) {
			loader = new EntityManagerLoader();
		}
		return loader.entityManager;
	}

	private Map<String, String> prepareDBProperties() {
		Map<String, String> settingsMap = new HashMap<>();
		DataBaseSettings settings = DataBaseSettings.getInstance();
		settingsMap.put("javax.persistence.jdbc.user", settings.username());
		settingsMap.put("javax.persistence.jdbc.password", settings.password());
		settingsMap.put("javax.persistence.jdbc.url", settings.url());
		settingsMap.put("javax.persistence.jdbc.driver", settings.driverClassName());
		settingsMap.put("hibernate.hbm2ddl.auto", "update");
		return settingsMap;
	}

}
