package model;

/** Enumeration representing the direction the snake has to move, following the key pressed by the user (part of the model in MVC design pattern) */
public enum Direction {
	
	NORTH,
	SOUTH,
	EAST,
	WEST;

	/**
	 * returns the opposite direction of <code> d </code>
	 * @param d the direction
	 * @return the opposite direction of <code> d </code>
	 */
	private static Direction oppositeDirection(Direction d) {
		switch (d) {
			case NORTH : return SOUTH;
			case SOUTH : return NORTH;
			case EAST : return WEST;
			default : return EAST;
		}
	}
	
	/**
	 * check if <code> d </code> is the opposite direction of <code> this </code>
	 * @param d the direction
	 * @return true if <code> d </code> is the opposite direction of <code> this </code>, <code> false </code> if not
	 */
	protected boolean isTheOppositeDirectionOf(Direction d) {
		return (Direction.oppositeDirection(this) == d);
	}

}
