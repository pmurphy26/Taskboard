import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.lang.String;

public class TaskViewer extends JPanel {
    private Task task;
    private JLabel titleLabel;
    private Border titleBorder;
    private JPanel attributesPanel;

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
    public TaskViewer() {
        setLayout(new BorderLayout());

        // Title panel with thicker black line border
        titleLabel = new JLabel("No task selected yet");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK); // Thicker black line border
        titleLabel.setBorder(titleBorder);
        add(titleLabel, BorderLayout.NORTH);

        attributesPanel = new JPanel(new GridLayout(4, 1));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        attributesPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Add attributes with vertical line border between attribute label and value
        addAttribute(attributesPanel, "Due Date", "Task not selected yet");
        addAttribute(attributesPanel, "Assigned Members", "Task not selected yet");
        addAttribute(attributesPanel, "Task Status", "Task not selected yet");
        addAttribute(attributesPanel, "Additional Notes", "Task not selected Yet");

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

        JLabel nameLabel = new JLabel(attributeName);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // Add padding
        attributePanel.add(nameLabel, BorderLayout.WEST);

        JLabel valueLabel = new JLabel(attributeValue);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // Add padding
        attributePanel.add(valueLabel, BorderLayout.CENTER);

        panel.add(attributePanel);

        // Add vertical line border between attribute label and value
        attributePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Vertical line border
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
                "not yet implemented", //task.getAssignedMembers().toString(),
                task.getStatusString(),
                task.getNote()
        };


        Component[] components = attributesPanel.getComponents();
        if (components.length == 4) {
            //loop through each attribute
            for (int i = 0; i < components.length; i++) {
                //if attribute is stored in jpanel
                if (components[i] instanceof JPanel) {
                    Component[] labels = ((JPanel) components[i]).getComponents();

                    //get the value label
                    if (labels.length == 2 && labels[1] instanceof JLabel) {
                        ((JLabel) labels[1]).setText(textComponents[i]);
                    }
                }
            }
        }
    }
}
