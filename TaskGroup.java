import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskGroup extends JPanel {
    private JLabel titleLabel;
    private ArrayList<Task_GUI> taskList;
    private TaskBoardGUI taskBoard;
    private Set<User> assignedMembers;

    /**
     * creates a new task group with title for taskboard tb
     *
     * @param title
     * @param tb
     */
    public TaskGroup(String title, TaskBoardGUI tb) {
        taskBoard = tb;
        setLayout(new BorderLayout());
        assignedMembers = new HashSet<>();

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Increase font size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        add(titleLabel, BorderLayout.NORTH);

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(taskPanel), BorderLayout.CENTER);

        taskList = new ArrayList<>();
    }

    /**
     * creates a new task group with title for taskboard tb
     *
     * @param members
     * @param tb
     */
    public TaskGroup(Set<User> members, TaskBoardGUI tb) {
        taskBoard = tb;
        setLayout(new BorderLayout());
        assignedMembers = members;
        String title = members.toString();

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Increase font size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        add(titleLabel, BorderLayout.NORTH);

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(taskPanel), BorderLayout.CENTER);

        taskList = new ArrayList<>();
    }

    /**
     * adds a task to the taskboard group
     *
     * @param newTask
     */
    public void addTask(Task newTask) {
        for (User member : newTask.getAssignedMembers()) {
            boolean inSet = false;
            for (User u : assignedMembers) {
                if (u.equals(member)) {
                    inSet = true;
                }
            }

            if (!inSet) {
                assignedMembers.add(member);
            }
        }

        JPanel taskGUIPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));

        JButton upButton = new JButton("\u2191"); // Up arrow Unicode character
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                componentMovedUp(taskGUIPanel);
            }
        });
        buttonPanel.add(upButton);

        JButton downButton = new JButton("\u2193"); // Down arrow Unicode character
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                componentMovedDown(taskGUIPanel);
            }
        });
        buttonPanel.add(downButton);

        Task_GUI taskObject = new Task_GUI(newTask, taskBoard);
        taskList.add(taskObject);
        JScrollPane jScrollPane = (JScrollPane) getComponents()[1];
        JPanel jScrollJPanel = (JPanel) jScrollPane.getViewport().getView();
        taskGUIPanel.add(buttonPanel, BorderLayout.EAST);
        taskGUIPanel.add(taskObject, BorderLayout.CENTER);
        jScrollJPanel.add(taskGUIPanel);

        revalidate();
        repaint();
    }

    /**
     * removes given task from task group
     *
     * @param removedTask
     * @return
     */
    public boolean removeTask(Task removedTask) {
        // Find and remove the corresponding Task_GUI from the panel
        for (Task_GUI taskGUI : taskList) {
            if (taskGUI.getTask().equals(removedTask)) {
                JScrollPane jScrollPane = (JScrollPane) getComponents()[1];
                JPanel jScrollJPanel = (JPanel) jScrollPane.getViewport().getView();

                for (Component c : jScrollJPanel.getComponents()) {
                    if (c instanceof JPanel) {
                        JPanel jp = (JPanel) c;
                        for (Component comp : jp.getComponents()) {
                            if (comp instanceof Task_GUI) {
                                Task_GUI taskGuiComp = (Task_GUI) comp;

                                if (taskGuiComp.getTask().equals(removedTask)) {
                                    jScrollJPanel.remove(c);
                                }
                            }
                        }
                    }
                }

                revalidate();
                repaint();

                return true;
            }
        }

        return false;
    }

    public void rearrangeTasks() {}

    /**
     * returns if a task group is the same as the group given
     *
     * @param taskGroup
     * @return
     */
    public boolean sameTeam(Set<User> taskGroup) {
        if (!assignedMembers.isEmpty() && assignedMembers.size() != taskGroup.size()) {
            return false;
        }

        //loop through team members
        for (User u : assignedMembers) {
            boolean taskGroupUserInAssignedMembers = false;

            //check if each member is in group
            for (User member : taskGroup) {
                if (u.equals(member)) {
                    taskGroupUserInAssignedMembers = true;
                }
            }

            //return false if they weren't in group
            if (!taskGroupUserInAssignedMembers) {
                return false;
            }
        }

        return true;
    }

    /**
     * returns whether the searched task is within the task group
     *
     * @param searchedTask
     * @return
     */
    public boolean contains(Task searchedTask) {
        for (Task_GUI tgui : taskList) {
            if (tgui.getTask().equals(searchedTask)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Moves a component up one spot
     * 
     * @param movingComponent
     */
    private void componentMovedUp(Component movingComponent) {
        JScrollPane jScrollPane = (JScrollPane) getComponents()[1];
        JPanel jScrollJPanel = (JPanel) jScrollPane.getViewport().getView();
        Component[] components = jScrollJPanel.getComponents();
        for (int i = 1; i < components.length; i++) {
            Component c = components[i];

            if (movingComponent.equals(c)) {
                Component temp = components[i-1];
                jScrollJPanel.remove(i-1);
                jScrollJPanel.add(temp, i);
                revalidate();
                repaint();
                return;
            }
        }
    }

    /**
     * Moves a component down one spot
     * 
     * @param movingComponent
     */
    private void componentMovedDown(Component movingComponent) {
        JScrollPane jScrollPane = (JScrollPane) getComponents()[1];
        JPanel jScrollJPanel = (JPanel) jScrollPane.getViewport().getView();
        Component[] components = jScrollJPanel.getComponents();
        for (int i = 0; i < components.length - 1; i++) {
            Component c = components[i];

            if (movingComponent.equals(c)) {
                Component temp = components[i+1];
                jScrollJPanel.remove(i+1);
                jScrollJPanel.add(temp, i);
                revalidate();
                repaint();
                return;
            }
        }
    }

    public JLabel getTitleLabel() {return titleLabel;}

    public Set<User> getAssignedMembers() {return assignedMembers;}
}
