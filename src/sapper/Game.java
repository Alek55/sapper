package sapper;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows)); // размер окна
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public Box getBox(Coord coord) { // что находится в этом месте экрана
        if(flag.get(coord) == Box.OPENED) return bomb.get(coord); // если клетка с флагами открыта, то вместо нее нужно показать картинку из карты бомб
        return flag.get(coord);
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public void pressLeftButton(Coord coord) {
        if(gameOver()) return;
        openBox(coord);
        checkWinner();
    }

    private boolean gameOver() {
        if(state == GameState.PLAYED) return false;
        start();
        return true;
    }

    private void checkWinner() {
        if(state == GameState.PLAYED)
            if(flag.getCountOfClosedBoxes() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) { // что находится в клетке, на которую кликнули
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); return; // если клетка уже открыта
            case FLAGED: return; // на ней флаг
            case CLOSED: // если клетка закрыта, то открыаем ее
                switch (bomb.get(coord)) {
                    case ZERO: openBoxAround(coord); return; // в закрытой клетке либо пусто,
                    case BOMB: openBombs(coord); return; // либо бомба
                    default: flag.setOpenedToBox(coord); // либо любая цифра
                }
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if(bomb.get(coord) != Box.BOMB)
            if(flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for(Coord around : Ranges.getCoordsAround(coord))
                    if(flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

    private void openBombs(Coord bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed); // показыаем бомбу взорванной на клетке, в которой взорвались
        for(Coord coord : Ranges.getAllCoords()) // перебираем все координаты
            if(bomb.get(coord) == Box.BOMB) // если в текущей клетке бомба, то нужно ее показать, т.к. мы взорвались
                flag.setOpenedToCloseBombBox(coord);
            else
                flag.setNoBombToFlagedSafeBox(coord);
    }

    private void openBoxAround(Coord coord) {
        flag.setOpenedToBox(coord); // открываем текущую пустую клетку
        for(Coord around : Ranges.getCoordsAround(coord)) { // рекурсивно открыаем все пустые клетки вокруг пустой клетки на которую нажали
            openBox(around);
        }
    }

    public void pressRightButton(Coord coord) {
        if(gameOver()) return;
        flag.toggleFlagedToBox(coord);
    }
}
