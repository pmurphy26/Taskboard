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
        this.dueDate = dueDate;
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
}
