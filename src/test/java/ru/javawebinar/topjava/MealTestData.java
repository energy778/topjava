package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int MEAL1_ID = 100010;
    public static final int ADMIN_MEAL_ID = 100017;
    public static final int NOT_FOUND = 10;

    public static final Meal MEAL1 = new Meal(100010, of(2030, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(100011, of(2030, 1, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(100012, of(2030, 1, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(100013, of(2030, 1, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL5 = new Meal(100014, of(2030, 1, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL6 = new Meal(100015, of(2030, 1, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL7 = new Meal(100016, of(2030, 1, 31, 20, 0), "Ужин", 410);
    public static final Meal ADMIN_MEAL1 = new Meal(100017, of(2030, 6, 1, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(100018, of(2030, 6, 1, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> userMeals = Arrays.asList(MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);  // порядок по убыванию даты

    public static Meal getNew() {
        return new Meal(null, of(2030, 1, 1, 1, 1), "description test 1", 111);
    }

    public static Meal getClone(Meal mealOld) {
        return new Meal(null, mealOld.getDateTime(), mealOld.getDescription(), mealOld.getCalories());
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, MEAL1.getDateTime().plusSeconds(1), "description test 1 (updated)", 222);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

}
