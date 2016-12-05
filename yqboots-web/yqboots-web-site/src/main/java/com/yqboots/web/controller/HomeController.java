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
package com.yqboots.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for basic functions.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/security/login", method = RequestMethod.GET)
    public String login() {
        return "security/login";
    }

    @RequestMapping(value = "/security/403", method = RequestMethod.GET)
    public String accessDenied() {
        return "security/403";
    }

    @PreAuthorize("hasPermission('/', 'com.yqboots.menu.core.MenuItem', 'READ')")
    @RequestMapping(value = {"", "/"})
    public String home() {
        return "index";
    }
}
