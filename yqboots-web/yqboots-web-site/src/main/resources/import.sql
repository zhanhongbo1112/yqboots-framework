CREATE TABLE acl_sid (id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 100) NOT NULL PRIMARY KEY, principal BOOLEAN NOT NULL, sid VARCHAR_IGNORECASE(100) NOT NULL, CONSTRAINT UN_ACL_SID_PRINCIPAL UNIQUE (sid, principal));

CREATE TABLE acl_class (id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 100) NOT NULL PRIMARY KEY, class VARCHAR_IGNORECASE(100) NOT NULL, CONSTRAINT UN_ACL_CLASS UNIQUE (class));

CREATE TABLE acl_object_identity (id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 100) NOT NULL PRIMARY KEY, object_id_class BIGINT NOT NULL, object_id_identity BIGINT NOT NULL, parent_object BIGINT, owner_sid BIGINT, entries_inheriting BOOLEAN NOT NULL, CONSTRAINT UN_ACL_CLASS_ID UNIQUE (object_id_class, object_id_identity), CONSTRAINT FK_ACL_OBJ_ID FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id), CONSTRAINT FK_ACL_CLASS FOREIGN KEY (object_id_class) REFERENCES acl_class (id), CONSTRAINT FK_ACL_SID FOREIGN KEY (owner_sid) REFERENCES acl_sid (id));

CREATE TABLE acl_entry (id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 100) NOT NULL PRIMARY KEY, acl_object_identity BIGINT  NOT NULL, ace_order INT NOT NULL, sid BIGINT  NOT NULL, mask INTEGER NOT NULL, granting BOOLEAN NOT NULL, audit_success BOOLEAN NOT NULL, audit_failure BOOLEAN NOT NULL, CONSTRAINT UK_ACL_OI_ORDER UNIQUE (acl_object_identity, ace_order), CONSTRAINT FK_ACL_ENTRY_OI FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id), CONSTRAINT FK_ACL_ENTRY_SID FOREIGN KEY (sid) REFERENCES acl_sid (id));

INSERT INTO SEC_USER (ID, USERNAME, PASSWORD, ENABLED) VALUES (1, 'admin', 'password', true);
INSERT INTO SEC_USER (ID, USERNAME, PASSWORD, ENABLED) VALUES (2, 'user', 'password', true);

INSERT INTO SEC_GROUP (ID, PATH, ALIAS, DESCRIPTION) VALUES (1, 'USERS', 'USERS', 'USERS');
INSERT INTO SEC_GROUP (ID, PATH, ALIAS, DESCRIPTION) VALUES (2, 'ADMINS', 'ADMINS', 'ADMINS');

INSERT INTO SEC_ROLE (ID, PATH, ALIAS, DESCRIPTION) VALUES (1, '/USER', 'USER', 'USER');
INSERT INTO SEC_ROLE (ID, PATH, ALIAS, DESCRIPTION) VALUES (2, '/USER/ADMIN', 'ADMIN', 'ADMIN');

INSERT INTO SEC_USER_GROUPS (USER_ID, GROUP_ID) VALUES (1, 2);
INSERT INTO SEC_USER_GROUPS (USER_ID, GROUP_ID) VALUES (2, 1);

INSERT INTO SEC_GROUP_ROLES (GROUP_ID, ROLE_ID) VALUES (2, 2);
INSERT INTO SEC_GROUP_ROLES (GROUP_ID, ROLE_ID) VALUES (1, 1);

INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('DATA_DICT', '/dict', 'ADMINISTRATION', 'ENVIRONMENT');
INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('MENU_ITEM', '/menu', 'ADMINISTRATION', 'ENVIRONMENT');
INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('FSS', '/fss', 'ADMINISTRATION', 'ENVIRONMENT');
INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('SECURITY_USER', '/security/user', 'ADMINISTRATION', 'SECURITY');
INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('SECURITY_GROUP', '/security/group', 'ADMINISTRATION', 'SECURITY');
INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('SECURITY_ROLE', '/security/role', 'ADMINISTRATION', 'SECURITY');
INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('SECURITY_PERMISSION', '/security/permission', 'ADMINISTRATION', 'SECURITY');
INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('SECURITY_AUDIT', '/security/audit', 'ADMINISTRATION', 'SECURITY');
INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('SECURITY_LOGIN_HISTORY', '/security/history', 'ADMINISTRATION', 'SECURITY');
INSERT INTO PRJ_MENUITEM (NAME, URL, MENU_GROUP, MENU_ITEM_GROUP) VALUES ('SECURITY_SESSION', '/security/session', 'ADMINISTRATION', 'SECURITY');

