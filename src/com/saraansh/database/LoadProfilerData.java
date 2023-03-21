package com.saraansh.database;

import java.util.concurrent.Callable;

import dao.ProfilerData;

@SuppressWarnings("rawtypes")
public class LoadProfilerData implements Callable {

	private String entityNumber = "";

	private ProfilerData profile = null;
	private Integer channel;
	private Integer mode;
	private Integer entityID;

	public LoadProfilerData(String entityNumber, Integer channel, Integer mode, Integer entityID) {
		this.entityNumber = entityNumber;
		this.channel = channel;
		this.mode = mode;
		this.entityID = entityID;
	}

	@Override
	public ProfilerData call() throws Exception {
		try {
			profile = new DatabaseAdaptor().getProfilerData(entityNumber, channel, mode, entityID);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			entityNumber = "";
		}

		return profile;

	}

}