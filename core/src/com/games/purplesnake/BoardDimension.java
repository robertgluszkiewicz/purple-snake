package com.games.purplesnake;

public enum BoardDimension {
    BOARD_WIDTH(600),
    BOARD_HEIGHT(600);

    private final int dimension;;

    BoardDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }
}
