---
sidebar_position: 0
title: TestBed
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  The TestBed is webforJ's browserless testing, which runs views and components
  in plain JUnit tests without a browser or a server.
sidebar_class_name: new-content
_i18n_hash: a97354d7621ab9d89fc8b88c7592847f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# TestBed <DocChip chip='since' label='26.03' />
<!-- vale Google.Headings = YES -->

Tester une vue webforJ signifie généralement démarrer l’application, ouvrir un navigateur et le piloter avec Selenium ou Playwright. Cela permet de détecter ce qui n’est visible que dans un navigateur, mais chaque test nécessite un navigateur, un serveur et du temps d’attente.

TestBed permet de tester webforJ sans navigateur. Une vue s’exécute dans un simple test JUnit. Aucun navigateur n’est ouvert et aucun serveur n’est démarré. Le test ajoute la vue, interagit avec elle comme le ferait un utilisateur et vérifie le résultat depuis Java. Les composants sont ajoutés, déclenchent leurs événements et naviguent exactement comme dans une application en cours d’exécution. Chaque interaction se termine avant l’exécution de la ligne suivante du test : rien n’attend et rien ne devient instable.

## Fonctionnement d’un test {#how-a-test-works}

Un test TestBed suit les étapes d’un utilisateur. Il trouve un composant sur la page, interagit avec lui et vérifie ce que la page affiche ensuite. Le formulaire suivant permet de s’abonner avec une adresse e-mail ; son bouton **Subscribe** reste désactivé tant que le champ n’en contient pas :

```java title="NewsletterForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

Le test trouve le champ et le bouton, saisit une adresse, clique sur le bouton et vérifie le message de confirmation :

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

Chaque étape correspond à une partie de TestBed :

- **Rechercher.** `ComponentLocator` décrit un composant comme le ferait un utilisateur pour le repérer, à partir de son type et de ce qu’il affiche, par exemple un champ grâce à son libellé. Consultez [Recherche de composants](./finding-components).
- **Interagir.** `UserInteraction` reproduit les gestes d’un utilisateur, et uniquement ceux qu’un utilisateur peut effectuer. Cliquer sur un bouton désactivé fait échouer le test au lieu d’exécuter son écouteur. Consultez [Interaction utilisateur](./user-interaction).
- **Vérifier.** `verifyThat` vérifie ce que l’utilisateur verrait sur un composant, tandis qu’un instantané compare la vue entière à une description enregistrée. Consultez [Assertions](./assertions) et [Instantanés](./snapshots).

Le test n’accède jamais directement aux champs de la vue. Il se base sur ce que la page affiche : il continue donc de réussir lorsque la vue est construite différemment et échoue lorsque l’utilisateur verrait autre chose.

Au-delà des vues individuelles, TestBed permet de naviguer entre les routes, de tester les vues sécurisées, de répondre aux boîtes de dialogue et d’exécuter des minuteries, comme le montre [Tester des applications](./testing-apps). Il peut également s’exécuter dans une application Spring Boot avec ses beans et sa sécurité.

Les tests dans un navigateur restent utiles pour vérifier ce que seul un navigateur peut afficher, comme le style, la mise en page et le comportement côté client. TestBed couvre le reste : le comportement de vos vues lorsque l’utilisateur interagit avec elles.

Commencez par [Prise en main](./getting-started) pour ajouter la dépendance et écrire un premier test.

## Sujets {#topics}

<DocCardList className="topics-section" />
