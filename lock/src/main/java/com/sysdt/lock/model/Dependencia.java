package com.sysdt.lock.model;

import java.io.Serializable;

public class Dependencia implements Serializable{
   
	private static final long serialVersionUID = -976973935111547091L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dependencia.dependiente
     *
     * @mbggenerated Sun Sep 25 09:26:29 CDT 2016
     */
    private String dependiente;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dependencia.encargado
     *
     * @mbggenerated Sun Sep 25 09:26:29 CDT 2016
     */
    private String encargado;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dependencia.dependiente
     *
     * @return the value of dependencia.dependiente
     *
     * @mbggenerated Sun Sep 25 09:26:29 CDT 2016
     */
    public String getDependiente() {
        return dependiente;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dependencia.dependiente
     *
     * @param dependiente the value for dependencia.dependiente
     *
     * @mbggenerated Sun Sep 25 09:26:29 CDT 2016
     */
    public void setDependiente(String dependiente) {
        this.dependiente = dependiente;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dependencia.encargado
     *
     * @return the value of dependencia.encargado
     *
     * @mbggenerated Sun Sep 25 09:26:29 CDT 2016
     */
    public String getEncargado() {
        return encargado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dependencia.encargado
     *
     * @param encargado the value for dependencia.encargado
     *
     * @mbggenerated Sun Sep 25 09:26:29 CDT 2016
     */
    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }
}