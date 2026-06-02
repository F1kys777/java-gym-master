package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();//сохраняем занятие в расписании

        TreeMap<TimeOfDay, List<TrainingSession>> trainingsOfDay = timetable.get(dayOfWeek);
            if (trainingsOfDay == null) {
                trainingsOfDay = new TreeMap<>();
                timetable.put(dayOfWeek, trainingsOfDay);
            }

        List<TrainingSession> trainingSessions = trainingsOfDay.get(timeOfDay);
            if (trainingSessions == null) {
                trainingSessions = new ArrayList<>();
                trainingsOfDay.put(timeOfDay, trainingSessions);
            }
        trainingSessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsOfDay = timetable.get(dayOfWeek);
        if (trainingsOfDay == null) {
            return new TreeMap<>();
        }

        return trainingsOfDay;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsOfDay = timetable.get(dayOfWeek);
        if (trainingsOfDay == null) {
            return Collections.emptyList();
        }

        return trainingsOfDay.getOrDefault(timeOfDay, Collections.emptyList());
    }

     public List<CounterOfTrainings> getCountByCoaches() {
    Map<Coach, Integer> coachCounters = new HashMap<>();

    for (TreeMap<TimeOfDay, List<TrainingSession>> trainingsOfDay : timetable.values()) {
            for (List<TrainingSession> trainingSessions : trainingsOfDay.values()) {
                for (TrainingSession session : trainingSessions) {
                    Coach coach = session.getCoach();
                    coachCounters.put(coach, coachCounters.getOrDefault(coach, 0) + 1);
                }
            }
        }

    List<CounterOfTrainings> result = new ArrayList<>();
    for (Coach coach : coachCounters.keySet()) {
        result.add(new CounterOfTrainings(coach, coachCounters.get(coach)));
    }

    Collections.sort(result);

    return result;
    }
}
