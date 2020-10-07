package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealServiceInMemory implements MealService {
    private static final AtomicLong counter = new AtomicLong();
    private final Map<Long, Meal> dbMeal = new ConcurrentHashMap<>();

    public MealServiceInMemory() {
    }

    @Override
    public Meal save(Meal meal) {
        Long currentId = meal.getId();
        if (currentId == null) {
            currentId = counter.incrementAndGet();
            meal.setId(currentId);
        }

        return dbMeal.compute(currentId, (id, oldValue) -> meal);
    }

    @Override
    public boolean delete(long id) {
        return dbMeal.remove(id) != null;
    }

    @Override
    public Meal get(long id) {
        return dbMeal.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(dbMeal.values());
    }
}
