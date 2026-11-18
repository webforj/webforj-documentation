---
title: Si vous venez de Swing
sidebar_position: 5
description: >-
  Map Java Swing concepts such as JFrame, layout managers, listeners, the event
  dispatch thread, table models, JOptionPane, and CardLayout to their webforJ
  counterparts.
_i18n_hash: a55f88307cad0553b509d1cbb9c3a3f3
---
Cette page établit une correspondance entre les concepts d’une application de bureau Java Swing et leurs équivalents dans webforJ. Dans les deux cas, l’interface utilisateur est entièrement en Java : les composants sont des objets Java, les événements sont gérés par des écouteurs et les mises en page sont composées dans le code. webforJ s’exécute sur le serveur, et le framework maintient le navigateur en phase avec l’état des composants. Consultez [Architecture des composants](/docs/architecture/controls-components) pour en savoir plus.

L’API webforJ est analogue à Swing, mais n’est pas compatible avec lui. Chaque écran est réécrit avec l’API webforJ, tandis que les classes qui ne dépendent pas de `javax.swing` ou de `java.awt`, comme les services, les entités et les règles de validation, sont appelées sans modification depuis les nouvelles vues.

## Fenêtres et cadres {#windows-and-frames}

Une application Swing crée un `JFrame`, y ajoute du contenu, puis l’affiche. Dans webforJ, la fenêtre correspond à la page du navigateur. La classe de l’application étend `App`, et le framework fournit un `Frame` qui contient le contenu visible. Lorsque le routage est activé avec `@Routify`, chaque écran est une classe annotée avec `@Route`, et le routeur l’affiche dans le cadre.

| Swing | webforJ | Référence |
| --- | --- | --- |
| `JFrame` | `App` et `Frame`, avec des écrans sous forme de classes `@Route` | [Applications routables](/docs/routing/routable-apps), [Routage](/docs/routing/overview) |
| `JFrame.setTitle` | `@FrameTitle` ou `HasFrameTitle` | [Titres des cadres de navigation](/docs/routing/navigational-frame-titles) |
| Méthode principale qui construit l’interface utilisateur | Sous-classe de `App` avec `@Routify` | [Bases de l’application](/docs/introduction/basics) |

## Panneaux et gestionnaires de mise en page {#panels-and-layout-managers}

Un `JPanel` associé à un gestionnaire de mise en page devient un `Composite` lié à un composant de mise en page. `FlexLayout` dispose les enfants en ligne ou en colonne, avec un espacement, un alignement et un retour à la ligne, et couvre la plupart des écrans créés avec `BoxLayout` ou `FlowLayout`. Une structure de fenêtre `BorderLayout` correspond à `AppLayout`, tandis que les autres panneaux `BorderLayout` correspondent à des conteneurs `FlexLayout` imbriqués.

| Swing | webforJ | Référence |
| --- | --- | --- |
| Sous-classe de `JPanel` | `Composite<T>` lié à un conteneur tel que `FlexLayout` ou `Div` | [Composition de composants](/docs/building-ui/composing-components) |
| `BoxLayout`, `FlowLayout` | `FlexLayout` | [FlexLayout](/docs/components/flex-layout) |
| `BorderLayout` | `AppLayout` pour une structure de fenêtre, conteneurs `FlexLayout` imbriqués pour les autres panneaux | [AppLayout](/docs/components/app-layout), [FlexLayout](/docs/components/flex-layout) |
| Formulaires `GridLayout`, `GridBagLayout` | `ColumnsLayout` | [ColumnsLayout](/docs/components/columns-layout) |
| `JSplitPane` | `Splitter` | [Splitter](/docs/components/splitter) |
| `JTabbedPane` | `TabbedPane` | [TabbedPane](/docs/components/tabbedpane) |
| `JToolBar` | `Toolbar` | [Toolbar](/docs/components/toolbar) |
| Fenêtre principale avec en-tête, menu latéral et zone de contenu | `AppLayout` | [AppLayout](/docs/components/app-layout) |

Un panneau Swing contenant un champ de texte et un bouton qui réagit aux clics devient la vue suivante :

```java
@Route("customers/new")
@FrameTitle("New customer")
public class NewCustomerView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField("Name");
  private final Button save = new Button("Save", ButtonTheme.PRIMARY);

  public NewCustomerView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    save.onClick(e -> OptionDialog.showMessageDialog("Saved " + name.getValue(), "Customer"));
    self.add(name, save);
  }
}
```

## Composants {#components}

La plupart des composants Swing ont un équivalent direct. Les composants textuels utilisent `setText()` pour leur légende, et les composants de saisie utilisent `getValue()` et `setValue()` pour leur contenu, comme décrit dans [Utilisation des composants](/docs/building-ui/using-components).

