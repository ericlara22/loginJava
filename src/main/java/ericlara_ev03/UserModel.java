
package ericlara_ev03;



public class UserModel {
    
    String rut, nombre, apellido, contrasena, perfil;
    int edad;

    public UserModel(String rut, String nombre, String apellido, String contrasena, String perfil, int edad) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.perfil = perfil;
        this.edad = edad;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    
 
}