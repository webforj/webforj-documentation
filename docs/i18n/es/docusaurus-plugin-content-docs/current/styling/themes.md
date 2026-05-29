---
sidebar_position: 2
title: Themes
sidebar_class_name: updated-content
description: >-
  Apply built-in light, dark, and dark-pure themes with @AppTheme or define
  custom themes through data-app-theme selectors.
_i18n_hash: 91e1a18f11aadea66df804dbaa4917d9
---
Un tema en webforJ es un conjunto nombrado de propiedades CSS personalizadas (tokens de diseño) que controla cómo se ve cada componente. Cambiar de tema recalcula colores, sombras, superficies y bordes en toda la aplicación al instante, sin necesidad de reconstruir.

## Temas integrados {#built-in-themes}

webforJ incluye tres temas de aplicación listos para usar:

| Tema        | Fondo            | Tinte                       |
|-------------|------------------|-----------------------------|
| `light`     | Claro (predeterminado) | Tinte sutil del color primario  |
| `dark`      | Oscuro           | Tinte sutil del color primario  |
| `dark-pure` | Oscuro           | Ninguno (grises neutrales puros)  |

Cualquier aplicación puede alternar entre ellos en tiempo de ejecución, y se pueden definir temas personalizados adicionales junto con los integrados.

## Aplicando un tema {#applying-a-theme}

Establece el tema activo declarativamente con la anotación `@AppTheme` o programáticamente con `App.setTheme()`. El nombre del tema debe ser uno de `system`, `light`, `dark`, `dark-pure`, o el nombre de un tema personalizado.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // código de la aplicación
}

// o programáticamente
App.setTheme("dark-pure");
```

Llamar a `App.setTheme()` nuevamente en cualquier momento cambia la aplicación a un tema diferente.

## Esquema de color {#color-scheme}

La declaración CSS `color-scheme` le indica al navegador cómo renderizar sus superficies incorporadas, como barras de desplazamiento nativas, widgets de control de formularios, resaltados de autocompletado y el fondo de página predeterminado antes de que se cargue el CSS. Los temas integrados `dark` y `dark-pure` ya configuran `color-scheme: dark` por ti, así que el marco del navegador se mezcla automáticamente con las superficies oscuras.

Solo necesitas pensar en esto al definir un tema oscuro personalizado. En ese caso, incluye `color-scheme: dark` en el selector del tema:

```css
html[data-app-theme="brand-dark"] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Si lo omites, las barras de desplazamiento y los rectángulos de autocompletar permanecen en modo claro por defecto y se ven fuera de lugar sobre tus superficies oscuras. Los temas claros no necesitan la declaración, los navegadores predeterminan a claro.

## Siguiendo la preferencia del usuario {#following-the-users-preference}

La mayoría de los sistemas operativos permiten a los usuarios elegir una apariencia clara u oscura a nivel del sistema. webforJ puede honrar esa preferencia y elegir el tema correcto automáticamente.

Registra qué tema aplicar para cada estado de apariencia con `@AppLightTheme` y `@AppDarkTheme` (o `App.setLightTheme()` y `App.setDarkTheme()`), luego pasa la palabra clave reservada `"system"` a `App.setTheme()` (o `@AppTheme("system")`) para permitir que webforJ elija entre ellos según la preferencia del sistema operativo del usuario.

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
class MyApp extends App {
  // código de la aplicación
}
```

Forma programática equivalente:

```java
App.setLightTheme("light");
App.setDarkTheme("dark");
App.setTheme("system");
```

`"system"` es una palabra clave reservada. webforJ la resuelve en tiempo de ejecución a cualquiera de los temas claros u oscuros registrados y se re-resuelve automáticamente siempre que cambie la preferencia del sistema operativo. Una vez resuelto, el atributo `data-app-theme` real en la página es `light` u `dark`, así que cualquier anulación de CSS debe dirigirse a esos nombres en lugar de `"system"`.

:::info Configuraciones de apariencia a nivel de sistema operativo
Dónde los usuarios habilitan la configuración de apariencia a nivel de sistema varía según la plataforma:

- **Windows 10/11**: Configuración > Personalización > Colores > Elegir tu color
- **macOS**: Configuración del Sistema > Apariencia
- **iOS**: Configuración > Pantalla y Brillo > Apariencia
- **Android**: Configuración > Pantalla > Tema oscuro
:::

## Sobrescribiendo temas predeterminados {#overriding-default-themes}

La mayor parte del trabajo de branding se realiza **sobrescribiendo los temas existentes** en lugar de crear nuevos. Ajusta los colores base (o cualquier otro token) para los temas integrados `light`, `dark` y `dark-pure`, y cada componente adoptará automáticamente el nuevo aspecto.

Puedes sobrescribir el tema **light** redefiniendo propiedades CSS personalizadas en el selector `:root`.

:::info Pseudo-clase `:root`
La pseudo-clase CSS `:root` apunta al elemento raíz del documento. En HTML, representa el elemento `<html>` y tiene mayor especificidad que el selector `html` simple.
:::

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Para sobrescribir los temas **dark** o **dark-pure**, utiliza selectores de atributos en el elemento `<html>`:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-seed: #a855f7;
}

html[data-app-theme="dark-pure"] {
  --dwc-color-primary-seed: #a855f7;
}
```

