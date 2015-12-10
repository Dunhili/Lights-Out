package com.dunhili.lightsout.board;

import com.dunhili.lightsout.board.TileColor;

import org.apache.log4j.Logger;

/**
 * Contains the logic for the game itself. Handles creating the game board and turning on and off the tiles while
 * also providing the logic for determining if the user has won the game.
 * @author dunhili
 */
public class GameBoard {
	////////////////////////////////////////
	// FIELDS
	////////////////////////////////////////
	
	private static final Logger log = Logger.getLogger(GameBoard.class);
	
	private TileColor[][] board;
	
	////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////
	
	/**
	 * Creates an empty (ie all white tile)  board of dimension width x width.
	 * @param width width and height of the board
	 */
	public GameBoard(int width) {
		this(width, width);
	}
	
	/**
	 * Creates an empty (ie all white tile)  board of dimension width x height.
	 * @param width width of the board
	 * @param height height of the board
	 */
	public GameBoard(int width, int height) {
		board = new TileColor[width][height];
		createEmptyBoard();
	}
	
	/**
	 * Creates a board from the list of Strings. The Strings should only contain the characters 
	 * 'W', 'w', 'B', or 'b.' Any other character will be considered an error. Each String should also
	 * be the same length and should be non-null and non-empty.
	 * @param boardLayout list of Strings to create the board from
	 */
	public GameBoard(String... boardLayout) {
		verifyBoardLayout(boardLayout);
		
		int width = boardLayout[0].length();
		int height = boardLayout.length;
		board = new TileColor[width][height];
		createBoardFromStrings(boardLayout);
	}
	
	////////////////////////////////////////
	// PUBLIC METHODS
	////////////////////////////////////////
	
	/**
	 * Returns the tile at the given index (x, y). If the index is outside the board, then returns the INVALID
	 * tile instead.
	 * @param x x coordinate to get the tile at
	 * @param y y coordinate to get the tile at
	 * @return tile at index (x, y) or INVALID if it's outside the board
	 */
	public TileColor getTileAt(int x, int y) {
		if (log.isTraceEnabled()) {
			log.trace("getTileAt(" + x + ", " + y + ")");
		}
		return (inBounds(x, y)) ? board[x][y] : TileColor.INVALID;
	}
	
	/**
	 * Sets the tile at the given index (x, y) to the given tile color (NOTE it will ignore setting a tile to INVALID).
	 * @param x x coordinate to set the tile at
	 * @param y y coordinate to set the tile at
	 * @param color color of the tile to change the cell to
	 */
	public void setTileAt(int x, int y, TileColor color) {
		if (log.isTraceEnabled()) {
			log.trace("setTileAt(" + x + ", " + y + ", " + color + ")");
		}
		
		if (inBounds(x, y) && color != TileColor.INVALID) {
			board[x][y] = color;
		}
	}
	
	/**
	 * Returns the width of the board.
	 * @return board width
	 */
	public int getWidth() {
		log.trace("getWidth()");
		return board.length;
	}
	
	/**
	 * Returns the height of the board.
	 * @return board height
	 */
	public int getHeight() {
		log.trace("getHeight()");
		return board[0].length;
	}
	
	/**
	 * Returns true if the user has won the game, ie they have turned all the tiles on the board black. Returns
	 * false if any of the tiles on the board are still white.
	 * @return true if all the tiles are black, otherwise returns false
	 */
	public boolean isGameWon() {
		log.trace("isGameWon()");
		for (int j = 0; j < getHeight(); j++) {
			for (int i = 0; i < getWidth(); i++) {
				if (board[i][j] == TileColor.WHITE) {
					return false;
				}
			}
		}
		
		log.debug("User has completed the level.");
		return true;
	}
	
	/**
	 * Resets the board to the given layout. The Strings should only contain the characters 
	 * 'W', 'w', 'B', or 'b.' Any other character will be considered an error. Each String should also
	 * be the same length and should be non-null and non-empty.
	 * @param boardLayout board layout to set the board to
	 */
	public void resetBoard(String... boardLayout) {
		log.trace("resetBoard()");
		verifyBoardLayout(boardLayout);
		createBoardFromStrings(boardLayout);
	}
	
	/**
	 * Sets the tile at the given index (x, y) to the opposite color (ie WHITE -> BLACK or BLACK -> WHITE) and
	 * changes the adjacent tiles as well (doesn't include diagonal tiles).
	 * @param x x coordinate to change the tile
	 * @param y y coordinate to change the tile
	 */
	public void swapTiles(int x, int y) {
		if (log.isTraceEnabled()) {
			log.trace("swapTiles(" + x + ", " + y + ")");
		}
		// ignore swapping tiles if the center tile is outside the board
		if (!inBounds(x, y)) {
			log.debug("Outside the boundary.");
			return;
		}
		
		swapTile(x, y);
		swapTile(x + 1, y);
		swapTile(x - 1, y);
		swapTile(x, y + 1);
		swapTile(x, y - 1);
	}
	
