<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="head">
        <title>Detalhe Despesa</title>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <div class="container">
            <div class="col-md-12">
                <div class="row">

                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Categoria</th>
                            <th>Valor</th>
                            <th>Descrição</th>
                            <th>Localização</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th scope="row">${despesa.id}</th>
                            <td>${despesa.categoria}</td>
                            <td>${despesa.valor} &euro;</td>
                            <td>${despesa.descricao}</td>
                            <td>${despesa.localizacao}</td>
                        </tr>
                        </tbody>
                    </table>

                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalAlterar">Alterar</button>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalEliminar">Eliminar</button>

                    <!-- Modal Alterar -->
                    <div class="modal fade" id="modalAlterar" tabindex="-1" role="dialog" aria-labelledby="modalLabelAlterar">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="modalLabelAlterar">Alterar Despesa</h4>
                                </div>
                                <div class="modal-body">
                                    Tem acerteza que pretende alterar os dados desta despesa?
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                                    <td><a href="/AsMinhasDespesas/despesa/consultar/alterar/${despesa.id}" class="btn btn-primary">Alterar</a></td>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal Eliminar -->
                    <div class="modal fade" id="modalEliminar" tabindex="-1" role="dialog" aria-labelledby="modalLabelEliminar">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="modalLabelEliminar">Eliminar Despesa</h4>
                                </div>
                                <div class="modal-body">
                                    Tem acerteza que pretende eliminar esta despesa?
                                </div>
                                <div class="modal-footer">
                                    <form action="/AsMinhasDespesas/despesa/consultar/eliminar/${despesa.id}" method="post" >
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                                        <button type="submit" class="btn btn-primary">Eliminar</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <br />
    </tiles:putAttribute>
</tiles:insertDefinition>