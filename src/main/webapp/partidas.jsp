<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>La Campaña del Glitch - Partidas </title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="partidas.css">
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=MedievalSharp&display=swap" rel="stylesheet"> 
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="content-container page-characters">
        <h1 class="title-text">Partidas</h1>

        <div class="characters-content">
            <div class="character-card">
                <p class="character-name">༺ Partida 1 ༻</p>
                <img src="Imagenes/PJs/nene.png" alt="Preview 1">
                <button class="select-btn">
                    <a href="PartidasServlet?idPartida=1"> < Seleccionar > </a>
                </button>
            </div>
            
            <div class="character-card">
                <p class="character-name">༺ Partida 2 ༻</p>
                <img src="Imagenes/PJs/nene.png" alt="Preview 2">
                <button class="select-btn">
                    <a href="PartidasServlet?idPartida=2"> < Seleccionar > </a>
                </button>
            </div>
            
            <div class="character-card">
                <p class="character-name">༺ Partida 3 ༻</p>
                <img src="Imagenes/PJs/nene.png" alt="Preview 3">
                <button class="select-btn">
                    <a href="PartidasServlet?idPartida=3"> < Seleccionar > </a>
                </button>
            </div>
        </div>

        <a href="PostInicio.jsp"><button class="btn-image back-btn back-btn-image">Volver</button></a>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
