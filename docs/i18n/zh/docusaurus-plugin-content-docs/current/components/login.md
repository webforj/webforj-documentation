---
title: Login
sidebar_position: 70
_i18n_hash: 59a9ab8cb7ba550b955ab83de0c6d878
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login` 组件通过提供一个现成的登录对话框，简化了用户身份验证，包含用户名和密码字段。它包括输入验证、可自定义标签和消息、密码可见性控制以及支持额外自定义字段的功能。

<!-- INTRO_END -->

## 创建一个 `Login` 对话框 {#creating-a-login-dialog}

通过实例化组件并调用 `open()` 来创建一个 `Login` 对话框以显示。对话框默认包含用户名和密码字段、输入验证以及一个登录按钮。

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## 登录提交 {#login-submission}

当用户输入用户名和密码时，`Login` 组件将这些输入验证为必填字段。一旦验证通过，将触发表单提交事件，传递输入的凭据。为了防止多次提交，[登录]按钮会立即被禁用。

以下演示了一个基本的 `Login` 组件。如果用户名和密码都设置为 `"admin"`，则登录对话框关闭，并出现一个 [注销] 按钮。如果凭据不匹配，则显示默认错误消息。

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info 禁用 [登录] 按钮
默认情况下，`Login` 在组件验证登录输入为正确后立即禁用 [登录] 按钮，以防止多次提交。您可以使用 `setEnabled(true)` 方法重新启用 [登录] 按钮。
:::

:::tip 允许空密码
您可以通过使用 `setEmptyPassword(true)` 方法允许用户仅使用用户名登录。
:::

## 表单动作 <DocChip chip='since' label='25.10' />{#form-action}

`Login` 组件可以直接将表单数据提交到指定的 URL，而不是通过提交事件处理提交。当设置一个动作 URL 时，表单将执行一个标准的 POST 请求，用户名和密码作为表单参数。

```java
Login login = new Login();
login.setAction("/api/auth");
```

使用 `setAction()` 时，表单提交将绕过 `LoginSubmitEvent`，而是对指定的端点执行传统的 HTTP POST 请求。用户名和密码分别作为名为 `"username"` 和 `"password"` 的表单参数发送。带有名称属性的自定义字段也会包含在 POST 请求中。

:::tip 
如果未设置动作 URL，表单提交将通过 `LoginSubmitEvent` 处理，允许您在服务器端对凭据进行编程处理。
:::

## 国际化 (i18n) {#internationalization-i18n}

`Login` 组件中的标题、描述、标签和消息可以使用 `LoginI18n` 类完全自定义。这种灵活性使您能够根据特定的本地化要求或个性化偏好来调整登录界面。

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '600px'
/>

## 自定义字段 {#custom-fields}

`Login` 组件包含几个插槽，允许您根据需要添加额外字段。自定义字段在表单提交时会自动收集，并可以通过提交事件的数据映射访问。

以下登录有一个用于客户 ID 的自定义字段。这可以帮助您管理共享内容的公司或部门。

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info 必须设置名称
自定义字段必须使用 `setName()` 设置名称，以包含在表单提交中。该名称用于作为从 `event.getData()` 检索字段值的键。
:::

## 取消按钮 {#cancel-button}

`Login` 包含一个默认隐藏的 [取消] 按钮。这在用户尝试访问应用的受限区域并需要返回到其先前位置而不完成登录时特别有用。

要使取消按钮可见，请为其提供一个标签。您还可以监听取消事件以适当地处理取消。

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip 隐藏元素
要隐藏一个元素，请将其标签设置为空字符串。这让您可以切换可见性，而无需从代码中移除组件。
:::

## 密码管理器 {#password-managers}

该组件与基于浏览器的密码管理器协同工作，以简化登录过程。在基于 Chromium 的浏览器中，它与 [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API 集成，提供：

- **自动填写**：如果用户为该站点保存了凭据，浏览器可能会自动填写用户名和密码字段。
- **凭据管理**：登录后，浏览器可以提示用户保存新凭据，从而使将来的登录更快、更轻松。
- **凭据选择**：如果保存了多个凭据，浏览器可以提供供用户从已保存的集合中进行选择的选项。

## 样式 {#styling}

<TableBuilder name="Login" />
