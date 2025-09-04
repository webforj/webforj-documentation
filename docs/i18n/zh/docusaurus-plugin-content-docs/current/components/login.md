---
title: Login
sidebar_position: 70
_i18n_hash: b95b5a072de318071d9d7ecae890a883
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

登录组件旨在提供用户友好的身份验证界面，允许用户使用用户名和密码登录。它支持各种自定义选项，以增强跨不同设备和地区的用户体验。

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## 用法 {#usages}

登录组件提供了一个用户友好的登录表单接口，在对话框中输入身份验证凭据。它通过提供以下功能来增强用户体验：
   >- 清晰的用户名和密码输入字段。
   >- 密码可见性切换，以验证输入。
   >- 输入验证反馈，以在提交前提示正确格式。

## 登录提交 {#login-submission}

当用户输入用户名和密码时，登录组件会将这些输入验证为必填字段。一旦验证通过，将触发表单提交事件，传递输入的凭据。为了防止多次提交，`Signin` 按钮会立即被禁用。

下面的演示展示了基本的表单提交过程。如果用户名和密码均设置为 `"admin"`，登录对话框将关闭，并显示注销按钮。如果凭据不匹配，则会显示登录表单的默认错误信息。

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info 禁用 Signin 按钮
默认情况下，一旦组件验证登录输入为正确，登录表单会立即禁用 `Signin` 按钮，以防止多次提交。您可以使用 `setEnabled(true)` 方法重新启用 `Signin` 按钮。
:::

:::tip 允许空密码
在某些情况下，空密码可能是允许的，允许用户仅使用用户名登录。可以通过设置 `setEmptyPassword(true)` 来配置登录对话框以接受空密码。
:::

## 国际化 (i18n) {#internationalization-i18n}

登录组件中的标题、描述、标签和消息可以通过 `LoginI18n` 类进行完全自定义。这种灵活性使您能够根据特定的本地化要求或个性化偏好调整登录界面。

下面的演示展示了如何为登录对话框提供德语翻译，确保所有界面元素适应德语以增强德语用户的体验。

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## 自定义字段 {#custom-fields}

登录组件包括几个插槽，允许您在必要时添加额外字段。此功能提供了对成功身份验证所需信息的更多控制。

在下面的示例中，登录表单中添加了客户ID字段。用户必须提供有效的ID才能完成身份验证，增强安全性，并确保在验证所有必要凭据后才授予访问权限。

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info 提交有效负载
请注意，登录组件不会自动识别或包含添加到表单中的额外字段作为其提交有效负载。这意味着开发人员必须明确从客户端检索任何附加字段的值，并根据应用程序的需求来处理，以完成身份验证过程。
:::

## 取消按钮 {#cancel-button}

在某些情况下，添加一个取消按钮与 `Signin` 按钮并排放置是可取的。此功能尤其在用户尝试访问应用程序的受限区域时非常有用，需要一个选项来取消操作并返回到之前的位置。登录表单默认包括一个取消按钮，但它在视图中是隐藏的。

要使取消按钮可见，您必须为其提供一个标签 - 一旦标记，它将出现在屏幕上。您还可以监听取消事件，以便适当地响应用户操作，确保在浏览应用程序时提供流畅和用户友好的体验。

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip 隐藏元素
要从登录屏幕上隐藏一个元素，只需将其标签设置为空字符串。这种方法特别适用于在不永久性更改代码库的情况下临时删除界面组件。
:::

## 密码管理器 {#password-managers}

登录组件设计为与基于浏览器的密码管理器兼容，通过简化登录过程提升用户体验。对于使用基于Chromium的浏览器的用户，该组件与 [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API 无缝集成。此集成使许多便利功能成为可能：

- **自动填充**：如果用户已为站点保存凭据，浏览器可能会自动填充用户名和密码字段。
- **凭据管理**：登录后，浏览器可以提示用户保存新凭据，使未来的登录更快更容易。
- **凭据选择**：如果保存了多个凭据，浏览器可以提供选项，供用户从已保存的凭据集合中进行选择。

## 样式 {#styling}

<TableBuilder name="Login" />
