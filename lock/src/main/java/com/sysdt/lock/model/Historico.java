package com.sysdt.lock.model;

import java.util.Date;

public class Historico {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column historico.id
     *
     * @mbggenerated Sat Jul 23 14:42:17 CDT 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column historico.username
     *
     * @mbggenerated Sat Jul 23 14:42:17 CDT 2016
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column historico.fecha
     *
     * @mbggenerated Sat Jul 23 14:42:17 CDT 2016
     */
    private Date fecha;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column historico.id
     *
     * @return the value of historico.id
     *
     * @mbggenerated Sat Jul 23 14:42:17 CDT 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column historico.id
     *
     * @param id the value for historico.id
     *
     * @mbggenerated Sat Jul 23 14:42:17 CDT 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column historico.username
     *
     * @return the value of historico.username
     *
     * @mbggenerated Sat Jul 23 14:42:17 CDT 2016
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column historico.username
     *
     * @param username the value for historico.username
     *
     * @mbggenerated Sat Jul 23 14:42:17 CDT 2016
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column historico.fecha
     *
     * @return the value of historico.fecha
     *
     * @mbggenerated Sat Jul 23 14:42:17 CDT 2016
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column historico.fecha
     *
     * @param fecha the value for historico.fecha
     *
     * @mbggenerated Sat Jul 23 14:42:17 CDT 2016
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}