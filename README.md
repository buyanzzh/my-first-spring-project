# 零壹星球 · 智能分类导航站

> 一个支持多级分类管理 + AI辅助内容生成的后台服务。  
> 目标用户：内容创作者 / 电商运营 / 知识付费从业者。

---

## 项目定位

分类导航是内容平台、电商网站、知识库系统的底层基础设施。  
本项目提供了一套**多级分类树管理服务**，让用户可以自由搭建分类体系，并计划接入 AI 接口，为每个分类自动生成营销文案或推荐语，提升内容生产效率。

---

## 当前已完成

- ✅ 分类树 CRUD（Spring Boot + MyBatis-Plus + MySQL）
- ✅ `getTree()` 接入 Redis 缓存（`@Cacheable`），QPS 预估提升 10 倍以上
- ✅ `parent_id` 增加索引，Explain 分析走 `range` + `ref`，解决慢查询隐患
- ✅ Git 分支管理 + 冲突解决实战（仓库含 14 次提交记录）
- ✅ `application.yml` 多环境配置预留
- 🚧 登录鉴权（Spring Security + JWT）—— 进行中
- 🚧 AI 文案生成接口（DeepSeek）—— 计划中

---

## 技术栈

| 模块 | 技术 |
| :--- | :--- |
| 基础框架 | Spring Boot 3.x |
| ORM | MyBatis-Plus |
| 数据库 | MySQL 8.0 |
| 缓存 | Redis（`RedisTemplate` + `@Cacheable`） |
| 项目管理 | Maven |
| 版本控制 | Git（含分支合并与冲突解决） |

---

## 本地启动

1. 修改 `application.yml` 中的 MySQL 和 Redis 连接信息
2. 执行 `./mvnw spring-boot:run`
3. 访问 `http://localhost:8080/tree` 验证

---

## 主要接口

| 方法 | 路径 | 说明 |
| :--- | :--- | :--- |
| GET | `/tree` | 获取完整分类树（Redis 缓存） |
| GET | `/node/{id}` | 获取单个节点 |
| POST | `/category` | 新增分类节点 |
| PUT | `/category` | 修改分类节点 |
| DELETE | `/category/{id}` | 删除节点（含子节点处理） |

---

## 项目演进日志

- `feat: 初始项目骨架搭建`
- `docs: README 初始版本`
- `feat: 菜单树接口 + CRUD 完成`
- `test: Git 冲突模拟与解决实战`
- `chore: Spring Boot 2.x → 3.x 升级`
- `perf: parent_id 增加索引，Explain 分析通过`
- `perf: getTree 接入 Redis 缓存`
- `docs: 重写 README，明确业务定位`
