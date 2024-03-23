package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 包括文章信息和用户信息，用于文章管理界面
 * @TableName allbloguser
 */
@TableName(value ="allbloguser")
@Data
public class Allbloguser implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Long userId;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String images;

    /**
     *
     */
    private String content;

    /**
     *
     */
    private Integer likedNum;

    /**
     *
     */
    private Integer commentsNum;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Integer isban;

    /**
     *
     */
    private String userNickname;

    /**
     *
     */
    private Integer userStatus;


    private String userIcon;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}