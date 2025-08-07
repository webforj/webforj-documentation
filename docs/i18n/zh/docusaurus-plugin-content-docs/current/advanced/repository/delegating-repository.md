---
title: Custom data sources
sidebar_position: 4
_i18n_hash: dbcaaa420987ee45f54d3f4059e98d92
---
<!-- vale off -->
# 自定义数据源 <DocChip chip='since' label='25.02' />
<!-- vale on -->

当您的数据存在于应用程序之外 - 在 REST API、数据库或外部服务中 - 您需要创建自定义存储库实现。<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> 类通过允许您提供功能而不是实现一个完整的类，使这一过程变得简单。

## `DelegatingRepository` 的工作原理 {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> 是一个具体类，它扩展了 <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>。您在构造函数中提供三个功能，而不是实现抽象方法：

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. 查找功能 - 返回过滤/排序/分页的数据
    criteria -> userService.findUsers(criteria),
    
    // 2. 计数功能 - 返回过滤条件的总数
    criteria -> userService.countUsers(criteria),
    
    // 3. 按键查找功能 - 根据 ID 返回单个实体
    userId -> userService.findById(userId)
);
```

每个功能都有特定的目的：

**查找功能**接收一个 `RepositoryCriteria` 对象，其中包含：
- `getFilter()` - 您的自定义过滤对象（`F` 类型参数）
- `getOffset()` 和 `getLimit()` - 用于分页
- `getOrderCriteria()` - 排序规则列表

此功能必须返回符合条件的实体的 `Stream<T>`。如果没有找到匹配项，则流可以为空。

**计数功能**也接收条件，但通常只使用过滤部分。它返回匹配实体的总数，忽略分页。这是UI组件用来显示总结果或计算页数的。

**按键查找功能**接收一个实体键（通常是 ID）并返回一个 `Optional<T>`。如果实体不存在，则返回 `Optional.empty()`。

## REST API 示例 {#rest-api-example}

在与 REST API 集成时，您需要将存储库条件转换为 HTTP 请求参数。首先定义一个过滤类，该类匹配您的 API 查询能力：

```java
public class UserFilter {
    private String department;
    private String status;
    // get 和 set 方法...
}
```

此过滤类表示您的 API 接受的搜索参数。当应用过滤时，存储库将把此类的实例传递给您的功能。

创建一个将条件翻译为 API 调用的存储库：

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
    // 查找用户
    criteria -> {
        Map<String, String> params = buildParams(criteria);
        List<User> users = restClient.get("/users", params);
        return users.stream();
    },
    
    // 计数用户
    criteria -> {
        Map<String, String> filterParams = buildFilterParams(criteria.getFilter());
        return restClient.getCount("/users/count", filterParams);
    },
    
    // 按 ID 查找
    userId -> restClient.getById("/users/" + userId)
);
```

`buildParams()` 方法将从条件中提取值并将其转换为类似 `?department=Sales&status=active&offset=20&limit=10` 的查询参数。您的 REST 客户端随后发出实际的 HTTP 请求并反序列化响应。

## 数据库示例 {#database-example}

数据库集成遵循类似模式，但将条件转换为 SQL 查询。关键的不同之处在于处理 SQL 生成和参数绑定：

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
    // 带过滤、排序、分页的查询
    criteria -> {
        String sql = buildQuery(criteria);
        return jdbcTemplate.queryForStream(sql, rowMapper);
    },
    
    // 计数匹配记录
    criteria -> {
        String countSql = buildCountQuery(criteria.getFilter());
        return jdbcTemplate.queryForObject(countSql, Integer.class);
    },
    
    // 按主键查找
    customerId -> {
        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
    }
);
```

`buildQuery()` 方法将构造类似于：
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

您的过滤对象属性映射到 `WHERE` 子句条件，而分页和排序则通过 `LIMIT/OFFSET` 和 `ORDER BY` 子句来处理。

## 与 UI 组件一起使用 {#using-with-ui-components}

存储库模式的优点在于 UI 组件并不知道数据来自何处，无论是内存中的集合、REST API，还是数据库，使用方式都是一样的：

```java
// 创建并配置存储库
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// 附加到表格
Table<User> table = new Table<>();
table.setRepository(repository);

// 表格自动显示过滤后的工程用户
```

当用户与 [`Table`](../../components/table/overview) 交互时（排序列，改变页面），`Table` 会调用您的存储库功能并传递更新后的条件。您的功能会将这些条件翻译为 API 调用或 SQL 查询，表格将自动更新结果。

## 何时扩展 `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

如果您需要自定义方法或复杂的初始化，直接扩展 <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>：

```java
public class CustomUserRepository extends AbstractQueryableRepository<User, UserFilter> {
    @Override
    public Stream<User> findBy(RepositoryCriteria<User, UserFilter> criteria) {
        // 实现
    }
    
    @Override
    public int size(RepositoryCriteria<User, UserFilter> criteria) {
        // 实现
    }
    
    @Override
    public Optional<User> find(Object key) {
        // 实现
    }
    
    // 添加自定义方法
    public List<User> findActiveManagers() {
        // 自定义查询逻辑
    }
}
```
