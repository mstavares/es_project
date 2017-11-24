<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="head">
        <title>Consultar Categoria</title>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <div class="container">
            <div class="col-md-12">
                <div class="row">
                    <c:choose>
                        <c:when test="${not empty categorias}">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Categoria</th>
                                    <th>Acções</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="categoria" items="${categorias}">
                                    <tr>
                                        <td>${categoria.categoria}</td>
                                        <td>
                                            <a href="/AsMinhasDespesas/categoria/consultar/alterar/${categoria.id}" class="btn btn-primary">Editar</a>
                                            <a href="/AsMinhasDespesas/categoria/consultar/eliminar/${categoria.id}" class="btn btn-primary">Eliminar</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p>Não há categorias a apresentar</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>