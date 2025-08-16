import javax.swing.*;
import java.awt.*;

public class TileButton extends JButton {
    private int number;
    private static final Color WOOD_COLOR = new Color(210, 180, 140);
    private static final Color DARK_WOOD = new Color(139, 69, 19);
    private static final Color NUMBER_COLOR = new Color(49, 29, 9);

    public TileButton(int number) {
        this.number = number;
        setFont(new Font("Arial", Font.BOLD, 24));
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setForeground(NUMBER_COLOR);

        updateButtonAppearance();
    }

    private void updateButtonAppearance() {
        if (number == 0) {
            setBackground(DARK_WOOD);
            setBorder(BorderFactory.createLineBorder(DARK_WOOD, 2));
            setText("");
        } else {
            setBackground(WOOD_COLOR);
            setBorder(BorderFactory.createLineBorder(DARK_WOOD, 2));
            setText(String.valueOf(number));
        }
        setPreferredSize(new Dimension(80, 80));
    }

    public void setNumber(int number) {
        this.number = number;

        if (number == 0) {
            setBackground(DARK_WOOD);
            setText("");
        } else {
            setBackground(WOOD_COLOR);
            setText(String.valueOf(number));
        }
    }
}