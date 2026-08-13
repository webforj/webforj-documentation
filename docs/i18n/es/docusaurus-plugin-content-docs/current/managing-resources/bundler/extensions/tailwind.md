---
title: Tailwind
sidebar_position: 60
sidebar_class_name: new-content
description: >-
  Turn on the webforj-tailwind extension, apply utility classes from a view, and
  understand how it generates and scans its own stylesheet.
_i18n_hash: f588624ebd738977bb8be4e9887141d1
---
[Tailwind CSS](https://tailwindcss.com/) es un marco CSS de utilidad primero cuyos nombres de clase se asignan a un pequeño conjunto de declaraciones CSS. Es la única extensión curada que se envía, porque la mayoría de los proyectos no la utilizan. Se habilita por id, de la misma manera que se activa cualquier extensión, consulta [Habilitado por id](/docs/managing-resources/bundler/extensions/overview#enabled-by-id). Cuando está activado, hace algo que los demás no hacen: genera su propia entrada.

## Cómo funciona {#how-it-works}

En lugar de compilar un archivo que escribiste, la extensión de Tailwind escanea las fuentes de tu aplicación en busca de los nombres de clases de utilidad que utilizan, genera una hoja de estilos que lleva solo esas utilidades y la carga para cada vista. Luego, una vista aplica utilidades como nombres de clase sin nada que importar:

```java title="TailwindView.java"
@Route("/tailwind")
public class TailwindView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TailwindView() {
    Div card = new Div("Estilizado por utilidades de Tailwind compiladas");
    card.addClassName("flex", "items-center", "gap-4", "p-8", "rounded-lg",
        "bg-blue-500", "text-white");
    self.add(card);
  }
}
```

La hoja de estilos generada importa el tema y las utilidades de Tailwind, pero no su reinicio base. El reinicio, el preflight de Tailwind, restyla cada elemento bare en la página, lo cual anularía el estilo que webforJ ya aplica a sus componentes. Dejarlo fuera mantiene las clases de utilidad que agregas funcionando sin alterar los componentes que no has cambiado.

Dado que las utilidades provienen de los nombres de clase que utilizan tus vistas, el [frontend watch](/docs/configuration/deploy-reload/frontend-watch) sigue tus fuentes de aplicación así como `src/main/frontend`. Agrega o quita una clase de utilidad en una vista y guarda, y la hoja de estilos se regenerará y se parcheará en la página en su lugar, de la misma manera que editar una hoja de estilos que escribiste.

## A dónde llegan las clases de utilidad {#where-utility-classes-reach}

:::warning Una clase de utilidad estiliza el elemento, no el interior de un componente
Los componentes de webforJ se renderizan con un shadow DOM que mantiene su estructura interna privada. Una clase de utilidad añadida a un componente estiliza solo su caja externa, su espaciado, dimensión y lugar en un diseño, y nunca alcanza los elementos dibujados dentro de él. Las utilidades se aplican de la manera que sus nombres de clase leen en un contenedor de diseño o un `Div` simple que construyas, donde no hay límite que cruzar, pero no en el interior de un componente elaborado.

Para estilizar lo que hay dentro de un componente, utiliza el estilo que el componente expone en su lugar: [partes de sombra](/docs/styling/shadow-parts) a través de `::part()` y las [propiedades personalizadas CSS](/docs/styling/css-variables) del componente, ambas listadas en la referencia de estilo de cada componente. Mantén las utilidades para el diseño y para tus propios elementos, y usa el estilo propio de un componente para ajustar el componente.
:::

La hoja de estilos lleva los nombres de clase de utilidad que el escaneo encuentra en tus fuentes, y solo esos. Una clase que escribas en el inspector del navegador para probar una idea no se aplicará, porque nunca fue compilada. Coloca la clase en una vista y guarda, y el watch regenerará la hoja de estilos con ella.

Cuando el mismo grupo de utilidades se repite en muchas vistas, nómbralo: define una clase CSS una vez y añade eso en su lugar. Unas pocas utilidades en línea permanecen legibles, una larga cadena repetida a mano se desvía mientras editas.

## Opciones {#options}

La extensión de Tailwind no toma opciones de `bun.config.ts`. Genera y escanea su propia hoja de estilos, y Tailwind en sí se configura a través de esa hoja de estilos en lugar de a través de la extensión.
