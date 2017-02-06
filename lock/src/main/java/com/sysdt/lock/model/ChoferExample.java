package com.sysdt.lock.model;

import java.util.ArrayList;
import java.util.List;

public class ChoferExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    public ChoferExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
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
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNombreIsNull() {
            addCriterion("nombre is null");
            return (Criteria) this;
        }

        public Criteria andNombreIsNotNull() {
            addCriterion("nombre is not null");
            return (Criteria) this;
        }

        public Criteria andNombreEqualTo(String value) {
            addCriterion("nombre =", value, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreNotEqualTo(String value) {
            addCriterion("nombre <>", value, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreGreaterThan(String value) {
            addCriterion("nombre >", value, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreGreaterThanOrEqualTo(String value) {
            addCriterion("nombre >=", value, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreLessThan(String value) {
            addCriterion("nombre <", value, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreLessThanOrEqualTo(String value) {
            addCriterion("nombre <=", value, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreLike(String value) {
            addCriterion("nombre like", value, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreNotLike(String value) {
            addCriterion("nombre not like", value, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreIn(List<String> values) {
            addCriterion("nombre in", values, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreNotIn(List<String> values) {
            addCriterion("nombre not in", values, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreBetween(String value1, String value2) {
            addCriterion("nombre between", value1, value2, "nombre");
            return (Criteria) this;
        }

        public Criteria andNombreNotBetween(String value1, String value2) {
            addCriterion("nombre not between", value1, value2, "nombre");
            return (Criteria) this;
        }

        public Criteria andApaternoIsNull() {
            addCriterion("apaterno is null");
            return (Criteria) this;
        }

        public Criteria andApaternoIsNotNull() {
            addCriterion("apaterno is not null");
            return (Criteria) this;
        }

        public Criteria andApaternoEqualTo(String value) {
            addCriterion("apaterno =", value, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoNotEqualTo(String value) {
            addCriterion("apaterno <>", value, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoGreaterThan(String value) {
            addCriterion("apaterno >", value, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoGreaterThanOrEqualTo(String value) {
            addCriterion("apaterno >=", value, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoLessThan(String value) {
            addCriterion("apaterno <", value, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoLessThanOrEqualTo(String value) {
            addCriterion("apaterno <=", value, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoLike(String value) {
            addCriterion("apaterno like", value, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoNotLike(String value) {
            addCriterion("apaterno not like", value, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoIn(List<String> values) {
            addCriterion("apaterno in", values, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoNotIn(List<String> values) {
            addCriterion("apaterno not in", values, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoBetween(String value1, String value2) {
            addCriterion("apaterno between", value1, value2, "apaterno");
            return (Criteria) this;
        }

        public Criteria andApaternoNotBetween(String value1, String value2) {
            addCriterion("apaterno not between", value1, value2, "apaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoIsNull() {
            addCriterion("amaterno is null");
            return (Criteria) this;
        }

        public Criteria andAmaternoIsNotNull() {
            addCriterion("amaterno is not null");
            return (Criteria) this;
        }

        public Criteria andAmaternoEqualTo(String value) {
            addCriterion("amaterno =", value, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoNotEqualTo(String value) {
            addCriterion("amaterno <>", value, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoGreaterThan(String value) {
            addCriterion("amaterno >", value, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoGreaterThanOrEqualTo(String value) {
            addCriterion("amaterno >=", value, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoLessThan(String value) {
            addCriterion("amaterno <", value, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoLessThanOrEqualTo(String value) {
            addCriterion("amaterno <=", value, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoLike(String value) {
            addCriterion("amaterno like", value, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoNotLike(String value) {
            addCriterion("amaterno not like", value, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoIn(List<String> values) {
            addCriterion("amaterno in", values, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoNotIn(List<String> values) {
            addCriterion("amaterno not in", values, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoBetween(String value1, String value2) {
            addCriterion("amaterno between", value1, value2, "amaterno");
            return (Criteria) this;
        }

        public Criteria andAmaternoNotBetween(String value1, String value2) {
            addCriterion("amaterno not between", value1, value2, "amaterno");
            return (Criteria) this;
        }

        public Criteria andTokenIsNull() {
            addCriterion("token is null");
            return (Criteria) this;
        }

        public Criteria andTokenIsNotNull() {
            addCriterion("token is not null");
            return (Criteria) this;
        }

        public Criteria andTokenEqualTo(String value) {
            addCriterion("token =", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotEqualTo(String value) {
            addCriterion("token <>", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThan(String value) {
            addCriterion("token >", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThanOrEqualTo(String value) {
            addCriterion("token >=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThan(String value) {
            addCriterion("token <", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThanOrEqualTo(String value) {
            addCriterion("token <=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLike(String value) {
            addCriterion("token like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotLike(String value) {
            addCriterion("token not like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenIn(List<String> values) {
            addCriterion("token in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotIn(List<String> values) {
            addCriterion("token not in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenBetween(String value1, String value2) {
            addCriterion("token between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotBetween(String value1, String value2) {
            addCriterion("token not between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("enabled is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("enabled is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(Boolean value) {
            addCriterion("enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Boolean value) {
            addCriterion("enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Boolean value) {
            addCriterion("enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Boolean value) {
            addCriterion("enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Boolean value) {
            addCriterion("enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Boolean> values) {
            addCriterion("enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Boolean> values) {
            addCriterion("enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled not between", value1, value2, "enabled");
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
     * This class corresponds to the database table chofer
     *
     * @mbggenerated do_not_delete_during_merge Sat Feb 04 19:40:18 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table chofer
     *
     * @mbggenerated Sat Feb 04 19:40:18 CST 2017
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