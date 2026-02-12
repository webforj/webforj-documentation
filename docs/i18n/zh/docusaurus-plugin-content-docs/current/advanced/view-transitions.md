---
sidebar_position: 40
title: View Transitions
sidebar_class_name: new-content
_i18n_hash: eb57126d50375aa6da9197fa846291ff
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

视图过渡在 [DOM](/docs/glossary#dom) 发生变化时提供动画过渡，减少视觉干扰，并在导航或内容更新期间保持空间上下文。webforJ 与浏览器的 [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) 集成，以处理协调旧状态与新状态之间动画的复杂性。

<ComponentDemo
  path='/webforj/viewtransitionchat?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java'
  cssURL='/css/viewtransitions/chat.css'
  height='450px'
/>

:::warning 实验性 API
此 API 自 25.11 起标记为实验性，未来版本可能会发生变化。API 签名、行为和性能特征可能会进行修改。
:::

## 基本用法 {#basic-usage}

要创建视图过渡，请使用 `Page.getCurrent().startViewTransition()`，该方法返回一个构建器用于配置过渡：

```java
Page.getCurrent().startViewTransition()
    .onUpdate(done -> {
        container.remove(oldView);
        container.add(newView);
        done.run();
    })
    .start();
```

过渡过程会捕获当前状态的快照，在 `onUpdate` 回调中应用您的 DOM 更改，然后从旧快照动画过渡到新内容。您必须调用 `done.run()` 来信号更改何时完成。

:::warning `onUpdate` 回调是必需的
在未设置更新回调的情况下调用 `start()` 会抛出 `IllegalStateException`。
:::

## 应用过渡 {#applying-transitions}

webforJ 提供预定义的过渡类型，可以将其应用于进入或退出 DOM 的组件：

| 常量 | 效果 |
|----------|--------|
| `ViewTransition.NONE` | 无动画 |
| `ViewTransition.FADE` | 旧内容与新内容之间的交叉淡入 |
| `ViewTransition.SLIDE_LEFT` | 内容向左流动（如前进导航） |
| `ViewTransition.SLIDE_RIGHT` | 内容向右流动（如后退导航） |
| `ViewTransition.SLIDE_UP` | 内容向上流动 |
| `ViewTransition.SLIDE_DOWN` | 内容向下流动 |
| `ViewTransition.ZOOM` | 旧内容缩小，新内容放大 |
| `ViewTransition.ZOOM_OUT` | 旧内容放大，新内容缩小 |

使用 `enter()` 来动画添加组件，使用 `exit()` 来动画移除组件：

```java
// 动画一个进入 DOM 的组件
Page.getCurrent().startViewTransition()
    .enter(chatPanel, ViewTransition.ZOOM)
    .onUpdate(done -> {
        container.add(chatPanel);
        done.run();
    })
    .start();

// 动画一个退出 DOM 的组件
Page.getCurrent().startViewTransition()
    .exit(chatPanel, ViewTransition.FADE)
    .onUpdate(done -> {
        container.remove(chatPanel);
        done.run();
    })
    .start();
```

## 共享组件过渡 {#shared-component-transitions}

共享组件过渡创建一种变形效果，使组件看起来从旧视图的位置变换到新视图的位置。这是通过使用 `setViewTransitionName()` 方法将组件赋予相同的过渡名称来实现的，该方法可用于实现 <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> 接口的任何组件。

```java
// 在卡片视图中
image.setViewTransitionName("blog-image");

// 在详细视图中 - 相同的名称创建变形
image.setViewTransitionName("blog-image");
```

在这些视图之间过渡时，浏览器会在位置之间动画组件，创建连贯的视觉体验。

:::tip 使用唯一名称
在处理列表或重复组件时，在过渡名称中包含唯一标识符。每个组件都需要自己独特的名称，以便与新视图中的相应组件正确变形。对多个可见组件使用相同名称会导致未定义行为。
:::

<ComponentDemo
  path='/webforj/viewtransitionmorph?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java'
  urls={[
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java'
  ]}
  cssURL='/css/viewtransitions/morph.css'
  height='650px'
/>

### 列表重新排序 {#list-reordering}

共享组件过渡的一个常见用例是当列表项的顺序发生变化时进行动画处理。通过为每个项目分配一个唯一的 `view-transition-name`，浏览器会自动将组件动画到它们的新位置：

```java
// 每个卡片都根据其 ID 获取唯一的过渡名称
card.setViewTransitionName("card-" + item.id());

// 在打乱时，只需更新 DOM - 浏览器负责处理动画
Page.getCurrent().startViewTransition()
    .onUpdate(done -> {
        renderList();
        done.run();
    })
    .start();
```

<ComponentDemo
  path='/webforj/viewtransitionshuffle?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionShuffleView.java'
  urls={[
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java'
  ]}
  cssURL='/css/viewtransitions/shuffle.css'
  height='550px'
/>

## 自定义 CSS 动画 {#custom-css-animations}

为了完全控制动画，您可以定义自定义 CSS 关键帧。webforJ 会在您的过渡名称后附加 `-enter` 或 `-exit` 后缀，您可以使用它们来定位视图过渡伪元素：

```css
/* 定义进入组件的关键帧 */
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

/* 应用到视图过渡伪元素 */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

通过将动画名称（不带后缀）传递给 `enter()` 或 `exit()` 来引用您的自定义动画：

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
  path='/webforj/viewtransitionenterexit?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionEnterExitView.java'
  cssURL='/css/viewtransitions/enterexit.css'
  height='400px'
/>

## CSS 自定义 {#css-customization}

每种预定义的过渡类型都暴露了 CSS 自定义属性以进行微调：

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>淡入</strong>
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
      | `--vt-zoom-scale` | `0.8` | 缩放因子（旧内容缩放到此，新内容从此缩放入） |
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
      | `--vt-zoom-out-scale` | `1.2` | 缩放因子（旧内容缩放到此，新内容从此缩放出） |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>重写变量</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      要自定义，请在您的 CSS 中重写这些变量：

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      对于高级自定义，直接定位视图过渡伪元素：

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
