package com.dunhili.lightsout.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dunhili.lightsout.board.Level;

/**
 * A utility class that handles extra behaviors for levels, such as reading and saving levels to files.
 * @author dunhili
 */
public class LevelUtil {
	public static final String LEVEL_FILE_NAME = "levels.txt";
	public static final String TEST_LEVEL_FILE_NAME = "levels_test.txt";
	
	private static final Logger log = Logger.getLogger(LevelUtil.class);
	
	/** Can't be instantiated. */
	private LevelUtil() { }
	
	/**
	 * Read in the levels from the default file location 'levels.txt' and create and return the list of levels
	 * located in that file. 
	 * @return list of levels located in the 'levels.txt' file
	 */
	public static List<Level> readLevelsFromFile() {
		return readLevelsFromFile(LEVEL_FILE_NAME);
	}
	
	/**
	 * Read in the levels in the file with the given file name and create and return the list of levels
	 * located in that file. 
	 * @param fileName name of the file with the list of levels to load
	 * @return list of levels located in the file with the given file name
	 */
	public static List<Level> readLevelsFromFile(String fileName) {
		if (log.isTraceEnabled()) {
			log.trace("readLevelsFromFile(" + fileName + ")");
		}
		List<Level> levels = new ArrayList<Level>();
		
		BufferedReader fileReader = null;
        try {
        	log.info("Reading in levels...");
        	InputStream in = LevelUtil.class.getResourceAsStream("resources" + File.separator + fileName); 
        	fileReader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = fileReader.readLine()) != null) {
            	levels.add(createLevelFromString(line));
            }
            log.info("Done reading levels.");
		} catch (IOException e) {
			log.error("Error while opening or reading file : ", e);
		} finally {
			if (fileReader != null) {
		        try {
		        	fileReader.close();
		        	log.info("Reader closed successfully.");
				} catch (IOException e) {
					log.error("Error while closing reader : ", e);
				}
			}
		}

        return levels;
	}
	
	/**
	 * Saves the list of levels to the file 'levels.txt.'
	 * @param levels list of levels to save to the file
	 */
	public static void saveLevels(List<Level> levels) {
		saveLevels(levels, LEVEL_FILE_NAME);
	}
	
	/**
	 * Saves the list of levels to a file with the given file name.
	 * @param levels list of levels to save to the file
	 * @param fileName name of the file to save the levels to
	 */
	public static void saveLevels(List<Level> levels, String fileName) {
		if (log.isTraceEnabled()) {
			log.trace("saveLevels(" + fileName + ")");
		}
		FileOutputStream out = null;
        try {
    		// write the new String with the replaced line OVER the same file
            out = new FileOutputStream("resources" + File.separator + fileName);
            log.info("Output file opened.");
            StringBuilder builder = new StringBuilder();
            for (Level level : levels) {
            	builder.append(level.getIdealNumberOfMoves() + " " + 
            				   level.getNumberOfMoves() + " " + 
            				   level.getCompressedLayout() + "\n");
            }
            builder.delete(builder.length() - 1, builder.length());  // delete last '\n'
            if (log.isDebugEnabled()) {
            	log.debug(builder.toString());
            }
			out.write(builder.toString().getBytes());
		} catch (IOException e) {
			log.error("Error while opening or writing to output file : ", e);
		} finally {
			if (out != null) {
		        try {
					out.close();
					log.info("Output file closed.");
				} catch (IOException e) {
					log.error("Error while closing output file : ", e);
				}
			}
		}
	}
	
	public static void deleteSaveFile() {
		deleteSaveFile(LEVEL_FILE_NAME);
	}
	
	public static void deleteSaveFile(String fileName) {
		if (log.isTraceEnabled()) {
			log.trace("deleteSaveFile(" + fileName + ")");
		}
		
    	String userHomeDirectory = System.getProperty("user.home");
    	File fileToDelete = new File(userHomeDirectory + File.separator + fileName);
    	fileToDelete.delete();
	}
	
	/**
	 * Creates a level from a String read in from a text file. The String should be in the format :
	 * <p>'number' 'number' 'one or more Strings of 'B' or 'W' that are the same length'
	 * <p>ex: 5 0 WBW BWB BBB
	 * @param str String to parse the level from
	 * @return level created from the given String
	 */
	private static Level createLevelFromString(String str) {
		if (log.isTraceEnabled()) {
			log.trace("Level : " + str);
		}
		String[] levelParts = str.split(" ");
		int idealNumberOfMoves = Integer.parseInt(levelParts[0]);
		int numberOfMoves = Integer.parseInt(levelParts[1]);
		String[] layout = new String[levelParts.length - 2];
		for (int i = 0; i < layout.length; i++) {
			layout[i] = levelParts[i + 2];
		}
		
		return new Level(layout, idealNumberOfMoves, numberOfMoves);
	}
}
