import java.util.ArrayList;
import java.util.List;

public class Season {
    private int length;
    private List<Race> races;

    public Season(int length, List<Race> races) {
        this.length = length;
        this.races = new ArrayList<>();
    }

    public void addRace(Course course, Route route) {
        if (races.size() < length) {
            races.add(new Race(course, route));
        } else {
            System.out.println("Season is full.");
        }
    }

    public List<Race> getRaces() {
        return races;
    }

    public int getLength() {
        return length;
    }
}