INSERT INTO m_user(username, email, password) VALUES ('admin', 'admin@gmail.com', 'A2ppuJf5kJHeDAVOCipEpA==');
INSERT INTO m_user(username, email, password) VALUES ('user', 'user@gmail.com', 'A2ppuJf5kJHeDAVOCipEpA==');

INSERT INTO m_role(name) VALUES ('USER');
INSERT INTO m_role(name) VALUES ('USER_MANAGEMENT_READ');
INSERT INTO m_role(name) VALUES ('USER_MANAGEMENT_WRITE');

INSERT INTO m_user_role(user_id, role_id) 
VALUES 
(select id from m_user where username = 'admin', select id from m_role where name = 'USER_MANAGEMENT_READ'),
(select id from m_user where username = 'admin', select id from m_role where name = 'USER_MANAGEMENT_WRITE');

INSERT INTO m_user_role(user_id, role_id) 
VALUES 
(select id from m_user where username = 'user', select id from m_role where name = 'USER');