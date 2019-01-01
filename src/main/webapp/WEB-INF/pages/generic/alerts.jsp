<%--
  Created by IntelliJ IDEA.
  User: Michal Drda
  Date: 09-Nov-18
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">

    <c:if test="${not empty param.err}">
        <div class="alert alert-danger alert-dismissible fade-in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <p>Error: ${param.err}</p>
        </div>
    </c:if>
    <c:if test="${not empty param.suc}">
        <div class="alert alert-success alert-dismissible" id="success-alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <p>${param.suc}</p>
        </div>
    </c:if>

</div>