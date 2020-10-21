package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static java.time.LocalDate.of;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() throws Exception {
        assertThrows(DataAccessException.class, () -> service.create(getClone(MEAL1), USER_ID));
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void getOtherUser() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), userMeals);
    }

    @Test
    public void getBetweenInclusive() throws Exception {
        assertMatch(service.getBetweenInclusive(of(2030, 1, 30), of(2030, 1, 31), USER_ID),
                MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateOtherUser() throws Exception {
        assertThrows(NotFoundException.class, () -> service.update(MEAL1, ADMIN_ID));
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteOtherUser() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL1_ID, ADMIN_ID));
    }

}
