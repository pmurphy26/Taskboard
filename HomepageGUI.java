import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class HomepageGUI extends JFrame {
    private User user;
    private LinkedList<TaskBoardGUI> taskboards;
    private JPanel buttonsPanel;

    /**
     * creates a new homepage GUI
     */
    public HomepageGUI() {
        user = new User("New user");
        buttonsPanel = new JPanel(new GridLayout(1,1));
        taskboards = new LinkedList<>();
        
        //set own layout
        setLayout(new GridLayout(0,1));

        // add a button representing each taskboard in taskboards
        for (TaskBoardGUI tbgui : taskboards) {
            Button tb = new Button(tbgui.getName());
            buttonsPanel.add(tb);
        }
        // button to create a new taskboard
        Button newTaskBoardButton = new Button("Create New Taskboard");
        newTaskBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewTaskBoard();
            }
        });
        buttonsPanel.add(newTaskBoardButton);
        add(buttonsPanel);

        // Set frame properties
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * creates a new taskboard object
     */
    public void createNewTaskBoard() {
        String taskBoardName = "";
        while (taskBoardName.isEmpty()) { 
            taskBoardName = JOptionPane.showInputDialog(this, "Enter the new taskboard's name: ");

            if (taskBoardName == null) {
                return;
            }
        }
        
        TaskBoardGUI tbgui = new TaskBoardGUI(taskBoardName);
        taskboards.add(tbgui);
        
        Button taskBoardButton = new Button(tbgui.getTaskBoardName());
        taskBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTaskBoard(tbgui);
            }
        });
       
        buttonsPanel.add(taskBoardButton);
        buttonsPanel.revalidate();
        buttonsPanel.repaint();
        
        tbgui.setVisible(false);
    }

    /**
     * method to open a given taskboard
     * 
     * @param tbgui
     */
    public void openTaskBoard(TaskBoardGUI tbgui) {
        if (tbgui != null) tbgui.setVisible(true);
        return;
    }
}
