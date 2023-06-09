import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeMath {
    static final int HOURS_IN_DAY = 24;
    //хуита
    static int modifierTime = (int) (HOURS_IN_DAY * Frame.getTimeModifier()[0]);
    DateTimeFormatter simpleDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static long diffInSeconds;
    static String dateT1;
    static String dateT2;
    public long seconds;

    public void timeMath() {
        LocalDateTime date = LocalDateTime.now();
        String dateOutSoloWrite = simpleDate.format(date);
        LocalDateTime dateTime1 = LocalDateTime.parse(dateOutSoloWrite, simpleDate);
        LocalDateTime dateTime2 = LocalDateTime.parse(dateOutSoloWrite, simpleDate).plusHours(modifierTime);
        dateT1 = dateTime1.format(simpleDate);
        dateT2 = dateTime2.format(simpleDate);
        diffInSeconds = java.time.Duration.between(dateTime1, dateTime2).getSeconds();
    }
    public long timeMath2(String rollback) {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime dateTest2 = LocalDateTime.parse(rollback, simpleDate);
        seconds = java.time.Duration.between(date, dateTest2).getSeconds();
        return seconds;
    }
     public long test(long seconds) {
         seconds--;
         return seconds;
     }
     public void retString(long ts){
         Timer timer = new Timer(1000, e -> {
             System.out.println(test(ts));
         });
         test(ts);
         long day = seconds / 86400;
         long hours = (seconds % 86400) / 3600;
         long minutes = (seconds % 3600) / 60;
         long secondsToMin = seconds % 60;
         String f1 = String.format("%02d:%02d:%02d:%02d", day, hours, minutes, secondsToMin);
         System.out.println();
             timer.start();
    }
    public static String getDateT2() {
        return dateT2;
    }
}