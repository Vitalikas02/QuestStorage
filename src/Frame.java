import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class Frame {
    static JFrame frame = new JFrame("QuestStorage");
    static JPanel grid = new JPanel(new GridLayout(10, 3, 20, 20));
    public static JDialog modalReplace = new JDialog();
    public static JDialog modalReplace2 = new JDialog(frame, "Изменение размера");
    public static String importRollback;
    public static String[] butName = { "dungeon1", "dungeon2", "dungeon3", "dungeon4", "dungeon5", "dungeon6", "dungeon7", "dungeon8", "dungeon9", "dungeon10",
            "quest1", "quest2", "quest3", "quest4", "quest5", "quest6", "quest7", "quest8", "quest9", "quest10",
            "quest11", "quest12", "quest13", "quest14", "quest15", "quest16", "quest17", "quest18", "quest19", "quest20" };
    public static float[] timeModifier = { 0.5F, 1F, 2F, 3F, 7F };
    public static int width = getWidth();
    public static int height = getHeight();

    JMenuBar menuBar = new JMenuBar();
    JMenu menuSize = new JMenu("Размер");
    JMenuItem menuItemResize = new JMenuItem("Изменить");
    WindowListener winListener = new EventListener();
    public void frameCreator() {
        frame.addWindowListener(winListener);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        frame.setSize(new Dimension(getWidth(), getHeight()));
        frame.setMinimumSize(new Dimension(768, 576));
        frame.setMaximumSize(new Dimension(1920, 1024));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(grid);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public void menuCreator(){
        menuSize.add(menuItemResize);
        menuItemResize.addActionListener(e -> new Frame().modalResize());
        menuBar.add(menuSize);
        frame.setJMenuBar(menuBar);
    }
    public void modalCreator(){
        modalFrame();
    }

    public void modalResize(){
        modalReplace2.setName("Изменение размера");
        modalReplace2.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        JLabel label = new JLabel("Ширина");
        JLabel label2 = new JLabel("Высота");
        label.setFont(new Font("Roboto", Font.BOLD, 14));
        label2.setFont(new Font("Roboto", Font.BOLD, 14));
        JTextField modalTextFieldWidth = new JTextField(15);
        JTextField modalTextFieldHeight = new JTextField(15);
        JButton button = new JButton("Принять");
        modalReplace2.setLocationRelativeTo(null);
        modalReplace2.setResizable(false);
        modalReplace2.setSize(300, 126);
        modalReplace2.setLocation(960-150, 509-63);
        modalReplace2.setLayout(new FlowLayout(FlowLayout.CENTER));
        label2.setBorder(BorderFactory.createCompoundBorder(
                label.getBorder(),
                BorderFactory.createEmptyBorder(0, 4, 0, 0)));
        modalReplace2.add(label);
        modalReplace2.add(modalTextFieldWidth);
        modalReplace2.add(label2);
        modalReplace2.add(modalTextFieldHeight);
        modalReplace2.add(button);
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            String width = modalTextFieldWidth.getText();
            String height = modalTextFieldHeight.getText();
            if (!width.isEmpty() && !height.isEmpty()) {
                System.out.println("Ширина: " + width + "px"
                          + "\n" + "Высота: " + height + "px");
                setWidth(Integer.parseInt(width));
                setHeight(Integer.parseInt(height));
                refreshFrame();
            }
            else {
                System.out.println("Значение не было получено." + "\n" + "Изменения не применены.");
            }
            modalReplace2.remove(label);
            modalReplace2.remove(label2);
            modalReplace2.remove(modalTextFieldHeight);
            modalReplace2.remove(modalTextFieldWidth);
            modalReplace2.remove(button);
            modalReplace2.setVisible(false);
        });
        modalReplace2.setVisible(true);
    }
    public static void refreshFrame(){
        grid.removeAll();
        frame.dispose();
        new Frame().frameInit();
    }
    public void buttonsCreator(){
        for (int i = 0; i < 30; ++i) {
            new JsonManager().jsonChecker(i);
            if(JsonManager.getImportRollback() != null){
                importRollback = JsonManager.getImportRollback();
                //ts = единоразовый высчет разницы json
                long ts = new TimeMath().timeMath2(importRollback);
                //
                new TimeMath().retString(ts);
                JButton b = new JButton((butName[i]));
                //сюда вывод таймера каждую секунду
                //не обновляется
                grid.add(b);
            }
            else {
                importRollback = null;
                JButton b = new JButton((butName[i]));
                b.setFocusPainted(false);
                int finalI = i;
                b.addActionListener(e -> new JsonManager().setClick(finalI));
                grid.add(b);
            }
        }
    }
    public void modalFrame(){
        JLabel label = new JLabel("Значение уже существует. Перезаписать?");
        label.setFont(new Font("Roboto", Font.BOLD, 14));

        JButton modalBut1 = new JButton("Да");
        JButton modalBut2 = new JButton("Нет");
        modalBut1.setFocusPainted(false);
        modalBut2.setFocusPainted(false);
        modalBut1.addActionListener(e -> new JsonManager().jsonReplace());
        modalBut2.addActionListener(e -> Frame.modalReplace.setVisible(false));

        modalReplace.setLocationRelativeTo(null);
        modalReplace.setSize(350, 100);
        modalReplace.setLocation(960-175, 509-50);
        modalReplace.setLayout(new FlowLayout(FlowLayout.CENTER));
        modalReplace.add(label);
        modalReplace.add(modalBut1);
        modalReplace.add(modalBut2);
        modalReplace.setVisible(false);
    }

    public void frameInit() {
        buttonsCreator();
        menuCreator();
        frameCreator();
        modalCreator();
    }

    public static String[] getButName() {
        return butName;
    }

    public static void setButName(String[] butName) {
        Frame.butName = butName;
    }

    public static float[] getTimeModifier() {
        return timeModifier;
    }

    public static void setTimeModifier(float[] timeModifier) {
        Frame.timeModifier = timeModifier;
    }
    public static int getWidth(){
        return width;
    }
    public static int getHeight(){
        return height;
    }
    public static void setWidth(int width){
        Frame.width = width;
    }

    public static void setHeight(int height) {
        Frame.height = height;
    }
}