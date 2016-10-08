

package model;


public enum Direction {
NORTH, SOUTH, EAST, WEST;
    private static model.Direction oppositeDirection(model.Direction d) {
        switch (d) {
            case NORTH :
                return model.Direction.SOUTH;
            case SOUTH :
                return model.Direction.NORTH;
            case EAST :
                return model.Direction.WEST;
            default :
                return model.Direction.EAST;
        }
    }

    protected boolean isTheOppositeDirectionOf(model.Direction d) {
        return (model.Direction.oppositeDirection(model.Direction.this)) == d;
    }
}

