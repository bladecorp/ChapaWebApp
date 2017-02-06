package com.sysdt.lock.dao;

import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.model.UnidadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UnidadMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int countByExample(UnidadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int deleteByExample(UnidadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int insert(Unidad record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int insertSelective(Unidad record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    List<Unidad> selectByExample(UnidadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    Unidad selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int updateByExampleSelective(@Param("record") Unidad record, @Param("example") UnidadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int updateByExample(@Param("record") Unidad record, @Param("example") UnidadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int updateByPrimaryKeySelective(Unidad record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unidad
     *
     * @mbggenerated Sat Feb 04 18:09:03 CST 2017
     */
    int updateByPrimaryKey(Unidad record);
}