package com.example.id_220041102_lab6;

public enum ShipType {
    BATTLESHIP(3),
    DESTROYER(2),
    SUBMARINE(1);

    private final int size;

    ShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
