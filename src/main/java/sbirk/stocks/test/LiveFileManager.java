package sbirk.stocks.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import sbirk.stocks.core.StockCore;

public class LiveFileManager {
private Object liveSyncObject = new Object();
	
	private String dataPath;
	private String liveFileName = new String("LiveQuote.txt");
	
	private FileWriter liveFileWriter;
	private BufferedReader liveBufferedReader;
	
	public LiveFileManager (String ticker) {
		
		new File(StockCore.getDataDirectory() + ticker).mkdirs();
		
		dataPath = new String(StockCore.getDataDirectory() + ticker + "\\" + liveFileName);
		
		try {
			Files.createFile(Paths.get(dataPath));
		} catch (FileAlreadyExistsException x) {
			x.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("liveQuote.txt file creation failed, program terminating");
			System.exit(1);
		}
		
		try {
			liveFileWriter = new FileWriter(dataPath);
			liveBufferedReader = new BufferedReader(new FileReader(dataPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean resetReader () {
		synchronized (liveSyncObject) {
			try {
				liveBufferedReader.close();
				liveBufferedReader = new BufferedReader(
						new FileReader(dataPath + "\\" + liveFileName));
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
		synchronized (liveSyncObject) {
			try {
				if (endLine) {
					liveFileWriter.write(line + "\r\n");
				} else {
					liveFileWriter.write(line);
				}
				liveFileWriter.flush();
				return true;
			} catch (IOException e) {
				return false;
			}
		}
	}
	
	public String readLine () {
		synchronized (liveSyncObject) {
			try {
				return liveBufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		}
	}
	
	public boolean close () {
		synchronized (liveSyncObject) {
			try {
				liveFileWriter.close();
				liveBufferedReader.close();
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
}
