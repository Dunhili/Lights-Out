package com.dunhili.lightsout.board;

/**
 * Represents the different types of tiles that the game will be using. 
 * @author dunhili
 */
public enum TileColor {
	/** A tile that has been "turned off." */
	BLACK,
	
	/** A tile that is still "on." */
	WHITE,
	
	/** An invalid tile, used mostly for tiles that are outside the edge of the board. */
	INVALID;
}
