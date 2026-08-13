---
title: Packages and assets
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Declare npm packages, load a module straight from one, install build-only
  dependencies, rely on tree shaking, and import CSS and assets from a
  component's entry.
_i18n_hash: 3b538e3785ee74397f156dd38ef8506a
---
Una entrada se basa en más que su propia fuente. Importa paquetes de npm, carga un módulo directamente desde uno, incorpora una hoja de estilos o una imagen, y puede provenir de una clase que extiendes o de una biblioteca de la que depende. Esta página cubre cómo esos elementos llegan a la compilación.

## Declaración de un paquete {#declaring-a-package}

`@BundlePackage` declara una dependencia de npm que una entrada importa. La compilación recopila cada declaración en el classpath y la agrega al [`package.json`](https://docs.npmjs.com/cli/v11/configuring-npm/package-json) de tu proyecto, luego [Bun](https://bun.sh/) la instala antes de compilar, de modo que un paquete esté presente para cuando su entrada se compila. Tus propias ediciones a ese archivo se preservan, y un proyecto que no declara paquetes y no tiene su propio `package.json` no mantiene ninguno, por lo que npm se mantiene fuera de una compilación que no lo necesita.

Una biblioteca de componentes web es el caso común. Declara el paquete, luego apunta `@BundleEntry` a los módulos de componentes que desees:

```java title="Ui5View.java"
@Route("/ui5")
@BundlePackage(value = "@ui5/webcomponents", version = "^2.0.0")
@BundleEntry("@ui5/webcomponents/dist/Button.js")
@BundleEntry("@ui5/webcomponents/dist/Input.js")
public class Ui5View extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public Ui5View() {
    self.setAlignment(FlexAlignment.CENTER)
      .setStyle("margin", "1em");

    Element input = new Element("ui5-input");
    input.setProperty("placeholder", "Escribe algo");

    Element button = new Element("ui5-button");
    button.setProperty("design", "Emphasized");
    button.setText("Di hola");
    button.addEventListener("click", e ->
      Toast.show("Hola " + input.getProperty("value"))
    );

    self.add(input, button);
  }
}
```

La `version` sigue la sintaxis de versión de npm, por lo que `^2.0.0` acepta versiones compatibles de 2.x. Tanto `@BundlePackage` como `@BundleEntry` son repetibles, así que una clase puede declarar tantos paquetes y cargar tantos módulos como necesite.

### Una entrada de archivo o un módulo de npm {#a-file-entry-or-an-npm-module}

El valor de `@BundleEntry` es una de dos cosas: una ruta a un archivo que tú autoras bajo `src/main/frontend`, o una ruta a un módulo dentro de un paquete npm. La vista anterior nombra rutas de módulo dentro de `@ui5/webcomponents`, por lo que no lleva ningún archivo fuente propio. Cada uno de esos módulos registra su propio elemento personalizado cuando se carga, razón por la cual la vista consume `ui5-input` y `ui5-button` sin ningún wrapper. Una entrada de archivo, en cambio, apunta a un archivo `.ts`, `.js` o `.css` que tú escribiste, compilado de la misma manera.

### Dependencias de construcción {#build-dependencies}

Un paquete que solo se necesita para compilar las fuentes, no en tiempo de ejecución, es una dependencia de construcción. Establece `dev = true` en `@BundlePackage`, y la compilación lo instala como una `devDependency`:

```java
@BundlePackage(value = "typescript", version = "^5.0.0", dev = true)
```

Las extensiones curadas utilizan esto para los paquetes que sus compiladores necesitan, razón por la cual una fuente SCSS incorpora `sass` como una dependencia de construcción y nada en tiempo de ejecución.

## Solo lo que importas {#tree-shaking}

El compilador incluye solo el código que una entrada realmente importa. Nombrar `@ui5/webcomponents/dist/Button.js` incorpora el componente Button y lo que necesita, no el resto de la biblioteca. Una biblioteca amplia cuesta solo las partes que necesitas, por lo que no hay penalización por declarar un paquete grande y cargar un módulo de él.

### Código compartido {#shared-code}

Cuando dos entradas importan el mismo paquete, la compilación incorpora el código compartido en un chunk que ambas cargan, en lugar de copiarlo en cada una. Varios componentes construidos sobre la misma biblioteca, un conjunto de elementos Lit, por ejemplo, comparten el código de esa biblioteca en una página en lugar de pagarlo una vez por cada elemento.

## Cómo se cargan las entradas {#how-entries-load}

Una entrada produce un script, una hoja de estilos, o ambos, y el runtime carga esa salida la primera vez que se crea un componente de su clase, donde sea que se use ese componente y cuán profundamente esté anidado. Una vista enrutada y un layout son componentes como cualquier otro, por lo que una entrada se vincula a la creación del componente, no al enrutamiento. Dos detalles siguen del hecho de que la anotación reside en la clase:

- **Herencia.** `@BundleEntry` y `@BundlePackage` se heredan. Una clase base declara la entrada, y una subclase que no añade nada propio aún la carga.
- **Entradas de depuración.** Una entrada declarada como `@BundleEntry(value = "...", debug = true)` se carga solo cuando la aplicación se ejecuta en modo de depuración, lo que conviene para un diagnóstico solo en desarrollo.

## Importando CSS y activos {#importing-css-and-assets}

La entrada de un componente maneja hojas de estilos y otros activos a través de importaciones, sin anotaciones y sin extensión. Bun las resuelve en tiempo de compilación.

Importa una hoja de estilos por su efecto secundario, y el bundler la incluye en los estilos de la entrada. Importa un activo no relacionado con el código, y la importación te da una URL para usar:

```ts title="card/card.ts"
import './card.css';
import logoPath from './logo.svg';

const logo = new URL(logoPath, import.meta.url).href;
// usa logo como fuente de imagen dentro del elemento
```

Resuelve una URL de activo contra `import.meta.url`, no el documento, para que apunte al activo compilado donde sea que se sirva la salida.

Importa una hoja de estilos como texto en su lugar y aplícala dentro de una raíz sombra para limitar los estilos a un elemento:

```ts title="swatch/swatch.ts"
import sheet from './swatch.css' with { type: 'text' };

class ColorSwatch extends HTMLElement {
  connectedCallback() {
    const root = this.attachShadow({ mode: 'open' });
    const style = document.createElement('style');
    style.textContent = sheet;
    root.append(style);
  }
}
```

Una entrada también puede ser un archivo `.css` simple sin script, ligado a una clase de la misma manera que se liga una entrada de script. El runtime lo carga como estilos para la vista:

```java title="ThemeView.java"
@Route("/theme")
@BundleEntry("theme/theme.css")
public class ThemeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ThemeView() {
    self.add(new Div("Estilizado por una entrada de paquete CSS solo")
                 .addClassName("themed-label"));
  }
}
```
