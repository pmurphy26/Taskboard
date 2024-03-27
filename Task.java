import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Task {
    private int[] dueDate;
    private String taskName;
    private String note;
    private TaskStatus status = TaskStatus.NOT_STARTED;

    private Set<User> assignedMembers;

    /**
     * Enum TaskStatus
     */
    public enum TaskStatus {
        NOT_STARTED(),
        IN_PROGRESS,
        COMPLETED
    }

    /**
     * creates a new Task object
     */
    public Task() {
        taskName = "";
        dueDate = new int[]{0, 0, 0}; //0's no date assigned, -1 late?, tbd
        note = "";
        assignedMembers = new HashSet<>();
    }

    /**
     * creates a new Task object
     *
     * @param taskName
     * @param dueDate
     * @param note
     */
    public Task(String taskName, int[] dueDate, String note) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.note = note;
    }

    /**
     * creates a new Task object
     *
     * @param taskName
     * @param dueDate
     * @param note
     */
    public Task(String taskName, int[] dueDate, String note,
                Set<User> assignedMembers) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.note = note;
        this.assignedMembers = assignedMembers;
    }

    /**
     * Getter and Setter methods for all the fields
     * @return
     */
    public int[] getDueDate() {
        return dueDate;
    }

    public void setDueDate(int[] dueDate) {
        if (dueDate != null) {
            this.dueDate = dueDate;
        }
    }

    public void setDueDate(String dueDateStr) {
        // Split the input string by "/"
        String[] parts = dueDateStr.split("/");

        // Check if the input string is in the correct format (xx/xx/xx)
        if (parts.length != 3) {
            System.out.println("not long enough");
            return;
        }

        // Parse the substrings into integers
        int[] dateArray = new int[3];
        for (int i = 0; i < 3; i++) {
            try {
                dateArray[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                System.out.println("need to enter a number");
                return;
            }
        }

        dueDate = dateArray;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getStatusString() {
        if (status == TaskStatus.NOT_STARTED) {
            return "not started";
        } else if (status == TaskStatus.IN_PROGRESS) {
            return "in progress";
        }

        return "completed";
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public boolean hasUser(User u) {
        return assignedMembers.contains(u);
    }

    public boolean addUser(User u) {
        return assignedMembers.add(u);
    }
    public boolean removeUser(User u) {
        if (assignedMembers.contains(u)) {
            u.removeTask(this);
            return assignedMembers.remove(u);
        }
        return false;
    }

    public Set<User> getAssignedMembers() {
        return assignedMembers;
    }

    /**
     * sets the assignedMembers of the task to the given list of users in the String
     *
     * @param membersStr
     */
    public void setAssignedMembers(String membersStr) {
        assignedMembers = getTeamMembers(membersStr);
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
     * returns the assigned members in a string format
     *
     * @return
     */
    public String getAssignedMembersString() {
        String returnStr = "";

        for (User u : assignedMembers) {
            returnStr += u + ", ";
        }

        return returnStr.substring(0, returnStr.length() - 2);
    }

    /**
     * returns whether other object and this object are equal
     *
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        if (o != null && o.getClass() == this.getClass()) {
            Task otherTask = (Task) o;

            if (otherTask.getTaskName().equals(taskName)) {
                return true;
            }
        }

        return false;
    }

    public String toString() {
        return taskName;
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
