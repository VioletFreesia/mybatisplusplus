# MybatisPlus增强工具

> MybatisPlus在配置了全局逻辑删除之后, 所有的RUD接口都会自动添加逻辑删除字段限制
>
> 该增强工具用于在已经配置了全局逻辑删除之后实现非逻辑删除操作
>
> 该增强工具遵循MybatisPlus的思想, 只做增强, 不做改变

## 在SpringBoot中使用

1. 添加仓库

   **Gradle**

   ```groovy
   repositories {
       maven { url 'https://www.jitpack.io' }
       ...
   }
   ```

   **Maven**

   ```xml
   <repositories>
   	<repository>
   		<id>jitpack.io</id>
   		<url>https://www.jitpack.io</url>
   	</repository>
   </repositories>
   ```

   

2. 引入依赖

   > 最新版为: 3.4.2-release

   **Gradle**

   ```groovy
   dependencies {
       implementation 'com.violetfreesia:mybatisplusplus:latestVersion'
   }
   ```

   **Maven**

   ```xml
   <dependency>
   	<groupId>com.violetfreesia</groupId>
   	<artifactId>mybatisplusplus</artifactId>
   	<version>latestVersion</version>
   </dependency>
   ```

3. 配置注入器

   ```java
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import com.violetfreesia.mybatisplusplus.IllogicalMethodInjector;
   
   /**
    * @author violetfreesia
    */
   @Configuration
   public class MybatisPlusConfig {
       @Bean
       public AbstractSqlInjector illogicMethodInjector() {
           return new IllogicalMethodInjector();
       }
   }
   ```

4. 将原来继承`BaseMapper`的接口改为继承自`IllogicalBaseMapper`

   ```java
   //原来为 public interface UserMapper extends BaseMapper<User>
   public interface UserMapper extends IllogicalBaseMapper<User> {
   	...
   }
   ```

5. 将原来继承`IService`的接口改为继承自`IllogicalBaseService`

   ```java
   //原来为 public interface UserService extends IService<User>
   public interface UserService extends IllogicalBaseService<User> {
   	...
   }
   ```

6. 将原理继承`ServiceImpl`的Service实现类改为继承自`IllogicalBaseServiceImpl`

   ```java
   //原来为 public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService
   public class UserServiceImpl extends IllogicalBaseServiceImpl<UserMapper, User> implements UserService {
   	...
   }
   ```

7. 使用方法

   > 调用增强工具提供的方法, 只需要在方法名后添加`Illogical`即可, 使用方法与原MybatPlus提供的方法相同

8. 使用案例

   ```java
   /**
    * @author violetfreesia
    */
   @RestController
   public class UserController {
       private final UserService userService;
   
       public UserController(UserService userService) {
           this.userService = userService;
       }
   
       @GetMapping("/user/{userId}")
       public User getById(@PathVariable String userId) {
           // 这里调用的方法就是该增强工具提供的方法
           return userService.getByIdIllogical(userId);
       }
   }
   ```

   > 上面演示的是在全局配置了逻辑删除的情况下, 调用增强工具提供的查询方法, 该方法是可以查询到已经逻辑删除的数据的(未被删除的肯定也是可以的)

9. 增强工具提供的方法

   > 该增强工具提供的所有方法名都是在原方法名后添加`Illogical`
   >
   > Sevice CRUD
   >
   > 参见[MybatisPlus官网](https://mybatis.plus/guide/crud-interface.html)的`Remove`, `Update`, `Get`, `List`, `Page`, `Count` 的所有方法
   >
   > Mapper CRUD
   >
   > 参见[MybatisPlus官网](https://mybatis.plus/guide/crud-interface.html)的 `Delete`, `Update`, `Select` 部分的所有方法