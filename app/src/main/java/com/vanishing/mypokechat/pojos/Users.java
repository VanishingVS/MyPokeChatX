package com.vanishing.mypokechat.pojos;

public class Users {
    private String id;
    private String mail;
    private String estado;
    private String fecha;
    private String hora;
    private int solicitudes;
    private int nuevomensaje;


    public Users() {
    }

    public Users(String id, String mail, String estado, String fecha, String hora, int solicitudes, int nuevomensaje) {
        this.id = id;
        this.mail = mail;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.solicitudes = solicitudes;
        this.nuevomensaje = nuevomensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(int solicitudes) {
        this.solicitudes = solicitudes;
    }

    public int getNuevomensaje() {
        return nuevomensaje;
    }

    public void setNuevomensaje(int nuevomensaje) {
        this.nuevomensaje = nuevomensaje;
    }
}
