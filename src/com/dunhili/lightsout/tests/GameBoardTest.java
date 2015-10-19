package com.dunhili.lightsout.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.dunhili.lightsout.board.GameBoard;
import com.dunhili.lightsout.board.TileColor;

/**
 * Tests the {@link GameBoard} class.
 * @author dunhili
 */
public class GameBoardTest {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private GameBoard board;
	
	/**
	 * Tests the {@link GameBoard#GameBoard(int)} constructor.
	 */
	@Test
	public void constructEmptyBoardWithEqualDimensions() {
		board = new GameBoard(4);
		assertEquals(4, board.getWidth());
		assertEquals(4, board.getHeight());
		assertEquals(TileColor.WHITE, board.getTileAt(0, 0));
		assertEquals(TileColor.WHITE, board.getTileAt(3, 3));
	}
	
	/**
	 * Tests the {@link GameBoard#GameBoard(int, int)} constructor.
	 */
	@Test
	public void constructEmptyBoardWithDifferentDimensions() {
		board = new GameBoard(4, 5);
		assertEquals(4, board.getWidth());
		assertEquals(5, board.getHeight());
		assertEquals(TileColor.WHITE, board.getTileAt(0, 0));
		assertEquals(TileColor.WHITE, board.getTileAt(3, 3));
	}
	
	/**
	 * Tests the {@link GameBoard#GameBoard(String)} constructor for a valid String.
	 */
	@Test
	public void validStringConstructor() {
		board = new GameBoard("WWWW", "BBBB", "WBWB", "BBWW");
		assertEquals(4, board.getWidth());
		assertEquals(4, board.getHeight());
		assertEquals(TileColor.WHITE, board.getTileAt(0, 0));
		assertEquals(TileColor.WHITE, board.getTileAt(3, 3));
		assertEquals(TileColor.BLACK, board.getTileAt(1, 1));
		assertEquals(TileColor.WHITE, board.getTileAt(0, 2));
		assertEquals(TileColor.BLACK, board.getTileAt(1, 2));
		assertEquals(TileColor.WHITE, board.getTileAt(2, 2));
		assertEquals(TileColor.BLACK, board.getTileAt(3, 2));
		
		board = new GameBoard("WWWWW", "BBBBB", "BWBWB", "BBBWW");
		assertEquals(5, board.getWidth());
		assertEquals(4, board.getHeight());
	}
	
