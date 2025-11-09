package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import baseDeDatos.ConexionContra;
import baseDeDatos.PartidaUsuario;
import jakarta.servlet.http.HttpServletRequest;
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
            + "Elige:\r\n"
            + "1) opcion 1\r\n"
            + "2) opcion 2\r\n"
            + "3) opcion 3";
    }

    public static String Inicio3(int idPartida, String accion) {    	
        ActivarFlag(idPartida, 2); 
        return "pasa algo";
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
