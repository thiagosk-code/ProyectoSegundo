package logica;

public class PersonajePartidaInfo {

    private String nombrePersonaje;
    private int idPartida;
    private int idPartidaSlot;
    private int vida_Actual;
    private int mana_Actual;
    private int dano;
    private int vida_Max;
    private int mana_Max;
    private String descripcion;
    private String listaHabilidades;

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }
    public void setNombrePersonaje(String nombrePersonaje) {
        this.nombrePersonaje = nombrePersonaje;
    }

    public int getIdPartida() {
        return idPartida;
    }
    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getIdPartidaSlot() {
        return idPartidaSlot;
    }
    public void setIdPartidaSlot(int idPartidaSlot) {
        this.idPartidaSlot = idPartidaSlot;
    }

    public int getVida_Actual() {
        return vida_Actual;
    }
    public void setVida_Actual(int vida_Actual) {
        this.vida_Actual = vida_Actual;
    }

    public int getMana_Actual() {
        return mana_Actual;
    }
    public void setMana_Actual(int mana_Actual) {
        this.mana_Actual = mana_Actual;
    }
    
    public int getDano() {
        return dano;
    }
    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getVida_Max() {
        return vida_Max;
    }
    public void setVida_Max(int vida_Max) {
        this.vida_Max = vida_Max;
    }

    public int getMana_Max() {
        return mana_Max;
    }
    public void setMana_Max(int mana_Max) {
        this.mana_Max = mana_Max;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getListaHabilidades() {
        return listaHabilidades;
    }
    public void setListaHabilidades(String listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }
}
