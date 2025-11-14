<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>La Campaña del Glitch - Jugar </title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="jugar.css"> 
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=MedievalSharp&display=swap" rel="stylesheet"> 

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="content-container page-jugar">
    
        <div class="main-layout">
            
  
           <div class="left-content-jugar">
                <h1 class="title-text main-title">Recorrido</h1>
                
                <div class="main-text-box">
                    <p class="text-content">
                		Durante toda la partida vas a ir avanzando por distintas zonas, siempre empiezando por la ciudad y el bosque siendo estas zonas introductorias y después avanzas por las zonas de categorías, donde vas a poder explorar, visitar las tiendas locales, combatir con enemigos únicos y pasa a la siguiente zona derrotando a un enemigo jefe. En cada partida solo visita 1 zona por categoría seleccionada al azar y solo ves 1 de los finales posibles.
        
                    </p>
                    
                </div>
            </div>

      
       <div class="right-aside">
                
                <div class="stats-box-jugar">
                    <div class="character-image-container-jugar">
                        <img src="Imagenes/PJs/Nino.png" alt="Goblin AristÃ³teles" class="character-image-jugar">
           
          </div>
                    
                    <p class="stats-subtitle-jugar">${sessionScope.personajeInfoActual.nombrePersonaje}</p>
                    
                    <ul class="stats-list-jugar">
        
                        <li><span class="stat-name">Vida:</span> <span class="stat-value">${sessionScope.personajeInfoActual.vida_Actual} / ${sessionScope.personajeInfoActual.vida_Max}</span></li>
                        <li><span class="stat-name">Defensa:</span> <span class="stat-value">${sessionScope.personajeInfoActual.mana_Actual} / ${sessionScope.personajeInfoActual.mana_Max}</span></li>
                        <li><span class="stat-name">Ataque:</span> <span class="stat-value">${sessionScope.personajeInfoActual.dano}</span></li>
                    </ul>
   
              </div>

                <div class="aside-buttons">
				    <button class="btn-image back-btn back-btn-image"><a href="infoPartida.jsp">Volver</a></button>
				</div>
      
       </div>

        </div> 
    </div> 
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

</html>

