import javax.swing.*;
import java.awt.*;

public class Counter {
    private final JLabel COUNT_LABEL;
    private int moveCount;


    public Counter() {
        COUNT_LABEL = new JLabel("Steps Count: 0");
        COUNT_LABEL.setFont(new Font("Serif", Font.BOLD, 16));
        COUNT_LABEL.setForeground(Color.BLACK);
        moveCount = 0;
    }

    public void increment() {
        moveCount++;
        COUNT_LABEL.setText("Steps Count: " + moveCount);
    }

    public void reset() {
        moveCount = 0;
        COUNT_LABEL.setText("Steps Count: 0");
    }

    public JLabel getCountLabel() {
        return COUNT_LABEL;
    }

    public int count(){
        return moveCount;
    }
}
