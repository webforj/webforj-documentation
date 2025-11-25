---
title: 自定义数据源
sidebar_position: 4
_i18n_hash: 44f087c7c2308fc7a0c3b8c4c4246531
---
<!-- vale off -->
# 自定义数据源 <DocChip chip='since' label='25.02' />
<!-- vale on -->

当您的数据位于应用程序外部 - 在 REST API、数据库或外部服务中 - 您需要创建自定义存储库实现。<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> 类通过允许您提供函数而不是实现完整类，使这一过程变得简单明了。

## `DelegatingRepository` 的工作原理 {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> 是一个具体类，它扩展了 <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>。您只需在构造函数中提供三个函数，而不是实现抽象方法：

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. 查找函数 - 返回过滤/排序/分页数据
    criteria -> userService.findUsers(criteria),
    
    // 2. 计数函数 - 返回过滤条件的总数
    criteria -> userService.countUsers(criteria),
    
    // 3. 按键查找函数 - 通过 ID 返回单个实体
    userId -> userService.findById(userId)
);
```

每个函数都有特定的目的：

**查找函数** 接收一个 `RepositoryCriteria` 对象，其中包含：
- `getFilter()` - 您的自定义过滤对象（`F` 类型参数）
- `getOffset()` 和 `getLimit()` - 用于分页
- `getOrderCriteria()` - 排序规则列表

此函数必须返回与条件匹配的 `Stream<T>` 实体。如果没有找到匹配项，流可以为空。

**计数函数** 也接收条件，但通常只使用过滤部分。它返回匹配实体的总数，忽略分页。这被 UI 组件用于显示总结果或计算页面数。

**按键查找函数** 接收一个实体键（通常是 ID）并返回 `Optional<T>`。如果实体不存在，则返回 `Optional.empty()`。

## REST API 示例 {#rest-api-example}

在与 REST API 集成时，您需要将存储库条件转换为 HTTP 请求参数。首先定义一个与您的 API 查询功能匹配的过滤类：

```java
public class UserFilter {
    private String department;
    private String status;
    // 获取器和设置器...
}
```

这个过滤类表示您的 API 接受的搜索参数。当应用过滤时，存储库将把这个类的实例传递给您的函数。

创建带有将条件转换为 API 调用的函数的存储库：

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

`buildParams()` 方法将从条件中提取值并将其转换为查询参数，例如 `?department=Sales&status=active&offset=20&limit=10`。您的 REST 客户端随后将实际进行 HTTP 请求并反序列化响应。

## 数据库示例 {#database-example}

数据库集成遵循类似的模式，但将条件转换为 SQL 查询。关键的区别在于 SQL 生成和参数绑定的处理：

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

`buildQuery()` 方法将构建类似于：
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

您的过滤对象属性映射到 `WHERE` 子句条件，而分页和排序则通过 `LIMIT/OFFSET` 和 `ORDER BY` 子句处理。

## 与 UI 组件一起使用 {#using-with-ui-components}

存储库模式的美在于 UI 组件不需要知道或关心数据的来源。无论是内存中的集合、REST API 还是数据库，使用方式都是相同的：

```java
// 创建并配置存储库
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// 附加到表格
Table<User> table = new Table<>();
table.setRepository(repository);

// 表格自动显示经过过滤的工程师用户
```

当用户与 [`Table`](../../components/table/overview) 交互（排序列、改变页面）时，`Table` 会调用您的存储库函数，并带上更新后的条件。您的函数将这些条件转换为 API 调用或 SQL 查询，表格会根据结果自动更新。

## 何时扩展 `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

如果您需要自定义方法或复杂初始化，请直接扩展 <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>：

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
