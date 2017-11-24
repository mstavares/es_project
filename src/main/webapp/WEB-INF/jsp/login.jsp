<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="head">
        <title>Login</title>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <div class="container">
            <div class="col-md-12">
                <div class="row">
                    <form action="<c:url value='j_spring_security_check' />" method="POST">
                        <div class="form-group input-m-b">
                            <input type="text" name="j_username" class="form-control input-lg login-form" placeholder="Nome de utilizador" />
                        </div>
                        <div class="form-group input-m-b">
                            <input type="password" name="j_password" class="form-control input-lg login-form" placeholder="Password" />
                        </div>
                        <div class="login-error">
                            <p>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
                        </div>
                        <input type="submit" value="Entrar" class="btn btn-lg btn-primary btn-block login-btn"/>
                    </form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>