package com.dunhili.lightsout.tests;

import static org.junit.Assert.assertEquals;
import java.util.List;

import org.junit.Test;

import com.dunhili.lightsout.board.Level;
import com.dunhili.lightsout.utils.LevelUtil;

/**
 * Tests the {@link LevelUtil} class.
 * @author dunhili
 */
public class LevelUtilTest {
	
	/**
	 * Tests the {@link LevelUtil#readLevelsFromFile(String)} method for a valid file.
	 */
	@Test
	public void readLevelFile() {
		List<Level> levels = LevelUtil.readLevelsFromFile(LevelUtil.TEST_LEVEL_FILE_NAME);
		
		Level level0 = levels.get(0);
		assertEquals(2, level0.getIdealNumberOfMoves());
		assertEquals("BWW WBW WWB", level0.getCompressedLayout());
		
		Level level4 = levels.get(4);
		assertEquals(4, level4.getIdealNumberOfMoves());
		assertEquals("BBWB WWWW BWBB", level4.getCompressedLayout());
	}
	
	/**
	 * Tests the {@link LevelUtil#readLevelsFromFile(String)} method for a file that isn't there.
	 */
	@Test
	public void readInvalidFile() {
		LevelUtil.readLevelsFromFile("badfilename.txt");
	}
	
	/**
	 * Tests the {@link LevelUtil#saveLevels(List, String)} method.
	 */
	@Test
	public void writeLevels() {
		List<Level> levels = LevelUtil.readLevelsFromFile(LevelUtil.TEST_LEVEL_FILE_NAME);
		assertEquals(0, levels.get(0).getNumberOfMoves());
		levels.get(0).setNumberOfMoves(3);
		LevelUtil.saveLevels(levels, LevelUtil.TEST_LEVEL_FILE_NAME);
		
		levels = LevelUtil.readLevelsFromFile(LevelUtil.TEST_LEVEL_FILE_NAME);
		assertEquals(3, levels.get(0).getNumberOfMoves());
		levels.get(0).setNumberOfMoves(0);
		LevelUtil.saveLevels(levels, LevelUtil.TEST_LEVEL_FILE_NAME);
	}
}
