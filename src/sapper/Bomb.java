package sapper;

class Bomb {

    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for(int i = 0; i < totalBombs; i++) {
            placeBomb();
        }
    }

    Box get(Coord coord) {
        return bombMap.get(coord);
    }

    private void fixBombsCount() {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if(totalBombs > maxBombs) totalBombs = maxBombs;
    }

    private void placeBomb() {
        while(true) {
            Coord coord = Ranges.getRandomCoord();
            if(bombMap.get(coord) == Box.BOMB) continue; // если в текущей координате уже есть бомба, то пропускаем эту клетку

            bombMap.set(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }
    }

    private void incNumbersAroundBomb(Coord coord) {
        for(Coord around : Ranges.getCoordsAround(coord)) // вокруг каждой бомбы ставим 1
            if(Box.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
