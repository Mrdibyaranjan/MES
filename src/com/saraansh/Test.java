package com.saraansh;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class Test {
	
	public static void main(String[] args) {
		
		
		
		
		 List<Integer> list = new ArrayList<>();
		 
		for (int i = 1; i <21; i++) {
			list.add(i);
		}
		System.out.println(list); 
	List<Integer> primeNo	=  list.stream().filter(x-> computePrime(x)).collect(Collectors.toList() );
	
	
		System.out.println(primeNo);  
	 
		
	}
	
	static boolean  computePrime(int number) {
		boolean  val = true;
		for (int i = 2; i < number; i++) {
			
			if(number % i == 0) {
				return false;
				
			}
			
		}
		
		
		
		return val;
	}
	 

}
