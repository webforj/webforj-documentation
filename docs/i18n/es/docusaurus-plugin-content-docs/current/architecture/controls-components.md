---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 7fd4306a016d3734d34336b8136c6e11
---
El marco webforJ está diseñado para proporcionar una API de Java en torno al DWC del lenguaje BBj, ofreciendo una arquitectura robusta para construir y gestionar componentes.

## Mapeo de controles BBj a componentes webforJ {#mapping-bbj-controls-to-webforj-components}
Uno de los principios fundamentales de webforJ es la vinculación de controles BBj con componentes webforJ. En esta arquitectura, cada componente webforJ incluido en el producto tiene un mapeo uno a uno con un control BBj subyacente. Este mapeo asegura que los componentes Java reflejen el comportamiento y las propiedades de sus contrapartes BBj de manera fluida.

Esta correspondencia cercana entre los componentes webforJ y los controles BBj simplifica el desarrollo y permite a los desarrolladores Java trabajar con conceptos familiares al construir aplicaciones web sin necesidad de escribir ningún código BBj.

## La clase base `DwcComponent` {#the-dwccomponent-base-class}
En el corazón de la arquitectura de componentes de webforJ se encuentra la clase base DWCComponent. Todos los componentes webforJ heredan de esta clase. Esta herencia otorga a cada componente webforJ acceso a su control BBj subyacente, proporcionando un enlace directo entre el componente Java y el control BBj que representa.

Sin embargo, es importante señalar que a los desarrolladores se les restringe extender la clase DWCComponent. Intentar hacerlo resultará en una excepción en tiempo de ejecución que prohíbe tales extensiones. Esta restricción está en su lugar para mantener la integridad del control BBj subyacente y asegurar que los desarrolladores no lo manipulen inadvertidamente de maneras que podrían llevar a consecuencias no deseadas.

### Clases finales y restricciones de extensión {#final-classes-and-extension-restrictions}
En webforJ, la mayoría de las clases de componentes, con la excepción de los elementos HTML integrados y cualquier clase que los extienda, se declaran como `final`. Esto significa que no están disponibles para extensión o subtipo. Esta elección de diseño es deliberada y sirve a múltiples propósitos:

1. **Control sobre el Control BBj Subyacente**: Como se mencionó anteriormente, extender las clases de componentes de webforJ otorgaría a los desarrolladores control sobre el control BBj subyacente. Para mantener la consistencia y la previsibilidad del comportamiento del componente, este nivel de control está restringido.

2. **Prevención de Modificaciones Inadvertidas**: Hacer que las clases de componentes sean `final` previene modificaciones no intencionadas a componentes centrales, reduciendo el riesgo de introducir comportamientos inesperados o vulnerabilidades.

3. **Promoción del Uso de Compuestos**: Para extender la funcionalidad de los componentes, el marco webforJ anima a los desarrolladores a utilizar un enfoque compuesto. Los componentes compuestos son clases Java que contienen otros componentes webforJ o elementos HTML estándar. Mientras que la herencia tradicional se desaconseja, los componentes compuestos ofrecen una forma de crear nuevos componentes personalizados que encapsulan los existentes.

## Componentes compuestos: extendiendo a través de la composición {#composite-components-extending-through-composition}
En el marco webforJ, el concepto de componentes compuestos juega un papel crucial en la extensión de la funcionalidad de los componentes. Los componentes compuestos son clases Java que no están restringidas por la palabra clave final, lo que permite a los desarrolladores crear nuevos componentes que extiendan el comportamiento de un solo componente o combinen múltiples componentes en uno, mediante la composición de componentes existentes. Se han creado clases que facilitan este comportamiento para el uso de los desarrolladores. Consulte las secciones `Composite` y `ElementComposite` para ver cómo crear correctamente componentes compuestos.

Este enfoque fomenta un estilo de desarrollo más modular y flexible, permitiendo a los desarrolladores construir componentes a medida que satisfacen requisitos específicos.
