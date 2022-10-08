package com.example.ojbot.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.03
 */
@Data
public class AllGroup {

    @TableId( type = IdType.AUTO)
    private Integer id;

    private Integer groupId;

    private Object groupMember;

    private Object groupProblem;

    private String groupRoot;

    private String groupQq;

}
