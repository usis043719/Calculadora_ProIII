package com.ugb.calculadora;

public class donantes {
    String userName, email, urlFoto, token, edad, telefono, direccion, tipoSangre ;

    public donantes() {}

    public donantes(String userName, String email, String urlFoto, String token , String edad , String telefono, String direccion, String tipoSangre) {
        this.userName = userName;
        this.email = email;
        this.urlFoto = urlFoto;
        this.token = token;
        this.edad = edad;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tipoSangre = tipoSangre;
    }
    public String getUserName() {
            return userName;
    }

    public void setUserName(String userName) {
            this.userName = userName;
    }

    public String getEmail() {
            return email;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    public String getUrlFoto() {
            return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
            this.urlFoto = urlFoto;
    }

    public String getToken() {
            return token;
    }

    public void setToken(String token) {
            this.token = token;
    }

    //-----------------------------------------------------------

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }
}

