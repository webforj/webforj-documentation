---
sidebar_position: 10
title: BBj Controls and webforJ Components
description: >-
  See how webforJ components map one-to-one to BBj controls through the
  DwcComponent base class and why composition replaces inheritance.
_i18n_hash: 83f48323774737067ddd9a6bebb0373d
---
El framework webforJ está diseñado para proporcionar una API de Java en torno al DWC del lenguaje BBj, ofreciendo una arquitectura robusta para construir y gestionar componentes.

## Mapeo de controles BBj a componentes webforJ {#mapping-bbj-controls-to-webforj-components}
Uno de los principios fundamentales de webforJ es la vinculación de los controles BBj con los componentes webforJ. En esta arquitectura, cada componente webforJ que se envía con el producto tiene un mapeo uno a uno con un control BBj subyacente. Este mapeo asegura que los componentes de Java reflejen el comportamiento y las propiedades de sus contrapartes BBj de manera fluida.

Esta estrecha correspondencia entre los componentes webforJ y los controles BBj simplifica el desarrollo y permite a los desarrolladores de Java trabajar con conceptos familiares al construir aplicaciones basadas en la web sin necesidad de escribir ningún código BBj.

## La clase base `DwcComponent` {#the-dwccomponent-base-class}
En el corazón de la arquitectura de componentes de webforJ se encuentra la clase base DWCComponent. Todos los componentes webforJ heredan de esta clase. Esta herencia otorga a cada componente webforJ acceso a su control BBj subyacente, proporcionando un enlace directo entre el componente de Java y el control BBj que representa.

Sin embargo, es importante señalar que se restringe a los desarrolladores la posibilidad de extender la clase DWCComponent. Intentar hacerlo resultará en una excepción en tiempo de ejecución que desautoriza tales extensiones. Esta restricción está en su lugar para mantener la integridad del control BBj subyacente y asegurar que los desarrolladores no lo manipulen accidentalmente de maneras que puedan llevar a consecuencias no deseadas.

### Clases finales y restricciones de extensión {#final-classes-and-extension-restrictions}
En webforJ, la mayoría de las clases de componentes, con la excepción de los elementos HTML integrados y cualquier clase que extienda estos, se declaran como `final`. Esto significa que no están disponibles para extensión o subclonación. Esta elección de diseño es deliberada y cumple múltiples propósitos:

1. **Control sobre el control BBj subyacente**: Como se mencionó anteriormente, extender las clases de componentes webforJ otorgaría a los desarrolladores control sobre el control BBj subyacente. Para mantener la consistencia y previsibilidad del comportamiento de los componentes, este nivel de control se restringe.

2. **Prevención de modificaciones no intencionadas**: Hacer que las clases de componentes sean `final` previene modificaciones no intencionadas a componentes centrales, reduciendo el riesgo de introducir comportamientos inesperados o vulnerabilidades.

3. **Promoción del uso de compuestos**: Para extender la funcionalidad de los componentes, el framework webforJ anima a los desarrolladores a usar un enfoque compuesto. Los componentes compuestos son clases de Java que contienen otros componentes webforJ o elementos HTML estándar. Si bien se desaconseja la herencia tradicional, los componentes compuestos ofrecen una manera de crear nuevos componentes personalizados que encapsulan los existentes.

## Componentes compuestos: extendiendo a través de la composición {#composite-components-extending-through-composition}
En el framework webforJ, el concepto de componentes compuestos juega un papel fundamental en la extensión de la funcionalidad de los componentes. Los componentes compuestos son clases de Java que no están restringidas por la palabra clave final, lo que permite a los desarrolladores crear nuevos componentes que extienden el comportamiento de un solo componente, o combinan múltiples componentes en uno, mediante la composición de componentes existentes. Se han creado clases que facilitan este comportamiento para el uso de los desarrolladores. Consulte las secciones `Composite` y `ElementComposite` para ver cómo crear correctamente componentes compuestos.

Este enfoque fomenta un estilo de desarrollo más modular y flexible, permitiendo a los desarrolladores construir componentes personalizados que satisfacen requisitos específicos.
