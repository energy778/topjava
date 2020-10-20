DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO public.meals(
    id, user_id, date_time, description, calories)
VALUES
    (100010, 100000, '2030-01-30 10:00:00', 'Завтрак', 500),
    (100011, 100000, '2030-01-30 13:00:00', 'Обед', 1000),
    (100012, 100000, '2030-01-30 20:00:00', 'Ужин', 500),
    (100013, 100000, '2030-01-31 00:00:00', 'Еда на граничное значение', 100),
    (100014, 100000, '2030-01-31 10:00:00', 'Завтрак', 1000),
    (100015, 100000, '2030-01-31 13:00:00', 'Обед', 500),
    (100016, 100000, '2030-01-31 20:00:00', 'Ужин', 410),
    (100017, 100001, '2030-06-01 14:00:00', 'Админ ланч', 510),
    (100018, 100001, '2030-06-01 21:00:00', 'Админ ужин', 1500)
;
