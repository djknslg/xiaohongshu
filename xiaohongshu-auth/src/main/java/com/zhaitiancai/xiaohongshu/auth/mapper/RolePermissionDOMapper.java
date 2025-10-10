package com.zhaitiancai.xiaohongshu.auth.mapper;

import com.zhaitiancai.xiaohongshu.auth.domain.dataobject.RolePermissionDO;

public interface RolePermissionDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermissionDO record);

    int insertSelective(RolePermissionDO record);

    RolePermissionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermissionDO record);

    int updateByPrimaryKey(RolePermissionDO record);
}