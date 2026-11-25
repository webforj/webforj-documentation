---
title: Aspecto y comportamiento de Swing
sidebar_position: 5
description: >-
  Aplica a una aplicación Swing integrada los colores, fuentes, botones y
  contornos de foco de webforJ con DWC Look and Feel for Swing, en modo claro u
  oscuro.
_i18n_title_hash: 3c0ddc5fa92ce0ba34ad9e57d896d42a
_i18n_description_hash: 8f86f1732f1d00ba4e1e87a16ff232f1
_i18n_hash: f7227a823be8a2f867fdf399bc56a1b4
---
Cuando `WebswingConnector` integra una aplicación Swing en una aplicación webforJ, los usuarios ven pantallas Swing junto a pantallas webforJ. Con la apariencia predeterminada de Swing, ambos lados parecen aplicaciones distintas. La apariencia DWC para Swing proporciona a la aplicación Swing las fuentes, los colores, los botones y los contornos de enfoque de los componentes DWC que usa webforJ. Incluye una paleta clara y otra oscura.

Es una apariencia estándar de Swing en un único JAR. Añádela al classpath de la aplicación Swing y selecciónala al iniciar. El resto del código Swing no cambia.

:::info Coincidencia aproximada
Swing y DWC dibujan sus controles de manera diferente. Ambos lados pueden usar la misma paleta clara u oscura, aunque es posible que notes pequeñas diferencias al pasar de una pantalla Swing a una pantalla webforJ.
:::

## Prueba la demostración {#try-the-demo}

Order Desk es una aplicación Swing con el tema instalado. [Descarga la demostración de Order Desk](https://dwc.style/docs/#/swing/downloads) y ejecútala con Java 21 o posterior:

```bash
java -jar dwc-swing-laf-demo.jar
```

Elige **View → DWC light** o **View → DWC dark** y luego **View → Primary color**. Compara el pedido seleccionado, la pestaña activa y el botón Save order.

Todos los controles de Order Desk son componentes Swing estándar. Cambia la paleta y el color principal debajo de la captura de pantalla, y selecciona la imagen para ampliarla.

<!-- vale off -->
<ThemeGallery alt="The Order Desk Swing demo" items={[
  { label: "Default", color: "#134ddf", light: "/img/swing-laf/order-desk-light-default.webp", dark: "/img/swing-laf/order-desk-dark-default.webp" },
  { label: "Violet", color: "#7c3aed", light: "/img/swing-laf/order-desk-light-violet.webp", dark: "/img/swing-laf/order-desk-dark-violet.webp" },
  { label: "Teal", color: "#0d9488", light: "/img/swing-laf/order-desk-light-teal.webp", dark: "/img/swing-laf/order-desk-dark-teal.webp" },
  { label: "Orange", color: "#ea580c", light: "/img/swing-laf/order-desk-light-orange.webp", dark: "/img/swing-laf/order-desk-dark-orange.webp" }
]} />
<!-- vale on -->

## Instala el tema {#install-the-theme}

[Descarga el JAR del tema](https://dwc.style/docs/#/swing/downloads) y añádelo al classpath de la aplicación Swing. Necesita Java 21 o posterior e incluye ambas paletas y la fuente Inter.

En el hilo de despacho de eventos de Swing, selecciona la apariencia antes de que la aplicación cree su primera ventana:

```java
try {
  UIManager.setLookAndFeel(new DwcLookAndFeel());
} catch (UnsupportedLookAndFeelException e) {
  throw new IllegalStateException(e);
}
```

Para usar la paleta oscura, utiliza `DwcDarkLookAndFeel`. Ambas clases están en el paquete `com.basis.dwc.swing.laf`. Si la aplicación selecciona la apariencia por nombre de clase, usa `com.basis.dwc.swing.laf.DwcLookAndFeel` o `com.basis.dwc.swing.laf.DwcDarkLookAndFeel`.

Si la aplicación usa módulos de Java, coloca el JAR en la ruta de módulos y requiere `com.basis.dwc.swing.laf`.

## Adapta el tema al de webforJ {#match-your-webforj-theme}

El tema lee los mismos nombres de tokens que los [temas de webforJ](/docs/styling/themes). Establece un token en la aplicación Swing con el valor que usa tu aplicación webforJ.

Para usar el color principal de tu aplicación webforJ, establece la semilla principal después de instalar la apariencia:

```java
DwcLookAndFeel.setToken("--dwc-color-primary-seed", new Color(117, 67, 188));
```

Los botones principales, las selecciones y los contornos de enfoque adoptan ese color. Otros colores semánticos, como el de peligro, conservan su propia paleta. Para volver al color principal de la paleta, pasa `null` como valor.

El tema usa Inter de forma predeterminada. webforJ usa la fuente de la plataforma mediante [`--dwc-font-family`](/docs/styling/typography). Para usar la misma fuente en la aplicación Swing, establece el token en `system-ui`:

```java
DwcLookAndFeel.setToken("--dwc-font-family", "system-ui");
```

Una fuente distinta puede cambiar el ancho de las etiquetas y la altura de los campos, así que ajusta el tamaño de las ventanas después de crear su contenido.

## Aplica estilo a un control {#style-one-control}

Una propiedad del cliente modifica un solo control. En este formulario se etiqueta un campo, se marca como obligatorio y se establece el botón Save como principal:

```java
email.putClientProperty(DwcLookAndFeel.LABEL, "Delivery email");
email.putClientProperty(DwcLookAndFeel.REQUIRED, true);
save.putClientProperty(DwcLookAndFeel.THEME, ButtonTheme.PRIMARY);
```

Para mostrar un mensaje de validación debajo de un campo, establece el estado no válido y el texto de ayuda:

```java
boolean missing = email.getText().isBlank();
email.putClientProperty(DwcLookAndFeel.INVALID, missing);
email.putClientProperty(DwcLookAndFeel.HELPER_TEXT,
    missing ? "Enter a delivery email." : "");
```

La [referencia de propiedades del cliente](https://dwc.style/docs/#/swing/client-properties) enumera las claves de cada control, incluidas las tablas, los árboles, las pestañas, los controles deslizantes y los cuadros de diálogo.

La [guía completa del tema](https://dwc.style/docs/#/swing/) trata los cuadros de diálogo, el enfoque del teclado y las animaciones.
