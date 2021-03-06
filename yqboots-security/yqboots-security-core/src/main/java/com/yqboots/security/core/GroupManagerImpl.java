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
package com.yqboots.security.core;

import com.yqboots.core.util.DBUtils;
import com.yqboots.security.core.audit.SecurityAudit;
import com.yqboots.security.core.audit.annotation.Auditable;
import com.yqboots.security.core.repository.GroupRepository;
import com.yqboots.security.core.repository.RoleRepository;
import com.yqboots.security.core.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Group manager implementation.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Service
@Transactional(readOnly = true)
public class GroupManagerImpl implements GroupManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_GROUP)
    public void addGroup(final Group group) throws GroupExistsException {
        Assert.isTrue(group.isNew());
        Assert.hasText(group.getPath());

        if (groupRepository.exists(group.getPath())) {
            throw new GroupExistsException(group.getPath());
        }

        groupRepository.save(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_GROUP)
    public void updateGroup(final Group group) throws GroupNotFoundException {
        Assert.isTrue(!group.isNew());
        Assert.hasText(group.getPath());

        final Group entity = groupRepository.findByPath(group.getPath());
        if (entity == null) {
            throw new GroupNotFoundException(group.getPath());
        }

        entity.setAlias(group.getAlias());
        entity.setDescription(group.getDescription());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_USERS_OF_GROUP)
    public void updateUsers(final String path, final Long... userIds) throws GroupNotFoundException {
        Assert.hasText(path);

        final Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final Set<User> groupUsers = group.getUsers();
        if (CollectionUtils.isNotEmpty(groupUsers)) {
            groupUsers.stream().forEach(user -> user.getGroups().remove(group));
        }

        if (ArrayUtils.isNotEmpty(userIds)) {
            final List<User> users = userRepository.findAll(Arrays.asList(userIds));
            users.stream().forEach(user -> user.getGroups().add(group));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_USERS_OF_GROUP)
    public void updateUsers(final String path, final String... usernames) throws GroupNotFoundException {
        Assert.hasText(path);

        final Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final Set<User> groupUsers = group.getUsers();
        if (CollectionUtils.isNotEmpty(groupUsers)) {
            groupUsers.stream().forEach(user -> user.getGroups().remove(group));
        }

        if (ArrayUtils.isNotEmpty(usernames)) {
            final List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
            users.stream().forEach(user -> user.getGroups().add(group));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_ROLES_OF_GROUP)
    public void updateRoles(final String path, final Long... roleIds) throws GroupNotFoundException {
        Assert.hasText(path);

        final Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final Set<Role> groupRoles = group.getRoles();
        if (CollectionUtils.isNotEmpty(groupRoles)) {
            groupRoles.clear();
        }

        if (ArrayUtils.isNotEmpty(roleIds)) {
            final List<Role> roles = roleRepository.findAll(Arrays.asList(roleIds));
            if (!roles.isEmpty()) {
                group.setRoles(new HashSet<>(roles));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_ROLES_OF_GROUP)
    public void updateRoles(final String path, final String... rolePaths) throws GroupNotFoundException {
        Assert.hasText(path);

        final Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final Set<Role> groupRoles = group.getRoles();
        if (CollectionUtils.isNotEmpty(groupRoles)) {
            groupRoles.clear();
        }

        if (ArrayUtils.isNotEmpty(rolePaths)) {
            final List<Role> roles = roleRepository.findByPathIn(Arrays.asList(rolePaths));
            if (!roles.isEmpty()) {
                group.setRoles(new HashSet<>(roles));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_GROUP)
    public void removeGroup(final Long id) throws GroupNotFoundException {
        Assert.notNull(id);

        final Group group = groupRepository.findOne(id);
        if (group == null) {
            throw new GroupNotFoundException(Long.toString(id));
        }

        final List<User> users = userRepository.findByGroupsPath(group.getPath());
        users.stream().forEach(user -> user.getGroups().remove(group));

        groupRepository.delete(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_GROUP)
    public void removeGroup(final String path) throws GroupNotFoundException {
        Assert.hasText(path);

        final Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final List<User> users = userRepository.findByGroupsPath(group.getPath());
        users.stream().forEach(user -> user.getGroups().remove(group));

        groupRepository.delete(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_USERS_FROM_GROUP)
    public void removeUsers(final String path, final Long... userIds) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(userIds);

        final Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final List<User> users = userRepository.findByGroupsPath(group.getPath());
        users.stream().filter(user -> ArrayUtils.contains(userIds, user.getId()))
                .forEach(user -> user.getGroups().remove(group));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_USERS_FROM_GROUP)
    public void removeUsers(final String path, final String... usernames) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        final Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final List<User> users = userRepository.findByGroupsPath(group.getPath());
        users.stream().filter(user -> ArrayUtils.contains(usernames, user.getUsername()))
                .forEach(user -> user.getGroups().remove(group));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_ROLES_FROM_GROUP)
    public void removeRoles(final String path, final Long... roleIds) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(roleIds);

        final Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final List<Role> roles = roleRepository.findByGroupsPath(path);
        roles.stream().filter(role -> ArrayUtils.contains(roleIds, role.getId()))
                .forEach(role -> group.getRoles().remove(role));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_ROLES_FROM_GROUP)
    public void removeRoles(final String path, final String... rolePaths) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(rolePaths);

        final Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final List<Role> roles = roleRepository.findByGroupsPath(path);
        roles.stream().filter(role -> ArrayUtils.contains(rolePaths, role.getPath()))
                .forEach(role -> group.getRoles().remove(role));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGroup(final String path) {
        return groupRepository.exists(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group findGroup(final Long id) throws GroupNotFoundException {
        Assert.notNull(id);

        Group result = groupRepository.findOne(id);
        if (result == null) {
            throw new GroupNotFoundException(Long.toString(id));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group findGroup(final String path) throws GroupNotFoundException {
        Assert.hasText(path);

        Group result = groupRepository.findByPath(path);
        if (result == null) {
            throw new GroupNotFoundException(path);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Group> findGroups(final String pathFilter, final Pageable pageable) {
        return groupRepository.findByPathLikeIgnoreCase(DBUtils.wildcard(pathFilter), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Group> findGroups(final Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findGroupUsers(final String path) {
        return userRepository.findByGroupsPath(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findGroupRoles(final String path) {
        return roleRepository.findByGroupsPath(path);
    }
}
