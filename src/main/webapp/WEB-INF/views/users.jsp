<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: fedora-2-jordi
  Date: 7/04/15
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>Users List</title>
</head>
<body>
  <h2>User list</h2>
    <ul>
        <c:if test="${not empty users}">
          <c:forEach var="user" items="${users}">
              <li> <a href="/users/${user.getId()}">${fn:escapeXml(user.getId())} </a> -  ${fn:escapeXml(user.getUsername())}</li>
          </c:forEach>
        </c:if>
      </ul>
  </body>
</html>
