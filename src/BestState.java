import java.io.*;

public class BestState {
    private int bestMoves = Integer.MAX_VALUE;
    private int bestTimeInSecond = Integer.MAX_VALUE;
    private final String FILE_NAME = "bestState.txt";

    public BestState() {
        loadFromFile();
    }

    public void updateIfBetter(int currentMoves, int currentTimeInSecond) {
        if (currentMoves < bestMoves ||
                (currentMoves == bestMoves && currentTimeInSecond < bestTimeInSecond)) {

            bestMoves = currentMoves;
            bestTimeInSecond = currentTimeInSecond;
            saveToFile();
        }
    }

    public String getBestState() {
        if (bestMoves == Integer.MAX_VALUE) {
            return "Best: --- moves, --:--";
        }

        int minutes = bestTimeInSecond / 60;
        int seconds = bestTimeInSecond % 60;
        return String.format("Best: %d moves, %02d:%02d", bestMoves, minutes, seconds);
    }

    public int getBestMoves() {
        return bestMoves;
    }

    public int getBestTimeInSecond() {
        return bestTimeInSecond;
    }


    //NumberFormatException - այն առաջանում է, երբ փորձում ենք String-ը վերածել թվի
    //IOException - այն առաջանում է ֆայլի հետ աշխատելիս տեղի ունեցող սխալների դեպքում, օրինակ՝
    private void saveToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            int min = bestTimeInSecond / 60;
            int sec = bestTimeInSecond % 60;
            out.printf("Best Time: %02d:%02d\n", min, sec);
            out.printf("Best Move: %d\n", bestMoves);
        } catch (IOException e) {
            System.out.println("Failed to save best stats:" + e.getMessage());
        }
    }

    //IOException, որը կարող է առաջանալ ֆայլի գրելու ժամանակ, օրինակ՝
    //Եթե FILE_NAME-ով նշված ֆայլը հնարավոր չէ ստեղծել կամ վերագրել
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String timeLine = br.readLine();
                String movesLine = br.readLine();

                if (timeLine != null && movesLine != null) {
                    String[] timeParts = timeLine.substring("Best Time: ".length()).split(":");
                    int min = Integer.parseInt(timeParts[0]);
                    int sec = Integer.parseInt(timeParts[1]);
                    bestTimeInSecond = min * 60 + sec;
                    bestMoves = Integer.parseInt(movesLine.substring("Best Move: ".length()));
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Failed to load best state: " + e.getMessage());
            }
        }
    }
}