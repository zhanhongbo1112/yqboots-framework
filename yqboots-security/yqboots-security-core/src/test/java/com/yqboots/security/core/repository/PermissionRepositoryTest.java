package com.yqboots.security.core.repository;

import com.yqboots.security.Application;
import com.yqboots.security.core.Permission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@Sql(scripts = "classpath:com/yqboots/security/core/00_init.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(scripts = "classpath:com/yqboots/security/core/01_destroy.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    public void testFindBySid() throws Exception {
        List<Permission> permissions = permissionRepository.findBySid("/USER/ADMIN");
        assertTrue(!permissions.isEmpty());
    }
}