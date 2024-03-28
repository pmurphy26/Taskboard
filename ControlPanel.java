import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ControlPanel extends JPanel {
    private JButton addTaskButton;
    private JButton addUserButton;
    private JButton addTaskGroupButton;
    private JButton removeTaskGroupButton;
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

        //user profile buttons
        userProfileButton = new JButton("User Profile");
        add(userProfileButton, BorderLayout.NORTH);

        //other buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 25));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        //add task button
        addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(ControlPanel.this, "Enter the task: ");
                String taskDateString = JOptionPane.showInputDialog(ControlPanel.this,"Enter the due date (must enter in the form xx/xx/xx): ");
                Set<User> dropDownUsers = teamMembersDropDown();   
                String additionalNotesString = JOptionPane.showInputDialog(ControlPanel.this,"Enter any notes: ");
                Task newTask = createTaskFromStrings(taskName, taskDateString, dropDownUsers, additionalNotesString);

                if (newTask != null) {
                    addNewTask(newTask);
                } else {
                    System.out.println("something wrong with input");
                }
            }
        });
        buttonPanel.add(addTaskButton);

        //add user button
        addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = JOptionPane.showInputDialog(ControlPanel.this, "Enter User Name: ");
                if (userName != null && !userName.isEmpty()) {
                    addNewUser(new User(userName));
                }
            }
        });
        buttonPanel.add(addUserButton);

        //add taskgroup button
        addTaskGroupButton = new JButton("Add Task Group");
        addTaskGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<User> selectedUsers = teamMembersDropDown();
                addTaskGroup(selectedUsers);
            }
        });

        buttonPanel.add(addTaskGroupButton);


        //remove taskgroup button
        removeTaskGroupButton = new JButton("Remove Task Group");
        removeTaskGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<User> selectedUsers = teamMembersDropDown();
                removeTaskGroup(selectedUsers);
            }
        });

        buttonPanel.add(removeTaskGroupButton);
        buttonPanel.setBackground(Color.white);
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

        /*SwingUtilities.invokeLater(() -> {
            revalidate(); // Revalidate the panel to reflect changes
            repaint(); // Repaint the panel to reflect changes
        });
        */
    }

    /**
     * adds a new user to the taskboard
     *
     * @param newUser
     */
    public void addNewUser(User newUser) {
        taskBoard.addNewUser(newUser);
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
        if (taskName != null && dueDateStr != null && teamMembers != null && notes != null) {
            int[] dueDate = changeDateString(dueDateStr);
            Set<User> taskMembers = getTeamMembers(teamMembers);

            if (dueDate != null) {
                return new Task(taskName, dueDate, notes, taskMembers);
            }
        }

        return null;
    }

    /**
     * creates a task from three strings and a set
     * 
     * @param taskName
     * @param dueDateStr
     * @param taskMembers
     * @param notes
     * @return
     */
    private Task createTaskFromStrings(String taskName, String dueDateStr, Set<User> taskMembers, String notes) {
        if (taskName != null && dueDateStr != null && taskMembers != null && notes != null) {
            int[] dueDate = changeDateString(dueDateStr);

            if (dueDate != null) {
                return new Task(taskName, dueDate, notes, taskMembers);
            }
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
        // Split the input string by ", "
        String[] parts = teamMembersStr.trim().split(", ");
        for (String user : parts) {
            User teamMember = new User(user);
            teamMembers.add(teamMember);
        }

        return teamMembers;
    }

    /**
     * adds a task group comprised of the given members
     *
     * @param members
     * @return
     */
    public boolean addTaskGroup(Set<User> members) {
        return taskBoard.addNewTaskGroup(members);
    }

    /**
     * removes a task group with the given members
     * 
     * @param members
     * @return
     */
    public boolean removeTaskGroup(Set<User> members) {
        return taskBoard.removeTaskGroup(members);
    }

    public Set<User> teamMembersDropDown() {
        ArrayList<User> users = taskBoard.getUsers();
        JList<User> userListComponent = new JList<>(users.toArray(new User[0]));
        userListComponent.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        int result = JOptionPane.showOptionDialog(null, new JScrollPane(userListComponent), "Select Users for Task Group", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            User[] selectedUsersArr = userListComponent.getSelectedValuesList().toArray(new User[0]);
            Set<User> selectedUsers = new HashSet<>();
            for (User user : selectedUsersArr) {
                selectedUsers.add(user);
            }
            
            if (selectedUsers.isEmpty()) {
                return null;
            }

            return selectedUsers;
        }

        return null;
    }
}
