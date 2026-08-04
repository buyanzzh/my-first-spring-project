-- 2026-08-04: 为 parent_id 增加单列索引，解决递归查子节点时的全表扫描问题
-- 原组合索引 (name, parent_id) 因最左前缀限制，无法覆盖 WHERE parent_id = ? 查询
CREATE INDEX idx_parent_id ON category(parent_id);