| Swing | webforJ | Référence |
| --- | --- | --- |
| `JButton` | `Button` | [Button](/docs/components/button) |
| `JLabel` | `Label` | [Utilisation des composants](/docs/building-ui/using-components) |
| `JTextField` | `TextField` | [TextField](/docs/components/fields/textfield) |
| `JPasswordField` | `PasswordField` | [PasswordField](/docs/components/fields/passwordfield) |
| `JFormattedTextField` | `MaskedTextField` et les autres champs masqués | [MaskedTextField](/docs/components/fields/masked/textfield) |
| `JTextArea` | `TextArea` | [TextArea](/docs/components/textarea) |
| `JCheckBox` | `CheckBox` | [CheckBox](/docs/components/checkbox) |
| `JRadioButton` et `ButtonGroup` | `RadioButton` et `RadioButtonGroup` | [RadioButton](/docs/components/radiobutton), [RadioButtonGroup](/docs/components/radiobuttongroup) |
| `JComboBox` | `ChoiceBox` pour un ensemble fixe, `ComboBox` lorsque les utilisateurs peuvent saisir une valeur | [ChoiceBox](/docs/components/lists/choicebox), [ComboBox](/docs/components/lists/combobox) |
| `JList` | `ListBox` | [ListBox](/docs/components/lists/listbox) |
| `JTree` | `Tree` | [Tree](/docs/components/tree) |
| `JProgressBar` | `ProgressBar` | [ProgressBar](/docs/components/progressbar) |
| `JSlider` | `Slider` | [Slider](/docs/components/slider) |

## Écouteurs et événements {#listeners-and-events}

Swing enregistre les écouteurs à l’aide de méthodes `addXxxListener`. Les composants webforJ proposent le même modèle : une méthode `addXxxListener` et, dans la plupart des cas, un alias plus court `on`. Chaque enregistrement renvoie un `ListenerRegistration`. L’appel de `remove()` sur celui-ci remplace `removeXxxListener` dans Swing. Consultez [Événements](/docs/building-ui/events).

