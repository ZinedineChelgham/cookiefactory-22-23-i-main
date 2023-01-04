package fr.unice.model;


import fr.unice.enums.DAY;
import fr.unice.enums.NotifyHour;
import javafx.util.Pair;

public class NotifyTimeConstraint {
    private Pair<DAY, NotifyHour> constraint;

    public NotifyTimeConstraint(Pair<DAY, NotifyHour> pair) {
        this.constraint = pair;
    }

    public NotifyTimeConstraint(DAY day, NotifyHour hour) {
        this.constraint = new Pair<>(day, hour);
    }

    public void setConstraint(Pair<DAY, NotifyHour> constraint) {
        this.constraint = constraint;
    }

    public Pair<DAY, NotifyHour> getConstraint() {
        return constraint;
    }
}
