package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import baseDeDatos.ConexionContra;
import controllers.JugarController;
import jakarta.servlet.http.HttpSession;


public class Jugar {

    public static String Inicio1() {
        return "En un barrio humilde vivía un niño cuyos sueños superaban los recursos de su familia. "
            + "Anhelaba una computadora capaz de llevarlo a mundos desconocidos, pero sus bolsillos estaban casi vacíos. "
            + "Una noche, mientras deambulaba por la calle principal, escuchó rumores de un hombre que vendía ordenadores "
            + "a precios insólitamente bajos desde una vieja camioneta aparcada bajo el parpadeo de un farol."
            + "Movido por la esperanza, el niño se acercó al improvisado puesto y, tras un breve regateo, "
            + "compró la máquina por el único dinero que tenía. Con unas monedas aún en la palma de la mano, "
            + "decidió invertirlas en un objeto adicional.\r\n"
            + "Elige:\r\n"
            + "1) Hachas de un guerrero muy prodigioso\r\n"
            + "2) Varita mágica de una maga desconocida\r\n"
            + "3) Arco y flecha de una arquera muy ágil\r\n"
            + "4) Dagas de asesino medieval\r\n"
            + "5) Libro de Dante Alighieri";
    }

    public static String Inicio2(int idPartida, String accion) {

        ActivarFlag(idPartida, 1);

        int mana = 0;
        int vida = 0;
        int dano = 0;
        int dinero = 0;
        String desc = null;

        int idPersonaje = Integer.parseInt(accion);
        idPersonaje++;

        String sql1 = "SELECT * FROM personaje WHERE ID_personaje = ?";
        ConexionContra cc = new ConexionContra();

        try (Connection conn = cc.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql1)) {

            stmt.setInt(1, idPersonaje);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mana = rs.getInt("Mana_Ini");
                vida = rs.getInt("Vida_Ini");
                dano = rs.getInt("Dano_Ini");
                dinero = rs.getInt("Dinero");
                desc = rs.getString("Descripcion");
            }

        } catch (SQLException ex) {
            System.out.print("Error al obtener personaje: " + ex.getMessage());
            return "Error de conexión.";
        }

        String sql2 = "UPDATE personaje_partida SET ID_personaje = ?, Mana_Max = ?, Mana_Act = ?, "
                    + "Vida_Max = ?, Vida_Act = ?, Dano = ?, Dinero = ?, Descripcion = ? WHERE ID_partida = ?";

        try (Connection conn = cc.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql2)) {

            stmt.setInt(1, idPersonaje);
            stmt.setInt(2, mana);
            stmt.setInt(3, mana);
            stmt.setInt(4, vida);
            stmt.setInt(5, vida);
            stmt.setInt(6, dano);
            stmt.setInt(7, dinero);
            stmt.setString(8, desc);
            stmt.setInt(9, idPartida);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.print("Error al actualizar personaje inicial: " + ex.getMessage());
        }

        
        return "El niño, emocionado por usar un nuevo objeto en sus juegos virtuales, conecta su computadora. "
            + "Al hacerlo, descubre un ícono llamado \"glitch\", y al abrirlo, la pantalla se funde en colores, "
            + "se apaga y la computadora muere tras una descarga eléctrica. Frustrado, se va a dormir. "
            + "Al despertar, ya no está en su habitación, sino en un bosque brumoso. Allí, un anciano le explica "
            + "que ha entrado en un mundo virtual y la única forma de volver a casa es atravesar una serie de zonas desafiantes. "
            + "Para su aventura, deberá usar unos dados mágicos de veinte caras que determinarán su suerte y acciones, "
            + "desatando diferentes poderes. Con el objeto de su mundo real en mano, el niño emprende con valentía el sendero "
            + "hacia la primera zona, impulsado por la determinación de regresar a su hogar.\r\n"
            + "Envía \"1\" o \"seguir\" para continuar";
    }

    public static String mostrarTienda(int idPartida, HttpSession session) {
        ConexionContra cc = new ConexionContra();
        StringBuilder tienda = new StringBuilder("TIENDA:\n");
        int idRecorridoActivo = 0;
        
        String sqlRecorrido = "SELECT MAX(ID_recorrido) AS RecorridoActivo FROM recorridos_partida WHERE ID_partida = ? AND Flag = TRUE";

        try (Connection con = cc.conectar();
             PreparedStatement stmtRecorrido = con.prepareStatement(sqlRecorrido)) {

            stmtRecorrido.setInt(1, idPartida);
            ResultSet rsRecorrido = stmtRecorrido.executeQuery();

            if (rsRecorrido.next()) {
                idRecorridoActivo = rsRecorrido.getInt("RecorridoActivo");
            } else {
                return "Error: No se pudo determinar la zona activa.";
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener recorrido activo: " + e.getMessage());
            return "Error de conexión al determinar zona.";
        }

       
        int idTiendaActual = idRecorridoActivo - 1;


        String sqlTienda = "SELECT o.ID_objeto, o.Nombre, tobj.Precio "
                         + "FROM Tienda_Objetos tobj "
                         + "JOIN Objetos o ON o.ID_objeto = tobj.ID_objeto "
                         + "WHERE tobj.ID_tienda = ? "
                         + "ORDER BY o.ID_objeto ASC";

        try (Connection con = cc.conectar();
             PreparedStatement stmtTienda = con.prepareStatement(sqlTienda)) {

            stmtTienda.setInt(1, idTiendaActual);
            ResultSet rs = stmtTienda.executeQuery();

            while (rs.next()) {
                tienda.append(rs.getInt("ID_objeto"))
                      .append(". ")
                      .append(rs.getString("Nombre"))
                      .append(" - ")
                      .append(rs.getInt("Precio"))
                      .append(" monedas\n");
            }

            tienda.append("\nEscribí el número del objeto para comprarlo o '0' para volver.");

        } catch (SQLException e) {
            System.err.println("Error al mostrar la tienda: " + e.getMessage());
            return "Error al mostrar la tienda.";
        }

        return tienda.toString();
    }
    
    public static String comprarObjeto(int idPartida, String accion, HttpSession session) {
        int idObjeto;
        try {
            idObjeto = Integer.parseInt(accion);
        } catch (NumberFormatException e) {
            return "no es valido"; 
        }

        if (idObjeto == 0) {
            return "menu principal"; 
        }

        ConexionContra cc = new ConexionContra();

        String sqlPrecio = "SELECT Precio FROM Tienda_Objetos WHERE ID_objeto = ?";
        String sqlDinero = "SELECT Dinero FROM personaje_partida WHERE ID_partida = ?";
        String sqlCompra = "INSERT INTO Inventario (ID_partida, ID_objeto) VALUES (?, ?)"; 
        String sqlUpdateDinero = "UPDATE personaje_partida SET Dinero = Dinero - ? WHERE ID_partida = ?";
        
        Connection con = null;

        try {
            con = cc.conectar();
            con.setAutoCommit(false);

            int precio = 0;
            try (PreparedStatement stmt = con.prepareStatement(sqlPrecio)) {
                stmt.setInt(1, idObjeto);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    precio = rs.getInt("Precio");
                } else {
                    return "no es valido";
                }
            }

            int dinero = 0;
            try (PreparedStatement stmt = con.prepareStatement(sqlDinero)) {
                stmt.setInt(1, idPartida);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    dinero = rs.getInt("Dinero");
                }
            }

            if (dinero < precio) {
                con.setAutoCommit(true); // Restaurar autocommit antes de salir
                return "no es valido"; 
            }

            try (PreparedStatement stmt = con.prepareStatement(sqlCompra)) {
                stmt.setInt(1, idPartida);
                stmt.setInt(2, idObjeto);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = con.prepareStatement(sqlUpdateDinero)) {
                stmt.setInt(1, precio);
                stmt.setInt(2, idPartida);
                stmt.executeUpdate();
            }
            
            // CIERRE DE TRANSACCIÓN EXITOSO
            con.commit(); 
            con.setAutoCommit(true); 

            JugarController controller = new JugarController();
            controller.ActualizarAtributosPersonaje(session, idPartida);

            int nuevoDinero = dinero - precio;
            return "Compra realizada con éxito. Te quedan " + nuevoDinero + " monedas.\n\n" 
                + mostrarTienda(idPartida, session);

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacer si hay error
                    con.setAutoCommit(true);
                } catch (SQLException rbex) {
                    System.err.println("Error en rollback: " + rbex.getMessage());
                }
            }
            System.err.println("Error al procesar la compra: " + e.getMessage());
            return "Error al procesar la compra: " + e.getMessage();           
        } finally {
            if (con != null) {
                try {
                    con.close(); // Asegurar el cierre de conexión
                } catch (SQLException closeEx) {
                    System.err.println("Error al cerrar la conexión: " + closeEx.getMessage());
                }
            }
        }
    }

    public static String mostrarInventario(int idPartida, HttpSession session) {
        String sql = "SELECT o.Nombre, o.Tipo FROM Inventario i "
                   + "JOIN Objetos o ON i.ID_objeto = o.ID_objeto "
                   + "WHERE i.ID_partida = ?";

        ConexionContra cc = new ConexionContra();
        StringBuilder inv = new StringBuilder("INVENTARIO:\n");

        try (Connection con = cc.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPartida);
            ResultSet rs = stmt.executeQuery();

            boolean vacio = true;
            while (rs.next()) {
                vacio = false;
                inv.append("- ").append(rs.getString("Nombre"))
                   .append(" (").append(rs.getString("Tipo")).append(")\n");
            }

            if (vacio) {
                inv.append("Tu inventario está vacío.\n");
            }

            inv.append("\nEscribí '0' para volver."); 

        } catch (SQLException e) {
            System.err.println("Error al mostrar inventario: " + e.getMessage());
            return "Error al mostrar el inventario.";
        }

        return inv.toString();
    }


    public static String mostrarMenuPrincipal() {
        String menu = "¿Qué querés hacer?\n"
                    + "1. Ir a la tienda\n"
                    + "2. Ver inventario\n"
                    + "3. Pelear";
        return menu;
    }
    
    public int obtenerMonedasJugador(Connection con, int idJugador) throws SQLException {
        String sql = "SELECT Monedas FROM Jugador WHERE ID_jugador = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idJugador);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Monedas");
            }
        }
        return 0;
    }
    
    public static boolean VerificarFlag(int idPartida, int idRecorrido) {
        String sql = "SELECT Flag FROM recorridos_partida WHERE ID_partida = ? AND ID_recorrido = ?";
        ConexionContra cc = new ConexionContra();

        try (Connection conn = cc.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPartida);
            stmt.setInt(2, idRecorrido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("Flag");
            }

        } catch (SQLException ex) {
            System.out.println("Error al verificar flag: " + ex.getMessage());
        }
        return false;
    }

    public static void ActivarFlag(int idPartida, int idRecorrido) {
        String sql = "UPDATE recorridos_partida SET Flag = true WHERE ID_partida = ? AND ID_recorrido = ?";
        ConexionContra cc = new ConexionContra();

        try (Connection conn = cc.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPartida);
            stmt.setInt(2, idRecorrido);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al activar flag: " + ex.getMessage());
        }
    }
}

