package com.mrsun.bootserver.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class MybatisPlusAuditMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createBy", String.class, Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName()).orElse("system"));
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateBy", String.class, Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName()).orElse("system"));
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }

}
