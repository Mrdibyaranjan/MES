package com.saraansh.profiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.saraansh.database.DatabaseAdaptor;

import dao.CustomerProfile;

@SuppressWarnings("rawtypes")
public class LoadCustomerProfile implements Callable {

	List<String> account_id_list = new ArrayList<String>();
	Map<String, CustomerProfile> customerProfileMap = new HashMap<>();

	public LoadCustomerProfile (List<String> account_id_list) {
		this.account_id_list = account_id_list;
	}

	
	public Map<String, CustomerProfile> getCustomerProfileMap() {
		return customerProfileMap;
	}

	@Override
	public Map<String, CustomerProfile> call() throws Exception {
		try {
			customerProfileMap = new DatabaseAdaptor().getCustomerProfileInfo(account_id_list);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (account_id_list != null) {
				account_id_list.clear();
				account_id_list = null;
			}
		}
		return customerProfileMap;
	}
}