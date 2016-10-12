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
package com.yqboots.project.security.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The ConfigurationProperties for Security related functions.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@ConfigurationProperties(prefix = "yqboots.project.security")
public class SecurityProperties {
    private UserProperties user;

    public UserProperties getUser() {
        return user;
    }

    public void setUser(final UserProperties user) {
        this.user = user;
    }

    public static class UserProperties {
        private boolean disabledWhenRemoving = false;

        private String passwordDefault = "password";

        public boolean isDisabledWhenRemoving() {
            return disabledWhenRemoving;
        }

        public void setDisabledWhenRemoving(final boolean disabledWhenRemoving) {
            this.disabledWhenRemoving = disabledWhenRemoving;
        }

        public String getPasswordDefault() {
            return passwordDefault;
        }

        public void setPasswordDefault(final String passwordDefault) {
            this.passwordDefault = passwordDefault;
        }
    }
}
