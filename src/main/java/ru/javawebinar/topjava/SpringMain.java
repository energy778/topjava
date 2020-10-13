package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "admin", "admin@mail.ru", "password", Role.ADMIN));

//            adminUserController.create(new User(null, "user2", "user2@mail.ru", "password", Role.USER));
//            adminUserController.create(new User(null, "user1", "user1@mail.ru", "password", Role.USER));

//            System.out.println(adminUserController.getAll());
//            System.out.println(adminUserController.getByMail("user2@mail.ru"));
//            System.out.println(adminUserController.getByMail("user3@mail.ru"));

//            MealRepository mealRepository = appCtx.getBean(MealRepository.class);
//            System.out.println(mealRepository.getAll(SecurityUtil.authUserId()));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(new Meal(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59), "Завтрак", 500, 1));
            System.out.println(mealRestController.getAll());
            System.out.println(mealRestController.get(56));
        }
    }
}
