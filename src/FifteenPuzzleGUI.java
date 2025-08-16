import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FifteenPuzzleGUI extends JFrame {

    private final TileButton[][] BUTTONS = new TileButton[4][4];
    private final FifteenPuzzleLogic LOGIC = new FifteenPuzzleLogic();
    private final int TILE_SIZE = 100;
    private final int GAP = 5;
    private final int PADDING = 20;
//    private static final Color WOOD_FRAME_COLOR = new Color(101, 67, 33);
    public static final Color WOOD_FRAME_COLOR = new Color((int) (Math.random() * 255) +1, (int) (Math.random() * 255) +1, (int) (Math.random() * 255) +1);
    private final JPanel BOARD_PANEL;
    private final JButton START_BUTTON;
    private final Counter COUNTER;
    private final GameTimer TIMER;
    private final BestState BEST_STATE = new BestState();
    private final JLabel BEST_LABEL = new JLabel("Best: --- moves, --:--");

    public FifteenPuzzleGUI() {

        setTitle("Fifteen Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(WOOD_FRAME_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        BOARD_PANEL = new JPanel(null);
        BOARD_PANEL.setOpaque(false);
        BOARD_PANEL.setPreferredSize(new Dimension(4 * TILE_SIZE + 3 * GAP, 4 * TILE_SIZE + 3 * GAP));
        BOARD_PANEL.setVisible(false);

        START_BUTTON = new JButton("Start Game");
        START_BUTTON.addActionListener(this::startNewGame);
        START_BUTTON.setFont(new Font("Serif", Font.BOLD, 16));
        START_BUTTON.setBackground(new Color(210, 180, 140));
        START_BUTTON.setForeground(Color.black);
        START_BUTTON.setFocusPainted(false);

        COUNTER = new Counter();
        TIMER = new GameTimer();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);

        buttonsPanel.add(START_BUTTON);
        buttonsPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonsPanel.add(COUNTER.getCountLabel());
        buttonsPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonsPanel.add(TIMER.getTimerLabel());

        BEST_LABEL.setText(BEST_STATE.getBestState());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.add(buttonsPanel, BorderLayout.WEST);

        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(BOARD_PANEL, BorderLayout.CENTER);
        add(mainPanel);

        int boardWidth = 4 * TILE_SIZE + 3 * GAP;
        int boardHeight = 4 * TILE_SIZE + 3 * GAP;
        int windowWidth = boardWidth + 2 * PADDING + 53;
        int windowHeight = boardHeight + 2 * PADDING + 110;

        setSize(windowWidth, windowHeight);
        setResizable(false);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void startNewGame(ActionEvent e) {
        COUNTER.reset();
        TIMER.start();
        LOGIC.initBoard();
        LOGIC.shuffleBoard();
        initButtons(BOARD_PANEL);
        BOARD_PANEL.setVisible(true);
        START_BUTTON.setText("Restart Game");
    }

    private void initButtons(JPanel panel) {
        BOARD_PANEL.removeAll();
        int[][] board = LOGIC.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                TileButton button = new TileButton(board[i][j]);
                BUTTONS[i][j] = button;
                panel.add(button);

                int x = PADDING + j * (TILE_SIZE + GAP);
                int y = PADDING + i * (TILE_SIZE + GAP);
                button.setBounds(x, y, TILE_SIZE, TILE_SIZE);

                int finalI = i;
                int finalJ = j;
                button.addActionListener(_ -> handleClick(finalI, finalJ));
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    private void handleClick(int row, int col) {
        if (LOGIC.makeMove(row, col)) {
            COUNTER.increment();
            updateButtons();
            if (LOGIC.isSolved()) {

                int moves = COUNTER.count();
                int seconds = TIMER.getTimeInSeconds();

                TIMER.stop();
                BEST_STATE.updateIfBetter(moves, seconds);

                JPanel winPanel = createWinPanel(
                        seconds, moves,
                        BEST_STATE.getBestMoves(),
                        BEST_STATE.getBestTimeInSecond()
                );
                JOptionPane.showMessageDialog(this, winPanel, "Congratulations, you won! Victory,", JOptionPane.INFORMATION_MESSAGE);
                BEST_LABEL.setText(BEST_STATE.getBestState());
            }
        }
    }

    private JPanel createWinPanel(int yourTimeSec, int yourMove, int bestMove, int bestTimeSec) {
        int ym = yourTimeSec / 60;
        int ys = yourTimeSec % 60;
        int bm = bestTimeSec / 60;
        int bs = bestTimeSec % 60;

        String[][] data = {
                {"Your", "", "Best"},
                {String.format("Time: %02d:%02d", ym, ys), "", String.format("Time: %02d:%02d", bm, bs)},
                {"Steps: " + yourMove, "", "Steps: " + bestMove}
        };

        JPanel panel = new JPanel(new GridLayout(data.length, 3, 20, 5));
        panel.setBackground(new Color(238, 238, 238));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (String[] row : data) {
            for (String cell : row) {
                JLabel label = new JLabel(cell, SwingConstants.RIGHT);
                label.setFont(new Font("SansSerif", Font.BOLD, 18));
                panel.add(label);
            }
        }
        return panel;
    }

    private void updateButtons() {
        int[][] board = LOGIC.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                BUTTONS[i][j].setNumber(board[i][j]);
            }
        }
    }
}