Cambiar de tema con `App.setTheme("dark")` activa el tema oscuro rebrandeado, no se necesita un nuevo nombre de tema.

## Creando temas personalizados {#creating-custom-themes}

Crea un tema completamente nuevo solo cuando necesites uno que coexista con los integrados (por ejemplo, una variante de alto contraste o una piel específica para un cliente). Escoge un nombre único y defínelo bajo su propio selector `html[data-app-theme='NOMBRE_DEL_TEMA']`:

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Para hacer que un tema personalizado sea oscuro, establece `--dwc-dark-mode: 1` y `color-scheme: dark`:

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Luego en tu aplicación:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // código de la aplicación
}

// o programáticamente
App.setTheme("new-theme");
```

## Trabajando con tokens DWC {#working-with-dwc-tokens}

Unos pocos hábitos mantienen el CSS personalizado alineado con el sistema de diseño y evitan que se desvíe en modo oscuro o en futuras versiones.

### Siempre referencia tokens con `var(...)` {#always-reference-tokens-with-var}

Los literales de color codificados (`#3b82f6`, `rgb(59 130 246)`, `oklch(0.6 0.18 250)`) no se adaptan al modo oscuro y no siguen los cambios de paleta. Usa el token en su lugar.

```css
/* evitar */
.my-panel {
  background: #ffffff;
  color: #1f2937;
  border: 1px solid #e5e7eb;
}

/* preferir */
.my-panel {
  background: var(--dwc-surface-3);
  color: var(--dwc-color-body-text);
  border: 1px solid var(--dwc-border-color);
}
```

### Prefiere tokens de variación sobre números de paso crudos {#prefer-variation-tokens-over-raw-step-numbers}

Los tokens de variación (`--dwc-color-primary`, `-dark`, `-light`, `-text`, `-alt`) se resuelven a un paso diferente en modo claro versus oscuro automáticamente. Los números de paso crudos (`--dwc-color-primary-50`) no lo hacen.

```css
/* evitar - congelado en el paso 50 en ambos modos */
.badge {
  background: var(--dwc-color-primary-50);
}

/* preferir - cambia el paso en modo oscuro */
.badge {
  background: var(--dwc-color-primary);
}
```

### Usa el sufijo que coincida con el rol {#use-the-suffix-that-matches-the-role}

| Sufijo                          | Rol                                                                  |
|---------------------------------|----------------------------------------------------------------------|
| `--dwc-color-{name}`            | Relleno sólido a plena resistencia (botones, distintivos, pancartas) |
| `--dwc-color-{name}-dark`       | Estado activo / presionado                                          |
| `--dwc-color-{name}-light`      | Fondo de paso de hover / enfoque                                    |
| `--dwc-color-{name}-alt`        | Fondo sutil con tinte para llamadas y filas alternativas            |
| `--dwc-color-{name}-text`       | Texto coloreado sobre una superficie neutral                        |
| `--dwc-color-on-{name}-text`    | Texto colocado **sobre** el sombreado coloreado como fondo (contraste automático) |
| `--dwc-border-color-{name}`     | Bordes y divisores                                                 |

