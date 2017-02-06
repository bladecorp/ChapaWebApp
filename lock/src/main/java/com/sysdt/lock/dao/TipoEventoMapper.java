package com.sysdt.lock.dao;

import com.sysdt.lock.model.TipoEvento;
import com.sysdt.lock.model.TipoEventoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TipoEventoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int countByExample(TipoEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int deleteByExample(TipoEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int insert(TipoEvento record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int insertSelective(TipoEvento record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    List<TipoEvento> selectByExample(TipoEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    TipoEvento selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int updateByExampleSelective(@Param("record") TipoEvento record, @Param("example") TipoEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int updateByExample(@Param("record") TipoEvento record, @Param("example") TipoEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int updateByPrimaryKeySelective(TipoEvento record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipo_evento
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int updateByPrimaryKey(TipoEvento record);
}