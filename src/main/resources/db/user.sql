-- 角色统一走 user_role → role 表（RBAC 归一化），user 表不再保留 role 列
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL           -- 存 BCrypt 哈希，约 60 字符
    );

-- 补 RBAC 演示种子：test(USER) / admin(ADMIN)，角色在 RBAC.sql 的 user_role 里绑定
INSERT INTO user (username, password) VALUES
('test',  '$2a$10$VbyxETY24WuL0qL8io04SO5.fZgmSLP1SpDbPeWNPHOHdNDKV7y42'),
('admin', '$2a$10$RfFUdqYTDUmJOBGL1PpgX.6wEjs02dXIeo1UL73UMyolB1mmKa6fy');

-- 已存在的库执行这一句删掉冗余列：
-- ALTER TABLE user DROP COLUMN role;
