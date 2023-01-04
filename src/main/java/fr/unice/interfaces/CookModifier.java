package fr.unice.interfaces;

import fr.unice.model.cooks.Cook;

import java.time.LocalTime;

public interface CookModifier {
    void defineSchedule(Cook cook, LocalTime startWorkHour, LocalTime endWorkHour);
}
