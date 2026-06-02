package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> mondayTest = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertNotNull(mondayTest);
        assertEquals(1, mondayTest.size());
        List<TrainingSession> sessions = mondayTest.get(new TimeOfDay(13, 0));
        assertNotNull(sessions);
        assertEquals(1, sessions.size());
        assertEquals(singleTrainingSession, sessions.getFirst());

        TreeMap<TimeOfDay, List<TrainingSession>> tuesdayTest = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNotNull(tuesdayTest);
        assertTrue(tuesdayTest.isEmpty());
    }

        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий


    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> mondayTest = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondayTest.size());
        List<TrainingSession> mondaySessions = mondayTest.get(new TimeOfDay(13, 0));
        assertEquals(1, mondaySessions.size());
        assertEquals(mondayChildTrainingSession, mondaySessions.getFirst());

        TreeMap<TimeOfDay, List<TrainingSession>> thursdayTest = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdayTest.size());

        TimeOfDay firstTime = thursdayTest.firstKey();
        TimeOfDay lastTime = thursdayTest.lastKey();
        assertEquals(13, firstTime.getHours());
        assertEquals(0, firstTime.getMinutes());
        assertEquals(20, lastTime.getHours());
        assertEquals(0, lastTime.getMinutes());

        List<TrainingSession> thursday = thursdayTest.get(new TimeOfDay(13, 0));
        assertEquals(1, thursday.size());
        assertEquals(thursdayChildTrainingSession, thursday.getFirst());

        List<TrainingSession> thursday2 = thursdayTest.get(new TimeOfDay(20, 0));
        assertEquals(1, thursday2.size());
        assertEquals(thursdayAdultTrainingSession, thursday2.getFirst());

        TreeMap<TimeOfDay, List<TrainingSession>> tuesdayMap = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdayMap.isEmpty());

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessionsAt13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        assertEquals(1, sessionsAt13.size());
        assertEquals(singleTrainingSession, sessionsAt13.getFirst());

        List<TrainingSession> sessionsAt14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0));
        assertTrue(sessionsAt14.isEmpty());

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testMultipleSessionsSameTime() {
        Timetable timetable = new Timetable();
        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach coach1 = new Coach("Царь", "Иван", "Васильевич");
        Coach coach2 = new Coach("Васильев", "Николай", "Сергеевич");
        TimeOfDay startTime = new TimeOfDay(10, 0);
        TrainingSession session1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, startTime);
        TrainingSession session2 = new TrainingSession(group2, coach2, DayOfWeek.MONDAY, startTime);

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessionsAt10 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, startTime);
        assertEquals(2, sessionsAt10.size());
        assertTrue(sessionsAt10.contains(session1));
        assertTrue(sessionsAt10.contains(session2));
    }

    @Test
    void testAddTrainingSessionToExistingDay() {
        Timetable timetable = new Timetable();
        Group group = new Group("Фитнес", Age.ADULT, 60);
        Coach coach = new Coach("Петров", "Пётр", "Петрович");
        DayOfWeek day = DayOfWeek.WEDNESDAY;

        TrainingSession firstSession = new TrainingSession(group, coach, day, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(firstSession);

        TrainingSession secondSession = new TrainingSession(group, coach, day, new TimeOfDay(15, 0));
        timetable.addNewTrainingSession(secondSession);

        TreeMap<TimeOfDay, List<TrainingSession>> dayTest = timetable.getTrainingSessionsForDay(day);
        assertEquals(2, dayTest.size());
        assertNotNull(dayTest.get(new TimeOfDay(13, 0)));
        assertNotNull(dayTest.get(new TimeOfDay(15, 0)));
        assertEquals(1, dayTest.get(new TimeOfDay(13, 0)).size());
        assertEquals(1, dayTest.get(new TimeOfDay(15, 0)).size());
        assertEquals(firstSession, dayTest.get(new TimeOfDay(13, 0)).getFirst());
        assertEquals(secondSession, dayTest.get(new TimeOfDay(15, 0)).getFirst());
    }

    @Test
    void testOutOfBounds() {
        Timetable timetable = new Timetable();
        Group group = new Group("Ночная йога", Age.ADULT, 60);
        Coach coach = new Coach("Березин", "Алексей", "Соло");
        DayOfWeek day = DayOfWeek.FRIDAY;

        TimeOfDay border1 = new TimeOfDay(0, 0);
        TimeOfDay border2 = new TimeOfDay(23, 59);

        TrainingSession borderSession1 = new TrainingSession(group, coach, day, border1);
        TrainingSession borderSession2 = new TrainingSession(group, coach, day, border2);
        timetable.addNewTrainingSession(borderSession1);
        timetable.addNewTrainingSession(borderSession2);

        TreeMap<TimeOfDay, List<TrainingSession>> dayTest = timetable.getTrainingSessionsForDay(day);
        assertEquals(2, dayTest.size());
        assertEquals(border1, dayTest.firstKey());
        assertEquals(border2, dayTest.lastKey());

        List<TrainingSession> border1List = dayTest.get(border1);
        List<TrainingSession> border2List = dayTest.get(border2);
        assertEquals(1, border1List.size());
        assertEquals(1, border2List.size());
        assertEquals(borderSession1, border1List.getFirst());
        assertEquals(borderSession2, border2List.getFirst());
    }

    @Test
    void testCountByCoachesSingle() {
        Timetable timetable = new Timetable();
        Group group = new Group("Ночная йога", Age.ADULT, 60);
        Coach coach = new Coach("Березин", "Алексей", "Соло");
        DayOfWeek day1 = DayOfWeek.FRIDAY;
        DayOfWeek day2 = DayOfWeek.SUNDAY;
        TimeOfDay time1 = new TimeOfDay(9, 0);
        TimeOfDay time2 = new TimeOfDay(23, 0);

        TrainingSession session1 = new TrainingSession(group, coach, day1, time1);
        TrainingSession session2 = new TrainingSession(group, coach, day2, time2);
        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(1, result.size());
        assertEquals(2, result.getFirst().getTrainingsCount());
    }

    @Test
    void testCountByCoachesMultipleSorted() {
        Timetable timetable = new Timetable();
        Group group = new Group("Ночная йога", Age.ADULT, 60);
        Coach coachA = new Coach("Березин", "Алексей", "Соло");
        Coach coachB = new Coach("Петров", "Пётр", "Петрович");
        Coach coachC = new Coach("Царь", "Иван", "Васильевич");
        DayOfWeek day1 = DayOfWeek.FRIDAY;
        DayOfWeek day2 = DayOfWeek.SUNDAY;
        DayOfWeek day3 = DayOfWeek.MONDAY;
        TimeOfDay time1 = new TimeOfDay(9, 0);
        TimeOfDay time2 = new TimeOfDay(23, 0);
        TimeOfDay time3 = new TimeOfDay(13, 0);

        timetable.addNewTrainingSession(new TrainingSession(group, coachA, day1, time1));

        timetable.addNewTrainingSession(new TrainingSession(group, coachB, day2, time1));
        timetable.addNewTrainingSession(new TrainingSession(group, coachB, day2, time2));

        timetable.addNewTrainingSession(new TrainingSession(group, coachC, day3, time1));
        timetable.addNewTrainingSession(new TrainingSession(group, coachC, day3, time2));
        timetable.addNewTrainingSession(new TrainingSession(group, coachC, day3, time3));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(3, result.size());
        assertEquals(3, result.get(0).getTrainingsCount());
        assertEquals(2, result.get(1).getTrainingsCount());
        assertEquals(1, result.get(2).getTrainingsCount());
        assertEquals(coachC, result.get(0).getCoach());
        assertEquals(coachB, result.get(1).getCoach());
        assertEquals(coachA, result.get(2).getCoach());
    }

    @Test
    void testCountByCoachesEqualCount() {
        Timetable timetable = new Timetable();
        Group group = new Group("Ночная йога", Age.ADULT, 60);
        Coach coachA = new Coach("Березин", "Алексей", "Соло");
        Coach coachB = new Coach("Петров", "Пётр", "Петрович");
        DayOfWeek day1 = DayOfWeek.FRIDAY;
        DayOfWeek day2 = DayOfWeek.SUNDAY;
        TimeOfDay time1 = new TimeOfDay(9, 0);
        TimeOfDay time2 = new TimeOfDay(23, 0);

        timetable.addNewTrainingSession(new TrainingSession(group, coachA, day1, time1));
        timetable.addNewTrainingSession(new TrainingSession(group, coachA, day1, time2));
        timetable.addNewTrainingSession(new TrainingSession(group, coachB, day2, time1));
        timetable.addNewTrainingSession(new TrainingSession(group, coachB, day2, time2));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getTrainingsCount());
        assertEquals(2, result.get(1).getTrainingsCount());

        Coach coach1 = result.get(0).getCoach();
        Coach coach2 = result.get(1).getCoach();
        assertTrue((coach1.equals(coachA) && coach2.equals(coachB)) ||
                (coach1.equals(coachB) && coach2.equals(coachA)));
    }

}
