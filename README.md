# keycloak-springboot

---

> 演示如何在 Spring Boot 应用中集成 Keycloak


## 在线访问

### 公开的页面，不需要登录即可查看：
- https://tranquil-plains-58233.herokuapp.com/

### 受保护的页，需要经过 Keycloak 登录后才能查看：
- https://tranquil-plains-58233.herokuapp.com/visitor

## 本地开发

### Mac OSX:
```shell
git clone https://github.com/Jeff-Tian/keycloak-springboot
cd keycloak-springboot
mvn clean install
mvn spring-boot:run -f pom.xml

open http://localhost:8080
open http://localhost:8080/visitor
```

### Windows
```shell
git clone https://github.com/Jeff-Tian/keycloak-springboot
cd keycloak-springboot
mvn clean install
mvnw spring-boot:run -f pom.xml

start http://localhost:8080
start http://localhost:8080/visitor
```

## 环境变量

在自动化创建用户时，使用了 Keycloak Admin 接口，
要获取 Keycloak Admin 的访问令牌，就需要 Keycloak 
Admin 的用户名和密码。它们不能明文放在代码里，因此采用了
环境变量动态注入。这两个环境变量分别是：

- KC_ADMIN
- KC_PASSWORD

如果你使用自己的 Keycloak 实例，就需要把你自己的 Keycloak 
的 Admin 用户名和密码设置在环境变量里。

代码详见： `KeycloakHelper.getAdminAccessToken()`

## 视频讲解

- https://www.zhihu.com/zvideo/1486055107167514624