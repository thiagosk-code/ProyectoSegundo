<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>La Campaña del Glitch - Información de Partida </title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="infoPartida.css">
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=MedievalSharp&display=swap" rel="stylesheet"> 

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="content-container page-infoPartida">
        <div class="main-content">
            
            <div class="left-content">
                
                 <h1 class="title-text character-title">${sessionScope.personajeInfoActual.nombrePersonaje}</h1>

                <div class="description-box">
                    <p class="description-title">Descripción:</p>
                
                    <p class="character-description">${sessionScope.personajeInfoActual.descripcion}</p>
                    
                    <div class="abilities-group">
                        <p class="description-title">Habilidades:</p>
                        
                        <div class="abilities-columns">
                            
                            <p>${sessionScope.personajeInfoActual.listaHabilidades}</p>
                            
                            <ul class="blocked-abilities-list">
                                <li>(Bloqueado)</li>
                                <li>(Bloqueado)</li>
                                <li>(Bloqueado)</li>
                                <li>(Bloqueado)</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="stats-box">
           
                <div class="character-image-container">
                    <img src="Imagenes/PJs/Aristo.png" alt="${sessionScope.personajeInfoActual.nombrePersonaje}" class="character-image">
                </div>
                
                <p class="stats-subtitle">${sessionScope.personajeInfoActual.nombrePersonaje}</p>
                
                <ul class="stats-list">
                    
                    <li><span class="stat-name">Vida:</span> <span class="stat-value">${sessionScope.personajeInfoActual.vida_Actual} / ${sessionScope.personajeInfoActual.vida_Max}</span></li>
                    
                    <li><span class="stat-name">Maná:</span> <span class="stat-value">${sessionScope.personajeInfoActual.mana_Actual} / ${sessionScope.personajeInfoActual.mana_Max}</span></li>
                    
                    <li><span class="stat-name">Ataque:</span> <span class="stat-value">${sessionScope.personajeInfoActual.dano}</span></li>
                    
                </ul>
            </div>
        </div>

        <div class="footer-buttons">
            <button class="btn-image back-btn back-btn-image"><a href="partidas.jsp">Volver</a></button>
            <button class="btn-image"><a href="jugar.jsp">Continuar</a></button>
            <button class="btn-image"><a href="jugar.jsp">Nueva Partida</a></button>
            <button class="btn-image"><a href="estadisticas.jsp">Recorrido</a></button>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>