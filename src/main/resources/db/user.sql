CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,          -- 存 BCrypt 哈希，约 60 字符
    role VARCHAR(20) NOT NULL DEFAULT 'USER' -- 角色：USER / ADMIN
    );
