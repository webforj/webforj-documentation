---
title: Login
sidebar_position: 70
_i18n_hash: e0aded01aee7ef12465b2d7661cc0477
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login`组件通过提供一个现成的对话框，简化了用户身份验证，包含用户名和密码字段。它包括输入验证、可自定义标签和消息、密码可见性控制以及对其他自定义字段的支持。

<!-- INTRO_END -->

## 创建 `Login` 对话框 {#creating-a-login-dialog}

通过实例化组件并调用 `open()` 方法来创建 `Login` 对话框以显示它。该对话框默认包含用户名和密码字段、输入验证和一个登录按钮。

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## 登录提交 {#login-submission}

当用户输入用户名和密码时，`Login`组件会验证这些输入为必填项。验证通过后，会触发表单提交事件，传递输入的凭据。为了防止多次提交，[登录]按钮立即被禁用。

以下演示了一个基本的 `Login` 组件。如果用户名和密码都设置为 `"admin"`，则登录对话框会关闭，并出现 [注销] 按钮。如果凭据不匹配，则会显示默认错误信息。

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info 禁用 [登录] 按钮
默认情况下，`Login` 在组件验证登录输入为正确后立即禁用 [登录] 按钮，以防止多次提交。您可以使用 `setEnabled(true)` 方法重新启用 [登录] 按钮。
:::

:::tip 允许空密码
您可以使用 `setEmptyPassword(true)` 方法允许用户仅用用户名登录。
:::

## 表单操作 <DocChip chip='since' label='25.10' />{#form-action}

`Login`组件可以直接将表单数据提交到指定的 URL，而不是通过提交事件处理提交。当设置操作 URL 时，表单会进行标准的 POST 请求，用户名和密码作为表单参数。

```java
Login login = new Login();
login.setAction("/api/auth");
```

使用 `setAction()` 时，表单提交会绕过 `LoginSubmitEvent`，而是执行对指定端点的传统 HTTP POST 请求。用户名和密码分别作为名称为 "username" 和 "password" 的表单参数发送。带有名称属性的自定义字段也包含在 POST 请求中。

:::tip 
如果未设置操作 URL，表单提交将通过 `LoginSubmitEvent` 处理，从而允许您在服务器端以编程方式处理凭据。
:::

## 国际化 (i18n) {#internationalization-i18n}

`Login`组件内的标题、描述、标签和消息可以通过 `LoginI18n` 类进行完全自定义。这种灵活性使您能够根据特定的本地化需求或个性化偏好来调整登录界面。

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## 自定义字段 {#custom-fields}

`Login`组件包含多个插槽，允许您根据需要添加额外字段。自定义字段在表单提交时会自动收集，并可以通过提交事件的数据映射访问。

以下登录界面添加了一个客户 ID 的自定义字段。这可以帮助您管理多个用户共享内容的公司或部门。

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info 姓名必填
自定义字段必须使用 `setName()` 设置名称，以包含在表单提交中。该名称用作从 `event.getData()` 检索字段值的键。
:::

## 取消按钮 {#cancel-button}

`Login`包含一个默认隐藏的 [取消] 按钮。这在用户尝试访问应用程序的受限区域时特别有用，需要有一个选项返回到之前的位置而无需完成登录。

要使取消按钮可见，请为其提供一个标签。您还可以监听取消事件以适当处理取消操作。

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip 隐藏元素
若要隐藏元素，请将其标签设置为空字符串。这使您能够在不从代码中删除组件的情况下切换可见性。
:::

## 密码管理器 {#password-managers}

该组件与基于浏览器的密码管理器协作，以简化登录过程。在基于 Chromium 的浏览器中，它与 [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API 集成，提供：

- **自动填写**：如果用户为该网站保存了凭据，浏览器可能会自动填写用户名和密码字段。
- **凭据管理**：登录后，浏览器可以提示用户保存新凭据，使以后的登录更快、更容易。
- **凭据选择**：如果保存了多个凭据，浏览器可以提供选择，让用户从保存的集合中选择。

## 样式 {#styling}

<TableBuilder name="Login" />
