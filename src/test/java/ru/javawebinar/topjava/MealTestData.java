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

    public static final Meal meal_1 = new Meal(MEAL1_ID, of(2030, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal_2 = new Meal(MEAL1_ID +1, of(2030, 1, 30, 13, 0), "Обед", 1000);
    public static final Meal meal_3 = new Meal(MEAL1_ID +2, of(2030, 1, 30, 20, 0), "Ужин", 500);
    public static final Meal meal_4 = new Meal(MEAL1_ID +3, of(2030, 1, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal_5 = new Meal(MEAL1_ID +4, of(2030, 1, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal_6 = new Meal(MEAL1_ID +5, of(2030, 1, 31, 13, 0), "Обед", 500);
    public static final Meal meal_7 = new Meal(MEAL1_ID +6, of(2030, 1, 31, 20, 0), "Ужин", 410);
    public static final Meal admin_meal_1 = new Meal(ADMIN_MEAL_ID, of(2030, 6, 1, 14, 0), "Админ ланч", 510);
    public static final Meal admin_meal_2 = new Meal(ADMIN_MEAL_ID +1, of(2030, 6, 1, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> userMeals = Arrays.asList(meal_7, meal_6, meal_5, meal_4, meal_3, meal_2, meal_1);  // порядок по убыванию даты

    public static Meal getNew() {
        return new Meal(null, of(2030, 1, 1, 1, 1), "description test 1", 111);
    }

    public static Meal getClone(Meal mealOld) {
        return new Meal(mealOld);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, meal_1.getDateTime().plusSeconds(1), "description test 1 (updated)", 222);
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
