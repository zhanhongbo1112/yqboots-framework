CREATE TABLE acl_sid (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 100 ) NOT NULL PRIMARY KEY,
  principal BOOLEAN NOT NULL,
  sid VARCHAR_IGNORECASE(100) NOT NULL,
  CONSTRAINT UN_ACL_SID_PRINCIPAL UNIQUE (sid, principal)
);

CREATE TABLE acl_class (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 100 ) NOT NULL PRIMARY KEY,
  class VARCHAR_IGNORECASE(100) NOT NULL,
  CONSTRAINT UN_ACL_CLASS UNIQUE (class)
);

CREATE TABLE acl_object_identity (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 100 ) NOT NULL PRIMARY KEY,
  object_id_class BIGINT NOT NULL,
  object_id_identity BIGINT NOT NULL,
  parent_object BIGINT,
  owner_sid BIGINT,
  entries_inheriting BOOLEAN NOT NULL,
  CONSTRAINT UN_ACL_CLASS_ID UNIQUE (object_id_class, object_id_identity),
  CONSTRAINT FK_ACL_OBJ_ID FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
  CONSTRAINT FK_ACL_CLASS FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
  CONSTRAINT FK_ACL_SID FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
);

CREATE TABLE acl_entry (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 100 ) NOT NULL PRIMARY KEY,
  acl_object_identity BIGINT  NOT NULL,
  ace_order INT NOT NULL,
  sid BIGINT  NOT NULL,
  mask INTEGER NOT NULL,
  granting BOOLEAN NOT NULL,
  audit_success BOOLEAN NOT NULL,
  audit_failure BOOLEAN NOT NULL,
  CONSTRAINT UK_ACL_OI_ORDER UNIQUE (acl_object_identity, ace_order),
  CONSTRAINT FK_ACL_ENTRY_OI FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
  CONSTRAINT FK_ACL_ENTRY_SID FOREIGN KEY (sid) REFERENCES acl_sid (id)
);