insert into acl_sid (id, principal, sid) values (100, FALSE, '/USER');
insert into acl_sid (id, principal, sid) values (101, FALSE, '/USER/ADMIN');
insert into acl_class (id, class) values (100, 'com.yqboots.menu.core.MenuItem');
-- 47 (hash code of '/')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (1, 100, 47, null, 100, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (1, 1, 1, 100, 1, TRUE, FALSE, FALSE);
-- 46753294 (hash code of '/menu')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (100, 100, 46753294, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (100, 100, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (101, 100, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (102, 100, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (103, 100, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (104, 100, 5, 101, 16, TRUE, FALSE, FALSE);
-- 46488677 (hash code of '/dict')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (101, 100, 46488677, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (105, 101, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (106, 101, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (107, 101, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (108, 101, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (109, 101, 5, 101, 16, TRUE, FALSE, FALSE);
-- 1501879 (hash code of '/fss')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (102, 100, 1501879, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (110, 102, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (111, 102, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (112, 102, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (113, 102, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (114, 102, 5, 101, 16, TRUE, FALSE, FALSE);
-- 1260081515 (hash code of '/security/user')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (103, 100, 1260081515, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (115, 103, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (116, 103, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (117, 103, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (118, 103, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (119, 103, 5, 101, 16, TRUE, FALSE, FALSE);
-- 394872031 (hash code of '/security/group')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (104, 100, 394872031, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (120, 104, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (121, 104, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (122, 104, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (123, 104, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (124, 104, 5, 101, 16, TRUE, FALSE, FALSE);
-- 1259988502 (hash code of '/security/role')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (105, 100, 1259988502, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (125, 105, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (126, 105, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (127, 105, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (128, 105, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (129, 105, 5, 101, 16, TRUE, FALSE, FALSE);
-- 1448047471 (hash code of '/security/permission')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (106, 100, 1448047471, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (130, 106, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (131, 106, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (132, 106, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (133, 106, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (134, 106, 5, 101, 16, TRUE, FALSE, FALSE);
-- 389409339 (hash code of '/security/audit')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (107, 100, 389409339, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (135, 107, 1, 101, 1, TRUE, FALSE, FALSE);
-- -2146559244 (hash code of '/security/history')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (108, 100, -2146559244, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (136, 108, 1, 101, 1, TRUE, FALSE, FALSE);
-- -1088505610 (hash code of '/security/session')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (109, 100, -1088505610, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (137, 109, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (138, 109, 2, 101, 8, TRUE, FALSE, FALSE);

-- insert record from the view for in-memory test
insert into sec_permissions (security_identity, object_id_identity, object_id_class, mask) select sid.sid, aoi.object_id_identity, ac.class as object_id_class, ae.mask from acl_sid as sid, acl_class ac, acl_object_identity aoi left outer join acl_entry ae on ae.acl_object_identity = aoi.id and ae.sid = sid.id where sid.id = aoi.owner_sid and ac.id = aoi.object_id_class order by sid.sid, aoi.object_id_identity, ac.class, ae.ace_order;

insert into PRJ_DATA_DICT (name, text, value, description) values ('FSS_AVAILABLE_DIRS', '/dict', '/dict', '');
insert into PRJ_DATA_DICT (name, text, value, description) values ('FSS_AVAILABLE_DIRS', '/menu', '/menu', '');

insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/', '47', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/menu', '46753294', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/dict', '46488677', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/fss', '1501879', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/security/user', '1260081515', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/security/group', '394872031', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/security/role', '1259988502', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/security/permission', '1448047471', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/security/audit', '389409339', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/security/history', '-2146559244', 'For Object Id Identity in Permission');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_OBJECT_ID_IDENTITY', '/security/session', '-1088505610', 'For Object Id Identity in Permission');

insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_MASK', 'read', '1', 'For Permission Mask 1');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_MASK', 'write', '2', 'For Permission Mask 2');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_MASK', 'create', '4', 'For Permission Mask 4');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_MASK', 'delete', '8', 'For Permission Mask 8');
insert into PRJ_DATA_DICT (name, text, value, description) values ('PERMISSION_MASK', 'administration', '16', 'For Permission Mask 16');

insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'ADD USER', '1', 'CODE_ADD_USER');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'UPDATE USER', '2', 'CODE_UPDATE_USER');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'UPDATE GROUPS OF USER', '20', 'CODE_UPDATE_GROUPS_OF_USER');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'UPDATE ROLES OF USER', '21', 'CODE_UPDATE_ROLES_OF_USER');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'REMOVE USER', '3', 'CODE_REMOVE_USER');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'REMOVE GROUPS FROM USER', '30', 'CODE_REMOVE_GROUPS_FROM_USER');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'REMOVE ROLES FROM USER', '31', 'CODE_REMOVE_ROLES_FROM_USER');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'ADD GROUP', '4', 'CODE_ADD_GROUP');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'UPDATE GROUP', '5', 'CODE_UPDATE_GROUP');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'UPDATE USERS OF GROUP', '50', 'CODE_UPDATE_USERS_OF_GROUP');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'UPDATE ROLES OF GROUP', '51', 'CODE_UPDATE_ROLES_OF_GROUP');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'REMOVE GROUP', '6', 'CODE_REMOVE_GROUP');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'REMOVE USERS FROM GROUP', '60', 'CODE_REMOVE_USERS_FROM_GROUP');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'REMOVE ROLES FROM GROUP', '61', 'CODE_REMOVE_ROLES_FROM_GROUP');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'ADD ROLE', '7', 'CODE_ADD_ROLE');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'UPDATE ROLE', '8', 'CODE_UPDATE_ROLE');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'UPDATE USERS OF ROLE', '80', 'CODE_UPDATE_USERS_OF_ROLE');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'UPDATE GROUPS OF ROLE', '81', 'CODE_UPDATE_GROUPS_OF_ROLE');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'REMOVE ROLE', '9', 'CODE_REMOVE_ROLE');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'REMOVE USERS FROM ROLE', '90', 'CODE_REMOVE_USERS_FROM_ROLE');
insert into PRJ_DATA_DICT (name, text, value, description) values ('SECURITY_AUDIT_CODES', 'REMOVE GROUPS FROM ROLE', '91', 'CODE_REMOVE_GROUPS_FROM_ROLE');
