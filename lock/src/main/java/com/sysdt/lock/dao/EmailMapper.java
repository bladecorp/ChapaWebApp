package com.sysdt.lock.dao;

import com.sysdt.lock.model.Email;
import com.sysdt.lock.model.EmailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    int countByExample(EmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    int deleteByExample(EmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    int insert(Email record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    int insertSelective(Email record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    List<Email> selectByExample(EmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    Email selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    int updateByExampleSelective(@Param("record") Email record, @Param("example") EmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    int updateByExample(@Param("record") Email record, @Param("example") EmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    int updateByPrimaryKeySelective(Email record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email
     *
     * @mbggenerated Fri Apr 14 21:13:50 CDT 2017
     */
    int updateByPrimaryKey(Email record);
}