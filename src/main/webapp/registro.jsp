<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calabazas y Drogones - Registro</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="registro.css">
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=MedievalSharp&display=swap" rel="stylesheet"> 

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="content-container page-register">
        <h1 class="title-text">Registrarse</h1>

        <div class="register-content">
            <div class="forms-group">
                <div class="rules-box">
                    <p class="rules-title">Contrase�a:</p>
                    <ul>
                        <li>De 6 a 12 caracteres</li>
                        <li>May�sculas (A,B,C...)</li>
                        <li>Min�sculas (a,b,c...)</li>
                        <li>Numeros (1,2,3...)</li>
                        <li>Simbolos (#,!,@...)</li>
                    </ul>
                        <div class="rules-error">
                            <% if (request.getAttribute("mensaje") != null) { %>
       							 <p class=�rules-error�><%= request.getAttribute("mensaje") %></p>
    						<% } %>
                        </div>
                </div>
                
                <div class="form-box">
                    <form action="<%= request.getContextPath() %>/CuentaServlet" method="get">
                        <div class="form-group">
                            <label for="nombre">Nombre del Usuario:</label>
                            <input type="text" id="nombre" name="nombre" value="${requestScope.nombre}" required>
                        </div>
                        <div class="form-group">
                            <label for="correo">Correo electronico:</label>
                            <input type="email" id="correo" name="correo" value="${requestScope.correo}" required>
                        </div>
                        <div class="form-group">
                            <label for="contrasena">Contrase�a:</label>
                            <input type="password" id="contrasena" name="contra" value="${requestScope.contra}" required>
                        </div>
                        <div class="form-group">
                            <label for="confirmacion">Confirmar Contrase�a:</label>
                            <input type="password" id="confirmacion" name="contraConf" value="${requestScope.contraConf}" required>
                            <input type="hidden" name="tipo" value="registro">
                        </div>


                        <div class="form-buttons">
                            <button class="btn-image register-btn" type="submit">Registrarse</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="form-buttons">
                <button id="VolverR" class="btn-image back-btn back-btn-image"><a href="index.html">Volver</a></button>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>