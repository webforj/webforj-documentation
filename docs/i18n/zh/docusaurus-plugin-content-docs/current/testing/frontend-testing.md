---
title: Frontend testing
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Write tests for the frontend sources of a webforJ app with the Bun test
  runner.
_i18n_hash: 31f4ee87c13ac75eb209408feef510fb
---
# 前端测试 <DocChip chip='since' label='26.01' /> {#frontend-testing}

当一个项目用 [frontend bundler](/docs/managing-resources/bundler/overview) 编写前端源时，它会用 [Bun](https://bun.sh/) 测试运行器进行测试。用 TypeScript 编写的组件或客户端逻辑的覆盖方式与后端相同。

## 测试位置 {#where-tests-live}

前端测试位于 `src/main/frontend` 下，和它们覆盖的源代码相邻。以 `*.test.ts` 或 `*.spec.ts` 命名的文件就是一个测试。

```ts title="src/main/frontend/math/math.ts"
export const add = (a: number, b: number): number => a + b;
```

```ts title="src/main/frontend/math/math.test.ts"
import { expect, test } from 'bun:test';
import { add } from './math';

test('add sums two numbers', () => {
  expect(add(2, 3)).toBe(5);
});
```

测试从 `bun:test` 导入其工具，这是运行器自己的 [测试 API](https://bun.sh/docs/cli/test)，包括 `expect`、`test` 和 `describe`。

## 运行测试 {#running-the-tests}

前端测试作为构建的一部分运行，因此 `mvn test` 会与 Java 测试一起运行它们，无需额外调用。有关构建中的测试步骤、何时跳过以及失败的测试如何阻止发布，请参阅 [构建和测试](/docs/managing-resources/bundler/build-and-tests#frontend-tests)。
