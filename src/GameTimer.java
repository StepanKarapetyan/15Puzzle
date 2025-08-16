import javax.swing.*;
import java.awt.*;

public class GameTimer {
    private final Timer TIMER;
    private final JLabel TIMER_LABEL;
    private int seconds;
    private int minutes;

    public GameTimer() {
        TIMER_LABEL = new JLabel("Time: 00:00");
        TIMER_LABEL.setFont(new Font("Times New Roman", Font.BOLD, 16));
        TIMER_LABEL.setForeground(Color.BLACK);
        seconds = 0;
        minutes = 0;

        TIMER = new Timer(1000, _ -> {
            seconds++;
            if (seconds == 60) {
                seconds = 0;
                minutes++;
            }
            updateTimerLabel();
        });
    }

    private void updateTimerLabel() {
        String time = String.format("Time: %02d:%02d", minutes, seconds);
        TIMER_LABEL.setText(time);
    }

    public void start() {
        seconds = 0;
        minutes = 0;
        updateTimerLabel();
        TIMER.start();
    }
    public void stop() {
        TIMER.stop();
    }

    public JLabel getTimerLabel() {
        return TIMER_LABEL;
    }

    public int getTimeInSeconds() {
        return minutes * 60 + seconds;
    }
}
