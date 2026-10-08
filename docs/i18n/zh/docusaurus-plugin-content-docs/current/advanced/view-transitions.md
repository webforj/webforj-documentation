---
sidebar_class_name: experimental-content
sidebar_position: 40
title: View Transitions
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: 3440413f572744ea709085a2975c0023
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

视图转换在[DOM](/docs/glossary#dom)更改时提供动画过渡，减少视觉冲击，并在导航或内容更新期间保持空间上下文。webforJ 与浏览器的[视图转换 API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API)集成，以处理协调旧状态和新状态之间动画的复杂性。

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/ChatCard.java',
  'src/main/frontend/css/viewtransitions/components/chat-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='450px'
/>

<ExperimentalWarning />

使用 `Page.getCurrent().startViewTransition()` 创建过渡，该方法返回用于配置过渡的构建器：

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

过渡过程捕获当前状态的快照，在 `onUpdate` 回调中应用您的 DOM 更改，然后从旧快照动画到新内容。必须调用 `done.run()` 来指示您的更改已完成。

:::warning `onUpdate` 回调是必需的
未设置更新回调直接调用 `start()` 将抛出 `IllegalStateException`。
:::

## 应用过渡 {#applying-transitions}

webforJ 提供了预定义的过渡类型，您可以将其应用于进入或退出 DOM 的组件：

| 常量 | 效果 |
|----------|--------|
| `ViewTransition.NONE` | 无动画 |
| `ViewTransition.FADE` | 旧内容与新内容之间的交叉淡化 |
| `ViewTransition.SLIDE_LEFT` | 内容向左流动（如向前导航） |
| `ViewTransition.SLIDE_RIGHT` | 内容向右流动（如返回导航） |
| `ViewTransition.SLIDE_UP` | 内容向上流动 |
| `ViewTransition.SLIDE_DOWN` | 内容向下流动 |
| `ViewTransition.ZOOM` | 旧内容缩小，新内容放大 |
| `ViewTransition.ZOOM_OUT` | 旧内容放大，新内容缩小 |

使用 `enter()` 动画一个组件的添加，使用 `exit()` 动画一个组件的移除：

```java
// 动画一个组件进入 DOM
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// 动画一个组件退出 DOM
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## 共享组件过渡 {#shared-component-transitions}

共享组件过渡创建了一种变形效果，组件似乎从旧视图中的位置转变为新视图中的位置。这是通过使用 `setViewTransitionName()` 方法赋予组件相同的过渡名称来实现的，该方法可用于实现 <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> 接口的任何组件。

```java
// 在卡片视图中
image.setViewTransitionName("blog-image");

// 在详细视图中 - 相同的名称创建变形
image.setViewTransitionName("blog-image");
```

在这些视图之间进行过渡时，浏览器在位置之间对组件进行动画处理，创造出连接的视觉体验。

:::tip 使用唯一名称
在处理列表或重复组件时，在过渡名称中包含唯一标识符。每个组件需要自身的独特名称，以便正确变形到新视图中的相应组件。对多个可见组件使用相同的名称会导致未定义行为。
:::

<ComponentDemo
path='/webforj/viewtransitionmorph'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java',
  'src/main/frontend/css/viewtransitions/morph.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java',
  'src/main/frontend/css/viewtransitions/components/blog-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='650px'
/>

### 列表重排序 {#list-reordering}

共享组件过渡的一个常见用例是当列表项的顺序发生变化时对其进行动画处理。通过为每个项分配唯一的 `view-transition-name`，浏览器会自动将组件动画到其新位置：

```java
// 每个卡片根据其 ID 获取唯一的过渡名称
card.setViewTransitionName("card-" + item.id());

// 当洗牌时，只需更新 DOM - 浏览器处理动画
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
  'src/main/frontend/css/viewtransitions/shuffle.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java',
  'src/main/frontend/css/viewtransitions/components/shuffle-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='550px'
/>

## 自定义 CSS 动画 {#custom-css-animations}

为了完全控制动画，您可以定义自定义 CSS 关键帧。webforJ 向您的过渡名称添加 `-enter` 或 `-exit` 后缀，您可以用来定位视图转换伪元素：

```css
/* 为进入组件定义关键帧 */
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

通过将其名称（不带后缀）传递给 `enter()` 或 `exit()` 来引用自定义动画：

```java
// 使用 "flip-in" - webforJ 自动添加 "-enter" 后缀
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// 使用 "blur-out" 进行退出 - webforJ 自动添加 "-exit" 后缀
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
  'src/main/java/com/webforj/samples/views/viewtransitions/components/NotificationCard.java',
  'src/main/frontend/css/viewtransitions/components/notification-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='400px'
/>

## CSS 自定义 {#css-customization}

每种预定义的过渡类型都提供了用于微调的 CSS 自定义属性：

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>淡化</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | 变量 | 默认值 | 描述 |
      |----------|---------|-------------|
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
      |----------|---------|-------------|
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
      |----------|---------|-------------|
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
      |----------|---------|-------------|
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
      |----------|---------|-------------|
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
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | 动画持续时间 |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | 缓动函数 |
      | `--vt-zoom-scale` | `0.8` | 缩放因子（旧的缩放到此，新则从此缩放入） |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>缩小</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | 变量 | 默认值 | 描述 |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | 动画持续时间 |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | 缓动函数 |
      | `--vt-zoom-out-scale` | `1.2` | 缩放因子（旧的缩放到此，新则从此缩放出） |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>重写变量</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      要自定义，请在 CSS 中重写这些变量：

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
