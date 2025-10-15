package logica;

public class PersonajePartidaInfo {

	 public String nombrePersonaje;
	 		public int idPartida;
	    public int vida_Actual;
	    public int mana_Actual;
	    public int dano_Actual;
	    public int vida_Max;
	    public int mana_Max;
	    public int dano_Base;
	    public String descripcion;
	    public String listaHabilidades 

	    public String getNombrePersonaje() {
	        return nombrePersonaje;
	    }
	    public void setNombrePersonaje(String nombrePersonaje) {
	        this.nombrePersonaje = nombrePersonaje;
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

	    public int getDano_Actual() {
	        return dano_Actual;
	    }
	    public void setDano_Actual(int dano_Actual) {
	        this.dano_Actual = dano_Actual;
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

	    public int getDano_Base() {
	        return dano_Base;
	    }
	    public void setDano_Base(int dano_Base) {
	        this.dano_Base = dano_Base;
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
	    
	    public void setListaHabilidades (String listaHabilidades) {
	        this.listaHabilidades = listaHabilidades;
	    }
}
