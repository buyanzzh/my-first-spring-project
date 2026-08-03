# My First Spring Project

> 一个用于练习 Spring Boot + MySQL + Redis 的菜单树管理项目，同时记录了 Git 操作实战。

## 技术栈
- Java 8 / 17
- Spring Boot 2.x
- MySQL
- Redis
- Maven

## 如何启动
1. 修改 `application.yml` 中的数据库和 Redis 连接信息。
2. 执行 `./mvnw spring-boot:run` 启动项目。

## 主要接口
| 方法 | 路径 | 说明 |
| :--- | :--- | :--- |
| GET | /tree | 获取完整的菜单树 |
| POST | /category | 新增节点 |
| PUT | /category | 修改节点 |
| DELETE | /category/{id} | 删除节点 |

## 备注
本项目主要用于 Java 后端实习备战，功能持续迭代中。