	/**
	 * Returns a String array representation of the board. WHITE tiles are converted to a 'W' and BLACK tiles are converted
	 * to a 'B'. This array can be used to initialize the board using the {@link #GameBoard(String...)} constructor.
	 * @return String array representing the board as a list of Strings
	 */
	public String[] saveBoard() {
		log.trace("saveBoard()");
		String[] rows = new String[getHeight()];
		for (int j = 0; j < getHeight(); j++) {
			StringBuilder str = new StringBuilder(getWidth());
			for (int i = 0; i < getWidth(); i++) {
				str.append((board[i][j] == TileColor.BLACK) ? 'B' : 'W');
			}
			rows[j] = str.toString();
		}

		return rows;
	}
	
	/**
	 * Returns a String representation of the board. WHITE tiles are converted to a 'W' and BLACK tiles are converted
	 * to a 'B'. Each row is separated by a newline character.
	 * @return String representation of the board
	 */
	@Override
	public String toString() {
		log.trace("toString()");
		StringBuilder str = new StringBuilder((getWidth() + 1) * getHeight());   // (n + 1) x m String, extra 1 is for \n
		String[] rows = saveBoard();
		for (String row : rows) {
			str.append(row + "\n");
		}
		
		str.deleteCharAt(str.length() - 1);
		return str.toString();
	}
	
	////////////////////////////////////////
	// PRIVATE METHODS
	////////////////////////////////////////
	
	/**
	 * Returns true if the given index (x, y) is within the board, otherwise returns false.
	 * @param x x coordinate to check
	 * @param y y coordinate to check
	 * @return true if the index is inside the board, otherwise returns false
	 */
	private boolean inBounds(int x, int y) {
		if (log.isTraceEnabled()) {
			log.trace("inBounds(" + x + ", " + y + ")");
		}
		return (x >= 0 && x < getWidth() && y >= 0 && y < getHeight());
	}
	
	/**
	 * Sets the tile at the given index (x, y) to the opposite color (ie WHITE -> BLACK or BLACK -> WHITE).
	 * @param x x coordinate to change the tile
	 * @param y y coordinate to change the tile
	 */
	private void swapTile(int x, int y) {
		if (log.isTraceEnabled()) {
			log.trace("swapTile(" + x + ", " + y + ")");
		}
		
		TileColor tile = getTileAt(x, y);
		if (tile == TileColor.BLACK) {
			setTileAt(x, y, TileColor.WHITE);
		} else if (tile == TileColor.WHITE) {
			setTileAt(x, y, TileColor.BLACK);
		} // else tile is INVALID, so do nothing
	}
	
	/**
	 * Verifies that the given board layout has valid dimensions (ie is non-null, non-empty and all the
	 * Strings are the same length).
	 * @param boardLayout board layout to verify
	 */
	private void verifyBoardLayout(String... boardLayout) {
		log.trace("verifyBoardLayout()");
		if (boardLayout == null) {
			log.error("Board cannot be null.");
			throw new IllegalArgumentException("Board cannot be null.");
		} else if (boardLayout.length == 0) {
			log.error("Board cannot be empty.");
			throw new IllegalArgumentException("Board cannot be empty.");
		}
		
		int width = boardLayout[0].length();
		for (String str : boardLayout) {
			if (str.length() == 0) {
				log.error("Each row in the board must be non-empty.");
				throw new IllegalArgumentException("Each row in the board must be non-empty.");
			} else if (str.length() != width) {
				log.error("Each row in the board must be the same length.");
				throw new IllegalArgumentException("Each row in the board must be the same length.");
			}
		}
	}
	
	/**
	 * Creates a new board that is all WHITE tiles.
	 */
	private void createEmptyBoard() {
		log.trace("createEmptyBoard()");
		for (int j = 0; j < getHeight(); j++) {
			for (int i = 0; i < getWidth(); i++) {
				board[i][j] = TileColor.WHITE;
			}
		}
	}
	
	/**
	 * Creates the board from the list of Strings. A character of 'W' or 'w' creates a WHITE tile while
	 * a character of 'B' or 'b' creates a BLACK tile. Any other character is considered an error.
	 * @param boardLayout list of Strings to create the board from
	 */
	private void createBoardFromStrings(String... boardLayout) {
		log.trace("createBoardFromStrings()");
		for (int j = 0; j < boardLayout.length; j++) {
			String row = boardLayout[j];
			char[] cells = row.toCharArray();
			for (int i = 0; i < cells.length; i++) {
				char cell = cells[i];
				if (cell == 'W' || cell == 'w') {
					setTileAt(i, j, TileColor.WHITE);
				} else if (cell == 'B' || cell == 'b') {
					setTileAt(i, j, TileColor.BLACK);
				} else {
					log.error(cell + " is an invaild character for creating the board.");
					throw new IllegalArgumentException(cell + " is an invaild character for creating the board. Must be"
							+ "either 'W' or 'B'.");
				}
			}
		}
	}
}
