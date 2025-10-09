---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
draft: true
_i18n_hash: 505eca822769ef1f30adc7acca089fac
---
本文件作为将 webforJ 应用程序从 25.00 版本升级到 26.00 版本的指南。以下是现有应用程序继续平稳运行所需的更改。如往常一样，请参阅 [GitHub 发布概述](https://github.com/webforj/webforj/releases) 以获取版本之间更改的更全面列表。

## `Repository` 的过滤器

在 webforJ 26.00 中，`RetrievalCriteria` 接口已被移除，包括 `setFilter()` 方法。请使用 `CollectionRepository` 进行简单过滤，或使用 [`QueryableRepository`](/docs/advanced/repository/querying-data) 进行更复杂的过滤类型、排序和分页。

### 之前：
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### 之后：
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```
