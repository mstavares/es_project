<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="head">
        <title>Consultar Despesa</title>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">

        <c:choose>
            <c:when test="${ agregado == true }">
                <div class="container">
                    <div class="col-md-offset-10">
                        <form:form method="post" action="/AsMinhasDespesas/despesa/consultar/agregado">
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary" >Consultar Agregado Familiar</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </c:when>
        </c:choose>

        <div class="container">
            <div class="col-md-12">
                <div class="row">
                    <c:choose>
                        <c:when test="${not empty despesas}">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Data</th>
                                    <th>Categoria</th>
                                    <th>Valor</th>
                                    <th>Utilizador</th>
                                    <th>Acções</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="despesa" items="${despesas}">
                                    <tr>
                                        <td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${despesa.data}"/></td>
                                        <td>${despesa.categoria}</td>
                                        <td>${despesa.valor} &euro;</td>
                                        <td>${despesa.utilizador}</td>
                                        <td><a href="/AsMinhasDespesas/despesa/consultar/detalhes/${despesa.id}" class="btn btn-primary">Editar</a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p>Não há despesas a apresentar</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>