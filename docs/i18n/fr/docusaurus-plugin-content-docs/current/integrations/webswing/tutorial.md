---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 97df9e800c5792a1ff22fb6e0e9a33e9
---
Ce tutoriel explique comment moderniser une application Java Swing existante en l'intégrant avec webforJ à l'aide du `WebswingConnector`. Vous apprendrez à rendre une application de bureau traditionnelle accessible sur le web et à ajouter progressivement des fonctionnalités web modernes, telles que des dialogues basés sur le web et des formulaires interactifs, en utilisant les composants webforJ.

:::note Prérequis
Avant de commencer ce tutoriel, complétez les étapes de [Configuration et Installation](./setup) pour configurer votre serveur Webswing et les paramètres CORS.
:::

:::tip Code source
Le code source complet de ce tutoriel est disponible sur GitHub : [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## Le scénario {#the-scenario}

Imaginez que vous avez une application de gestion de clients construite avec Swing qui est en production depuis des années. Elle fonctionne bien, mais les utilisateurs s'attendent maintenant à un accès web et à une interface moderne. Plutôt que de réécrire depuis le début, vous utiliserez Webswing pour la rendre immédiatement accessible sur le web, puis ajouter progressivement des fonctionnalités web modernes, telles que des dialogues et des formulaires basés sur le web, en utilisant les composants webforJ.

## Point de départ : l'application Swing {#starting-point-the-swing-app}

L'exemple d'application Swing est un tableau de clients avec des opérations CRUD typiques. Comme beaucoup d'applications Swing d'entreprise, elle suit des modèles standards :

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Name", "Company", "Email" };
    model = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    table = new JTable(model);
    table.setRowHeight(30);
    table.setRowSelectionAllowed(true);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          // Handle double-click to edit
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "Name:", nameField,
        "Company:", companyField,
        "Email:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Edit Customer",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Cette application fonctionne parfaitement en tant qu'application de bureau, mais manque d'accessibilité web. Les utilisateurs doivent installer Java et exécuter le fichier JAR localement.

## Étape 1 : la rendre consciente de Webswing {#step-1-making-it-webswing-aware}

La première étape est de faire en sorte que l'application Swing détecte si elle fonctionne sous Webswing. Cela lui permet d'adapter son comportement sans rompre la compatibilité avec le bureau.

### Détection de l'environnement Webswing {#detecting-the-webswing-environment}

Ajoutez la dépendance de l'API Webswing à votre projet Swing :

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Ensuite, modifiez votre application pour détecter l'environnement d'exécution Webswing :

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

L'idée clé ici est que `WebswingUtil.getWebswingApi()` renvoie `null` lorsqu'elle fonctionne comme une application de bureau classique, ce qui vous permet de maintenir la compatibilité en mode double.

### Adapter le comportement pour le déploiement web {#adapting-behavior-for-web-deployment}

Avec la détection en place, vous pouvez maintenant adapter le comportement de l'application. Le changement le plus important est la manière dont les interactions des utilisateurs sont gérées :

```java
private void handleDoubleClick(MouseEvent e) {
  int row = table.rowAtPoint(e.getPoint());
  if (row >= 0 && row < customers.size()) {
    Customer customer = customers.get(row);

    if (isWebswing) {
      api.sendActionEvent("select-customer", gson.toJson(customer), null);
    } else {
      showEditDialog(customer);
    }
  }
}
```

En branchant le comportement selon la valeur de `isWebswing`, le code peut gérer les deux environnements.

## Étape 2 : créer le wrapper webforJ {#step-2-creating-the-webforj-wrapper}

Maintenant que l'application Swing peut communiquer par événements, créez une application webforJ qui intègre l'application Swing et ajoute des fonctionnalités web modernes telles que des dialogues et des formulaires basés sur le web.

### Configuration du connecteur {#setting-up-the-connector}

Le composant `WebswingConnector` intègre votre application hébergée par Webswing dans une vue webforJ :

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

Le connecteur se connecte à votre serveur Webswing, établissant un canal de communication bidirectionnel.

### Gestion des événements depuis Swing {#handling-events-from-swing}

Lorsque l'application Swing envoie des événements (comme lorsque l'utilisateur double-clique sur une ligne), le connecteur les reçoit :

```java
connector.onAction(event -> {
  switch (event.getActionName()) {
    case "select-customer":
      event.getActionData().ifPresent(data -> {
        JsonObject customer = JsonParser.parseString(data).getAsJsonObject();
        CustomerForm dialog = new CustomerForm(customer);
        self.add(dialog);
        dialog.onSave(() -> {
          Gson gson = new Gson();
          connector.performAction("update-customer", gson.toJson(customer));
        });
      });
      break;
  }
});
```

Maintenant, au lieu du dialogue Swing, les utilisateurs voient un formulaire web moderne construit avec les composants webforJ.

## Étape 3 : communication bidirectionnelle {#step-3-bidirectional-communication}

L'intégration devient puissante lorsque la communication s'effectue dans les deux sens. L'application webforJ peut envoyer des mises à jour à l'application Swing, maintenant ainsi les deux interfaces synchronisées.

### Envoi de mises à jour à Swing {#sending-updates-to-swing}

Après que l'utilisateur a modifié un client dans le dialogue webforJ :

```java
dialog.onSave(() -> {
  // Envoyer le client mis à jour vers Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Traitement des mises à jour dans Swing {#processing-updates-in-swing}

L'application Swing écoute ces mises à jour et rafraîchit son affichage :

```java
private void setupWebswingListeners() {
  api.addBrowserActionListener(event -> {
    if ("update-customer".equals(event.getActionName())) {
      Customer updated = gson.fromJson(event.getData(), Customer.class);
      updateCustomer(updated);
    }
  });
}
```

## Avantages architecturaux {#architecture-benefits}

Cette approche offre plusieurs avantages par rapport à une réécriture complète :

### Déploiement web immédiat {#immediate-web-deployment}

Votre application Swing devient immédiatement accessible sur le web sans modifications de code. Les utilisateurs peuvent y accéder via un navigateur pendant que vous travaillez sur les améliorations.

### Amélioration progressive {#progressive-enhancement}

Commencez par remplacer juste le dialogue de modification, puis remplacez progressivement d'autres composants :

1. **Phase 1** : Intégrer l'ensemble de l'application Swing, remplacer uniquement le dialogue de modification
2. **Phase 2** : Ajouter la navigation et les menus webforJ autour de l'application intégrée
3. **Phase 3** : Remplacer le tableau par un tableau webforJ, en conservant Swing pour les fonctionnalités non remplaçables
4. **Phase 4** : Finalement remplacer tous les composants Swing

### Atténuation des risques {#risk-mitigation}

Puisque l'application Swing originale reste fonctionnelle, vous pouvez :

- Revenir au déploiement de bureau si nécessaire
- Tester de nouvelles fonctionnalités aux côtés des fonctionnalités existantes
- Migrer les utilisateurs progressivement
- Maintenir la même logique métier
