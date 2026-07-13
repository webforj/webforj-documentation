---
title: Custom data sources
sidebar_position: 4
description: >-
  Wire REST APIs, databases, or external services to webforJ components by
  supplying find, count, and key-lookup functions to DelegatingRepository.
_i18n_hash: 7d203b803816c64e9ca77d8b49bf34ed
---
<!-- vale off -->
# 自定义数据源 <DocChip chip='since' label='25.02' />
<!-- vale on -->

当您的数据存储在应用程序之外 - 在REST API、数据库或外部服务中 - 您需要创建自定义的存储库实现。<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> 类通过允许您提供函数而非实现一个完整的类，使这一过程变得简单。

## `DelegatingRepository` 的工作原理 {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> 是一个具体类，扩展了 <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>。您不需要实现抽象方法，而是在构造函数中提供三个函数：

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
  // 1. 查找函数 - 返回过滤/排序/分页数据
  criteria -> userService.findUsers(criteria),

  // 2. 计数函数 - 返回过滤器的总数
  criteria -> userService.countUsers(criteria),

  // 3. 通过键查找函数 - 通过ID返回单个实体
  userId -> userService.findById(userId)
);
```

每个函数都有特定的用途：

**查找函数**接收一个 `RepositoryCriteria` 对象，其中包含：
- `getFilter()` - 您的自定义过滤器对象（类型参数 `F`）
- `getOffset()` 和 `getLimit()` - 用于分页
- `getOrderCriteria()` - 排序规则的列表

该函数必须返回一个符合条件的实体流 `Stream<T>`。如果没有找到匹配项，流可以为空。

**计数函数**也接收条件，但通常只使用过滤部分。它返回匹配实体的总数，忽略分页。这用于UI组件显示总结果或计算页面。

**通过键查找函数**接收一个实体键（通常是ID）并返回一个 `Optional<T>`。如果实体不存在，返回 `Optional.empty()`。

## REST API 示例 {#rest-api-example}

在与REST API集成时，您需要将存储库条件转换为HTTP请求参数。首先，定义一个与您的API查询能力匹配的过滤器类：

```java
public class UserFilter {
  private String department;
  private String status;
  // getters and setters...
}
```

此过滤器类表示您的API接受的搜索参数。存储库将在应用过滤时将此类的实例传递给您的函数。

创建一个存储库，其中的函数将条件转换为API调用：

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

  // 通过ID查找
  userId -> restClient.getById("/users/" + userId)
);
```

`buildParams()` 方法将从条件中提取值并转换为查询参数，如 `?department=Sales&status=active&offset=20&limit=10`。您的REST客户端随后执行实际的HTTP请求并反序列化响应。

## 数据库示例 {#database-example}

数据库集成遵循类似的模式，但将条件转换为SQL查询。关键区别在于处理SQL生成和参数绑定：

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
  // 根据过滤器、排序、分页查询
  criteria -> {
    String sql = buildQuery(criteria);
    return jdbcTemplate.queryForStream(sql, rowMapper);
  },

  // 计数匹配的记录
  criteria -> {
    String countSql = buildCountQuery(criteria.getFilter());
    return jdbcTemplate.queryForObject(countSql, Integer.class);
  },

  // 通过主键查找
  customerId -> {
    String sql = "SELECT * FROM customers WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
  }
);
```

`buildQuery()` 方法将构建类似于以下的SQL：
```sql
SELECT * FROM customers
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

您的过滤器对象属性映射到 `WHERE` 子句条件，而分页和排序则通过 `LIMIT/OFFSET` 和 `ORDER BY` 子句处理。

## 与UI组件一起使用 {#using-with-ui-components}

存储库模式的优点在于UI组件无需关心数据的来源。无论是内存集合、REST API，还是数据库，使用都是相同的：

```java
// 创建并配置存储库
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// 附加到表格
Table<User> table = new Table<>();
table.setRepository(repository);

// 表格自动显示过滤后的工程师用户
```

当用户与 [`Table`](../../components/table/overview) 进行交互（排序列、改变页面）时，`Table` 会使用更新的条件调用存储库函数。您的函数将这些条件转换为API调用或SQL查询，表格会根据结果自动更新。

## 何时扩展 `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

如果您需要自定义方法或复杂初始化，直接扩展 <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>：

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
