package com.sysdt.lock.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoricoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public HistoricoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andNomusuarioIsNull() {
            addCriterion("nomUsuario is null");
            return (Criteria) this;
        }

        public Criteria andNomusuarioIsNotNull() {
            addCriterion("nomUsuario is not null");
            return (Criteria) this;
        }

        public Criteria andNomusuarioEqualTo(String value) {
            addCriterion("nomUsuario =", value, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioNotEqualTo(String value) {
            addCriterion("nomUsuario <>", value, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioGreaterThan(String value) {
            addCriterion("nomUsuario >", value, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioGreaterThanOrEqualTo(String value) {
            addCriterion("nomUsuario >=", value, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioLessThan(String value) {
            addCriterion("nomUsuario <", value, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioLessThanOrEqualTo(String value) {
            addCriterion("nomUsuario <=", value, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioLike(String value) {
            addCriterion("nomUsuario like", value, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioNotLike(String value) {
            addCriterion("nomUsuario not like", value, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioIn(List<String> values) {
            addCriterion("nomUsuario in", values, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioNotIn(List<String> values) {
            addCriterion("nomUsuario not in", values, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioBetween(String value1, String value2) {
            addCriterion("nomUsuario between", value1, value2, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andNomusuarioNotBetween(String value1, String value2) {
            addCriterion("nomUsuario not between", value1, value2, "nomusuario");
            return (Criteria) this;
        }

        public Criteria andFechaIsNull() {
            addCriterion("fecha is null");
            return (Criteria) this;
        }

        public Criteria andFechaIsNotNull() {
            addCriterion("fecha is not null");
            return (Criteria) this;
        }

        public Criteria andFechaEqualTo(Date value) {
            addCriterion("fecha =", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotEqualTo(Date value) {
            addCriterion("fecha <>", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaGreaterThan(Date value) {
            addCriterion("fecha >", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaGreaterThanOrEqualTo(Date value) {
            addCriterion("fecha >=", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaLessThan(Date value) {
            addCriterion("fecha <", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaLessThanOrEqualTo(Date value) {
            addCriterion("fecha <=", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaIn(List<Date> values) {
            addCriterion("fecha in", values, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotIn(List<Date> values) {
            addCriterion("fecha not in", values, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaBetween(Date value1, Date value2) {
            addCriterion("fecha between", value1, value2, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotBetween(Date value1, Date value2) {
            addCriterion("fecha not between", value1, value2, "fecha");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoIsNull() {
            addCriterion("placas_eco is null");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoIsNotNull() {
            addCriterion("placas_eco is not null");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoEqualTo(String value) {
            addCriterion("placas_eco =", value, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoNotEqualTo(String value) {
            addCriterion("placas_eco <>", value, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoGreaterThan(String value) {
            addCriterion("placas_eco >", value, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoGreaterThanOrEqualTo(String value) {
            addCriterion("placas_eco >=", value, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoLessThan(String value) {
            addCriterion("placas_eco <", value, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoLessThanOrEqualTo(String value) {
            addCriterion("placas_eco <=", value, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoLike(String value) {
            addCriterion("placas_eco like", value, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoNotLike(String value) {
            addCriterion("placas_eco not like", value, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoIn(List<String> values) {
            addCriterion("placas_eco in", values, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoNotIn(List<String> values) {
            addCriterion("placas_eco not in", values, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoBetween(String value1, String value2) {
            addCriterion("placas_eco between", value1, value2, "placasEco");
            return (Criteria) this;
        }

        public Criteria andPlacasEcoNotBetween(String value1, String value2) {
            addCriterion("placas_eco not between", value1, value2, "placasEco");
            return (Criteria) this;
        }

        public Criteria andEstadoIsNull() {
            addCriterion("estado is null");
            return (Criteria) this;
        }

        public Criteria andEstadoIsNotNull() {
            addCriterion("estado is not null");
            return (Criteria) this;
        }

        public Criteria andEstadoEqualTo(Boolean value) {
            addCriterion("estado =", value, "estado");
            return (Criteria) this;
        }

        public Criteria andEstadoNotEqualTo(Boolean value) {
            addCriterion("estado <>", value, "estado");
            return (Criteria) this;
        }

        public Criteria andEstadoGreaterThan(Boolean value) {
            addCriterion("estado >", value, "estado");
            return (Criteria) this;
        }

        public Criteria andEstadoGreaterThanOrEqualTo(Boolean value) {
            addCriterion("estado >=", value, "estado");
            return (Criteria) this;
        }

        public Criteria andEstadoLessThan(Boolean value) {
            addCriterion("estado <", value, "estado");
            return (Criteria) this;
        }

        public Criteria andEstadoLessThanOrEqualTo(Boolean value) {
            addCriterion("estado <=", value, "estado");
            return (Criteria) this;
        }

        public Criteria andEstadoIn(List<Boolean> values) {
            addCriterion("estado in", values, "estado");
            return (Criteria) this;
        }

        public Criteria andEstadoNotIn(List<Boolean> values) {
            addCriterion("estado not in", values, "estado");
            return (Criteria) this;
        }

        public Criteria andEstadoBetween(Boolean value1, Boolean value2) {
            addCriterion("estado between", value1, value2, "estado");
            return (Criteria) this;
        }

        public Criteria andEstadoNotBetween(Boolean value1, Boolean value2) {
            addCriterion("estado not between", value1, value2, "estado");
            return (Criteria) this;
        }

        public Criteria andLatitudIsNull() {
            addCriterion("latitud is null");
            return (Criteria) this;
        }

        public Criteria andLatitudIsNotNull() {
            addCriterion("latitud is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudEqualTo(String value) {
            addCriterion("latitud =", value, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudNotEqualTo(String value) {
            addCriterion("latitud <>", value, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudGreaterThan(String value) {
            addCriterion("latitud >", value, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudGreaterThanOrEqualTo(String value) {
            addCriterion("latitud >=", value, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudLessThan(String value) {
            addCriterion("latitud <", value, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudLessThanOrEqualTo(String value) {
            addCriterion("latitud <=", value, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudLike(String value) {
            addCriterion("latitud like", value, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudNotLike(String value) {
            addCriterion("latitud not like", value, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudIn(List<String> values) {
            addCriterion("latitud in", values, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudNotIn(List<String> values) {
            addCriterion("latitud not in", values, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudBetween(String value1, String value2) {
            addCriterion("latitud between", value1, value2, "latitud");
            return (Criteria) this;
        }

        public Criteria andLatitudNotBetween(String value1, String value2) {
            addCriterion("latitud not between", value1, value2, "latitud");
            return (Criteria) this;
        }

        public Criteria andLongitudIsNull() {
            addCriterion("longitud is null");
            return (Criteria) this;
        }

        public Criteria andLongitudIsNotNull() {
            addCriterion("longitud is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudEqualTo(String value) {
            addCriterion("longitud =", value, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudNotEqualTo(String value) {
            addCriterion("longitud <>", value, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudGreaterThan(String value) {
            addCriterion("longitud >", value, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudGreaterThanOrEqualTo(String value) {
            addCriterion("longitud >=", value, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudLessThan(String value) {
            addCriterion("longitud <", value, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudLessThanOrEqualTo(String value) {
            addCriterion("longitud <=", value, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudLike(String value) {
            addCriterion("longitud like", value, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudNotLike(String value) {
            addCriterion("longitud not like", value, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudIn(List<String> values) {
            addCriterion("longitud in", values, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudNotIn(List<String> values) {
            addCriterion("longitud not in", values, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudBetween(String value1, String value2) {
            addCriterion("longitud between", value1, value2, "longitud");
            return (Criteria) this;
        }

        public Criteria andLongitudNotBetween(String value1, String value2) {
            addCriterion("longitud not between", value1, value2, "longitud");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoIsNull() {
            addCriterion("idTipoEvento is null");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoIsNotNull() {
            addCriterion("idTipoEvento is not null");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoEqualTo(Integer value) {
            addCriterion("idTipoEvento =", value, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoNotEqualTo(Integer value) {
            addCriterion("idTipoEvento <>", value, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoGreaterThan(Integer value) {
            addCriterion("idTipoEvento >", value, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoGreaterThanOrEqualTo(Integer value) {
            addCriterion("idTipoEvento >=", value, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoLessThan(Integer value) {
            addCriterion("idTipoEvento <", value, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoLessThanOrEqualTo(Integer value) {
            addCriterion("idTipoEvento <=", value, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoIn(List<Integer> values) {
            addCriterion("idTipoEvento in", values, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoNotIn(List<Integer> values) {
            addCriterion("idTipoEvento not in", values, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoBetween(Integer value1, Integer value2) {
            addCriterion("idTipoEvento between", value1, value2, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdtipoeventoNotBetween(Integer value1, Integer value2) {
            addCriterion("idTipoEvento not between", value1, value2, "idtipoevento");
            return (Criteria) this;
        }

        public Criteria andIdchoferIsNull() {
            addCriterion("idChofer is null");
            return (Criteria) this;
        }

        public Criteria andIdchoferIsNotNull() {
            addCriterion("idChofer is not null");
            return (Criteria) this;
        }

        public Criteria andIdchoferEqualTo(Integer value) {
            addCriterion("idChofer =", value, "idchofer");
            return (Criteria) this;
        }

        public Criteria andIdchoferNotEqualTo(Integer value) {
            addCriterion("idChofer <>", value, "idchofer");
            return (Criteria) this;
        }

        public Criteria andIdchoferGreaterThan(Integer value) {
            addCriterion("idChofer >", value, "idchofer");
            return (Criteria) this;
        }

        public Criteria andIdchoferGreaterThanOrEqualTo(Integer value) {
            addCriterion("idChofer >=", value, "idchofer");
            return (Criteria) this;
        }

        public Criteria andIdchoferLessThan(Integer value) {
            addCriterion("idChofer <", value, "idchofer");
            return (Criteria) this;
        }

        public Criteria andIdchoferLessThanOrEqualTo(Integer value) {
            addCriterion("idChofer <=", value, "idchofer");
            return (Criteria) this;
        }

        public Criteria andIdchoferIn(List<Integer> values) {
            addCriterion("idChofer in", values, "idchofer");
            return (Criteria) this;
        }

        public Criteria andIdchoferNotIn(List<Integer> values) {
            addCriterion("idChofer not in", values, "idchofer");
            return (Criteria) this;
        }

        public Criteria andIdchoferBetween(Integer value1, Integer value2) {
            addCriterion("idChofer between", value1, value2, "idchofer");
            return (Criteria) this;
        }

        public Criteria andIdchoferNotBetween(Integer value1, Integer value2) {
            addCriterion("idChofer not between", value1, value2, "idchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferIsNull() {
            addCriterion("nomChofer is null");
            return (Criteria) this;
        }

        public Criteria andNomchoferIsNotNull() {
            addCriterion("nomChofer is not null");
            return (Criteria) this;
        }

        public Criteria andNomchoferEqualTo(String value) {
            addCriterion("nomChofer =", value, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferNotEqualTo(String value) {
            addCriterion("nomChofer <>", value, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferGreaterThan(String value) {
            addCriterion("nomChofer >", value, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferGreaterThanOrEqualTo(String value) {
            addCriterion("nomChofer >=", value, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferLessThan(String value) {
            addCriterion("nomChofer <", value, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferLessThanOrEqualTo(String value) {
            addCriterion("nomChofer <=", value, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferLike(String value) {
            addCriterion("nomChofer like", value, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferNotLike(String value) {
            addCriterion("nomChofer not like", value, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferIn(List<String> values) {
            addCriterion("nomChofer in", values, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferNotIn(List<String> values) {
            addCriterion("nomChofer not in", values, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferBetween(String value1, String value2) {
            addCriterion("nomChofer between", value1, value2, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andNomchoferNotBetween(String value1, String value2) {
            addCriterion("nomChofer not between", value1, value2, "nomchofer");
            return (Criteria) this;
        }

        public Criteria andIdclienteIsNull() {
            addCriterion("idCliente is null");
            return (Criteria) this;
        }

        public Criteria andIdclienteIsNotNull() {
            addCriterion("idCliente is not null");
            return (Criteria) this;
        }

        public Criteria andIdclienteEqualTo(Integer value) {
            addCriterion("idCliente =", value, "idcliente");
            return (Criteria) this;
        }

        public Criteria andIdclienteNotEqualTo(Integer value) {
            addCriterion("idCliente <>", value, "idcliente");
            return (Criteria) this;
        }

        public Criteria andIdclienteGreaterThan(Integer value) {
            addCriterion("idCliente >", value, "idcliente");
            return (Criteria) this;
        }

        public Criteria andIdclienteGreaterThanOrEqualTo(Integer value) {
            addCriterion("idCliente >=", value, "idcliente");
            return (Criteria) this;
        }

        public Criteria andIdclienteLessThan(Integer value) {
            addCriterion("idCliente <", value, "idcliente");
            return (Criteria) this;
        }

        public Criteria andIdclienteLessThanOrEqualTo(Integer value) {
            addCriterion("idCliente <=", value, "idcliente");
            return (Criteria) this;
        }

        public Criteria andIdclienteIn(List<Integer> values) {
            addCriterion("idCliente in", values, "idcliente");
            return (Criteria) this;
        }

        public Criteria andIdclienteNotIn(List<Integer> values) {
            addCriterion("idCliente not in", values, "idcliente");
            return (Criteria) this;
        }

        public Criteria andIdclienteBetween(Integer value1, Integer value2) {
            addCriterion("idCliente between", value1, value2, "idcliente");
            return (Criteria) this;
        }

        public Criteria andIdclienteNotBetween(Integer value1, Integer value2) {
            addCriterion("idCliente not between", value1, value2, "idcliente");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table historico
     *
     * @mbggenerated do_not_delete_during_merge Sat May 06 20:17:22 CDT 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table historico
     *
     * @mbggenerated Sat May 06 20:17:22 CDT 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}