---
sidebar_position: 40
title: View Transitions
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: fb54ad2ee8205e9dbdc27165635fda55
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

视图转换在 [DOM](/docs/glossary#dom) 更改时提供动画过渡，减少视觉冲击，并在导航或内容更新期间维护空间上下文。 webforJ 与浏览器的 [视图转换 API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) 集成，以处理协调旧状态和新状态之间动画的复杂性。

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
]}
height='450px'
/>

<ExperimentalWarning />

## 基本用法 {#basic-usage}

要创建视图转换，请使用 `Page.getCurrent().startViewTransition()`，它返回一个构建器来配置转换：

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

转换过程捕获当前状态的快照，在 `onUpdate` 回调中应用您的 DOM 更改，然后从旧快照动画到新内容。您必须调用 `done.run()` 来信号您的更改已完成。

:::warning `onUpdate` 回调是必需的
不设置更新回调直接调用 `start()` 会抛出 `IllegalStateException`。
:::

## 应用转换 {#applying-transitions}

webforJ 提供了预定义的转换类型，您可以将其应用于进入或退出 DOM 的组件：

| 常量 | 效果 |
|------|------|
| `ViewTransition.NONE` | 无动画 |
| `ViewTransition.FADE` | 旧内容与新内容之间交叉淡入 |
| `ViewTransition.SLIDE_LEFT` | 内容向左流动（像向前导航） |
| `ViewTransition.SLIDE_RIGHT` | 内容向右流动（像返回导航） |
| `ViewTransition.SLIDE_UP` | 内容向上流动 |
| `ViewTransition.SLIDE_DOWN` | 内容向下流动 |
| `ViewTransition.ZOOM` | 旧内容缩小，新内容放大 |
| `ViewTransition.ZOOM_OUT` | 旧内容放大，新内容缩小 |

使用 `enter()` 来动画化正在添加的组件，使用 `exit()` 来动画化正在移除的组件：

```java
// 动画化一个进入 DOM 的组件
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// 动画化一个退出 DOM 的组件
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## 共享组件转换 {#shared-component-transitions}

共享组件转换创建一种变形效果，其中一个组件似乎从旧视图中的位置转变为新视图中的位置。 这通过使用 `setViewTransitionName()` 方法为组件赋予相同的转换名称来实现，该方法在实现 <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> 接口的任何组件上可用。

```java
// 在卡片视图中
image.setViewTransitionName("blog-image");

// 在详细视图中 - 相同的名称创建了变形
image.setViewTransitionName("blog-image");
```

在这些视图之间转换时，浏览器在位置之间动画化组件，创建一个连接的视觉体验。

:::tip 使用唯一名称
在处理列表或重复组件时，在转换名称中包含唯一标识符。每个组件需要自己的独特名称，以便正确地变形到新视图中的相应组件。对多个可见组件使用相同的名称会导致未定义行为。
:::

<ComponentDemo
path='/webforj/viewtransitionmorph'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java',
  'src/main/frontend/css/viewtransitions/morph.css',
]}
height='650px'
/>

### 列表重新排序 {#list-reordering}

共享组件转换的一个常见用例是当列表项的顺序改变时为列表项动画。 通过为每个项目分配唯一的 `view-transition-name`，浏览器会自动将组件动画到它们的新位置：

```java
// 每个卡片都根据其 ID 获取唯一的过渡名称
card.setViewTransitionName("card-" + item.id());

// 在洗牌时，只需更新 DOM - 浏览器处理动画
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    renderList();
    done.run();
  })
  .start();
```

<ComponentDemo
path='/webforj/viewtransitionshuffle'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionShuffleView.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java',
  'src/main/frontend/css/viewtransitions/shuffle.css',
]}
height='550px'
/>

## 自定义 CSS 动画 {#custom-css-animations}

要完全控制动画，可以定义自定义 CSS 关键帧。 webforJ 会将 `-enter` 或 `-exit` 后缀附加到您的转换名称，您可以使用它来定位视图转换伪元素：

```css
/* 定义组件进入时的关键帧 */
@keyframes flip-enter {
  from {
    opacity: 0;
    transform: perspective(1000px) rotateX(-90deg);
  }
  to {
    opacity: 1;
    transform: perspective(1000px) rotateX(0deg);
  }
}

/* 应用到视图转换伪元素 */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

通过将其名称（不带后缀）传递给 `enter()` 或 `exit()` 来引用您的自定义动画：

```java
// 使用 "flip-in" - webforJ 会自动添加 "-enter" 后缀
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// 使用 "blur-out" 进行退出 - webforJ 会自动添加 "-exit" 后缀
Page.getCurrent().startViewTransition()
  .exit(notification, "blur-out")
  .onUpdate(done -> {
    stage.remove(notification);
    done.run();
  })
  .start();
```

<ComponentDemo
path='/webforj/viewtransitionenterexit'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionEnterExitView.java',
  'src/main/frontend/css/viewtransitions/enterexit.css',
]}
height='400px'
/>

## CSS 自定义 {#css-customization}

每种预定义转换类型都公开了 CSS 自定义属性以进行微调：

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>淡入</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | 变量 | 默认值 | 描述 |
      |------|--------|------|
      | `--vt-fade-duration` | `200ms` | 动画持续时间 |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | 缓动函数 |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>向左滑动</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | 变量 | 默认值 | 描述 |
      |------|--------|------|
      | `--vt-slide-left-duration` | `200ms` | 动画持续时间 |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | 缓动函数 |
      | `--vt-slide-left-distance` | `30%` | 滑动距离 |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>向右滑动</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | 变量 | 默认值 | 描述 |
      |------|--------|------|
      | `--vt-slide-right-duration` | `200ms` | 动画持续时间 |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | 缓动函数 |
      | `--vt-slide-right-distance` | `30%` | 滑动距离 |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>向上滑动</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | 变量 | 默认值 | 描述 |
      |------|--------|------|
      | `--vt-slide-up-duration` | `200ms` | 动画持续时间 |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | 缓动函数 |
      | `--vt-slide-up-distance` | `30%` | 滑动距离 |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>向下滑动</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | 变量 | 默认值 | 描述 |
      |------|--------|------|
      | `--vt-slide-down-duration` | `200ms` | 动画持续时间 |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | 缓动函数 |
      | `--vt-slide-down-distance` | `30%` | 滑动距离 |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>缩放</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | 变量 | 默认值 | 描述 |
      |------|--------|------|
      | `--vt-zoom-duration` | `200ms` | 动画持续时间 |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | 缓动函数 |
      | `--vt-zoom-scale` | `0.8` | 缩放因子（旧内容缩小到此，新内容从此放大） |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>缩放出</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | 变量 | 默认值 | 描述 |
      |------|--------|------|
      | `--vt-zoom-out-duration` | `200ms` | 动画持续时间 |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | 缓动函数 |
      | `--vt-zoom-out-scale` | `1.2` | 缩放因子（旧内容放大到此，新内容缩小从此） |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>覆盖变量</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      要自定义，请在 CSS 中覆盖这些变量：

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      对于高级自定义，直接定位视图转换伪元素：

      ```css
      ::view-transition-old(vt-slide-left-exit) {
        animation-duration: 400ms;
      }

      ::view-transition-new(vt-slide-left-enter) {
        animation-timing-function: ease-out;
      }
      ```
    </div>
  </AccordionDetails>
</Accordion>
<br />
