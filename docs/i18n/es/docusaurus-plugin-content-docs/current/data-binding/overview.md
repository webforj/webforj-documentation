---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: fef9723206ef7122c3ada5503f97edf1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

 webforJ incluye una función de enlace de datos que integra sin problemas los componentes de la interfaz de usuario con los modelos de datos del backend en aplicaciones Java. Esta función cierra la brecha entre la interfaz de usuario y la capa de datos, garantizando que los cambios en la interfaz se reflejen en el modelo de datos y viceversa. Como resultado, mejora la experiencia del usuario y reduce la complejidad del manejo de eventos y la sincronización de datos.

## Concepto {#concept}

La siguiente demostración presenta una aplicación webforJ simple para registrar superhéroes utilizando el enlace de datos de webforJ. La aplicación consta de dos partes principales: `HeroRegistration.java` y `Hero.java`.

En `HeroRegistration.java`, el código configura la interfaz de usuario con un `TextField` para ingresar el nombre del héroe, un `ComboBox` para seleccionar un superpoder y un `Button` para enviar el registro.

La clase `Hero` define el modelo de datos con restricciones de validación sobre el nombre y el poder del héroe, asegurando que las entradas sean válidas y cumplan con criterios específicos como longitud y patrón.

La aplicación utiliza el `BindingContext` para vincular los componentes de la interfaz de usuario a las propiedades del objeto `Hero`. Cuando un usuario hace clic en el botón de envío, la aplicación escribe los datos ingresados en el formulario de nuevo en el bean `Hero` si son válidos.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Campo de Texto");
  private ComboBox power = new ComboBox("Poder");
  private Button submit = new Button("Enviar Solicitud");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Volar", "Invisible", "Visión Láser", "Velocidad", "Teletransportación");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Volar");

    // reflejar los datos del bean en el formulario
    context.read(bean);

    submit.onClick(e -> {
      // escribir los datos del formulario de nuevo en el bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // hacer algo con el bean
        // repository.persist(bean)
      }
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Hero" label="Hero.java">

```java showLineNumbers
public class Hero {

  @NotEmpty(message = "El nombre no puede estar vacío")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Poder no especificado")
  @Pattern(regexp = "Volar|Invisible|Visión Láser|Velocidad|Teletransportación", message = "Poder no válido")
  private String power;

  public Hero(String name, String power) {
    this.name = name;
    this.power = power;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public String toString() {
    return "Nombre: " + name + ", Poder: " + power;
  }
}
```

</TabItem>
</Tabs>

## Características clave {#key-features}

- **Enlace Bidireccional:**  Soporta el enlace de datos bidireccional, permitiendo que los cambios en el modelo de datos actualicen la interfaz de usuario y las interacciones del usuario en la interfaz de usuario actualicen el modelo de datos.

- **Soporte de Validación:** Integra mecanismos de validación integral que puedes personalizar y extender. Los desarrolladores pueden implementar sus propias reglas de validación o utilizar frameworks de validación existentes como Jakarta Validation para asegurar la integridad de los datos antes de actualizar el modelo.

- **Extensibilidad:** Puede ser fácilmente extendido para soportar diferentes tipos de componentes de UI, transformaciones de datos, y escenarios de validación complejos.

- **Configuración Basada en Anotaciones:**  Utiliza anotaciones para minimizar el código repetitivo, haciendo que los enlaces entre componentes de UI y modelos de datos sean declarativos y fáciles de gestionar.

# Temas

<DocCardList className="topics-section" />
