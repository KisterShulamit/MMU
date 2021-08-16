package com.hit.services;

import java.io.IOException;

//import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.Random;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

//import java.io.IOException;

public class CacheUnitService<T> {

	private static final String algoCache = "RANDOM";
	private static final int capacity = 2;
	private int sumSwaps, sumRequests, sumDataModels;
	private final CacheUnit<T> cacheUnit;
	IDao<Long, DataModel<T>> dao;

	public CacheUnitService() {

		this.dao = new DaoFileImpl<>("src/main/resources/datasource.txt", capacity);

		this.cacheUnit = new CacheUnit<>(new Random<Long, DataModel<T>>(capacity));
		this.sumRequests = 0;
		this.sumDataModels = 0;
		this.sumSwaps = 0;
	}

	public boolean delete(DataModel<T>[] dataModels) {

		Long[] ids = new Long[dataModels.length];

		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId();
			dao.delete(dataModels[i]);
		}
		this.sumRequests++;
		cacheUnit.removeDataModels(ids);

		return true;

	}

	public boolean update(DataModel<T>[] dataModels) {

		System.out.println("update request!");
		sumRequests++;
		sumDataModels += dataModels.length;
		DataModel<T>[] removedElements = cacheUnit.putDataModels(dataModels);
		for (DataModel<T> removedElement : removedElements) {
			if (removedElement != null) {
				sumSwaps++;
				dao.save(removedElement);
			}
		}

		return true;

	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
			sumRequests++;
			sumDataModels += dataModels.length;

			Long[] ids = new Long[dataModels.length];

			for (int i = 0; i < dataModels.length; i++) {
				ids[i] = dataModels[i].getDataModelId();
			}
			DataModel<T>[] models = cacheUnit.getDataModels(ids);

			for (int i = 0; i < models.length; i++) {
				if (models[i] == null) {
					models[i] = dao.find(dataModels[i].getDataModelId());
				}
			}
			cacheUnit.putDataModels(models);

			return models;
		
	}

	public String getStatistics() {
		return algoCache + "," + capacity + "," + sumSwaps + "," + sumRequests + "," + sumDataModels;
	}
}
