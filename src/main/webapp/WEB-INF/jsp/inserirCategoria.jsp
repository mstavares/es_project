<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="head">
        <title>Inserir Categoria</title>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">

        <div class="container">
            <div class="col-md-12">
                <div class="row">
                    <form:form method="post" action="/AsMinhasDespesas/categoria/inserir" modelAttribute="categoriaForm">
                        <div class="form-group">
                            <c:if test="${not empty categoria.id}">
                                <form:hidden path="id" value="${categoria.id}"/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <form:label path="categoria">Categoria: </form:label>
                            <form:input  path="categoria" class="form-control" value="${categoria.categoria}" label="categoria" />
                            <form:errors path="categoria" cssClass="error" />
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary" >Adicionar</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
