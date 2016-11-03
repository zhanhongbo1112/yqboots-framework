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

import com.yqboots.dict.core.support.AbstractDataDictResolver;
import com.yqboots.dict.core.DataDict;
import com.yqboots.security.core.RoleManager;
import com.yqboots.security.core.User;
import com.yqboots.security.web.support.consumer.UserToDataDictConsumer;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves {@link com.yqboots.security.core.User}s of a specified {@link com.yqboots.security.core.Role}<br/>
 * <p>for the implementation, the first attribute should be the specified path of the role.</p>
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Component
public class RoleUsersDataDictResolver extends AbstractDataDictResolver {
    /**
     * name key: ROLE_USERS
     */
    private static final String NAME_KEY = "ROLE_USERS";

    /**
     * RoleManager.
     */
    private final RoleManager roleManager;

    /**
     * Constructs <code>RoleUsersDataDictResolver</code>.
     *
     * @param roleManager roleManager
     */
    @Autowired
    public RoleUsersDataDictResolver(final RoleManager roleManager) {
        super(NAME_KEY);
        this.roleManager = roleManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> getDataDicts(String... attributes) {
        List<DataDict> results = new ArrayList<>();

        if (ArrayUtils.isNotEmpty(attributes) && attributes[0] != null) {
            // attributes[0] is path
            List<User> users = roleManager.findRoleUsers(attributes[0]);
            users.forEach(new UserToDataDictConsumer(getName(), results));
        }

        return results;
    }
}
