CREATE TABLE category (
                          id        BIGINT PRIMARY KEY,
                          name      VARCHAR(50),
                          parent_id BIGINT           -- 根节点的 parent_id = NULL（或 0）
);
