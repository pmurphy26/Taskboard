import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Task_GUI extends JPanel {
    private JButton taskButton;
    private Task task;
    private TaskBoardGUI taskBoard;


    /**
     * creates a new Task_GUI object
     */
    public Task_GUI(Task task, TaskBoardGUI tb) {
        taskBoard = tb;
        this.task = task;
        setLayout(new BorderLayout());

        taskButton = new JButton(task.getTaskName());
        taskButton.setHorizontalAlignment(SwingConstants.LEFT);
        taskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameButtonPush();
            }
        });
        setColor();
        taskButton.setForeground(Color.WHITE);
        add(taskButton, BorderLayout.CENTER);
    }

    /**
     * method currently called when taskName button is pressed
     */
    public void nameButtonPush() {
        // Perform action that modifies GUI components
        // For example, change the text of the taskButton
        taskBoard.changeSelectedTask(task);

        // Update the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
                revalidate(); // Revalidate the panel to reflect changes
                repaint(); // Repaint the panel to reflect changes
        });
    }

    /**
     * getter method for the task field
     *
     * @return
     */
    public Task getTask() {return task;}

    /**
     * sets the GUI color based on the task status
     */
    public void setColor() {
        if (task.getStatus() == TaskStatus.NOT_STARTED) {
            taskButton.setBackground(new Color(225, 75, 50));
        }  else if (task.getStatus() == TaskStatus.IN_PROGRESS) {
            taskButton.setBackground(new Color(250, 200, 0));
        } else if (task.getStatus() == TaskStatus.COMPLETED) {
            taskButton.setBackground(new Color(100, 200, 0));
        }
    }

    /**
     * boolean whether they are equal
     *
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        if (o != null && (o instanceof Task_GUI)) {
            Task_GUI otherTask = (Task_GUI) o;

            if (otherTask.getTask().getTaskName().equals(task.getTaskName())) {
                return true;
            }
        }

        return false;
    }
}