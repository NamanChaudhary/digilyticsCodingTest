CREATE TABLE user
(
 userName varchar(100) NOT NULL,
  userId varchar(11) NOT NULL ,
 emailId varchar(100) DEFAULT NULL,
 roleId varchar(11),
 PRIMARY KEY (userId)
);

CREATE TABLE role
(
  roleName varchar(100) NOT NULL,
  roleId varchar(11) NOT NULL ,
  PRIMARY KEY (roleId)
);
insert into role(roleId, roleName) values('101','admin');
insert into user(userId, userName,roleId,emailId) values('1','Jack' '101','jack@gmail.com');