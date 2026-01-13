package th.ac.swu.oopproject;

public class User implements Comparable<User> {
    private String name;
    private String score;

    public User(String name, String score) {
        this.name = name;
        this.score = score;
    }
    
    public String getName() {
        return name;
    }
    
    public String getScore() {
        return score;
    }
    
    @Override
    public int compareTo(User user) {
        int inputUserScore = Integer.parseInt(user.getScore());
        int currentUserScore = Integer.parseInt(this.getScore());

        return Integer.compare(currentUserScore, inputUserScore);
    }

    @Override
    public String toString() {
        return "User{name:'" + name + "', score:" + score + "}";
    }
}