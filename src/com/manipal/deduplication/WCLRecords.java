package com.manipal.deduplication;

import java.util.ArrayList;

public class WCLRecords {
    private int uid;
	private String category;
    private String subcategory;
    private String ssn;
    private String ei;
    private String first_name;
    private String last_name;
    private ArrayList<String>  arrListAliases = null;
    private String dob;
    private ArrayList<String> arrListPassports = null;
    private ArrayList<String>  arrListCountries = null;
    private ArrayList<String>  arrListCity = null;
    private ArrayList<String>  arrListState = null;
    private ArrayList<String>  arrListAddress = null;
    
    
    public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getEi() {
		return ei;
	}
	public void setEi(String ei) {
		this.ei = ei;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public ArrayList<String> getArrListAliases() {
		return arrListAliases;
	}
	public void setArrListAliases(ArrayList<String> arrListAliases) {
		this.arrListAliases = arrListAliases;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public ArrayList<String> getArrListPassports() {
		return arrListPassports;
	}
	public void setArrListPassports(ArrayList<String> arrListPassports) {
		this.arrListPassports = arrListPassports;
	}
	public ArrayList<String> getArrListCountries() {
		return arrListCountries;
	}
	public void setArrListCountries(ArrayList<String> arrListCountries) {
		this.arrListCountries = arrListCountries;
	}
	public ArrayList<String> getArrListCity() {
		return arrListCity;
	}
	public void setArrListCity(ArrayList<String> arrListCity) {
		this.arrListCity = arrListCity;
	}
	public ArrayList<String> getArrListState() {
		return arrListState;
	}
	public void setArrListState(ArrayList<String> arrListState) {
		this.arrListState = arrListState;
	}
	public ArrayList<String> getArrListAddress() {
		return arrListAddress;
	}
	public void setArrListAddress(ArrayList<String> arrListAddress) {
		this.arrListAddress = arrListAddress;
	}
	
}
