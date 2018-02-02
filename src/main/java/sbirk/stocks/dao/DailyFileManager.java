package sbirk.stocks.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sbirk.stocks.core.StockCore;

public class DailyFileManager {
	
	private Object dailySyncObject = new Object();
	
	private String dataPath;
	private String dailyFileName = new String("dailyQuote.txt");
	
	private FileWriter dailyFileWriter;
	private BufferedReader dailyBufferedReader;
	
	public DailyFileManager (String ticker) {
		
		new File(StockCore.getDataDirectory() + ticker).mkdirs();
		
		dataPath = new String(StockCore.getDataDirectory() + ticker + "\\" + dailyFileName);
		
		try {
			Files.createFile(Paths.get(dataPath));
		} catch (FileAlreadyExistsException x) {
			x.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("dailyQuote.txt file creation failed, program terminating");
			System.exit(1);
		}
		
		try {
			dailyFileWriter = new FileWriter(dataPath);
			dailyBufferedReader = new BufferedReader(new FileReader(dataPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean resetReader () {
		synchronized (dailySyncObject) {
			try {
				dailyBufferedReader.close();
				dailyBufferedReader = new BufferedReader(
						new FileReader(dataPath + "\\" + dailyFileName));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}
	
	public boolean write (String line, boolean endLine) {
		synchronized (dailySyncObject) {
			try {
				if (endLine) {
					dailyFileWriter.write(line + "\r\n");
				} else {
					dailyFileWriter.write(line);
				}
				dailyFileWriter.flush();
				return true;
			} catch (IOException e) {
				return false;
			}
		}
	}
	
	public String readLine () {
		synchronized (dailySyncObject) {
			try {
				return dailyBufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		}
	}
	
	public boolean close () {
		synchronized (dailySyncObject) {
			try {
				dailyFileWriter.close();
				dailyBufferedReader.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public String getLineAt (int lineNumber) {
		String line;
		int count = 0;
		
		if (resetReader()) {
			while ((line = readLine()) != null) {
				if (count == lineNumber) return line;
				count++;
			}
		}
		
		return "";
	}
	
	public String getLineContaining (String sample) {
		String line;
		
		if(resetReader()) {
			while ((line = readLine()) != null) {
				if (line.contains(sample)) return line;
			}
		}
		return "";
	}
	
	// takes date in <ddd + yyyy>
	// date to be stored as <ddd + yyyy + ***everything else thats useless***>
	public String getLineFromDate (String date) {
		String line;
		String[] dateInfo = date.split(" ");
		String[] lineDateInfo;
		if (resetReader()) {
			while((line = readLine()) != null) {
				lineDateInfo = line.split("||")[0].split(" ");
				if (lineDateInfo[0].equals(dateInfo[0]) && lineDateInfo[1].equals(dateInfo[1])) {
					return line;
				}
			}
		}
		return "";
	}
	
	public String getStatistic (String date, String valueName) {
		String line = getLineFromDate(date);
		String[] values = line.split("||");
		for (String value : values) {
			if (value.split("|")[0].contains(valueName)) return value.split("|")[1];
		}
		return "";
	}
}
