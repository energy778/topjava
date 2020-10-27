package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    public JpaMealRepository(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(userRepository.get(userId));
//        region попытка сделать через запрос
//        em.createNamedQuery(Meal.UPDATE, Meal.class)
//                .setParameter("mealParam", meal)
//                .setParameter("id", meal.getId())
//                .setParameter("userId", userId)
//                .executeUpdate();
//        return meal;
//        endregion
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else if (get(meal.id(), userId) == null) {
            return null;
        } else {
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE, Meal.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getSingleResult() != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal mealDB = em.createNamedQuery(Meal.GET_ONE, Meal.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getSingleResult();
        if (mealDB != null && mealDB.getUser().getId().equals(userId))
            return mealDB;
        else
            return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL_SORTED, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN_HALF_OPEN, Meal.class)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .setParameter("userId", userId)
                .getResultList();
    }
}