### Reserva superficies y bordes para sus roles {#reserve-surfaces-and-borders-for-their-roles}

Las superficies (`--dwc-surface-1` / `-2` / `-3`) construyen la jerarquía de la página. Los bordes (`--dwc-border-color`, `--dwc-border-color-*`) trazan separadores. Volver a usar pasos de paleta para estos roles funciona visualmente pero pierde la adaptación automática del modo que llevan los tokens dedicados.

### Sobrescribe a nivel de semilla en temas personalizados {#override-at-the-seed-level-in-custom-themes}

Al construir un tema personalizado, establece la semilla (`--dwc-color-{name}-h`, `-s`, o `-seed`) en lugar de sobrescribir pasos individuales. El generador reconstruye la paleta completa de 19 pasos alrededor de la semilla, manteniendo la gama tonal completa consistente. Sobrescribir pasos individuales deja al resto de la paleta desalineada con tu color de marca.

```css
/* evitar - deja otros pasos inconsistentes */
html[data-app-theme="brand"] {
  --dwc-color-primary-50: #6366f1;
}

/* preferir - regenera toda la paleta */
html[data-app-theme="brand"] {
  --dwc-color-primary-seed: #6366f1;
}
```

### Usa tokens para espaciado, tamaño, radio y transiciones {#use-tokens-for-spacing-sizing-radius-and-transitions}

La misma regla se extiende a través del resto del sistema de diseño: referencia tokens, nunca números mágicos.

```css
/* evitar */
.my-panel {
  padding: 16px;
  border-radius: 8px;
  transition: background-color 250ms;
}

/* preferir */
.my-panel {
  padding: var(--dwc-space-m);
  border-radius: var(--dwc-border-radius);
  transition: background-color var(--dwc-transition);
}
```

Los valores codificados ignoran el escalado de tamaño de fuente según la preferencia de usuario, te bloquean en un lenguaje de forma fija y omiten las curvas de tiempo suavizadas del sistema de diseño.

### Usa `::part(...)` para acceder a los componentes {#use-part-to-reach-into-components}

Los componentes webforJ son Shadow DOM. Su marcado interno está oculto de los selectores externos, por lo que una regla como `.dwc-button-label { ... }` no coincidirá con nada. Para estilizar piezas internas, apunta a las partes expuestas:

```css
/* estiliza la etiqueta dentro de cada botón primario */
dwc-button[theme="primary"]::part(label) {
  letter-spacing: 0.02em;
}
```

Consulta [Shadow Parts](./shadow-parts) para el mecanismo completo, y la sección **Estilos → Shadow Parts** de cada componente para las partes que expone.

### Delimita las anulaciones de tokens con un selector contenedor {#scope-token-overrides-with-a-wrapper-selector}

Las propiedades CSS personalizadas tienen cascada. Establecer un token en un elemento contenedor reajusta todo dentro de él sin afectar al resto de la aplicación.

```css
.danger-section {
  --dwc-color-primary-seed: #ef4444;
}
```

Cada componente dentro de `.danger-section` (botones, enlaces, anillos de enfoque) ahora usa el tono rojo de peligro, mientras que el tema global permanece sin cambios.

### Prueba en modo claro y oscuro {#test-in-both-light-and-dark-mode}

Antes de enviar cualquier CSS personalizado, cambia el tema a `dark` y `dark-pure` y recorre la pantalla. La regresión más común son valores de color codificados que se veían bien en un modo y se leen como ilegibles o fuera de paleta en el otro.

### No recurras a `!important` {#dont-reach-for-important}

Escapa de la cascada y hace que cada anulación futura sea más difícil. Si una regla no está ganando, la causa suele ser un desajuste de especificidad con una solución más limpia: apunta al mismo selector que usa el marco, o añade un calificador padre. Reserva `!important` para estilos de terceros que realmente no tengas otra forma de superar.

## Temas de componente {#component-themes}

Además de los temas a nivel de aplicación, los componentes de webforJ admiten un conjunto de **temas de componente** basados en las paletas de colores predeterminados: `default`, `primary`, `success`, `warning`, `danger`, `info`, y `gray`. Esto es independiente del tema activo de la aplicación.

Cada componente documenta sus temas soportados en la sección **Estilos → Temas**.
