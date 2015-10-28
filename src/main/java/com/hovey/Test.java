package com.hovey;

import java.sql.Date;
import java.util.Calendar;



public class Test {
	
	
	public static void main (String args[]){
		Calendar dealStartDate=Calendar.getInstance();
		dealStartDate.set(2013, 7, 30);
		Calendar dealEndDate=Calendar.getInstance();
		dealEndDate.set(2014, 7, 20);
		
		Long start=dealStartDate.getTimeInMillis();
		Long end=dealEndDate.getTimeInMillis();
		Long diff=start-end;
		double days=(diff/(1000*60*60*24));
		Long months= (long) Math.round((days/30));
		
		
		/*int year = Calendar.YEAR;
		int months = Calendar.MONTH;
		int days=Calendar.DAY_OF_YEAR;
		int difference=((dealStartDate.get(year) - dealEndDate.get(year)) * 12) + dealStartDate.get(months) - dealEndDate.get(months);*/
	
		/*int difference = dealEndDate.get(year) - dealStartDate.get(year);
		if(difference>0){
		 months=year*12;
		}*/
		/*int difference = dealEndDate.get(year) - dealStartDate.get(year);
		if (difference > 0 && 
		    (dealEndDate.get(month) < dealStartDate.get(month))) {
		    difference--;
		} */
	System.out.println("difference"+months);
	
	int a=11/12;
	System.out.println("a "+a);
	
	System.out.println(6%12);
	
	}

	
}
	
	
		
		
	
	

