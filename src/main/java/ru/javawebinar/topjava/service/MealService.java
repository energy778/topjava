package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    Meal save(Meal meal);
    boolean delete(long id);
    Meal get(long id);
    List<Meal> getAll();
}
