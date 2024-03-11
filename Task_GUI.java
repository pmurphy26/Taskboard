import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Task_GUI extends JPanel {
    private JCheckBox checkBox;
    private JButton taskButton;
    private JButton upButton;
    private JButton downButton;
    private Task task;
    private TaskBoardGUI taskBoard;


    /**
     * creates a new Task_GUI object
     */
    public Task_GUI(Task task, TaskBoardGUI tb) {
        taskBoard = tb;
        this.task = task;
        setLayout(new BorderLayout());

        checkBox = new JCheckBox();
        add(checkBox, BorderLayout.WEST);

        taskButton = new JButton(task.getTaskName());
        taskButton.setHorizontalAlignment(SwingConstants.LEFT);
        taskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPush();
            }
        });
        add(taskButton, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));

        upButton = new JButton("\u2191"); // Up arrow Unicode character
        buttonPanel.add(upButton);

        downButton = new JButton("\u2193"); // Down arrow Unicode character
        buttonPanel.add(downButton);

        add(buttonPanel, BorderLayout.EAST);
    }

    /**
     * method currently called when taskName button is pressed
     */
    public void buttonPush() {
        // Perform action that modifies GUI components
        // For example, change the text of the taskButton
        taskBoard.changeSelectedTask(task);

        // Update the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
                revalidate(); // Revalidate the panel to reflect changes
                repaint(); // Repaint the panel to reflect changes
        });
    }
}