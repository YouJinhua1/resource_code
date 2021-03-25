package cn.yjh.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-18 20:23
 */
public interface UserMapper {

    @Select("select * from user")
    public List<Map<String,String>> query();
}
