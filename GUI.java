import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ToDoListGui extends JFrame implements ActionListener {
    private JPanel taskPanel, taskComponentPanel;

    public ToDoListGui() {
        super("To Do List");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(CommonConstants.GUI_SIZE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        // Using layout manager instead of null layout
        setLayout(new BorderLayout());
        addGuiComponents();
    }

    private void addGuiComponents() {
        // banner text with colorful background
        JLabel bannerLabel = new JLabel("To Do List");
        bannerLabel.setFont(createFont("resources/LEMONMILK-Light.otf", 36f));
        bannerLabel.setOpaque(true);
        bannerLabel.setBackground(Color.CYAN);
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(bannerLabel, BorderLayout.NORTH);

        // task panel
        taskPanel = new JPanel();
        taskPanel.setBackground(Color.WHITE);

        // task component panel
        taskComponentPanel = new JPanel();
        taskComponentPanel.setLayout(new BoxLayout(taskComponentPanel, BoxLayout.Y_AXIS));
        taskComponentPanel.setBackground(Color.WHITE);
        taskPanel.add(taskComponentPanel);

        // add scrolling to the task panel
        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // add task button with colorful background
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setFont(createFont("resources/LEMONMILK-Light.otf", 24f));
        addTaskButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addTaskButton.setBackground(Color.GREEN);
        addTaskButton.setForeground(Color.WHITE);
        addTaskButton.addActionListener(this);
        add(addTaskButton, BorderLayout.SOUTH);
    }

    private Font createFont(String resource, float size) {
        // get the font file path
        String filePath = getClass().getClassLoader().getResource(resource).getPath();

        // check to see if the path contains a folder with spaces in them
        if (filePath.contains("%20")) {
            filePath = getClass().getClassLoader().getResource(resource).getPath()
                .replaceAll("%20", " ");
        }

        // create font
        try {
            File customFontFile = new File(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile).deriveFont(size);
            return customFont;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("Add Task")) {
            // create a task component
            TaskComponent taskComponent = new TaskComponent(taskComponentPanel);
            taskComponentPanel.add(taskComponent);

            // make the previous task appear disabled
            if (taskComponentPanel.getComponentCount() > 1) {
                TaskComponent previousTask = (TaskComponent) taskComponentPanel.getComponent(
                        taskComponentPanel.getComponentCount() - 2);
                previousTask.getTaskField().setBackground(null);
            }

            // make the task field request focus after creation
            taskComponent.getTaskField().requestFocus();
            repaint();
            revalidate();
        }
    }
}
