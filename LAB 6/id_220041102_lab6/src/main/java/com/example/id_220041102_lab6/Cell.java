package com.example.id_220041102_lab6;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    private final int x;
    private final int y;
    private Ship ship;
    private boolean hit = false;

    public Cell(int x, int y) {
        super(40, 40);
        this.x = x;
        this.y = y;
        setFill(Color.LIGHTBLUE);
        setStroke(Color.BLACK);
    }

    public int getXCoord() { return x; }
    public int getYCoord() { return y; }

    public boolean hasShip() {
        return ship != null;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        if (!GameController.isGameStartedGlobal) setFill(Color.GRAY);
    }


    public boolean isHit() {
        return hit;
    }

    public boolean shoot() {
        hit = true;
        if (ship != null) {
            setFill(Color.RED);
            ship.checkDestroyed();
            return true;
        } else {
            setFill(Color.WHITE);
            return false;
        }
    }

    public void clearShip() {
        this.ship = null;
        setFill(Color.LIGHTBLUE);
    }

    public void setHit(boolean hit) {
        this.hit = hit;
        if (!hit) {
            if (ship != null && !GameController.isGameStartedGlobal) setFill(Color.GRAY);
            else setFill(Color.LIGHTBLUE);
        }
    }

}
