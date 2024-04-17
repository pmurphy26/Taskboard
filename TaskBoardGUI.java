import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class TaskBoardGUI extends JFrame {
    private TaskGroup allTasks;
    private JPanel taskPanel;
    private ControlPanel controlPanel;
    private TaskViewer taskViewer;
    private TaskBoard taskboard;

    /**
     * creates a new taskboard GUI
     */
    public TaskBoardGUI(String taskboardName) {
        taskboard = new TaskBoard(taskboardName);
        // Create TaskGroups
        allTasks = new TaskGroup("All tasks", this);
        
        //set own layout
        setLayout(new BorderLayout());

        // Create TaskViewer
        taskViewer = new TaskViewer(this);

        //create control panel
        controlPanel = new ControlPanel(this);

        // Create panel for TaskGroups and TaskViewer
        taskPanel = new JPanel(new GridLayout(1, 4));
        taskPanel.add(allTasks);
        taskPanel.add(taskViewer);

        // Add components to JFrame
        add(controlPanel, BorderLayout.WEST);
        add(taskPanel, BorderLayout.CENTER);

        // Set frame properties
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//EXIT_ON_CLOSE);
        setVisible(true);
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


        //make task be selected task immediately
        changeSelectedTask(newTask);
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
        if (members == null) {
            return false;
        }

        TaskGroup newTaskGroup = new TaskGroup(members, this);
        Component[] components = taskPanel.getComponents();

        for (Task t : taskboard.getTasksForGroup(members)) {
            newTaskGroup.addTask(t);
        }

        //add in new task group
        taskPanel.remove(components.length - 1);
        taskPanel.add(newTaskGroup);
        taskPanel.add(taskViewer);

        // Revalidate and repaint the frame
        taskPanel.revalidate();
        taskPanel.repaint();
        return true;
    }

    /**
     * removes a task group with the given set of team members
     * 
     * @param members
     * @return
     */
    public boolean removeTaskGroup(Set<User> members) {
        Component[] components = taskPanel.getComponents();

        if (members != null) {
            for (int i = 1; i < components.length; i++) {
                if (components[i] instanceof TaskGroup) {
                    TaskGroup taskGroup = (TaskGroup) components[i];

                    if (taskGroup.sameTeam(members)) {
                        taskPanel.remove(components[i]);
                        taskPanel.revalidate();
                        taskPanel.repaint();
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * returns a list of the team members on the taskboard
     * 
     * @return
     */
    public ArrayList<User> getUsers() {
        return taskboard.getTeamMembers();
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

    /**
     * updates the task name of the given task in the task groups
     * 
     * @param changedTask
     */
    public void taskNameChanged(Task changedTask) {
        for (int i = 0; i < taskPanel.getComponents().length; i++) {
            if (taskPanel.getComponents()[i] instanceof TaskGroup) {
                TaskGroup group = (TaskGroup) taskPanel.getComponents()[i];

                if (group.contains(changedTask)) {
                    group.getTaskGUI(changedTask).changeTaskName(changedTask.getTaskName());
                }
            }
        }
    }

    /**
     * updates given task's progress
     * 
     * @param searchedTask
     */
    public void taskProgressChange(Task searchedTask) {
        for (int i = 0; i < taskPanel.getComponents().length; i++) {
            if (taskPanel.getComponents()[i] instanceof TaskGroup) {
                TaskGroup group = (TaskGroup) taskPanel.getComponents()[i];
                //if searched task was in this group
                if (group.contains(searchedTask)) {
                    group.getTaskGUI(searchedTask).setColor();

                    if (searchedTask.getStatus() == TaskStatus.COMPLETED) {
                        group.moveTaskToBottom(searchedTask);
                    }

                    group.revalidate();
                    group.repaint();
                }
            }
        }
    }

    /**
     * getter method for the taskboard's name
     * 
     * @return
     */
    public String getTaskBoardName() {
        return taskboard.getName();
    }
}
