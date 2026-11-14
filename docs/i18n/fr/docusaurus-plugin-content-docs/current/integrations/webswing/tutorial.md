---
title: Tutoriel de modernisation
sidebar_position: 4
description: >-
  Modernize a Swing customer management app step by step by embedding it with
  WebswingConnector and layering in webforJ dialogs and forms.
_i18n_hash: ded3a5a3abe3905086532c58861652f8
---
Ce tutoriel explique comment moderniser une application Java Swing existante en l’intégrant à webforJ à l’aide de `WebswingConnector`. Vous apprendrez à rendre une application de bureau traditionnelle accessible sur le Web, puis à y ajouter progressivement des fonctionnalités Web modernes, telles que des boîtes de dialogue Web et des formulaires interactifs utilisant les composants webforJ.

:::note Prérequis
Avant de commencer ce tutoriel, suivez les étapes de [Configuration](./setup) pour configurer votre serveur Webswing et les paramètres CORS.
:::

:::tip Code source
Le code source complet de ce tutoriel est disponible sur GitHub : [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<VideoPlayer video="webswing/modernization-tutorial" width={1920} height={1080} title="Modernization tutorial" />

## Le scénario {#the-scenario}

Imaginez que vous disposez d’une application de gestion de clients créée avec Swing, en production depuis des années. Elle fonctionne bien, mais les utilisateurs s’attendent désormais à pouvoir y accéder sur le Web et à bénéficier d’une interface moderne. Plutôt que de tout réécrire, vous allez utiliser Webswing pour la rendre immédiatement accessible sur le Web, puis ajouter progressivement des fonctionnalités Web modernes, telles que des boîtes de dialogue et des formulaires Web utilisant les composants webforJ.

## Point de départ : l’application Swing {#starting-point-the-swing-app}

L’exemple d’application Swing est un tableau de clients proposant les opérations CRUD habituelles. Comme beaucoup d’applications Swing d’entreprise, elle suit des modèles standards :

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

Cette application fonctionne parfaitement comme application de bureau, mais n’est pas accessible sur le Web. Les utilisateurs doivent installer Java et exécuter le fichier JAR localement.

## Étape 1 : la rendre compatible avec Webswing {#step-1-making-it-webswing-aware}

La première étape consiste à faire en sorte que l’application Swing détecte si elle s’exécute sous Webswing. Elle peut ainsi adapter son comportement sans compromettre la compatibilité avec les environnements de bureau.

### Détecter l’environnement Webswing {#detecting-the-webswing-environment}

Ajoutez la dépendance de l’API Webswing à votre projet Swing :

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Modifiez ensuite votre application pour détecter l’environnement d’exécution Webswing :

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

L’idée clé est que `WebswingUtil.getWebswingApi()` renvoie `null` lorsque l’application s’exécute comme une application de bureau classique, ce qui permet de conserver la compatibilité avec les deux modes.

### Adapter le comportement au déploiement Web {#adapting-behavior-for-web-deployment}

Une fois la détection en place, vous pouvez adapter le comportement de l’application. Le changement le plus important concerne la gestion des interactions utilisateur :

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

En choisissant le comportement en fonction de la valeur de `isWebswing`, la base de code peut prendre en charge les deux environnements.

## Étape 2 : créer le wrapper webforJ {#step-2-creating-the-webforj-wrapper}

Maintenant que l’application Swing peut communiquer par événements, créez une application webforJ qui intègre l’application Swing et ajoute des fonctionnalités Web modernes, telles que des boîtes de dialogue et des formulaires Web.

### Configurer le connecteur {#setting-up-the-connector}

Le composant `WebswingConnector` intègre votre application hébergée par Webswing dans une vue webforJ :

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

Le connecteur se connecte à votre serveur Webswing et établit un canal de communication bidirectionnel.

### Gérer les événements provenant de Swing {#handling-events-from-swing}

Lorsque l’application Swing envoie des événements (par exemple, lorsqu’un utilisateur double-clique sur une ligne), le connecteur les reçoit :

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

Au lieu de la boîte de dialogue Swing, les utilisateurs voient maintenant un formulaire Web moderne créé avec les composants webforJ.

## Étape 3 : communication bidirectionnelle {#step-3-bidirectional-communication}

L’intégration devient particulièrement puissante lorsque la communication s’effectue dans les deux sens. L’application webforJ peut renvoyer des mises à jour à l’application Swing, ce qui permet de synchroniser les deux interfaces utilisateur.

### Envoyer des mises à jour à Swing {#sending-updates-to-swing}

Après que l’utilisateur a modifié les informations d’un client dans la boîte de dialogue webforJ :

```java
dialog.onSave(() -> {
  // Send updated customer back to Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Traiter les mises à jour dans Swing {#processing-updates-in-swing}

L’application Swing écoute ces mises à jour et actualise son affichage :

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

## Avantages de l’architecture {#architecture-benefits}

Cette approche présente plusieurs avantages par rapport à une réécriture complète :

### Déploiement Web immédiat {#immediate-web-deployment}

Votre application Swing devient immédiatement accessible sur le Web, sans aucune modification du code. Les utilisateurs peuvent y accéder depuis un navigateur pendant que vous travaillez à l’ajout de nouvelles fonctionnalités.

### Amélioration progressive {#progressive-enhancement}

Commencez par remplacer uniquement la boîte de dialogue de modification, puis remplacez progressivement d’autres composants :

1. **Phase 1** : intégrer toute l’application Swing et remplacer uniquement la boîte de dialogue de modification
2. **Phase 2** : ajouter une navigation et des menus webforJ autour de l’application intégrée
3. **Phase 3** : remplacer le tableau par un tableau webforJ, tout en conservant Swing pour les fonctionnalités irremplaçables
4. **Phase 4** : remplacer, à terme, tous les composants Swing

### Réduction des risques {#risk-mitigation}

Comme l’application Swing d’origine reste fonctionnelle, vous pouvez :

- Revenir au déploiement de bureau si nécessaire
- Tester les nouvelles fonctionnalités parallèlement aux fonctionnalités existantes
- Migrer les utilisateurs progressivement
- Conserver la même logique métier
