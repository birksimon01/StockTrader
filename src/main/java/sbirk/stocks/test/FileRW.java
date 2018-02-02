package sbirk.stocks.test;

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

public class FileRW {
	
	private Object syncObject = new Object();
	
	private String dataPath;
	private String liveFileName = new String("liveQuote.txt");
	
	private FileWriter liveFW;
	private FileWriter dailyFW;
	private	BufferedReader liveFR;
	private BufferedReader dailyFR;
	
	private String ticker;
	
	public FileRW (String ticker) {
		this.ticker = ticker;
		dataPath = new String(StockCore.getDataDirectory() + ticker);
		new File(dataPath).mkdir();
		Path liveFile = Paths.get(dataPath + "\\" + liveFileName);
		Path dailyFile = Paths.get(dataPath + "\\" + dailyFileName);
		System.out.println("liveQuote.txt startup initiation");
		try {
		    // Create the empty file with default permissions, etc.
		    Files.createFile(liveFile);
		    System.out.println("liveQuote.txt not found, successfully created");
		} catch (FileAlreadyExistsException x) {
			//Does nothing if file already exists
			System.out.println("liveQuote.txt already created, no file initialization needed");
		} catch (IOException x) {
		    // Some other sort of failure, such as permissions.
			System.out.println("liveQuote.txt file creation failed, program terminating");
			System.exit(1);
		}
		
		System.out.println("dailyQuote.txt startup initiation");
		try {
		    // Create the empty file with default permissions, etc.
		    Files.createFile(dailyFile);
		    System.out.println("dailyQuote.txt not found, successfully created");
		} catch (FileAlreadyExistsException x) {
			//Does nothing if file already exists
			System.out.println("dailyQuote.txt already created, no file initialization needed");
		} catch (IOException x) {
		    // Some other sort of failure, such as permissions.
			System.out.println("dailyQuote.txt file creation failed, program terminating");
			System.exit(1);
		}
		
		try {
			liveFW = new FileWriter(dataPath + ticker + "\\" + "liveQuote.txt");
			dailyFW = new FileWriter(dataPath + ticker + "\\" + "dailyQuote.txt");
			liveFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "liveQuote.txt"));
			dailyFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "dailyQuote.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean resetLiveReader () {
		try {
			liveFR.close();
			liveFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "liveQuote.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean resetDailyReader () {
		try {
			dailyFR.close();
			dailyFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "dailyQuote.txt"));
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
	
	public void write(String input, boolean isLiveData) {
		synchronized (syncObject) {
			try {
				if (isLiveData) {
					liveFW.write(input);
					liveFW.flush();
				} else {
					dailyFW.write(input);
					dailyFW.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// when read
	public String readLine (boolean isLiveData, int lineLoc) {
		synchronized (syncObject) {
			int i = 0;
			try {
				if (isLiveData) {
					if (lineLoc == -1) {
						return liveFR.readLine();
					} else {
						liveFR.close();
						liveFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "liveQuote.txt"));
						while (i < lineLoc) {
							liveFR.readLine();
							i++;
						}
						return liveFR.readLine();
					}
				} else {
					if (lineLoc == -1) {
						return dailyFR.readLine();
					} else {
						dailyFR.close();
						dailyFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "dailyQuote.txt"));
						while (i < lineLoc) {
							dailyFR.readLine();
							i++;
						}
						return dailyFR.readLine();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		}
	}
	
	public String findLineWith (boolean isLiveData, String stringWith, String year) {
		synchronized (syncObject) {
			String s;
			try {
				// go until find, then return line
				if (isLiveData) {
					liveFR.close();
					liveFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "liveQuote.txt"));
					while ((s = liveFR.readLine()) != null) {
						if (s.contains(stringWith)) {
							return s;
						}
					}
				} else {
					dailyFR.close();
					dailyFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "dailyQuote.txt"));
					while ((s = dailyFR.readLine()) != null) {
						if (s.contains(stringWith)) {
							return s;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		}
	}
		
	//returns line count of specified file
	public int fileLength (boolean isLiveData) { 
		synchronized (syncObject) {
			int lineCount = 0;
			if (isLiveData) {
				try {
					while (liveFR.readLine() != null) {
						lineCount++;
					}
					liveFR.close();
					liveFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "liveQuote.txt"));
					return lineCount;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					while (dailyFR.readLine() != null) {
						lineCount++;
					}
					dailyFR.close();
					dailyFR = new BufferedReader(new FileReader(dataPath + ticker + "\\" + "dailyQuote.txt"));
					return lineCount;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return -1;
		}
	}
	
	//close all FileReaders/FileWriters
	public void close () {
		synchronized (syncObject) {
			try {
				liveFW.close();
				liveFR.close();
				dailyFW.close();
				dailyFR.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getStatistic (String date, String year, String valueName) {
		String line = this.findLineWith(false, date, year);
		String[] values = line.split("||");
		for (String value: values) {
			if (value.contains(valueName)) {
				return value.split("|")[1];
			}
		}
		return "";
	}
}