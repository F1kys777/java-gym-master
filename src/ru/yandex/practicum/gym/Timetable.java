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

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> coachCounters = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> trainingsOfDay : timetable.values()) {
            for (List<TrainingSession> trainingSessions : trainingsOfDay.values()) {
                for (TrainingSession session : trainingSessions) {
                    Coach coach = session.getCoach();
                    coachCounters.put(coach, coachCounters.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<Map.Entry<Coach, Integer>> result = new ArrayList<>(coachCounters.entrySet());

        Collections.sort(result, new Comparator<Map.Entry<Coach, Integer>>() {
            @Override
            public int compare(Map.Entry<Coach, Integer> e1, Map.Entry<Coach, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        }
        );

        return result;
    }
}
