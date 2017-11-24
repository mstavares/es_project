<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="head">
        <title>Upload CSV</title>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <div class="container">
            <div class="col-md-12">
                <div class="row">
                    <form:form method="POST" enctype="multipart/form-data" action="/AsMinhasDespesas/despesa/upload" modelAttribute="uploadForm">
                        <div class="form-group">
                            <label for="file">Importar ficheiro HomeBanking</label>
                            <input type="file" id="file" name="file" accept=".csv">
                            <p class="help-block">O ficheiro importado deve conter a extens�o .csv. <br />
                                O mesmo dever� ter tr�s colunas "Despesa","Valor" e "Localiza��o" as mesmas dever�o ser separadas por ';'.</p>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary" >Upload Ficheiro</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>