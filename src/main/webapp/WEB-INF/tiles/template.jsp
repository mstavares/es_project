<%@ page contentType="text/html;" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html>
<html>
<head>
    <tiles:insertAttribute name="head" />



    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mycss.css" />" />

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>



<tiles:insertAttribute name="nav-bar" />

<c:choose>
    <c:when test="${not empty message}">
        <div class="container">
            <div class="alert alert-success alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${message}
            </div>
        </div>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${not empty messageError}">
        <div class="container">
            <div class="alert alert-danger alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${messageError}
            </div>
        </div>
    </c:when>
</c:choose>





<tiles:insertAttribute name="body" />


<tiles:insertAttribute name="footer" />




</body>
</html>