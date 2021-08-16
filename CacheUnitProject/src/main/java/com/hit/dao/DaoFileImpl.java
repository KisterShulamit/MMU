package com.hit.dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<java.lang.Long, DataModel<T>> {

	// use for the default c'tor
	private static final int DEFAULT_CAPACITY = 2;

	// global gson object
	private static final Gson gson = new Gson();

	// class members
	private String filePath;
	private int capacity;

	// Default constructor
	public DaoFileImpl(String filePath) {

		// initialization class members
		super();
		this.filePath = filePath;
		this.capacity = DEFAULT_CAPACITY;

		// initialization the JSON file
		this.initializationFile(filePath);
	}

	// Constructor
	public DaoFileImpl(String filePath, int capacity) {

		// initialization class members
		super();
		this.filePath = filePath;
		this.capacity = capacity;

		// initialization the JSON file
		this.initializationFile(filePath);
	}

	// initialization the JSON file
	void initializationFile(String filePath) {
		try {

			// clear the JSON file and close it
			new FileWriter(filePath, false).close();

			// throw IOException
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Read data source, return it converted to DataModeles
	public ArrayList<DataModel<T>> readFromJson() {
		try {

			// create a reader
			FileReader reader = new FileReader(this.filePath);

			// convert JSON array to array list of DataModels
			Type dataModelsListType = new TypeToken<ArrayList<DataModel<T>>>() {
			}.getType();
			ArrayList<DataModel<T>> dataModels = gson.fromJson(reader, dataModelsListType);

			// close reader
			reader.close();

			return dataModels;

		} // throw IOException
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Get dataModels ,convert them to JSON, write to data source
	public void writeToJson(ArrayList<DataModel<T>> dataModels) {
		try {

			// create a writer
			FileWriter writer = new FileWriter(this.filePath);

			// convert dataModels object to JSON file
			gson.toJson(dataModels, writer);

			// close writer
			writer.close();
			
			// throw IOException
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	//Saves a given entity in data source
	public void save(DataModel<T> entity) {

		//contain converted dataModels
		ArrayList<DataModel<T>> dataModels = this.readFromJson();
		
		//if there are no dataModels, initialization dataModels to be empty
		if (dataModels == null) {
			dataModels = new ArrayList<DataModel<T>>();
		}
		
		// if the memory is full, print a message
		if (this.capacity == dataModels.size()) {
			System.out.println("Memory capacity is full");
			return;
		}
		
		//not save null object
		if (entity == null) {
			return;
		}
		
		for (DataModel<T> dataModel : dataModels) {
			if(dataModel.getId() == entity.getId()) {
				dataModels.remove(dataModel);
			}
		}
		
		//add entity to source file just if it not already exist
		
		dataModels.add(entity);
		this.writeToJson(dataModels);
		

	}

	@Override
	//Deletes a given entity from data source
	public void delete(DataModel<T> entity) throws IllegalArgumentException {
		
		//throw IllegalArgumentException - in case the given entity is null.
		if (entity == null) {
			throw new IllegalArgumentException();
		}
		
		//contain converted dataModels
		ArrayList<DataModel<T>> dataModels = this.readFromJson();

		//if there are no dataModels, return null
		if (dataModels == null) {
			return;
		}
		
		//remove entity from dataModels list
		dataModels.remove(entity);
		
		//write the dataModels list update to GSON
		this.writeToJson(dataModels);
	}

	@Override
	//Retrieves an entity by its id
	public DataModel<T> find(Long id) throws IllegalArgumentException {

		//throw IllegalArgumentException - in case the given entity is null.
		if (id == null) {
			throw new IllegalArgumentException();
		}
		
		//contain converted dataModels
		ArrayList<DataModel<T>> dataModels = this.readFromJson();

		//if there are no dataModels, return null
		if (dataModels == null) {
			return null;
		}
		
		//return an entity by its id- if exist
		for (DataModel<T> dataModel : dataModels) {
			if (dataModel.getDataModelId() == id) {
				return dataModel;
			}
		}

		//else, return null
		return null;
	}

}
