
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: fedora-2-jordi
  Date: 7/04/15
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>



<html>
  <head>
        <title>User greetings</title>
    </head>
  <body>
    <p>Username: ${fn:escapeXml(usergreeting.getUsername())}</p>
    <p>User email: ${fn:escapeXml(user.getEmail())}</p>
    <h2>Greetings:</h2>
      <c:if test="${not empty user.getGreetings()}">
          <c:forEach var="usergreeting" items="${user.getGreetings()}">
            <li>
              <a href="/greetings/${usergreeting.getId()}">${usergreeting.getId()}</a>: ${fn:escapeXml(usergreeting.getContent())} <a href="/greetings/${usergreeting.getId()}/form">Edit greeting</a>
            </li>
          </c:forEach>
        </c:if>
    <a href="/users">Lst users</a>
  </body>
</html>