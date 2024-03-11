import java.util.ArrayList;

public class User {
    private String name;
    private ArrayList<Task> tasks;

    /**
     * creates a new User
     *
     * @param n
     */
    public User(String n) {
        name = n;
        tasks = new ArrayList<>();
    }

    /**
     * creates a new User
     *
     * @param n
     * @param t
     */
    public User(String n, ArrayList<Task> t) {
        name = n;
        tasks = t;
    }

    /**
     * returns the User's name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * checks if user is equal
     *
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        System.out.println("using equals");
        if (o != null && o.getClass() == this.getClass()) {
            User otherUser = (User) o;

            if (otherUser.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public boolean addTask(Task newTask) {
        return tasks.add(newTask);
    }

    public boolean removeTask(Task t) {
        return tasks.remove(t);
    }

    public String toString() {
        return name;
    }
}