| Swing | webforJ | Référence |
| --- | --- | --- |
| `ActionListener` sur un bouton | `onClick` ou `addClickListener` | [Button](/docs/components/button) |
| `DocumentListener` sur un champ de texte | `onModify` | [Événements](/docs/building-ui/events) |
| `ItemListener` sur une `JCheckBox` | `onToggle` | [CheckBox](/docs/components/checkbox) |
| `ChangeListener` et écouteurs de changement de propriété sur les valeurs | `onValueChange` | [Événements](/docs/building-ui/events) |
| `FocusListener` | `onFocus` et `onBlur` | [Événements](/docs/building-ui/events) |
| `KeyListener` sur un champ | `onKeypress` | [Événements](/docs/building-ui/events) |
| `removeXxxListener` | `ListenerRegistration.remove()` | [Suppression d’un événement](/docs/building-ui/events#removing-an-event) |

## Fil de distribution des événements et mises à jour de l’interface {#event-dispatch-thread-and-ui-updates}

Swing exige que toutes les opérations sur l’interface utilisateur soient exécutées sur le fil de distribution des événements. webforJ impose la même règle : chaque session utilisateur possède un fil `Environment`, et toutes les mises à jour de l’interface doivent y être effectuées. Les constructeurs, les gestionnaires d’événements et les méthodes du cycle de vie s’exécutent déjà sur ce fil. Un fil d’arrière-plan doit renvoyer ses modifications de l’interface à l’aide de `Environment.runLater(Environment, Runnable)`.

Récupérez la cible avec `Environment.getCurrent()` sur le fil `Environment`, avant le début du traitement en arrière-plan. Sur un fil d’arrière-plan, `getCurrent()` renvoie `null`. Consultez [Mises à jour asynchrones](/docs/advanced/asynchronous-updates) pour en savoir plus sur le modèle de threads et l’annulation des tâches avec `PendingResult`.

| Swing | webforJ | Référence |
| --- | --- | --- |
| Fil de distribution des événements | Fil `Environment` de la session | [Mises à jour asynchrones](/docs/advanced/asynchronous-updates#understanding-the-thread-model) |
| `SwingUtilities.invokeLater` | `Environment.runLater(Environment, Runnable)` | [Mises à jour asynchrones](/docs/advanced/asynchronous-updates#passing-the-target) |
| `SwingWorker` | Traitement en arrière-plan, par exemple avec `CompletableFuture`, puis `Environment.runLater` | [Mises à jour asynchrones](/docs/advanced/asynchronous-updates) |
| `javax.swing.Timer` | `Interval` | [Interval](/docs/advanced/interval) |
| Redémarrer un minuteur à chaque frappe | `Debouncer` | [Anti-rebond](/docs/advanced/debouncing) |

Le corps de `doInBackground` d’un `SwingWorker` devient la tâche en arrière-plan, et la méthode `done` devient les blocs `runLater` : l’un pour le résultat et l’autre pour l’échec.

```java
public class ReportView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final CustomerService service = new CustomerService();
  private final Button export = new Button("Export");
  private final Label status = new Label();

  public ReportView() {
    self.add(export, status);
    export.onClick(e -> {
      Environment env = Environment.getCurrent();
      CompletableFuture.supplyAsync(service::exportReport)
          .thenAccept(path -> Environment.runLater(env, () -> { status.setText("Saved to " + path); }))
          .exceptionally(error -> {
            Environment.runLater(env, () -> { status.setText("Export failed"); });
            return null;
          });
    });
  }
}
```

## Modèles et liaison de données {#models-and-data-binding}

Swing sépare les données des vues à l’aide d’interfaces de modèle telles que `TableModel` et `ListModel`, et de code qui copie manuellement les valeurs entre les champs et les objets. webforJ utilise un `Repository` comme source de données pour des composants tels que `Table`, et un `BindingContext` pour relier les champs de formulaire aux propriétés d’un bean dans les deux sens.

| Swing | webforJ | Référence |
| --- | --- | --- |
| `TableModel` | `Repository`, par exemple `CollectionRepository` | [Repository](/docs/advanced/repository/overview) |
| `ListModel` | Entrées `ListItem` ajoutées à un `ListBox`, un `ChoiceBox` ou un `ComboBox` | [Listes](/docs/components/lists/overview) |
| `fireTableDataChanged`, `fireTableRowsUpdated` | `commit()` et `commit(entity)` sur le dépôt | [Synchronisation des données](/docs/advanced/repository/overview#data-synchronization) |
| Copie des valeurs des champs vers un objet et inversement | `BindingContext` avec `read(bean)` et `write(bean)` | [Liaison de données](/docs/data-binding/overview), [Liaison automatique](/docs/data-binding/automatic-binding) |
| `InputVerifier` | Validateurs sur les liaisons ou Jakarta Validation | [Validation](/docs/data-binding/validation/overview), [Jakarta Validation](/docs/data-binding/validation/jakarta-validation) |

`BindingContext.of` lie les champs des composants aux propriétés du bean portant le même nom :

```java
public class CustomerForm extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField("Name");
  private final TextField email = new TextField("Email");
  private final BindingContext<Customer> context;

  public CustomerForm(Customer customer) {
    self.setDirection(FlexDirection.COLUMN).add(name, email);
    context = BindingContext.of(this, Customer.class);
    context.read(customer);
  }

  public boolean save(Customer customer) {
    return context.write(customer).isValid();
  }
}
```

## Boîtes de dialogue {#dialogs}

Les boîtes de dialogue d’options sont modales et bloquent l’exécution jusqu’à ce que l’utilisateur réponde, comme `JOptionPane`. Le code qui suit l’appel lit directement le résultat, sans rappel. Consultez [Boîtes de dialogue d’options](/docs/components/option-dialogs/overview).

| Swing | webforJ | Référence |
| --- | --- | --- |
| `JOptionPane.showMessageDialog` | `OptionDialog.showMessageDialog` | [Boîte de dialogue de message](/docs/components/option-dialogs/message) |
| `JOptionPane.showConfirmDialog` | `OptionDialog.showConfirmDialog`, qui renvoie `ConfirmDialog.Result` | [Boîte de dialogue de confirmation](/docs/components/option-dialogs/confirm) |
| `JOptionPane.showInputDialog` | `OptionDialog.showInputDialog` | [Boîte de dialogue de saisie](/docs/components/option-dialogs/input) |
| `JFileChooser` pour ouvrir un fichier sur l’ordinateur de l’utilisateur | `OptionDialog.showFileUploadDialog` | [Boîte de dialogue d’envoi de fichier](/docs/components/option-dialogs/file-upload) |
| `JFileChooser` pour parcourir les fichiers du serveur | `OptionDialog.showFileChooserDialog` | [Boîte de dialogue de sélection de fichier](/docs/components/option-dialogs/file-chooser) |
| `JDialog` avec du contenu personnalisé | `Dialog`, ouvert avec `open()` et fermé avec `close()` | [Dialog](/docs/components/dialog) |

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Delete this customer?", "Delete customer",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);

if (result == ConfirmDialog.Result.YES) {
  service.delete(customer);
}
```

## Tableaux {#tables}

Un `JTable` lit les données d’une sous-classe de `TableModel`. Un `Table<T>` webforJ contient une liste d’entités et lie chaque colonne à un accesseur ; il n’est donc pas nécessaire d’écrire une classe de modèle. `setItems` enveloppe une collection dans un dépôt, et `setRepository` accepte un dépôt reposant sur votre couche de données. Consultez [Table](/docs/components/table/overview).

| Swing | webforJ | Référence |
| --- | --- | --- |
| `JTable` avec un `TableModel` | `Table<T>` avec `setItems` ou `setRepository` | [Table](/docs/components/table/overview) |
| `TableColumn` et `getValueAt` | `addColumn(id, getter)` | [Colonnes](/docs/components/table/columns) |
| `ListSelectionModel` | `setSelectionMode`, `getSelectedItems`, `onItemSelect` | [Sélection](/docs/components/table/selection) |
| `MouseListener` qui détecte un double-clic | `onItemDoubleClick` | <JavadocLink type="table" location="com/webforj/component/table/Table" code="true">Table</JavadocLink> |
| `TableRowSorter` | `Column.setSortable(true)` | [Tri](/docs/components/table/sorting) |
| `RowFilter` | `setBaseFilter(Predicate)` sur un `CollectionRepository`, appliqué lors de l’appel à `commit()` | [Filtrage](/docs/components/table/filtering) |
| `TableCellRenderer` | `Renderer` défini avec `Column.setRenderer` | [Rendu](/docs/components/table/rendering) |

```java
Table<Customer> table = new Table<>();
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);
table.setSelectionMode(Table.SelectionMode.SINGLE);
table.setItems(service.findAll());
table.onItemDoubleClick(e -> confirmDelete(e.getItem()));
```

## Navigation {#navigation}

Les applications Swing changent d’écran en remplaçant des panneaux, souvent avec `CardLayout`, ou en ouvrant de nouvelles fenêtres. Dans webforJ, chaque écran est une route avec sa propre URL : l’historique du navigateur, les marque-pages et les liens profonds fonctionnent donc. Consultez [Routage](/docs/routing/overview).

| Swing | webforJ | Référence |
| --- | --- | --- |
| `CardLayout.show` ou remplacement du panneau de contenu | Vues `@Route` et `Router.getCurrent().navigate(View.class)` | [Définition des routes](/docs/routing/defining-routes), [Navigation entre les routes](/docs/routing/route-navigation) |
| `JMenu` ou liste latérale des écrans | `AppNav` avec des entrées `AppNavItem` liées aux routes | [AppNav](/docs/components/appnav) |
| Ouverture d’un écran avec des paramètres | Paramètres de route et paramètres de requête | [Modèles de route](/docs/routing/route-patterns), [Paramètres de requête](/docs/routing/query-parameters) |

```java
Router.getCurrent().navigate(NewCustomerView.class);
```

## Migrer écran par écran {#moving-screen-by-screen}

Il n’est pas nécessaire de réécrire l’application Swing en une seule fois. Webswing exécute dans le navigateur une application Swing, JavaFX ou SWT existante sans modifier son code source, et le composant `WebswingConnector` l’intègre dans une vue webforJ. Seuls les échanges de messages dans les deux sens nécessitent un petit ajout à l’application Swing, décrit ci-dessous. Les anciens et les nouveaux écrans cohabitent alors dans une même application, tandis que les écrans sont migrés un par un.

Le connecteur permet une communication dans les deux sens. `performAction` envoie des actions de webforJ vers l’application Swing, et `onAction` reçoit les actions envoyées par l’application Swing via l’API Java de Webswing. Pour renvoyer des actions, il faut ajouter cette API à l’application Swing.

Côté Swing, la dépendance `webswing-api` du dépôt Webswing fournit `WebswingUtil.getWebswingApi()`. Elle renvoie `null` lorsque l’application s’exécute comme une application de bureau classique, de sorte que le même code continue de fonctionner sur le bureau. Ici, un double-clic sur une ligne du tableau envoie le client sélectionné à webforJ au lieu d’ouvrir une boîte de dialogue Swing :

```java
WebswingApi api = WebswingUtil.getWebswingApi();

if (api != null) {
  api.sendActionEvent("select-customer", gson.toJson(customer), null);
} else {
  showEditDialog(customer);
}
```

- [Webswing](/docs/integrations/webswing/overview) présente l’architecture et la modernisation progressive.
- [Configuration](/docs/integrations/webswing/setup) traite du serveur Webswing et de CORS.
- [Communication](/docs/integrations/webswing/communication) explique `performAction` et `onAction`.
- [Tutoriel de modernisation](/docs/integrations/webswing/tutorial) remplace une boîte de dialogue de modification Swing par un formulaire webforJ, tout en conservant le tableau Swing.
