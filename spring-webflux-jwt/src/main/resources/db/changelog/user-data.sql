INSERT INTO m_user(username, email, password) VALUES ('admin', 'admin@gmail.com', 'A2ppuJf5kJHeDAVOCipEpA==');

INSERT INTO m_role(name) VALUES ('ROLE_TEST1');
INSERT INTO m_role(name) VALUES ('ROLE_TEST2');
INSERT INTO m_role(name) VALUES ('ROLE_TEST3');

INSERT INTO m_user_role(user_id, role_id) 
VALUES (select id from m_user where username = 'admin', select id from m_role where name = 'ROLE_TEST1');

INSERT INTO m_user_role(user_id, role_id) 
VALUES (select id from m_user where username = 'admin', select id from m_role where name = 'ROLE_TEST2');

INSERT INTO m_user_role(user_id, role_id) 
VALUES (select id from m_user where username = 'admin', select id from m_role where name = 'ROLE_TEST3');