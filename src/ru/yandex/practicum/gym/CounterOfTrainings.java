package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private  Coach coach;
    private  int trainingsCount;

    public CounterOfTrainings(Coach coach, int trainingsCount) {
        this.coach = coach;
        this.trainingsCount = trainingsCount;
    }

    public Coach getCoach() {
        return coach;
    }
    public int getTrainingsCount() {
        return trainingsCount;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {

        return o.trainingsCount - trainingsCount;
    }
}
