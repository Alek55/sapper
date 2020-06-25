package sapper;

class Matrix {

    private Box[][] matrix;

    Matrix(Box defaultBox) {
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];

        for(Coord coord : Ranges.getAllCoords()) {
            matrix[coord.x][coord.y] = defaultBox; // заполняем массив всех ячеек дефолтной картинкой
        }
    }

    Box get(Coord coord) {
        if(!Ranges.inRange(coord)) return null; // если координата не находится в пределах окна
        return matrix[coord.x][coord.y];
    }

    void set(Coord coord, Box box) {
        if(Ranges.inRange(coord)) matrix[coord.x][coord.y] = box;
    }

}
