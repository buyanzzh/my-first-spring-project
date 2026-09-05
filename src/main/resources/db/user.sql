CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,          -- 存 BCrypt 哈希，约 60 字符
    role VARCHAR(20) NOT NULL DEFAULT 'USER' -- 角色：USER / ADMIN
    );

-- 补 RBAC 演示种子：test(USER) / admin(ADMIN)
INSERT INTO user (username, password, role) VALUES
('test',  '$2a$10$VbyxETY24WuL0qL8io04SO5.fZgmSLP1SpDbPeWNPHOHdNDKV7y42', 'USER'),
('admin', '$2a$10$RfFUdqYTDUmJOBGL1PpgX.6wEjs02dXIeo1UL73UMyolB1mmKa6fy', 'ADMIN');
