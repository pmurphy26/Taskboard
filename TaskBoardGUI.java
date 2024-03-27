import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class TaskBoardGUI {
    private TaskGroup allTasks;
    private JPanel taskPanel;
    private ControlPanel controlPanel;
    private TaskViewer taskViewer;
    private TaskBoard taskboard = new TaskBoard("Testing board");

    /**
     * creates a new taskboard GUI
     */
    public TaskBoardGUI() {
        // Create TaskGroups
        allTasks = new TaskGroup("All tasks", this);

        // Create TaskViewer
        taskViewer = new TaskViewer(this);

        //create control panel
        controlPanel = new ControlPanel(this);

        // Create JFrame
        JFrame frame = new JFrame("Task Viewer and Groups Example");
        frame.setLayout(new BorderLayout());

        // Create panel for TaskGroups and TaskViewer
        taskPanel = new JPanel(new GridLayout(1, 4));
        taskPanel.add(allTasks);
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

        for (int i = 1; i < taskPanel.getComponents().length; i++) {
            if (taskPanel.getComponents()[i] instanceof TaskGroup) {
                TaskGroup group = (TaskGroup) taskPanel.getComponents()[i];

                if (group.sameTeam(newTask.getAssignedMembers())) {
                    group.addTask(newTask);
                }
            }
        }

        allTasks.addTask(newTask);
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

    /**
     * creates and adds a new task group with given members
     *
     * @param members
     * @return
     */
    public boolean addNewTaskGroup(Set<User> members) {
        TaskGroup newTaskGroup = new TaskGroup(members, this);
        Component[] components = taskPanel.getComponents();

        for (Task t : taskboard.getTasksForGroup(members)) {
            newTaskGroup.addTask(t);
        }

        //add in new task group
        taskPanel.remove(components.length - 1);
        taskPanel.add(newTaskGroup); //add new taskgroup
        taskPanel.add(taskViewer);

        // Revalidate and repaint the frame
        taskPanel.revalidate();
        taskPanel.repaint();
        return false;
    }

    /**
     * updates the task groups in the GUI when a task's assigned members are changed
     *
     * @param changedTask
     */
    public void tasksGroupChanged(Task changedTask) {
        for (int i = 1; i < taskPanel.getComponents().length; i++) {
            if (taskPanel.getComponents()[i] instanceof TaskGroup) {
                TaskGroup group = (TaskGroup) taskPanel.getComponents()[i];
                //if changed task was in this group and shouldn't be anymore
                if (group.contains(changedTask)) {
                    group.removeTask(changedTask);
                }

                //if changed task should be in this group
                if (group.sameTeam(changedTask.getAssignedMembers())) {
                    group.addTask(changedTask);
                }
            }
        }
    }
}
