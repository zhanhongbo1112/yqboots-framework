/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.security.web.support;

import com.yqboots.security.core.Role;
import com.yqboots.security.core.RoleManager;
import com.yqboots.security.web.support.consumer.RoleToHtmlOptionConsumer;
import com.yqboots.web.thymeleaf.support.HtmlOption;
import com.yqboots.web.thymeleaf.support.AbstractHtmlOptionsResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves all {@link com.yqboots.security.core.Role}s
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class AllRolesHtmlOptionsResolver extends AbstractHtmlOptionsResolver {
    /**
     * name key: ALL_ROLES
     */
    private static final String NAME_KEY = "ALL_ROLES";

    /**
     * RoleManager
     */
    private final RoleManager roleManager;

    /**
     * Constructs {@link AllRolesHtmlOptionsResolver}.
     *
     * @param roleManager roleManager
     */
    @Autowired
    public AllRolesHtmlOptionsResolver(final RoleManager roleManager) {
        super(NAME_KEY);
        this.roleManager = roleManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HtmlOption> getHtmlOptions(final String name, final String... attributes) {
        final List<HtmlOption> results = new ArrayList<>();

        List<Role> roles = roleManager.findAllRoles();
        roles.forEach(new RoleToHtmlOptionConsumer(name, results));

        return results;
    }
}
