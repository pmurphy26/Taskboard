import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class ControlPanel extends JPanel {
    private JButton addTaskButton;
    private JButton addUserButton;
    private JButton userProfileButton;
    private TaskBoardGUI taskBoard;

    /**
     * creates a new control panel for the given taskboard GUI
     *
     * @param tbgui
     */
    public ControlPanel(TaskBoardGUI tbgui) {
        setLayout(new BorderLayout());
        taskBoard = tbgui;

        userProfileButton = new JButton("User Profile");
        add(userProfileButton, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        addTaskButton = new JButton("Add Task");
        buttonPanel.add(addTaskButton);

        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(ControlPanel.this,
                        "Enter the task: ");
                String taskDateString = JOptionPane.showInputDialog(ControlPanel.this,
                        "Enter the due date (must enter in the form xx/xx/xx): ");
                String assignedMembersString = JOptionPane.showInputDialog(ControlPanel.this,
                        "Enter the assigned members: ");
                String additionalNotesString = JOptionPane.showInputDialog(ControlPanel.this,
                        "Enter any notes: ");
                Task newTask = createTaskFromStrings(taskName, taskDateString,
                        assignedMembersString, additionalNotesString);

                if (newTask != null) {
                    addNewTask(newTask);
                } else {
                    System.out.println("something wrong with input");
                }
            }
        });

        addUserButton = new JButton("Add User");
        buttonPanel.add(addUserButton);


        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = JOptionPane.showInputDialog(ControlPanel.this, "Enter User Name: ");
                if (userName != null && !userName.isEmpty()) {
                    // Do something with the entered user name, such as adding it to a list of members
                    System.out.println("New user added: " + userName);
                    addNewUser(new User(userName));
                }
            }
        });

        add(buttonPanel, BorderLayout.CENTER);

    }

    public JButton getAddTaskButton() {
        return addTaskButton;
    }

    public JButton getUserProfileButton() {
        return userProfileButton;
    }

    /**
     * adds a new task to the taskboard
     *
     * @param newTask
     */
    public void addNewTask(Task newTask) {
        taskBoard.addNewTask(newTask);

        SwingUtilities.invokeLater(() -> {
            revalidate(); // Revalidate the panel to reflect changes
            repaint(); // Repaint the panel to reflect changes
        });
    }

    /**
     * adds a new user to the taskboard
     *
     * @param newUser
     */
    public void addNewUser(User newUser) {
        taskBoard.addNewUser(newUser);

        SwingUtilities.invokeLater(() -> {
            revalidate(); // Revalidate the panel to reflect changes
            repaint(); // Repaint the panel to reflect changes
        });
    }

    /**
     * creates a task from four strings
     *
     * @param taskName
     * @param dueDateStr
     * @param teamMembers
     * @param notes
     * @return
     */
    private Task createTaskFromStrings(String taskName, String dueDateStr, String teamMembers, String notes) {
        int[] dueDate = changeDateString(dueDateStr);
        Set<User> taskMembers = getTeamMembers(teamMembers);

        if (dueDate != null) {
            return new Task(taskName, dueDate, notes, taskMembers);
        }

        return null;
    }

    /**
     * changes the date string into an int[], returns null if incorrectly formatted
     *
     * @param dueDateStr
     * @return
     */
    private int[] changeDateString(String dueDateStr) {
        // Split the input string by "/"
        String[] parts = dueDateStr.split("/");

        // Check if the input string is in the correct format (xx/xx/xx)
        if (parts.length != 3) {
            System.out.println("not long enough");
            return null;
        }

        // Parse the substrings into integers
        int[] dateArray = new int[3];
        for (int i = 0; i < 3; i++) {
            try {
                dateArray[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                System.out.println("need to enter a number");
                return null;
            }
        }

        return dateArray;
    }

    /**
     * gets all the team members from a given set
     *
     * @param teamMembersStr
     * @return
     */
    private Set<User> getTeamMembers(String teamMembersStr) {
        Set<User> teamMembers = new HashSet<>();
        // Split the input string by "/"
        String[] parts = teamMembersStr.split(",");
        for (String user : parts) {
            User teamMember = new User(user);
            teamMembers.add(teamMember);
        }

        return teamMembers;
    }
}
