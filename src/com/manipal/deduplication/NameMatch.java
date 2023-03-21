package com.manipal.deduplication;

import java.util.ArrayList;

public class NameMatch {
	String matched_name = new String();
	String matched_ID = new String();
	String list_type = new String();
	ArrayList<NameScore> match_scores = new ArrayList<NameScore>();
	public double fullNameScore()
	{
		double score = 0.0;
		for( NameScore partNameScore:match_scores)
		{
			score += partNameScore.getMatch_score() * getWeightage(partNameScore.getField_name());
		}
		return score;
	}
	
	public double getWeightage(String fieldName){
		double weightage = 0;
		if(fieldName.equalsIgnoreCase("FIRST_NAME") )
			weightage = ConstantConfiguration.FFWeightage;
		else if(fieldName.equalsIgnoreCase("SECOND_NAME") )
			weightage = ConstantConfiguration.MMWeightage;
		else if(fieldName.equalsIgnoreCase("THIRD_NAME") )
			weightage = ConstantConfiguration.LLWeightage;		
		return weightage;
	}
	
	public static double getThreshold(String fieldName){
		double threshold = 0;
		if(fieldName.equalsIgnoreCase("FIRST_NAME") )
			threshold = ConstantConfiguration.FFThreshold;
		else if(fieldName.equalsIgnoreCase("SECOND_NAME") )
			threshold = ConstantConfiguration.MMThreshold;
		else if(fieldName.equalsIgnoreCase("THIRD_NAME") )
			threshold = ConstantConfiguration.LLThreshold;		
		return threshold;
	}
	
	public void setIfHigher(NameMatch newMatch)
	{
		if(fullNameScore() < newMatch.fullNameScore())
	
			this.match_scores = newMatch.getMatch_scores();	
		
	}
	
	public String getMatched_name() {
		return matched_name;
	}
	public void setMatched_name(String matched_name) {
		this.matched_name = matched_name;
	}
	public String getMatched_ID() {
		return matched_ID;
	}
	public void setMatched_ID(String matched_ID) {
		this.matched_ID = matched_ID;
	}
	public String getList_type() {
		return list_type;
	}
	public void setList_type(String list_type) {
		this.list_type = list_type;
	}
	public ArrayList<NameScore> getMatch_scores() {
		return match_scores;
	}
	public void setMatch_scores(ArrayList<NameScore> match_scores) {
		this.match_scores = match_scores;
	}
	public void addScore(NameScore score)
	{
		if(score != null)
			match_scores.add(score);
	}
}
