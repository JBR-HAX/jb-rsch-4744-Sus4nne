package org.jetbrains.assignment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LocationController {

    @PostMapping("/locations")
    public List<Position> handleLocations(@RequestBody List<Move> moves) {
        int x = 0, y = 0;
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(x, y));

        for (Move move : moves) {
            switch (move.getDirection()) {
                case "NORTH":
                    y += move.getSteps();
                    break;
                case "SOUTH":
                    y -= move.getSteps();
                    break;
                case "EAST":
                    x += move.getSteps();
                    break;
                case "WEST":
                    x -= move.getSteps();
                    break;
            }
            positions.add(new Position(x, y));
        }

        return positions;
    }

    @PostMapping("/moves")
    public List<Move> handleMoves(@RequestBody List<Position> positions) {
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < positions.size() - 1; i++) {
            Position current = positions.get(i);
            Position next = positions.get(i + 1);

            int dx = next.getX() - current.getX();
            int dy = next.getY() - current.getY();

            if (dx > 0) {
                moves.add(new Move("EAST", dx));
            } else if (dx < 0) {
                moves.add(new Move("WEST", -dx));
            }

            if (dy > 0) {
                moves.add(new Move("NORTH", dy));
            } else if (dy < 0) {
                moves.add(new Move("SOUTH", -dy));
            }
        }

        return moves;
    }

    static class Move {
        private String direction;
        private int steps;

        public Move() {
        }

        public Move(String direction, int steps) {
            this.direction = direction;
            this.steps = steps;
        }

        // getters and setters
        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }
    }

    static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // getters and setters
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}