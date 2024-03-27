import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TaskViewer extends JPanel {
    private Task task;
    private JLabel titleLabel;
    private Border titleBorder;
    private JPanel attributesPanel;
    private TaskBoardGUI taskboard;

    /**
     * Creates a new taskviewer object
     *
     * @param task
     */
    public TaskViewer(Task task) {
        selectTask(task);
    }

    /**
     * Creates a new empty taskviewer object
     */
    public TaskViewer(TaskBoardGUI taskboard) {
        this.taskboard = taskboard;
        setLayout(new BorderLayout());

        // Title panel with thicker black line border
        titleLabel = new JLabel("No task selected yet");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK); // Thicker black line border
        titleLabel.setBorder(titleBorder);
        add(titleLabel, BorderLayout.NORTH);

        attributesPanel = new JPanel(new BorderLayout());
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        attributesPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Add attributes with vertical line border between attribute label and value
        /*
        addAttribute(attributesPanel, "Due Date", "Task not selected yet");
        addAttribute(attributesPanel, "Assigned Members", "Task not selected yet");
        addAttribute(attributesPanel, "Task Status", "Task not selected yet");
        addAttribute(attributesPanel, "Additional Notes", "Task not selected Yet");
        */

        addAttributes(attributesPanel, new String[] {"Due<br>date", "Assigned<br>Members",
                "Task<br>Status", "Additional<br>Notes"},
                new String[] {"Task not selected yet", "Task not selected yet",
                        "Task not selected yet", "Task not selected yet"});

        add(attributesPanel, BorderLayout.CENTER);
    }

    /**
     * adds the attribute to the task viewer
     *
     * @param panel
     * @param attributeName
     * @param attributeValue
     */
    private void addAttribute(JPanel panel, String attributeName, String attributeValue) {
        JPanel attributePanel = new JPanel(new BorderLayout());

        //create edit button
        JButton editButton = new JButton("EDIT " + attributeName.toUpperCase());
        attributePanel.add(editButton, BorderLayout.EAST);

        //attribute label
        JLabel nameLabel = new JLabel(attributeName);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5)); // Add padding
        attributePanel.add(nameLabel, BorderLayout.WEST);

        //attribute value
        JTextField valueLabel = new JTextField(attributeValue);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5)); // Add padding
        attributePanel.add(valueLabel, BorderLayout.CENTER);

        panel.add(attributePanel);

        // Add vertical line border between attribute label and value
        attributePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Vertical line border
    }

    /**
     * adds the list of attributes
     *
     * @param viewerPanel
     * @param attributeNames
     * @param attributeValues
     */
    private void addAttributes(JPanel viewerPanel, String[] attributeNames, String[] attributeValues) {
        JPanel panel = new JPanel(new GridLayout(4, 3));

        // Add components to the panel
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                if (col == 0) {
                    //attribute label
                    JLabel nameLabel = new JLabel("<html>" + attributeNames[row] + "</html>");
                    nameLabel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5)); // Add padding
                    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    panel.add(nameLabel);
                } else if (col == 1) {
                    //attribute value
                    JTextField valueLabel = new JTextField(attributeValues[row]);
                    valueLabel.setBackground(panel.getComponents()[0].getBackground());

                    valueLabel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5)); // Add padding
                    panel.add(valueLabel);
                } else {
                    //create edit button
                    JButton editButton = new JButton("edit " + row);
                    int finalRow = row;
                    editButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editTask(finalRow);
                        }
                    });
                    panel.add(editButton);
                }
            }
        }

        viewerPanel.add(panel);
    }

    /**
     * updates the task being displayed
     *
     * @param newTask
     */
    public void updateTask(Task newTask) {
        this.task = newTask;
        titleLabel.setText(task.getTaskName());
        // Update other task attributes display as needed
        getNewAttributes();
    }

    /**
     * selects the first task
     *
     * @param newTask
     */
    public void selectTask(Task newTask) {
        task = newTask;
        setLayout(new BorderLayout());

        // Title panel with thicker black line border
        titleLabel = new JLabel(task.getTaskName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK); // Thicker black line border
        titleLabel.setBorder(titleBorder);
        add(titleLabel, BorderLayout.NORTH);

        attributesPanel = new JPanel(new GridLayout(4, 1));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        attributesPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Add attributes with vertical line border between attribute label and value
        addAttribute(attributesPanel, "Due Date", task.getDueDate()[0] + "-" + task.getDueDate()[1] + "-" + task.getDueDate()[2]);
        addAttribute(attributesPanel, "Assigned Members", "not implemented yet");
        addAttribute(attributesPanel, "Task Status", "Not started (or implemented)");
        addAttribute(attributesPanel, "Additional Notes", task.getNote());

        add(attributesPanel, BorderLayout.CENTER);
    }

    /**
     * gets the attributes for a newly selected task
     */
    public void getNewAttributes() {
        //new text for attributes
        String[] textComponents = new String[] {
                task.getDueDate()[0] + "-" + task.getDueDate()[1] + "-" + task.getDueDate()[2],
                task.getAssignedMembersString(),
                task.getStatusString(),
                task.getNote()
        };

        Component[] components = attributesPanel.getComponents();
        if (components.length == 1) {
            JPanel comp = (JPanel) attributesPanel.getComponents()[0];
            for (int i = 1; i < comp.getComponents().length; i+=3) {
                JLabel panel = (JLabel) comp.getComponents()[i - 1];
                JTextField textField = (JTextField) comp.getComponents()[i];
                textField.setText(textComponents[(i - 1) / 3]);
                textField.setBackground(panel.getBackground());
            }
        }
    }

    /**
     * function to edit the attribute of a task based on the given row number
     *
     * @param rowNum
     */
    public void editTask(int rowNum) {
        if (rowNum == 0) { //Due date
            String taskValue = JOptionPane.showInputDialog(TaskViewer.this,
                    "Enter the new due date (must be in format xx/xx/xx): ");
            task.setDueDate(taskValue);
            getNewAttributes();
        } else if (rowNum == 1) { //team members
            ArrayList<User> users = taskboard.getUsers();
            JList<User> userListComponent = new JList<>(users.toArray(new User[0]));
            userListComponent.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    
            int result = JOptionPane.showOptionDialog(null, new JScrollPane(userListComponent), "Select Users for Task Group", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (result == JOptionPane.OK_OPTION) {
                User[] selectedUsersArr = userListComponent.getSelectedValuesList().toArray(new User[0]);
                Set<User> selectedUsers = new HashSet<>();
                for (User user : selectedUsersArr) {
                    selectedUsers.add(user);
                }

            

                task.setAssignedMembers(selectedUsers);
                getNewAttributes();
                taskboard.tasksGroupChanged(task);
            }
        } else if (rowNum == 2) { //task status

        } else { //notes
            String taskValue = JOptionPane.showInputDialog(TaskViewer.this,
                    "Enter the new note");
            task.setNote(taskValue);
            getNewAttributes();
        }
    }
}
