package com.example.id_220041102_lab6;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final ShipType type;
    private final List<Cell> cells = new ArrayList<>();
    private boolean destroyed = false;

    public Ship(ShipType type) {
        this.type = type;
    }

    public ShipType getType() {
        return type;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void checkDestroyed() {
        destroyed = cells.stream().allMatch(Cell::isHit);
    }
}
