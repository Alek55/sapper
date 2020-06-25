import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sapper.Box;
import sapper.Coord;
import sapper.Game;
import sapper.Ranges;

public class JavaSapper extends JFrame {

    private Game game;

    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50; // размер одной картинки 50x50

    public static void main(String[] args) {
        new JavaSapper();
    }

    private JavaSapper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start(); // init matrix coords => picture
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel("Welcome");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for(Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image)game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if(e.getButton() == MouseEvent.BUTTON1) game.pressLeftButton(coord);
                if(e.getButton() == MouseEvent.BUTTON3) game.pressRightButton(coord);
                if(e.getButton() == MouseEvent.BUTTON2) game.start(); // restart

                label.setText(getMessage());

                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
        add(panel); // добавляет панель на форму
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED: return "Think twice";
            case BOMBED: return "You lose";
            case WINNER: return "You win";
            default: return "";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // действие по умолчанию при закрытии программы
        setTitle("Сапер");
        setResizable(false); // запретить изменять размер окна
        setVisible(true); // показать окно
        setIconImage(getImage("icon")); // favicon
        pack(); // устанавливает минимальный размер контейнера, который достаточен для отображения всех компонентов
        setLocationRelativeTo(null); // расположить по центру
    }

    private void setImages() {
        for(Box box : Box.values()) {
            box.image = getImage(box.name());
        }
    }

    private Image getImage(String name) {
        String filename = name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

}
