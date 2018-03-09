package sbirk.stocks.test;

import java.util.HashMap;

public class CalendarHelper {
	
	private HashMap<Integer, Integer> calendarMap = new HashMap<Integer, Integer>();
	private int BASE_LEAP_YEAR = 1980;
	private int YEARS_BETWEEN_LEAP_YEAR = 4;
	
	public CalendarHelper () {
		calendarMap.put(1, 31);
		calendarMap.put(2, 28);
		calendarMap.put(3, 31);
		calendarMap.put(4, 30);
		calendarMap.put(5, 31);
		calendarMap.put(6, 30);
		calendarMap.put(7, 31);
		calendarMap.put(8, 31);
		calendarMap.put(9, 30);
		calendarMap.put(10, 31);
		calendarMap.put(11, 30);
		calendarMap.put(12, 31);
	}

	public int monthToInteger (String month) {
		int count = 1;
		String[] months = new String[] 
					{"Jan", "Feb", "Mar", "Apr",
						"May", "Jun", "Jul", "Aug",
							"Sep", "Oct", "Nov", "Dec"};
		for (String m: months) {
			if (month.equalsIgnoreCase(m)) return count;
			count++;
		}
		return -1;
	}
	
	public String parseDate (String date) {
		int numberDaysIntoYear = dayOfYear(date);
		String[] dateSplit = date.split(" ");
		return new String (numberDaysIntoYear + " " + dateSplit[5] + " " + dateSplit[3]);
	}
	
	// normal date value --> input
	public int dayOfYear (String date) {
		
		String[] splitDate = date.split(" ");
		
		int total = 0;
		int dayOfMonth = Integer.parseInt(splitDate[2]);
		int month = monthToInteger(splitDate[1]);
		
		for (int key: calendarMap.keySet()) {
			if (month < key) {
				if (key == 2) {
					
					total += calendarMap.get(key);
					
					if (isLeapYear(Integer.parseInt(splitDate[5]))) {
						total++;
					}
				}
			} else {
				break;
			}
		}
		return total + dayOfMonth;
	}
	
	public boolean isLeapYear (int year) {
		return (year - BASE_LEAP_YEAR) % YEARS_BETWEEN_LEAP_YEAR == 0;
	}
	
}
