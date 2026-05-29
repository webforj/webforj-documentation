---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: 663a49d7134273428b9b7648a1fd321e
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

El `PropertyDescriptorTester` en webforJ simplifica las pruebas de **componentes web de terceros** integrados en tu aplicación. Valida que las propiedades definidas con [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) estén correctamente vinculadas a sus métodos getter y setter, y asegura que los comportamientos por defecto se manejen de manera consistente. Esta herramienta es especialmente útil para verificar la funcionalidad de las propiedades expuestas por componentes de terceros sin requerir lógica de prueba repetitiva.

<ExperimentalWarning />

## Resumen {#overview}

Al trabajar con componentes web de terceros, garantizar que las propiedades se comporten como se espera es esencial. El `PropertyDescriptorTester` automatiza este proceso validando que las propiedades:
- Estén correctamente mapeadas a sus métodos getter y setter.
- Mantengan valores por defecto esperados y comportamientos personalizados.
- Eviten problemas comunes de integración, como nombres de propiedades desajustados o por defecto inconsistentes.

La herramienta admite anotaciones para casos de uso más complejos, como excluir propiedades irrelevantes o definir métodos getter y setter personalizados, lo que la convierte en una opción versátil para pruebas de integración.

## Cómo funciona `PropertyDescriptorTester` {#how-propertydescriptortester-works}

El proceso de prueba implica varios pasos automatizados:

1. **Escaneo de Clase**: 
   El `PropertyDescriptorScanner` identifica todos los campos `PropertyDescriptor` dentro de una clase de componente, excluyendo automáticamente los campos anotados con `@PropertyExclude`.

2. **Resolución de Métodos**:
   Se detectan los métodos getter y setter estándar basándose en convenciones de nomenclatura (`get<PropertyName>`/`set<PropertyName>`). Para implementaciones no estándar, anotaciones como `@PropertyMethods` especifican nombres de método personalizados o clases objetivo.

3. **Validación**:
   Se asignan valores por defecto utilizando el método setter, se recuperan utilizando el getter y se comparan para asegurar su corrección. Cualquier discrepancia genera un `AssertionError`, resaltando el problema específico.

4. **Informe de Errores**:
   El tester proporciona retroalimentación detallada sobre cualquier fallo de validación, como métodos faltantes, por defectos inconsistentes o configuraciones incorrectas de propiedades.

## Escribiendo pruebas con `PropertyDescriptorTester` {#writing-tests-with-propertydescriptortester}

Aquí hay un ejemplo que demuestra la validación básica de propiedades para un componente `AppLayout`:

### Ejemplo: Validación básica {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Título por Defecto");

  // setters y getters
}
```

#### Caso de prueba {#test-case}

```java title="MyComponentTest.java"
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class MyComponentTest {

  MyComponent component = new MyComponent();

  @Test
  void validateProperties() {
    try {
      PropertyDescriptorTester.run(MyComponent.class, component);
    } catch (Exception e) {
      fail("La prueba PropertyDescriptor falló: " + e.getMessage());
    }
  }
}
```

Esta prueba verifica automáticamente:
- Que `drawerOpened` tenga métodos getter y setter válidos.
- Que `headerTitle` tenga como valor por defecto `"Título por Defecto"`.

## Casos de uso avanzados con anotaciones {#advanced-use-cases-with-annotations}

Para escenarios más complejos, `PropertyDescriptorTester` admite anotaciones para personalizar o excluir propiedades de las pruebas.

### Excluir propiedades con `@PropertyExclude` {#exclude-properties-with-propertyexclude}

Excluye propiedades que dependen de sistemas externos o que no son relevantes para la prueba. Por ejemplo:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Excluido");
```

### Personalizar métodos con `@PropertyMethods` {#customize-methods-with-propertymethods}

Define un getter, setter o clase objetivo personalizados cuando las convenciones de nomenclatura predeterminadas no se aplican:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Valor por Defecto");
```
