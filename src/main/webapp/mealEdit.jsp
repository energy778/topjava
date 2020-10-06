<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <div><label>
            Datetime:
            <input type="datetime-local" value="${meal.dateTime}" name="dateTime" required>
        </label></div>
        <div><label>
            Description:
            <input type="text" value="${meal.description}" size=40 name="description" required>
        </label></div>
        <div><label>
            Calories:
            <input type="number" value="${meal.calories}" name="calories" required>
        </label></div>
        <button type="submit">Save</button>
    </form>
</section>
</body>
</html>


