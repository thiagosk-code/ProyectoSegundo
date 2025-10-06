<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calabazas y Drogones - Iniciar Sesión</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="inicio.css">
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=MedievalSharp&display=swap" rel="stylesheet"> 

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="content-container page-login">
        <h1 class="title-text">Iniciar Sesión</h1>

        <div class="login-content">
            <div class="forms-group">
                <div class="form-box">
                    <form action="<%= request.getContextPath() %>/CuentaServlet" method="get">
                        <div class="form-group">
                            <label for="correo">Correo del Usuario:</label>
                            <input type="email" id="correo" name="correo" maxlength="100" value="${requestScope.correo}" required>
                        </div>
                        <div class="form-group">
                            <label for="contrasena">Contraseña:</label>
                            <input type="password" id="contrasena" name="contra" maxlength="12" value="${requestScope.contra}" required>
                            <input type="hidden" name="tipo" value="inicio">
                        </div>
                        
                        <div class="form-buttons">
                            <button class="btn-image login-btn" type="submit">Iniciar Sesión</button>
                        </div>
                    </form>
                    <div class="rules-error">
                            <% if (request.getAttribute("mensaje") != null) { %>
       							 	<p class="rules-error"><%= request.getAttribute("mensaje") %></p>
    						<% } %>
                    </div>
                </div>
            </div>

            <div class="form-buttons">
            	<button class="btn-image back-btn" ><a href="registro.jsp">Aun no tengo una Cuenta</a></button>
                <button id="VolverI" class="btn-image back-btn back-btn-image"><a href="index.html">Volver</a></button> 
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
