package com.example.id_220041102_lab6;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML private GridPane playerGrid;
    @FXML private GridPane opponentGrid;
    @FXML private Label statusLabel;
    @FXML private Button rotateButton, startButton, resetButton;

    public static boolean isGameStartedGlobal = false;
    private static final int REQUIRED_SHIP_COUNT = 6;

    private boolean verticalPlacement = true;
    private boolean gameStarted = false;

    private int placementPhase = 1;
    private int currentPlayer = 1;

    private final List<Ship> player1Ships = new ArrayList<>();
    private final List<Ship> player2Ships = new ArrayList<>();
    private final Cell[][] player1Cells = new Cell[9][9];
    private final Cell[][] player2Cells = new Cell[9][9];

    @FXML
    public void initialize() {
        createGrid(playerGrid, player1Cells, false);
        createGrid(opponentGrid, player2Cells, false);
        statusLabel.setText("Player 1: Place your ships");
    }

    private void createGrid(GridPane grid, Cell[][] cellArray, boolean clickableForShooting) {
        grid.getChildren().clear();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                final int fx = x;
                final int fy = y;

                Cell cell = new Cell(fx, fy);
                cellArray[fx][fy] = cell;

                if (!clickableForShooting) {
                    cell.setOnMouseClicked(e -> {
                        if (!gameStarted) {
                            if (placementPhase == 1 && grid == playerGrid) {
                                placeShip(player1Cells, player1Ships, fx, fy);
                                if (player1Ships.size() == REQUIRED_SHIP_COUNT) {
                                    hideShips(player1Cells); // hide P1 ships
                                    placementPhase = 2;
                                    statusLabel.setText("Player 2: Place your ships");
                                    createGrid(opponentGrid, player2Cells, false);
                                }
                            } else if (placementPhase == 2 && grid == opponentGrid) {
                                placeShip(player2Cells, player2Ships, fx, fy);
                                if (player2Ships.size() == REQUIRED_SHIP_COUNT) {
                                    hideShips(player2Cells); // hide P2 ships
                                    placementPhase = 3; // ready to start
                                    statusLabel.setText("Press Start to begin the game!");
                                }
                            }
                        }
                    });
                }

                grid.add(cell, fx, fy);
            }
        }
    }

    private void makeGridsClickable() {
        opponentGrid.getChildren().clear();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                final int fx = x, fy = y;
                Cell cell = player2Cells[fx][fy];
                cell.setOnMouseClicked(e -> {
                    if (gameStarted && currentPlayer == 1 && !cell.isHit()) {
                        boolean hit = cell.shoot();
                        statusLabel.setText(hit ? "Player 1 hit!" : "Player 1 missed!");
                        checkGameEnd();
                        if (gameStarted) {
                            currentPlayer = 2;
                            statusLabel.setText("Player 2's turn!");
                            makePlayer1GridClickable();
                        }
                    }
                });
                opponentGrid.add(cell, fx, fy);
            }
        }
    }

    private void makePlayer1GridClickable() {
        playerGrid.getChildren().clear();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                final int fx = x, fy = y;
                Cell cell = player1Cells[fx][fy];
                cell.setOnMouseClicked(e -> {
                    if (gameStarted && currentPlayer == 2 && !cell.isHit()) {
                        boolean hit = cell.shoot();
                        statusLabel.setText(hit ? "Player 2 hit!" : "Player 2 missed!");
                        checkGameEnd();
                        if (gameStarted) {
                            currentPlayer = 1;
                            statusLabel.setText("Player 1's turn!");
                            makeGridsClickable();
                        }
                    }
                });
                playerGrid.add(cell, fx, fy);
            }
        }
    }

    private void placeShip(Cell[][] grid, List<Ship> ships, int x, int y) {
        ShipType type = getNextShipType(ships);
        if (type == null) return;

        if (canPlaceShip(grid, type, x, y, verticalPlacement)) {
            Ship ship = new Ship(type);
            for (int i = 0; i < type.getSize(); i++) {
                int nx = x + (verticalPlacement ? 0 : i);
                int ny = y + (verticalPlacement ? i : 0);
                Cell cell = grid[nx][ny];
                cell.setShip(ship);
                ship.getCells().add(cell);
            }
            ships.add(ship);
        }
    }

    private ShipType getNextShipType(List<Ship> ships) {
        int countBattleship = (int) ships.stream().filter(s -> s.getType() == ShipType.BATTLESHIP).count();
        int countDestroyer = (int) ships.stream().filter(s -> s.getType() == ShipType.DESTROYER).count();
        int countSubmarine = (int) ships.stream().filter(s -> s.getType() == ShipType.SUBMARINE).count();

        if (countBattleship < 1) return ShipType.BATTLESHIP;
        if (countDestroyer < 2) return ShipType.DESTROYER;
        if (countSubmarine < 3) return ShipType.SUBMARINE;
        return null;
    }

    private boolean canPlaceShip(Cell[][] grid, ShipType type, int x, int y, boolean vertical) {
        for (int i = 0; i < type.getSize(); i++) {
            int nx = x + (vertical ? 0 : i);
            int ny = y + (vertical ? i : 0);
            if (nx < 0 || nx >= 9 || ny < 0 || ny >= 9) return false;
            if (grid[nx][ny].hasShip()) return false;
        }
        return true;
    }

    @FXML public void onRotate() {
        verticalPlacement = !verticalPlacement;
        statusLabel.setText(verticalPlacement ? "Vertical placement" : "Horizontal placement");
    }

    @FXML public void onStartGame() {
        if (placementPhase != 3) {
            statusLabel.setText("Both players must place all ships first!");
            return;
        }

        gameStarted = true;
        isGameStartedGlobal = true;
        currentPlayer = 1;
        statusLabel.setText("Game Started! Player 1's turn");

        hideShips(player1Cells);
        hideShips(player2Cells);

        makeGridsClickable();
    }

    @FXML
    public void onResetGame() {
        gameStarted = false;
        isGameStartedGlobal = false;
        verticalPlacement = true;
        placementPhase = 1;
        currentPlayer = 1;

        player1Ships.clear();
        player2Ships.clear();

        // Clear cells' ships and hits
        clearCells(player1Cells);
        clearCells(player2Cells);

        // Reset status label
        statusLabel.setText("Player 1: Place your ships");

        createGrid(playerGrid, player1Cells, false);
        createGrid(opponentGrid, player2Cells, false);

        statusLabel.setText("Player 1: Place your ships");
    }

    private void clearCells(Cell[][] cells) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Cell c = cells[x][y];
                c.clearShip();
                c.setHit(false);
                c.setFill(javafx.scene.paint.Color.LIGHTBLUE);
            }
        }
    }


    private void checkGameEnd() {
        if (player2Ships.stream().allMatch(Ship::isDestroyed)) {
            statusLabel.setText("Player 1 Wins!");
            gameStarted = false;
        } else if (player1Ships.stream().allMatch(Ship::isDestroyed)) {
            statusLabel.setText("Player 2 Wins!");
            gameStarted = false;
        }
    }

    private void hideShips(Cell[][] grid) {
        for (int x = 0; x < 9; x++)
            for (int y = 0; y < 9; y++) {
                Cell c = grid[x][y];
                if (c.hasShip() && !c.isHit())  c.setFill(javafx.scene.paint.Color.LIGHTBLUE);
            }
    }
}
