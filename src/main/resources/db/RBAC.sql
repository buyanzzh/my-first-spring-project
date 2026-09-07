-- 如需重跑，先清旧表（子表在前）
DROP TABLE IF EXISTS role_permission;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS permission;
DROP TABLE IF EXISTS role;

-- ① 角色表
CREATE TABLE role (
                      id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
                      role_key    VARCHAR(50) NOT NULL COMMENT '角色标识 ADMIN/USER',
                      name        VARCHAR(50) NOT NULL COMMENT '角色名 超级管理员/用户',
                      created_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      PRIMARY KEY (id),
                      UNIQUE KEY uk_role_key (role_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ② 权限表
CREATE TABLE permission (
                            id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
                            perm_key    VARCHAR(100) NOT NULL COMMENT '权限标识 tree:query/tree:delete',
                            name        VARCHAR(100) NOT NULL COMMENT '权限说明 查树/删树',
                            created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            PRIMARY KEY (id),
                            UNIQUE KEY uk_perm_key (perm_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ③ 用户-角色中间表
CREATE TABLE user_role (
                           id         BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
                           user_id    BIGINT   NOT NULL COMMENT '逻辑外键→user.id',
                           role_id    BIGINT   NOT NULL COMMENT '逻辑外键→role.id',
                           created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (id),
                           UNIQUE KEY uk_user_role (user_id, role_id),
                           KEY idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色中间表';

-- ④ 角色-权限中间表
CREATE TABLE role_permission (
                                 id            BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 role_id       BIGINT   NOT NULL COMMENT '逻辑外键→role.id',
                                 permission_id BIGINT   NOT NULL COMMENT '逻辑外键→permission.id',
                                 created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 PRIMARY KEY (id),
                                 UNIQUE KEY uk_role_permission (role_id, permission_id),
                                 KEY idx_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限中间表';


-- 插入数据
INSERT INTO role (role_key, name) VALUES
            ('ADMIN', '超级管理员'), ('USER', '普通用户');

INSERT INTO permission (perm_key, name) VALUES
            ('tree:query', '查树'), ('tree:add', '增树'),
            ('tree:delete', '删树'), ('user:manage', '用户管理');

DELETE FROM user_role;                            -- 推倒重来最干净（中间表就 2 行）
INSERT INTO user_role (user_id, role_id) VALUES
            (2, 1),   -- admin(id=2) → ADMIN(role_id=1)
            (1, 2);   -- test(id=1)  → USER(ro
INSERT INTO role_permission (role_id, permission_id) VALUES
            (1, 1), (1, 2), (1, 3), (1, 4),  -- ADMIN：全部权限
            (2, 1);                          -- USER：只读