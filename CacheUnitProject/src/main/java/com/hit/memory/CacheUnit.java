package com.hit.memory;

import com.hit.memory.CacheUnit;
//import com.hit.algorithm.IAlgoCache;
import com.hit.dm.DataModel;

public class CacheUnit<T> extends java.lang.Object {

	/* member of type IAlgoCache */
	private com.hit.algorithm.IAlgoCache<java.lang.Long, DataModel<T>> algorithmCache;

	/* Return all id's of DataModel objects */
	public DataModel<T>[] getDataModels(Long[] ids){

        DataModel<T>[] dataModels = new DataModel[ids.length];
        for (int i = 0; i < ids.length; i++) {
            dataModels[i]= (DataModel) algorithmCache.getElement(ids[i]);
        }
        return dataModels;
    }

	/* put all DataModel objects  to the file */
	public DataModel<T>[] putDataModels(DataModel<T>[] dataModels) {
		DataModel<T>[] dataModelsRes =  new DataModel[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) {
            DataModel<T> removedElement = (DataModel<T>) algorithmCache.putElement(dataModels[i].getDataModelId(), dataModels[i]);
            dataModelsRes[i] = removedElement;
        }
        return dataModelsRes;
	}
	
	/* remove all DataModel objects from the file */
	public void removeDataModels(java.lang.Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			algorithmCache.removeElement(ids[i]);
		}
	}

	/* C'tor */
	public CacheUnit(com.hit.algorithm.IAlgoCache<java.lang.Long, DataModel<T>> algo) {
		this.algorithmCache = algo;
	}

	/* Getter & Setter */
	public com.hit.algorithm.IAlgoCache<java.lang.Long, DataModel<T>> getAlgorithmCache() {
		return algorithmCache;
	}

	public void setAlgorithmCache(com.hit.algorithm.IAlgoCache<java.lang.Long, DataModel<T>> algorithmCache) {
		this.algorithmCache = algorithmCache;
	}

}

