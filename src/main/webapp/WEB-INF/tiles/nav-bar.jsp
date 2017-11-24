<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <security:authorize access="isAuthenticated()">
                <a class="navbar-brand" href="/AsMinhasDespesas/home">As minhas despesas</a>
            </security:authorize>
            <security:authorize access="!isAuthenticated()">
                <a class="navbar-brand" >As minhas despesas</a>
            </security:authorize>
        </div>

        <security:authorize access="isAuthenticated()">
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">

                    <li><a href="/AsMinhasDespesas/home">Home</a></li>

                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Categoria <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="/AsMinhasDespesas/categoria/inserir">Adicionar</a></li>
                                <li><a href="/AsMinhasDespesas/categoria/consultar">Consultar</a></li>
                            </ul>
                        </li>
                    </security:authorize>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Despesa <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/AsMinhasDespesas/despesa/inserir">Adicionar</a></li>
                            <li><a href="/AsMinhasDespesas/despesa/consultar">Consultar</a></li>
                            <li><a href="/AsMinhasDespesas/despesa/upload">Upload Ficheiro</a></li>
                        </ul>
                    </li>



                    <security:authorize access="isAuthenticated()">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><security:authentication property="principal.username" /><span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:url value="/j_spring_security_logout" var="logoutUrl" />
                                <li><a href="${logoutUrl}">Logout</a></li>
                            </ul>
                        </li>
                    </security:authorize>


                </ul>
            </div>
        </security:authorize>
    </div>
</nav>















<script>
    $(".nav a").on("click", function(){
        $(".nav").find(".active").removeClass("active");
        $(this).parent().addClass("active");
    });
</script>
