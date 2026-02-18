---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: ba33283588df8722a31ad0c5fb15892a
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

 webforJ incluye una función de vinculación de datos que integra componentes de UI con modelos de datos backend en aplicaciones Java. Esta función cierra la brecha entre la UI y la capa de datos, de modo que los cambios en la UI se reflejan en el modelo de datos y viceversa, reduciendo la complejidad del manejo de eventos y la sincronización de datos.

## Concepto {#concept}

La siguiente demostración muestra una aplicación sencilla de webforJ para registrar superhéroes utilizando la vinculación de datos de webforJ. La aplicación consta de dos partes principales: `HeroRegistration.java` y `Hero.java`. 

En `HeroRegistration.java`, el código configura la interfaz de usuario con un `TextField` para ingresar el nombre del héroe, un `ComboBox` para seleccionar un superpoder, y un `Button` para enviar el registro.

La clase `Hero` define el modelo de datos con restricciones de validación sobre el nombre y el poder del héroe. Las entradas deben ser válidas y adherirse a criterios específicos como longitud y patrón.

La aplicación utiliza el `BindingContext` para vincular los componentes de la UI a las propiedades del objeto `Hero`. Cuando un usuario hace clic en el botón de enviar, la aplicación escribe los datos ingresados en el formulario de regreso al bean `Hero` si son válidos.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Campo de texto");
  private ComboBox power = new ComboBox("Poder");
  private Button submit = new Button("Enviar solicitud");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Volador", "Invisible", "VisionLaser", "Velocidad", "Teletransportación");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Volador");

    // reflejar los datos del bean en el formulario
    context.read(bean);

    submit.onClick(e -> {
      // escribir los datos del formulario de regreso al bean
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
  @Pattern(regexp = "Volador|Invisible|VisionLaser|Velocidad|Teletransportación", message = "Poder inválido")
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

- **Vinculación Bidireccional:** Soporta vinculación de datos bidireccional, permitiendo que los cambios en el modelo de datos actualicen la UI, y las interacciones del usuario en la UI actualicen el modelo de datos.

- **Soporte de Validación:** Integra mecanismos de validación completos que puedes personalizar y extender. Los desarrolladores pueden implementar sus propias reglas de validación o utilizar marcos de validación existentes como Jakarta Validation para verificar la integridad de los datos antes de actualizar el modelo.

- **Extensibilidad:** Se puede extender fácilmente para soportar diferentes tipos de componentes de UI, transformaciones de datos y escenarios complejos de validación.

- **Configuración Basada en Anotaciones:** Utiliza anotaciones para minimizar el código repetitivo, haciendo que las vinculaciones entre componentes de UI y modelos de datos sean declarativas y fáciles de gestionar.

# Temas

<DocCardList className="topics-section" />
