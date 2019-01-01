<%--
  Created by IntelliJ IDEA.
  User: drdm
  Date: 29.12.2018
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table id="transactions" class="table box-background max-width-1300">

    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Date</th>
            <th scope="col">In/Out</th>
            <th scope="col">Account</th>
            <th scope="col">Amount</th>
            <th scope="col">Vs</th>
            <th scope="col">Cs</th>
            <th scope="col" class="hidden-xs">Ss</th>
            <th scope="col" class="hidden-xs">Your message</th>
            <th scope="col" class="hidden-xs">Their message</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${transactions}" var="transaction">
            <tr>
                <th scope="row">${transaction.id}</th>
                <td>${transaction.date}</td>
                <td>${transaction.direction}</td>
                <td>${transaction.account}</td>
                <td>${transaction.amount}</td>
                <td>${transaction.vs}</td>
                <td>${transaction.cs}</td>
                <td class="hidden-xs">${transaction.ss}</td>
                <td class="hidden-xs">${transaction.yourMessage}</td>
                <td class="hidden-xs">${transaction.theirMessage}</td>
            </tr>
        </c:forEach>
    </tbody>

</table>
