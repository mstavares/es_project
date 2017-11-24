<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<tiles:insertDefinition name="defaultTemplate">

    <tiles:putAttribute name="head">
        <title>As minhas despesas</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/home.css" />" />
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>
    </tiles:putAttribute>


    <tiles:putAttribute name="body">

    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${not empty categorias}">

                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead style="background:#f7a35c;">
                        <tr>
                            <th class="text-center">Mês</th>
                            <c:forEach var="categoria" items="${categorias}">
                                <th class="text-center">${categoria.categoria}</th>
                            </c:forEach>
                            <th class="text-center">Total</th>
                            <th class="text-center">Variacao</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach begin="1" end="${fn:length(resultados)-1}" varStatus="loopMes">
                            <tr>

                                <td class="text-center">
                                    <script>
                                        var monthNames = ["","Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];
                                        document.write(monthNames[${loopMes.index}]);
                                    </script>
                                </td>


                                <c:forEach begin="0" end="${fn:length(categorias)}" varStatus="loopCategorias">
                                    <td class="text-center">${resultados[loopMes.index][loopCategorias.index]}&euro;</td>
                                </c:forEach>

                                <td class="text-center">${variacaoMensal[loopMes.index]}</td>

                            </tr>

                        </c:forEach>


                        <tr>
                            <th class="text-center">Total</th>

                            <c:forEach var="resultadoTotal" items="${resultadosTotal}">
                                <td class="text-center">${resultadoTotal}</td>
                            </c:forEach>

                            <td class="text-center">-</td>

                        </tr>

                        </tbody>
                    </table>
                </div>

                </c:when>
                <c:otherwise>
                    <p>Não existem dados a apresentar.</p>
                </c:otherwise>
            </c:choose>


            <div class="col-md-6">
                <div id="graficoCategorias"></div>
            </div>

            <div class="col-md-6">
                <div id="graficoMeses"></div>
            </div>



            <script>
                var arrayCategorias = [];
                var arrayMeses = [];
            </script>

            <c:forEach begin="0" end="${fn:length(resultadosTotal)-2}" varStatus="loopResultados">
                <script>
                    arrayCategorias.push(['${categorias[loopResultados.index].categoria}',${resultadosTotal[loopResultados.index]}]);
                </script>
            </c:forEach>

            <c:forEach begin="1" end="${fn:length(resultados)-1}" varStatus="loopMesR">
                <script>
                    arrayMeses.push([${resultados[loopMesR.index][fn:length(categorias)]}]);
                </script>
            </c:forEach>



            <script>
                $(function () {
                    Highcharts.chart('graficoCategorias', {
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false,
                            type: 'pie'
                        },
                        title: {
                            text: 'Despesas por categoria'
                        },
                        tooltip: {
                            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: true,
                                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                    style: {
                                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                    }
                                }
                            }
                        },
                        series: [{
                            name: 'Despesas',
                            colorByPoint: true,
                            data: arrayCategorias
                        }]
                    });
                });



                $(function () {
                    Highcharts.chart('graficoMeses', {
                        chart: {
                            type: 'column'
                        },
                        title: {
                            text: 'Despesas por mês'
                        },
                        xAxis: {
                            categories: [
                                'Jan',
                                'Feb',
                                'Mar',
                                'Apr',
                                'May',
                                'Jun',
                                'Jul',
                                'Aug',
                                'Sep',
                                'Oct',
                                'Nov',
                                'Dec'
                            ],
                            crosshair: true
                        },
                        yAxis: {
                            min: 0,
                            title: {
                                text: 'Despesas'
                            }
                        },
                        tooltip: {
                            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
                            footerFormat: '</table>',
                            shared: true,
                            useHTML: true
                        },
                        plotOptions: {
                            column: {
                                pointPadding: 0.2,
                                borderWidth: 0
                            }
                        },
                        series: [{
                            name: 'Despesa',
                            data: arrayMeses
                        }]
                    });
                });


            </script>


        </div>
    </div>

        <br /><br />


</tiles:putAttribute>
</tiles:insertDefinition>