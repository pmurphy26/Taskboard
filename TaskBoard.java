import java.util.ArrayList;
import java.util.Set;

public class TaskBoard {
    private String name;
    private ArrayList<Task> tasks;
    private ArrayList<User> teamMembers;

    /**
     * creates a new Taskboard
     *
     * @param n
     */
    public TaskBoard(String n) {
        name = n;
        tasks = new ArrayList<>();
        teamMembers = new ArrayList<>();
    }

    /**
     * creates a new Taskboard
     *
     * @param n
     * @param tasks
     * @param teamMembers
     */
    public TaskBoard(String n, ArrayList<Task> tasks, ArrayList<User> teamMembers) {
        name = n;
        this.tasks = tasks;
        this.teamMembers = teamMembers;
    }

    /**
     * gets all the tasks assigned to a given member of the team
     *
     * @param u
     * @return
     */
    public ArrayList<Task> getTasksForMember(User u) {
        ArrayList<Task> assignedTasks = new ArrayList<>();

        for (Task t : tasks) {
            if (t.hasUser(u)) {
                assignedTasks.add(t);
            }
        }

        return assignedTasks;
    }

    public ArrayList<Task> getTasksForGroup(Set<User> users) {
        ArrayList<Task> assignedTasks = new ArrayList<>();

        for (Task t : tasks) {
            if (t.sameTeam(users)) {
                assignedTasks.add(t);
            }
        }

        return assignedTasks;
    }

    /**
     * adds a new task to the taskboard
     *
     * @param newTask
     */
    public void addNewTask(Task newTask) {
        tasks.add(newTask);

        for (User member : newTask.getAssignedMembers()) {
            member.addTask(newTask);

            if (!teamMembers.contains(member)) {
                teamMembers.add(member);
            }
        }
    }

    /**
     * adds a new team member to the taskboard
     *
     * @param newUser
     * @return
     */
    public boolean addNewMember(User newUser) {
        return teamMembers.add(newUser);
    }
}
