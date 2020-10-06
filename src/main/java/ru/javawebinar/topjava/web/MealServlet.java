package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceInMemory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.defaultMeals;

public class MealServlet extends HttpServlet {

    private static final String INSERT_OR_EDIT = "/mealEdit.jsp";
    private static final String MEALS_LIST = "/meals.jsp";

    private static final Logger log = getLogger(MealServlet.class);
    private static final MealService mealService = new MealServiceInMemory();

    // TODO: 007 07.10.20 initDB 
    static {
        defaultMeals.forEach(mealService::save);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals (post)");

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        Meal meal;
        if (id == null || id.isEmpty()){
            meal = new Meal();
        } else {
            meal = mealService.get(Long.parseLong(id));
        }
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        mealService.save(meal);

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals (get)");

        request.setCharacterEncoding("UTF-8");
        String forward="";
        String action = request.getParameter("action");

        if ("delete".equalsIgnoreCase(action)){
            forward = MEALS_LIST;
            long mealId = Long.parseLong(request.getParameter("id"));
            boolean success = mealService.delete(mealId);
            request.setAttribute("meals", getMealsTO(mealService.getAll()));
            request.setAttribute("deleteSuccess", success);

        } else if ("edit".equalsIgnoreCase(action)
                || "create".equalsIgnoreCase(action)){
            forward = INSERT_OR_EDIT;
            Meal currentMeal;
            if ("create".equalsIgnoreCase(action))
                currentMeal = new Meal(LocalDateTime.now(), "",0);
            else
                currentMeal = mealService.get(Long.parseLong(request.getParameter("id")));
            request.setAttribute("meal", currentMeal);

        } else {
            forward = MEALS_LIST;
            request.setAttribute("meals", getMealsTO(mealService.getAll()));

        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private List<MealTo> getMealsTO(List<Meal> meals) {
        return MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY);
    }

}
