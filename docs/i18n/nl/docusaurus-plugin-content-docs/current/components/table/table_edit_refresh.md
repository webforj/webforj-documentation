---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 6ecf362668be7d0633c3c13e7da068ec
---
Het bewerken van gegevens binnen de `Table` werkt via interactie met de `Repository` die de gegevens voor de `Table` bevat. De `Repository` fungeert als een brug tussen de `Table` en de onderliggende dataset, en biedt methoden voor gegevensophaling, -wijziging en -vernieuwing. Hieronder staat een voorbeeld dat gedrag implementeert om de "Titel" van een gewenste rij aan te passen.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

In het bovenstaande voorbeeld faciliteert de `TitleEditorComponent` klasse het bewerken van het "Titel" veld voor een geselecteerde `MusicRecord`. De component bevat een invoerveld voor de nieuwe titel, samen met "Opslaan" en "Annuleren" knoppen.

Om de bewerkingscomponent te verbinden met de `Table`, wordt er een "Bewerken" knop toegevoegd aan de `Table` via een `VoidElementRenderer`. Door op deze knop te klikken, wordt de `edit()` methode van de `TitleEditorComponent` geactiveerd, waardoor gebruikers de "Titel" waarde kunnen aanpassen.

## Commit-methode {#commit-method}

Zodra de gebruiker de titel aanpast en op de "Opslaan" knop klikt, activeert de `TitleEditorComponent` de `save()` methode. Deze methode werkt de titel van de corresponderende `MusicRecord` bij en dispatcht een aangepaste `SaveEvent`.

De realtime-update van gegevens in de repository wordt bereikt via de `commit()` methode. Deze methode wordt gebruikt binnen de `onSave` eventlistener, waarmee ervoor wordt gezorgd dat wijzigingen die via de bewerkingscomponent zijn aangebracht, worden weerspiegeld in de onderliggende dataset.

De `commit()` methode wordt aangeroepen om alle geïnteresseerde componenten te informeren dat de gegevens zijn gewijzigd. De `Table` vangt de `RepositoryCommitEvent` en werkt bij op basis van de nieuwe gegevens.

:::tip Het bijwerken en creëren van invoeringen
Het aanroepen van de `commit()` methode werkt zowel bestaande invoeringen bij als **voegt eventuele nieuwe invoeringen toe die zijn aangemaakt**.
:::
