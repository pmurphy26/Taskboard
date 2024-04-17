import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Main {
    /*
    public static void main(String[] args) {
        System.out.println("Hello world!");

        JFrame frame = new JFrame("Task Group Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        TaskGroup taskGroup = new TaskGroup("My Tasks");
        frame.add(taskGroup);

        taskGroup.addTask(new Task("Task 1", new int[] {0,0,0},""));
        taskGroup.addTask(new Task("Task 2", new int[] {0,0,0},""));
        taskGroup.addTask(new Task("Task 3", new int[] {0,0,0},""));

        frame.setVisible(true);
    }
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new HomepageGUI();//TaskBoardGUI();
            }
        });
            //() -> );
        
    }
}