package io.github.followsclosley.reverse.impl;

import io.github.followsclosley.reverse.Board;
import io.github.followsclosley.reverse.Coordinate;
import io.github.followsclosley.reverse.Turn;

import java.util.ArrayList;

public abstract class AbstractBoard implements Board {

    protected int turn = -1;
    protected int[][] state;
    protected int width, height;

    public AbstractBoard() {
        this(8, 8);
    }

    public AbstractBoard(int width, int height) {
        this.state = new int[this.width = width][this.height = height];
    }

    @Override
    public int getPiece(int x, int y) {
        return (x >= 0 && x < width && y >= 0 & y < height) ? state[x][y] : 0;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getTurn() {
        return turn;
    }

    public Turn getTurnContext(Coordinate coordinate) {

        Turn c = new Turn(coordinate);

        if (getPiece(coordinate.getX(), coordinate.getY()) == 0) {
            ArrayList<Coordinate> flips = new ArrayList<>();
            for (int deltaX = -1; deltaX <= 1; deltaX++) {
                for (int deltaY = -1; deltaY <= 1; deltaY++) {

                    if (deltaX != 0 || deltaY != 0) {
                        flips.clear();
                        for (int x = c.getMove().getX() + deltaX, y = c.getMove().getY() + deltaY; x < getWidth() && x >= 0 && y < getHeight() && y >= 0; x += deltaX, y += deltaY) {

                            int color = getPiece(x, y);

                            if (color == 0) {
                                break;
                            } else if (color == getTurn()) {
                                if (flips.size() > 0) {
                                    c.getFlips().addAll(flips);
                                }
                                break;
                            } else if (color != getTurn()) {
                                flips.add(new Coordinate(x, y));
                            }
                        }
                    }
                }
            }
        }

        return c;
    }

    public String toMatrixString() {
        Board board = this;
        StringBuffer b = new StringBuffer();
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                b.append(String.format("[%d,%d]  ", x, y));
            }
            b.append("\n");
        }
        return b.toString();
    }

    public String toString() {
        Board board = this;
        StringBuffer b = new StringBuffer("----------------\nboard = ");
        for (int x = 0; x < board.getWidth(); x++) {
            b.append(x).append(" ");
        }
        b.append("\n");

        for (int y = 0; y < board.getHeight(); y++) {
            b.append("     ").append(y).append(": ");
            for (int x = 0; x < board.getWidth(); x++) {
                int p = board.getPiece(x, y);
                b.append(p == -1 ? '-' : p == 1 ? '+' : '0').append(" ");
            }
            b.append("\n");
        }
        return b.toString();
    }
}