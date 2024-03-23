package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TuserMapper extends BaseMapper<User> {
    List<User> TqueryById(@Param("id") long id);
}
