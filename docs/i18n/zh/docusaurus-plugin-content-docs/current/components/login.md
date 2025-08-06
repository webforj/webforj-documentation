---
title: Login
sidebar_position: 70
_i18n_hash: f2f1f96cfde1dbbede5bfdaafd3f0a92
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

登录组件旨在提供用户友好的身份验证界面，允许用户使用用户名和密码登录。它支持各种自定义，以增强不同设备和地区的用户体验。

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## 使用方式 {#usages}

登录组件在对话框中提供用户友好的登录表单界面，以输入身份验证凭据。它通过提供以下功能来增强用户体验：
   >- 清晰的用户名和密码输入框。
   >- 密码可见性切换，以验证输入。
   >- 输入验证反馈，以提示在提交之前的正确格式。

## 登录提交 {#login-submission}

当用户输入他们的用户名和密码时，登录组件将这些输入验证为必填字段。一旦验证通过，将触发表单提交事件，传递输入的凭据。为防止多次提交，`Signin`按钮会立即禁用。

下面的演示说明了基本的表单提交过程。如果用户名和密码都设置为“admin”，登录对话框将关闭，并出现注销按钮。如果凭据不匹配，将显示登录表单的默认错误消息。

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info 禁用 Signin 按钮
默认情况下，登录表单在组件验证登录输入为正确后立即禁用 `Signin` 按钮，以防止多次提交。您可以使用 `setEnabled(true)` 方法重新启用 `Signin` 按钮。
:::

:::tip 允许空密码
在某些情况下，空密码可能是可接受的，这允许用户仅使用用户名登录。可以通过设置 `setEmptyPassword(true)` 来配置登录对话框以接受空密码。
:::

## 国际化 (i18n) {#internationalization-i18n}

登录组件中的标题、描述、标签和消息均可使用 `LoginI18n` 类完全自定义。这种灵活性允许您根据特定的本地化需求或个性化偏好为登录界面量身定制。

下面的演示说明了如何为登录对话框提供德语翻译，确保所有界面元素都适应德语，以增强德语用户的体验。

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## 自定义字段 {#custom-fields}

登录组件包含多个插槽，允许您在必要时添加额外字段。此功能提供对成功身份验证所需信息的更多控制。

在下面的示例中，登录表单中添加了客户ID字段。用户必须提供有效ID以完成身份验证，从而增强安全性并确保在验证所有必填凭据后才授予访问权限。

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info 提交有效负载
请注意，登录组件不会自动识别或包括添加到表单中的额外字段在其提交有效负载中。这意味着开发人员必须明确从客户端检索任何附加字段的值，并根据应用程序的要求处理它以完成身份验证过程。
:::

## 取消按钮 {#cancel-button}

在某些情况下，可能希望在 `Signin` 按钮旁边添加取消按钮。此功能在用户尝试访问应用程序的受限区域并需要一个选项来取消操作并返回到先前的位置时特别有用。登录表单默认包含一个取消按钮，但默认是隐藏的。

要使取消按钮可见，您必须为其提供标签 - 一旦标记，它将在屏幕上显示。您还可以监听取消事件，以根据用户操作作出适当响应，确保在应用程序中导航的顺畅和用户友好的体验。

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip 隐藏元素
要从登录屏幕中隐藏元素，只需将其标签设置为空字符串。这种方法对于暂时删除界面组件而不永久更改代码库特别有用。
:::

## 密码管理器 {#password-managers}

登录组件旨在与基于浏览器的密码管理器兼容，通过简化登录过程来增强用户体验。对于使用基于Chromium的浏览器的用户，组件与 [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API 无缝集成。这种集成启用一些方便的功能：

- **自动填充**：如果用户为该站点保存了凭据，浏览器可能会自动填充用户名和密码字段。
- **凭证管理**：登录后，浏览器可以提示用户保存新凭据，从而使将来的登录更快更容易。
- **凭证选择**：如果保存了多个凭据，浏览器可以向用户提供选择，从已保存的凭据集中进行选择。

## 样式 {#styling}

<TableBuilder name="Login" />
