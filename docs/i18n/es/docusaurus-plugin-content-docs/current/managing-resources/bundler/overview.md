---
title: Frontend bundler
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Understand how the webforJ frontend bundler turns class-level annotations into
  compiled, per-route frontend assets, when to reach for it, and how a class
  binds to the entry it needs.
_i18n_hash: 1276a78ae5197924d6577eae5bafe73b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Bundler frontend <DocChip chip='since' label='26.01' /> {#frontend-bundler}

El bundler frontend permite que una clase de webforJ utilice el ecosistema de [npm](https://www.npmjs.com/), escriba componentes en [React](https://react.dev/), [Svelte](https://svelte.dev/) o [Lit](https://lit.dev/), y autorice [SCSS](https://sass-lang.com/), todo desde un proyecto Java sin necesidad de instalar o ejecutar una herramienta de Node. Una clase nombra el frontend que necesita con una anotación, y la construcción instala los paquetes, compila las fuentes y carga el resultado cuando se crea un componente de esa clase.

El bundler se ejecuta como parte del [plugin de construcción webforJ](/docs/configuration/build-plugin), que agregas una vez a tu construcción de Maven o Gradle. La mecánica de compilar un tipo específico de fuente, SCSS, [Less](https://lesscss.org/), [Tailwind](https://tailwindcss.com/), y el resto, son tarea de [extensiones](/docs/managing-resources/bundler/extensions/overview).

## Cuándo necesitas el bundler {#when-you-need-the-bundler}

webforJ funciona sin un bundler. Si ya tienes un script o una hoja de estilos y quieres adjuntarlo a un componente o a la aplicación, las [anotaciones de activos](/docs/managing-resources/importing-assets) lo hacen sin ningún paso de construcción, sin npm y sin compilación.

El bundler se justifica cuando el frontend es más que un archivo estático:

- Quieres un paquete de npm, por nombre y versión, instalado y compilado en tu aplicación.
- Quieres escribir un componente en React, Svelte o Lit y consumirlo desde Java.
- Quieres autorizar SCSS, Sass o Less, o compilar utilidades de Tailwind.
- Quieres ejecutar pruebas frontend como parte de la construcción.

El bundler es el camino predeterminado para ese trabajo, y hace todo lo que hacen las anotaciones de activos, por lo que un proyecto que lo adopte no pierde la opción más simple.

## Agregándolo a un proyecto existente {#adding-it-to-an-existing-project}

El bundler es optativo, por lo que puedes agregarlo a una aplicación que ya utiliza las [anotaciones de activos](/docs/managing-resources/importing-assets) y usarlo solo donde lo necesites.

1. Agrega el [plugin de construcción webforJ](/docs/configuration/build-plugin) a tu construcción de Maven o Gradle. Se encarga de Bun por ti, así que no hay necesidad de instalar una herramienta de Node.
2. Autoriza tus fuentes frontend bajo `src/main/frontend`.
3. Declara lo que una clase necesita con `@BundlePackage` y `@BundleEntry`.

Tus anotaciones de activos existentes `@StyleSheet`, `@JavaScript` y las demás seguirán funcionando sin cambios, por lo que puedes mover un recurso al bundler cuando necesites un paquete, una fuente compilada o una prueba, y dejar el resto sin cambios.

## Vinculando una clase a una entrada {#binding-a-class-to-an-entry}

`@BundleEntry` vincula una clase a una entrada de frontend, y `@BundlePackage` declara los paquetes de npm que esa entrada importa. Ambas anotaciones viven en la clase, por lo que el frontend que necesita una vista viaja con la vista.

```java title="GreetingView.java"
@Route("/greeting")
@BundlePackage(value = "lit", version = "^2.0.0")
@BundleEntry("greeting/hello-greeting.ts")
public class GreetingView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public GreetingView() {
    self.add(new Element("hello-greeting"));
  }
}
```

```ts title="greeting/hello-greeting.ts"
import { LitElement, html } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement("hello-greeting")
class HelloGreeting extends LitElement {
  render() {
    return html`<p>Saludos de webforJ</p>`;
  }
}
```

La entrada registra el elemento personalizado `hello-greeting` y se encarga de su renderizado. El lado de Java lo consume con `new Element("hello-greeting")` y escucha sus eventos. La construcción compila la entrada, instala `lit`, y carga la salida cuando se renderiza `/greeting`.

## Temas {#topics}

<DocCardList className="topics-section" />
