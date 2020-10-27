package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.model.Meal.*;

@NamedQueries({
        @NamedQuery(name = UPDATE, query = "UPDATE Meal m " +
                "set m=:mealParam " +
                "where m.id=:id and m.user.id=:userId "),
        @NamedQuery(name = GET_ONE, query = "SELECT m FROM Meal m " +
                "where m.id=:id and m.user.id=:userId "),
        @NamedQuery(name = GET_ALL_SORTED, query = "SELECT m FROM Meal m " +
                "where m.user.id=:userId " +
                "order by m.dateTime desc "),
        @NamedQuery(name = GET_BETWEEN_HALF_OPEN, query = "SELECT m FROM Meal m " +
                "where m.user.id=:userId and m.dateTime >= :startDateTime and m.dateTime < :endDateTime " +
                "order by m.dateTime desc ")
        })
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {
    public static final String UPDATE = "Meal.update";
    public static final String DELETE = "DELETE FROM Meal m where m.id=:id and m.user.id=:userId ";
//    "DELETE FROM Meal m where m.id=:id and m.user in (select u from User u where u.id=:userId) "
    public static final String GET_ONE = "Meal.getOne";
    public static final String GET_ALL_SORTED = "Meal.getAll";
    public static final String GET_BETWEEN_HALF_OPEN = "Meal.getBetweenHalfOpen";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 10000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
