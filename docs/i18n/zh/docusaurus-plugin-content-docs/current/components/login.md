---
title: Login
sidebar_position: 70
_i18n_hash: 929bacbc38791adc906102078bdd6bfa
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login` 组件通过提供一个现成的用户名和密码字段的登录对话框来简化用户身份验证。它包括输入验证、可定制的标签和消息、密码可见性控件以及对其他自定义字段的支持等功能。

<!-- INTRO_END -->

## 创建一个 `Login` 对话框 {#creating-a-login-dialog}

通过实例化组件并调用 `open()` 方法来创建一个 `Login` 对话框以显示它。对话框默认包含用户名和密码字段、输入验证和一个登录按钮。

<ComponentDemo
path='/webforj/loginbasic'
files={['src/main/java/com/webforj/samples/views/login/LoginBasicView.java']}
height='450px'
/>

## 登录提交 {#login-submission}

当用户输入他们的用户名和密码时，`Login` 组件将验证这些输入作为必填字段。一旦验证通过，将触发表单提交事件，传递输入的凭据。为了防止多次提交， [Sign in] 按钮会立即被禁用。

以下说明了一个基本的 `Login` 组件。如果用户名和密码均设置为 `"admin"`，登录对话框将关闭，并出现 [Logout] 按钮。如果凭据不匹配，将显示默认错误消息。

<ComponentDemo
path='/webforj/loginsubmission'
files={['src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java']}
height='450px'
/>

:::info 禁用 [Sign in] 按钮
默认情况下，一旦组件验证登录输入为正确，`Login` 会立即禁用 [Sign in] 按钮，以防止多次提交。您可以使用 `setEnabled(true)` 方法重新启用 [Sign in] 按钮。
:::

:::tip 允许空密码
您可以通过使用 `setEmptyPassword(true)` 方法来允许用户仅使用用户名进行登录。
:::

## 表单操作 <DocChip chip='since' label='25.10' />{#form-action}

`Login` 组件可以直接将表单数据提交到指定的 URL，而不是通过提交事件来处理提交。当设置了操作 URL 时，表单执行标准的 POST 请求，并将用户名和密码作为表单参数。

```java
Login login = new Login();
login.setAction("/api/auth");
```

使用 `setAction()` 时，表单提交将绕过 `LoginSubmitEvent`，而执行到指定端点的传统 HTTP POST 请求。用户名和密码分别以表单参数 `"username"` 和 `"password"` 发送。带有名称属性的自定义字段也会包含在 POST 请求中。

:::tip 
如果未设置操作 URL，将通过 `LoginSubmitEvent` 处理表单提交，这样您可以在服务器端以编程方式处理凭据。
:::

## 国际化 (i18n) {#internationalization-i18n}

`Login` 组件中的标题、描述、标签和消息均可通过 `LoginI18n` 类完全自定义。这种灵活性使您能够根据特定的本地化要求或个性化偏好定制登录界面。

<ComponentDemo
path='/webforj/logininternationalization'
files={['src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java']}
height='600px'
/>

## 自定义字段 {#custom-fields}

`Login` 组件包括几个插槽，允许您根据需要添加额外的字段。自定义字段在表单提交时会自动收集，并可以通过提交事件的数据映射进行访问。

以下登录具有为客户 ID 添加的自定义字段。这可以帮助您管理在多个用户之间共享内容的公司或部门。

<ComponentDemo
path='/webforj/logincustomfields'
files={[
  'src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java',
  'src/main/resources/static/css/login/loginCustomFields.css',
]}
height='700px'
/>

:::info 名称必填
自定义字段必须使用 `setName()` 设置名称，以便包含在表单提交中。此名称用作从 `event.getData()` 检索字段值的键。
:::

## 取消按钮 {#cancel-button}

`Login` 包含一个默认隐藏的 [Cancel] 按钮。这在用户尝试访问应用的受限区域并需要返回以前位置的选项时尤其有用，而无需完成登录。

要使取消按钮可见，请为其提供标签。您还可以监听取消事件以适当处理取消操作。

<ComponentDemo
path='/webforj/logincancelbutton'
files={['src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java']}
height='450px'
/>

:::tip 隐藏元素
要隐藏一个元素，请将其标签设置为空字符串。这让您可以切换可见性而无需从代码中移除组件。
:::

## 密码管理器 {#password-managers}

该组件与基于浏览器的密码管理器配合使用，以简化登录过程。在基于 Chromium 的浏览器中，它与 [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API 集成，提供：

- **自动填充**：如果用户已为该网站保存凭据，浏览器可能会自动填写用户名和密码字段。
- **凭据管理**：登录后，浏览器可以提示用户保存新凭据，使未来的登录更快更容易。
- **凭据选择**：如果保存了多个凭据，浏览器可以提供选择，以便用户从一个已保存的集合中选择。

## 样式 {#styling}

<TableBuilder name="Login" />
