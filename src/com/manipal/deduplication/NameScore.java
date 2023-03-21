package com.manipal.deduplication;

public class NameScore {
	String field_name;
	String sanctioned_entity_field_name;
    String screened_entity_value;
    String sanctioned_entity_value;
    Double match_score;
    Double text_matching_score = 0.0;
    Double phonetic_score = 0.0;
    
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getScreened_entity_value() {
		return screened_entity_value;
	}
	public void setScreened_entity_value(String screened_entity_value) {
		this.screened_entity_value = screened_entity_value;
	}
	public String getSanctioned_entity_value() {
		return sanctioned_entity_value;
	}
	public void setSanctioned_entity_value(String sanctioned_entity_value) {
		this.sanctioned_entity_value = sanctioned_entity_value;
	}
	public Double getMatch_score() {
		return match_score;
	}
	public void setMatch_score(Double match_score) {
		this.match_score = match_score;
	}
	public String getSanctioned_entity_field_name() {
		return sanctioned_entity_field_name;
	}
	public void setSanctioned_entity_field_name(String sanctioned_entity_field_name) {
		this.sanctioned_entity_field_name = sanctioned_entity_field_name;
	}
}
