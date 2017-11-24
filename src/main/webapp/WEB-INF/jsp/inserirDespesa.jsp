<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="head">
        <title>Inserir Despesa</title>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <div class="container">
            <div class="col-md-12">
                <div class="row">
                    <form:form method="post" action="/AsMinhasDespesas/despesa/inserir" modelAttribute="despesasForm">
                        <div class="form-group">
                            <c:if test="${not empty despesa.id}">
                                <form:hidden path="id" value="${despesa.id}"/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <form:label path="categoria">Categoria: </form:label>
                            <form:select path="categoria" class="form-control">
                                <c:forEach var="categoria" items="${categorias}">
                                    <option>${categoria.categoria}</option>
                                </c:forEach>
                                <c:if test="${not empty despesa.categoria}">
                                    <option selected>${despesa.categoria}</option>
                                </c:if>
                            </form:select>
                            <form:errors path="categoria" cssClass="error" />
                        </div>
                        <div class="form-group">
                            <form:label path="valor">Valor: </form:label>
                            <form:input path="valor" type="number" step="0.01" value="${despesa.valor}" class="form-control" id="valor" />
                            <form:errors path="valor" cssClass="error" />
                        </div>
                        <div class="form-group">
                            <form:label path="descricao">Descrição: </form:label>
                            <form:input path="descricao" class="form-control" value="${despesa.descricao}" label="descricao" />
                            <form:errors path="descricao" cssClass="error" />
                        </div>
                        <div class="form-group">
                            <form:label path="localizacao">Localização: </form:label>
                            <form:input path="localizacao" type="text" class="form-control" value="${despesa.localizacao}" id="localizacao" />
                        </div>
                        <div class="form-group">
                            <form:button type="submit" class="btn btn-primary" >Adicionar</form:button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>