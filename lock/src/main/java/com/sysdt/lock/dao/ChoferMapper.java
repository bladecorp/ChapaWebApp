package com.sysdt.lock.dao;

import com.sysdt.lock.model.Chofer;
import com.sysdt.lock.model.ChoferExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChoferMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    int countByExample(ChoferExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    int deleteByExample(ChoferExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    int insert(Chofer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    int insertSelective(Chofer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    List<Chofer> selectByExample(ChoferExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    Chofer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    int updateByExampleSelective(@Param("record") Chofer record, @Param("example") ChoferExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    int updateByExample(@Param("record") Chofer record, @Param("example") ChoferExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    int updateByPrimaryKeySelective(Chofer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chofer
     *
     * @mbggenerated Mon May 01 19:47:25 CDT 2017
     */
    int updateByPrimaryKey(Chofer record);
}