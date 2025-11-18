---
title: Login
sidebar_position: 70
sidebar_class_name: updated-content
_i18n_hash: cdcad4b5ef5d3ba0bd84e4d9deac49b5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login` 组件通过提供一个现成的登录对话框，包含用户名和密码字段，简化了用户身份验证。它包括输入验证、可自定义标签和消息、密码可见性控制及对额外自定义字段的支持等功能。

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## 登录提交 {#login-submission}

当用户输入用户名和密码时，`Login` 组件将这些输入验证为必填字段。一旦验证通过，将触发表单提交事件，发送已输入的凭证。为了防止多次提交， [登录] 按钮会立即被禁用。

以下演示了一个基本的 `Login` 组件。如果用户名和密码都设置为 `"admin"`，登录对话框将关闭，并出现 [注销] 按钮。如果凭证不匹配，则显示默认错误消息。

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info 禁用 [登录] 按钮
默认情况下，一旦 `Login` 组件验证登录输入正确， [登录] 按钮会立即被禁用，以防止多次提交。您可以使用 `setEnabled(true)` 方法重新启用 [登录] 按钮。
:::

:::tip 允许空密码
您可以通过使用 `setEmptyPassword(true)` 方法允许用户仅使用用户名登录。
:::

## 表单动作 <DocChip chip='since' label='25.10' />{#form-action}

`Login` 组件可以直接将表单数据提交到指定的 URL，而不是通过提交事件处理提交。当设置了动作 URL 时，表单会执行标准的 POST 请求，用户名和密码作为表单参数。

```java
Login login = new Login();
login.setAction("/api/auth");
```

使用 `setAction()` 时，表单提交会绕过 `LoginSubmitEvent`，而是向指定端点执行传统的 HTTP POST 请求。用户名和密码分别作为名为 "username" 和 "password" 的表单参数发送。同时，包括带有名称属性的自定义字段也会包含在 POST 请求中。

:::tip 
如果未设置动作 URL，表单提交通过 `LoginSubmitEvent` 进行处理，允许您在服务器端以编程方式处理凭证。
:::

## 国际化 (i18n) {#internationalization-i18n}

`Login` 组件中的标题、描述、标签和消息都可以通过 `LoginI18n` 类进行完全自定义。这种灵活性使您能够根据特定的本地化要求或个性化偏好来调整登录界面。

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## 自定义字段 {#custom-fields}

`Login` 组件包括多个插槽，允许您根据需要添加额外字段。表单提交时会自动收集自定义字段，并可以通过提交事件的数据映射访问。

以下登录增添了一个客户 ID 的自定义字段。这可以帮助您管理跨多个用户共享内容的公司或部门。

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info 必须设置名称
自定义字段必须使用 `setName()` 设置名称，以便包含在表单提交中。该名称用作从 `event.getData()` 中检索字段值的键。
:::

## 取消按钮 {#cancel-button}

`Login` 包含一个默认隐藏的 [取消] 按钮。这在用户尝试访问受限区域时尤其有用，使用户能够在不完成登录的情况下返回到之前的位置。

要使取消按钮可见，请为其提供标签。您还可以监听取消事件，以便适当地处理取消操作。

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip 隐藏元素
要隐藏元素，请将其标签设置为空字符串。这允许您在不从代码中移除组件的情况下切换可见性。
:::

## 密码管理器 {#password-managers}

该组件与基于浏览器的密码管理器配合使用，以简化登录过程。在基于 Chromium 的浏览器中，它与 [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API 集成，提供：

- **自动填充**：如果用户已为该站点保存凭据，浏览器可能会自动填写用户名和密码字段。
- **凭证管理**：登录后，浏览器可以提示用户保存新凭证，使未来的登录更快捷、更便利。
- **凭证选择**：如果保存了多个凭证，浏览器可以提供选择，让用户从保存的集合中选择。

## 样式 {#styling}

<TableBuilder name="Login" />
