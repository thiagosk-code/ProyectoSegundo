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
                <h1 class="title-text main-title">La Ciudad</h1>
                
                <div class="main-text-box ">
                    <p class="text-content scroll-box">
                		${empty requestScope.mensaje ? "presione enviar para empezar/seguir" : requestScope.mensaje}
                    </p>
                               
    				<div class="input-container"> 
                        <form class="text-input-form" action="<%= request.getContextPath() %>/JugarServlet" method="get">
                            <input type="text" placeholder="Escribe tu acción aquí..." class="text-input-field" id="accion" name="accion" maxlength="50" value="${requestScope.accion}">
                            <input type="hidden" name="estado_actual" value="${empty requestScope.mensaje ? 'inicia' : 'sigue'}">
                        	<button class="btn-image-jugar submit-btn" type="submit">Enviar</button>
                        </form>
                    </div>
                    
                </div>
            </div>

      
       <div class="right-aside">
                
                <div class="stats-box-jugar">
                    <div class="character-image-container-jugar">
                        <img src="Imagenes/PJs/Nino.png" alt="Goblin Aristóteles" class="character-image-jugar">
           
          </div>
                    
                    <p class="stats-subtitle-jugar">${sessionScope.personajeInfoActual.nombrePersonaje}</p>
                    
                    <ul class="stats-list-jugar">
        
                        <li><span class="stat-name">Vida:</span> <span class="stat-value">${sessionScope.personajeInfoActual.vida_Actual} / ${sessionScope.personajeInfoActual.vida_Max}</span></li>
                        <li><span class="stat-name">Mana:</span> <span class="stat-value">${sessionScope.personajeInfoActual.mana_Actual} / ${sessionScope.personajeInfoActual.mana_Max}</span></li>
                        <li><span class="stat-name">Ataque:</span> <span class="stat-value">${sessionScope.personajeInfoActual.dano}</span></li>
                        <li><span class="stat-name">Dinero:</span> <span class="stat-value">${sessionScope.personajeInfoActual.dinero}</span></li>
                    </ul>
   
              </div>

                <div class="aside-buttons">
				    <button class="btn-image-jugar"><a href="#">Guardar Partida</a></button>
				    
				    <button class="btn-image-jugar bottom-aligned-button">
				        <a href="JugarServlet?accion=guardarYSalir">Guardar y Salir</a>
				    </button>
				</div>
      
       </div>

        </div> 
    </div> 
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
