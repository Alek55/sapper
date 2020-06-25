package sapper;

class Flag {

    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y; // изначально все клетки закрыты
    }

    Box get (Coord coord) {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--; // уменьшаем кол-во откртых клеток
    }

    void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED: setCloseToBox(coord); break;
            case CLOSED: setFlagToBox(coord); break;
        }
    }

    private void setFlagToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    private void setCloseToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    void setOpenedToCloseBombBox(Coord coord) {
        if(flagMap.get(coord) == Box.CLOSED) flagMap.set(coord, Box.OPENED);
    }

    void setNoBombToFlagedSafeBox(Coord coord) {
        if(flagMap.get(coord) == Box.FLAGED) flagMap.set(coord, Box.NOBOMB); // флаг стоит, но бомбы под ним нет - показыаем
    }

    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for(Coord around : Ranges.getCoordsAround(coord))
            if(flagMap.get(around) == Box.FLAGED) count++;
        return count;
    }
}
