package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class UserMealWithExcessCollector implements Collector<UserMeal, List<UserMealWithExcess>, List<UserMealWithExcess>> {

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int caloriesPerDay;
    private final Map<LocalDate, Integer> dateNumberCaloriesMap = new ConcurrentHashMap<>();

    public UserMealWithExcessCollector(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.caloriesPerDay = caloriesPerDay;
    }

    @Override
    public Supplier<List<UserMealWithExcess>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<UserMealWithExcess>, UserMeal> accumulator() {
        return (userMealWithExcesses, userMeal) -> {
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                userMealWithExcesses.add(UserMealsUtil.buildUserMealWithExcess(userMeal, true));
            dateNumberCaloriesMap.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
        };
    }

    @Override
    public BinaryOperator<List<UserMealWithExcess>> combiner() {
        return (left, right) -> {
            if (left.size() < right.size()) {
                right.addAll(left);
                return right;
            } else {
                left.addAll(right);
                return left;
            }
        };
    }

    @Override
    public Function<List<UserMealWithExcess>, List<UserMealWithExcess>> finisher() {
        return userMealWithExcesses -> {
            userMealWithExcesses.forEach(mealWithExcess ->
                    mealWithExcess.setExcess(dateNumberCaloriesMap.get(mealWithExcess.getDateTime().toLocalDate()) > caloriesPerDay));
            return userMealWithExcesses;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }

}
