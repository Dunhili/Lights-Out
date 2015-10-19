package com.dunhili.lightsout.board;

import org.apache.log4j.Logger;

/**
 * Represents a level that the user has or has not yet completed. A level contains the layout that is used to
 * initialize the game boards as well as the user's score and whether they have completed it.
 * @author dunhili
 */
public class Level {
	
	////////////////////////////////////////
	// FIELDS
	////////////////////////////////////////
	
	private final String[] layout;
	private final int idealNumberOfMoves;
	
	private int numberOfMoves = 0;
	
	private static final Logger log = Logger.getLogger(Level.class);
	
	////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////
	
	/**
	 * Creates a level with the given layout and minimum number of moves required to clear the level.
	 * @param layout layout of the board
	 * @param idealNumberOfMoves minimum number of moves to clear a level
	 */
	public Level(String[] layout, int idealNumberOfMoves) {
		this(layout, idealNumberOfMoves, 0);
	}
	
	/**
	 * Creates a level with the given layout and minimum number of moves required to clear the level as well
	 * as the user's score and if they have completed the level.
	 * @param layout layout of the board
	 * @param idealNumberOfMoves minimum number of moves to clear a level
	 * @param numberOfMoves number of moves the user took to clear the level
	 */
	public Level(String[] layout, int idealNumberOfMoves, int numberOfMoves) {
		this.idealNumberOfMoves = idealNumberOfMoves;
		this.layout = layout;
		this.numberOfMoves = numberOfMoves;
	}
	
	////////////////////////////////////////
	// PUBLIC METHODS
	////////////////////////////////////////
	
	/**
	 * Returns the layout of the current level.
	 * @return layout of level
	 */
	public String[] getLayout() {
		return layout;
	}
	
	/**
	 * Returns the layout of the level compressed into a single space-delimited String.
	 * @return layout compressed into a single String
	 */
	public String getCompressedLayout() {
		StringBuilder builder = new StringBuilder();
		for (String row : layout) {
			builder.append(row + " ");
		}
		builder.delete(builder.length() - 1, builder.length());	// delete extra space
		return builder.toString();
	}
	
	/**
	 * Returns the ideal, or minimum, number of moves to clear a level.
	 * @return ideal number of moves to clear a level
	 */
	public int getIdealNumberOfMoves() {
		return idealNumberOfMoves;
	}
	
	/**
	 * Returns true if this level has been completed, otherwise false.
	 * @return true if the level has been completed, otherwise false
	 */
	public boolean isCompleted() {
		return numberOfMoves > 0;
	}
	
	/**
	 * Returns the user's current number of moves to clear this level.
	 * @return number of moves to clear the level
	 */
	public int getNumberOfMoves() {
		return numberOfMoves;
	}
	
	/**
	 * Sets the user's current number of moves for clearing this level to the given value.
	 * @param numberOfMoves current number of moves to clear level
	 */
	public void setNumberOfMoves(int numberOfMoves) {
		this.numberOfMoves = numberOfMoves;
	}
	
	/**
	 * Returns true if the user has a 'perfect' score, ie they have cleared the level and they have also 
	 * cleared the level in the minimum number of moves, otherwise returns false.
	 * @return true if the user has a perfect score, otherwise returns false
	 */
	public boolean hasPerfectScore() {
		return (getIdealNumberOfMoves() == getNumberOfMoves()) && isCompleted();
	}
}
