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
package com.yqboots.menu.core.repository;

import com.yqboots.menu.core.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The repository interface for MenuItem
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, JpaSpecificationExecutor<MenuItem> {
    /**
     * Finds by name.
     *
     * @param name the name of the MenuItem
     * @return a MenuItem
     */
    MenuItem findByName(String name);

    /**
     * Finds by wildcard name, ignore its case and order by name.
     *
     * @param name     the name of the MenuItem
     * @param pageable pageable
     * @return paged list of MenuItem
     */
    Page<MenuItem> findByNameLikeIgnoreCaseOrderByName(String name, Pageable pageable);
}
