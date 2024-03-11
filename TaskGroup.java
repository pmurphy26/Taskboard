import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskGroup extends JPanel {
    private JLabel titleLabel;
    private List<Task_GUI> taskList;
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
     * adds a task to the taskboard group
     *
     * @param newTask
     */
    public void addTask(Task newTask) {
        for (User member : newTask.getAssignedMembers()) {
            assignedMembers.add(member);
        }

        Task_GUI taskObject = new Task_GUI(newTask, taskBoard);
        taskList.add(taskObject);
        ((JPanel) ((JScrollPane) getComponent(1)).getViewport().getView()).add(taskObject);
        revalidate();
        repaint();
    }

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
}