	/**
	 * Tests the {@link GameBoard#GameBoard(String)} constructor for a null String.
	 */
	@Test
	public void invalidStringConstructorNullString() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Board cannot be null.");
		board = new GameBoard((String[]) null);
	}
	
	/**
	 * Tests the {@link GameBoard#GameBoard(String)} constructor for an empty String.
	 */
	@Test
	public void invalidStringConstructorEmptyString() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Each row in the board must be non-empty.");
		board = new GameBoard("", "");
	}
	
	/**
	 * Tests the {@link GameBoard#GameBoard(String)} constructor for a String with unequal rows.
	 */
	@Test
	public void invalidStringConstructorNonEqualRows() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Each row in the board must be the same length.");
		board = new GameBoard("WW", "BBB");
	}
	
	/**
	 * Tests {@link GameBoard#getTileAt(int, int)} for a valid x and y coordinate.
	 */
	@Test
	public void getTileAtValid() {
		board = new GameBoard(4);
		assertEquals(TileColor.WHITE, board.getTileAt(0, 0));
	}
	
	/**
	 * Tests {@link GameBoard#getTileAt(int, int)} for an invalid x and y coordinate.
	 */
	@Test
	public void getTileAtInvalid() {
		board = new GameBoard(4);
		assertEquals(TileColor.INVALID, board.getTileAt(4, 4));
		assertEquals(TileColor.INVALID, board.getTileAt(-1, -1));
	}
	
	/**
	 * Tests {@link GameBoard#setTileAt(int, int, TileColor)} for a valid x and y coordinate.
	 */
	@Test
	public void setTileAtValid() {
		board = new GameBoard(4);
		assertEquals(TileColor.WHITE, board.getTileAt(0, 0));
		board.setTileAt(0, 0, TileColor.BLACK);
		assertEquals(TileColor.BLACK, board.getTileAt(0, 0));
	}
	
	/**
	 * Tests {@link GameBoard#setTileAt(int, int, TileColor)} for an invalid x and y coordinate.
	 */
	@Test
	public void setTileAtInvalid() {
		board = new GameBoard(4);
		assertEquals(TileColor.WHITE, board.getTileAt(0, 0));
		board.setTileAt(5, 5, TileColor.BLACK);
		assertEquals(TileColor.WHITE, board.getTileAt(0, 0));
	}
	
	/**
	 * Tests {@link GameBoard#isGameWon()} when the user hasn't won the game.
	 */
	@Test
	public void gameIsNotWon() {
		board = new GameBoard(4);
		assertFalse(board.isGameWon());
	}
	
	/**
	 * Tests {@link GameBoard#isGameWon()} when the user has won the game.
	 */
	@Test
	public void gameIsWon() {
		board = new GameBoard("BBB", "BBB", "BBB");
		assertTrue(board.isGameWon());
	}
	
	/**
	 * Tests the {@link GameBoard#resetBoard(String)} method.
	 */
	@Test
	public void resetBoard() {
		board = new GameBoard("BBB", "WWW");
		assertEquals(TileColor.BLACK, board.getTileAt(0, 0));
		board.resetBoard("WWW", "BBB");
		assertEquals(TileColor.WHITE, board.getTileAt(0, 0));
	}
	
	/**
	 * Tests the {@link GameBoard#saveBoard()} method.
	 */
	@Test
	public void saveBoard() {
		board = new GameBoard("BBB", "WWW", "BWB");
		assertArrayEquals(new String[] {"BBB", "WWW", "BWB"}, board.saveBoard());
	}
	
	/**
	 * Tests the {@link GameBoard#toString()} method.
	 */
	@Test
	public void toStringCheck() {
		board = new GameBoard("BBB", "WWW");
		assertEquals("BBB\nWWW", board.toString());
		

		board = new GameBoard("BBBW", "BWWW", "BWBW");
		assertEquals(TileColor.WHITE, board.getTileAt(3, 0));
		assertEquals("BBBW\nBWWW\nBWBW", board.toString());
	}
	
	/**
	 * Tests the {@link GameBoard#swapTiles(int, int)} method for valid coordinates.
	 */
	@Test
	public void swapTilesValid() {
		board = new GameBoard("WWW", "WWW", "WWW");
		board.swapTiles(1, 1);
		assertEquals(TileColor.BLACK, board.getTileAt(1, 1));
		assertEquals(TileColor.BLACK, board.getTileAt(0, 1));
		assertEquals(TileColor.BLACK, board.getTileAt(1, 0));
		assertEquals(TileColor.BLACK, board.getTileAt(2, 1));
		assertEquals(TileColor.BLACK, board.getTileAt(1, 2));
		
		board.swapTiles(0, 0);
		assertEquals(TileColor.BLACK, board.getTileAt(0, 0));
		assertEquals(TileColor.WHITE, board.getTileAt(0, 1));
		assertEquals(TileColor.WHITE, board.getTileAt(1, 0));
	}
	
	/**
	 * Tests the {@link GameBoard#swapTiles(int, int)} method for invalid coordinates.
	 */
	@Test
	public void swapTilesInvalid() {
		board = new GameBoard("WWW", "WWW", "WWW");
		board.swapTiles(-1, 0);
		assertEquals(TileColor.WHITE, board.getTileAt(0, 0));
		
		board.swapTiles(2, 3);
		assertEquals(TileColor.WHITE, board.getTileAt(2, 2));
	}
	
	/**
	 * Tests the {@link GameBoard#isGameWon()} method after the user solves a puzzle.
	 */
	@Test
	public void winTheGame() {
		board = new GameBoard("WWW", "WWW", "WWW");
		board.swapTiles(1, 1);
		assertFalse(board.isGameWon());
		
		board.swapTiles(0, 0);
		assertFalse(board.isGameWon());
		
		board.swapTiles(2, 0);
		board.swapTiles(0, 2);
		board.swapTiles(2, 2);
		assertTrue(board.isGameWon());
	}
}
