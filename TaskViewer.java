import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TaskViewer extends JPanel {
    private Task task;
    private JButton titleLabel;
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
        task = null;
        this.taskboard = taskboard;
        setLayout(new BorderLayout());

        // Title panel with thicker black line border

        
        titleLabel = new JButton("No task selected yet");
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check for double-click event
                if (e.getClickCount() == 2) {
                    // Display prompt
                    String newTaskName = JOptionPane.showInputDialog(TaskViewer.this,
                    "Enter the new task name: ");
                    changeTaskName(newTaskName);
                }
            }
        });
        titleLabel.setBackground(Color.WHITE);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK); // Thicker black line border
        titleLabel.setBorder(titleBorder);
        add(titleLabel, BorderLayout.NORTH);

        attributesPanel = new JPanel(new BorderLayout());
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        //attributesPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        attributesPanel.setBorder(border);


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
        //JPanel panel = new JPanel(new GridLayout(4, 3));
        JPanel panel = new JPanel(new GridBagLayout());

        // Add components to the panel
        for (int row = 0; row < 4; row++) {
            Color backgroundColor = getRowColor(row);
            
            for (int col = 0; col < 3; col++) {
                if (col == 0) {
                    //attribute label
                    JLabel nameLabel = new JLabel("<html>" + attributeNames[row] + "</html>");
                    //Border border = BorderFactory.createLineBorder(Color.black, 5);
                    Border border = BorderFactory.createMatteBorder(5, 5, 5, 0, Color.black);
                    nameLabel.setBorder(border); // Add padding
                    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    GridBagConstraints gbc1 = new GridBagConstraints();
                    gbc1.gridx = 0;
                    gbc1.gridy = row;
                    gbc1.weightx = 2;
                    gbc1.weighty = 1;
                    gbc1.fill = GridBagConstraints.BOTH;

                    nameLabel.setOpaque(true);
                    nameLabel.setBackground(backgroundColor);
                    panel.add(nameLabel, gbc1);

                    //panel.add(nameLabel);
                } else if (col == 1) {
                    //attribute value
                    JTextArea valueLabel = new JTextArea(attributeValues[row]);
                    valueLabel.setBackground(backgroundColor);
                    valueLabel.setLineWrap(true);
                    //valueLabel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5)); // Add padding
                    //Border border = BorderFactory.createLineBorder(Color.black, 5);
                    Border border = BorderFactory.createMatteBorder(5, 0, 5, 5, Color.black);
                    valueLabel.setBorder(border); // Add padding

                    GridBagConstraints gbc2 = new GridBagConstraints();
                    gbc2.gridx = 1;
                    gbc2.gridy = row;
                    gbc2.weightx = 2; // Twice as wide as other columns
                    gbc2.weighty = 1;
                    gbc2.fill = GridBagConstraints.BOTH;
                    panel.add(valueLabel, gbc2);

                    //panel.add(valueLabel);
                } else {
                    //create edit button
                    JButton editButton = new JButton("edit");
                    int finalRow = row;
                    editButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editTask(finalRow);
                        }
                    });

                    GridBagConstraints gbc3 = new GridBagConstraints();
                    gbc3.gridx = 2;
                    gbc3.gridy = row;
                    gbc3.weightx = 1;
                    gbc3.weighty = 1;
                    gbc3.fill = GridBagConstraints.BOTH;
                    panel.add(editButton, gbc3);

                    //panel.add(editButton);
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
        titleLabel = new JButton(task.getTaskName());
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check for double-click event
                if (e.getClickCount() == 2) {
                    // Display prompt
                    String newTaskName = JOptionPane.showInputDialog(TaskViewer.this,
                    "Enter the new task name: ");
                    changeTaskName(newTaskName);
                }
            }
        });
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
                JTextArea textField = (JTextArea) comp.getComponents()[i];
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
            TaskStatus[] possibleStatus = new TaskStatus[] {TaskStatus.NOT_STARTED, TaskStatus.IN_PROGRESS, TaskStatus.COMPLETED};
            JComboBox<TaskStatus> userComboBox = new JComboBox<>(possibleStatus);

            int result = JOptionPane.showOptionDialog(this, userComboBox, "Select task status", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (result == JOptionPane.OK_OPTION) {
                TaskStatus selectedStatus = (TaskStatus) userComboBox.getSelectedItem();
                task.setStatus(selectedStatus);
                getNewAttributes();
            }

            taskboard.taskProgressChange(task);
        } else { //notes
            String taskValue = JOptionPane.showInputDialog(TaskViewer.this,
                    "Enter the new note");
            task.setNote(taskValue);
            getNewAttributes();
        }
    }

    /**
     * returns the corresponding color for the given row
     * 
     * @param rowNum
     * @return
     */
    public Color getRowColor(int rowNum) {
        int red = 0; // Red component
        int green = 0; // Green component
        int blue = 0; // Blue component
        
        if (rowNum == 0) { //light green
            red = 145;
            green = 240;
            blue = 145; 
        } else if (rowNum == 1) {
            red = 100;
            green = 225;
            blue = 200; 
        } else if (rowNum == 2) {
            red = 115;
            green = 170;
            blue = 225; 
        } else {
            red = 200;
            green = 230;
            blue = 255; 
        }

        // Create light green color using RGB values
        Color returnColor = new Color(red, green, blue);
        return returnColor;
    }

    /**
     * sets the selected task's name to the new name
     * 
     * @param newName
     *      new name of the task
     */
    public void changeTaskName(String newName) {
        if (task != null) {
            task.setTaskName(newName);
            titleLabel.setText(newName);
            taskboard.taskNameChanged(task);
        }
    }
}
