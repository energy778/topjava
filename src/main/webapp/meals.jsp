<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <br><br>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="filter">
        <div>
            <label>From date (inclusive):
                <input type="date" value="${param.dateFrom}" name="dateFrom">
            </label>
        </div>
        <div>
            <label>To date (inclusive):
                <input type="date" value="${param.dateTill}" name="dateTill">
            </label>
        </div>
        <div>
            <label>From time (inclusive):
                <input type="time" value="${param.timeFrom}" name="timeFrom">
            </label>
        </div>
        <div>
            <label>To time (exclusive):
                <input type="time" value="${param.timeTill}" name="timeTill">
            </label>
        </div>
        <button type="submit">Apply filter</button>
    </form>
    <br><br>
    <a href="meals?action=create">Add Meal</a>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>${fn:formatDateTime(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>