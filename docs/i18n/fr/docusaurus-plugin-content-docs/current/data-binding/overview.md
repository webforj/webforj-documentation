---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: b05f45d2f2725defb3d5fba7cb0fb622
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

 webforJ inclut une fonctionnalité de liaison de données qui intègre des composants UI avec des modèles de données backend dans des applications Java. Cette fonctionnalité comble le fossé entre l'UI et la couche de données afin que les changements dans l'UI se reflètent dans le modèle de données et vice versa, réduisant ainsi la complexité de la gestion des événements et de la synchronisation des données.

<AISkillTip skill="webforj-building-forms" />

## Concept {#concept}

La démonstration suivante présente une simple application webforJ pour enregistrer des super-héros en utilisant la liaison de données webforJ. L'application se compose de deux parties principales : `HeroRegistration.java` et `Hero.java`. 

Dans `HeroRegistration.java`, le code configure l'interface utilisateur avec un `TextField` pour entrer le nom du héros, un `ComboBox` pour sélectionner un superpouvoir, et un `Button` pour soumettre l'enregistrement.

La classe `Hero` définit le modèle de données avec des contraintes de validation sur le nom et le pouvoir du héros. Les entrées doivent être valides et respecter des critères spécifiés comme la longueur et le motif.

L'application utilise le `BindingContext` pour lier les composants UI aux propriétés de l'objet `Hero`. Lorsque l'utilisateur clique sur le bouton de soumission, l'application écrit les données saisies dans le formulaire dans le bean `Hero` si elles sont valides.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Champ de texte");
  private ComboBox power = new ComboBox("Pouvoir");
  private Button submit = new Button("Soumettre la candidature");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Voler", "Invisible", "VisionLaser", "Vitesse", "Téléportation");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Voler");

    // refléter les données bean dans le formulaire
    context.read(bean);

    submit.onClick(e -> {
      // écrire les données du formulaire dans le bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // faire quelque chose avec le bean
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

  @NotEmpty(message = "Le nom ne peut pas être vide")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Pouvoir non spécifié")
  @Pattern(regexp = "Voler|Invisible|VisionLaser|Vitesse|Téléportation", message = "Pouvoir invalide")
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
    return "Nom: " + name + ", Pouvoir: " + power;
  }
}
```

</TabItem>
</Tabs>

## Caractéristiques clés {#key-features}

- **Liaison Bidirectionnelle :** Prend en charge la liaison de données bidirectionnelle, permettant aux changements dans le modèle de données de mettre à jour l'UI, et aux interactions de l'utilisateur dans l'UI de mettre à jour le modèle de données.

- **Support de Validation :** Intègre des mécanismes de validation complets que vous pouvez personnaliser et étendre. Les développeurs peuvent implémenter leurs propres règles de validation ou utiliser des frameworks de validation existants comme Jakarta Validation pour vérifier l'intégrité des données avant de mettre à jour le modèle.

- **Extensibilité :** Peut être facilement étendue pour prendre en charge différents types de composants UI, des transformations de données et des scénarios de validation complexes.

- **Configuration Basée sur Annotations :** Utilise des annotations pour minimiser le code répétitif, rendant les liaisons entre les composants UI et les modèles de données déclaratifs et faciles à gérer.

## Sujets {#topics}

<DocCardList className="topics-section" />
