import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskBoardGUI {
    private TaskGroup taskGroup1;
    private TaskGroup taskGroup2;
    private ControlPanel controlPanel;
    private TaskViewer taskViewer;
    private TaskBoard taskboard = new TaskBoard("Testing board");

    /**
     * creates a new taskboard GUI
     */
    public TaskBoardGUI() {
        // Create TaskGroups
        taskGroup1 = new TaskGroup("Group 1", this);
        taskGroup2 = new TaskGroup("Group 2", this);

        // Create TaskViewer
        taskViewer = new TaskViewer();

        //create control panel
       controlPanel = new ControlPanel(this);

        // Create JFrame
        JFrame frame = new JFrame("Task Viewer and Groups Example");
        frame.setLayout(new BorderLayout());

        // Create panel for TaskGroups and TaskViewer
        JPanel taskPanel = new JPanel(new GridLayout(1, 3));
        taskPanel.add(taskGroup1);
        taskPanel.add(taskGroup2);
        taskPanel.add(taskViewer);

        // Add components to JFrame
        frame.add(controlPanel, BorderLayout.WEST);
        frame.add(taskPanel, BorderLayout.CENTER);

        // Set frame properties
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * changes the selected task
     *
     * @param newTask
     */
    public void changeSelectedTask(Task newTask) {
        taskViewer.updateTask(newTask);
    }

    /**
     * adds a new task to the taskboard
     *
     * @param newTask
     */
    public void addNewTask(Task newTask) {
        taskboard.addNewTask(newTask);

        if (taskGroup1.sameTeam(newTask.getAssignedMembers())) {
            taskGroup1.addTask(newTask);
        } else if (taskGroup2.sameTeam(newTask.getAssignedMembers())) {
            taskGroup2.addTask(newTask);
        }
    }

    /**
     * adds a new User
     *
     * @param newUser
     * @return
     */
    public boolean addNewUser(User newUser) {
        return taskboard.addNewMember(newUser);
    }
}
