---
sidebar_position: 30
title: Bewerken en vernieuwen
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 84dec48293e6fa794713881310b6f303
---
Het bewerken van gegevens in de `Table` gebeurt via interactie met de `Repository` die de gegevens voor de `Table` bevat. De `Repository` vormt een brug tussen de `Table` en de onderliggende dataset en biedt methoden voor het ophalen, wijzigen en vernieuwen van gegevens. Hieronder staat een voorbeeld dat gedrag implementeert voor het bewerken van de "Title" van een gewenste rij.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

In het bovenstaande voorbeeld maakt de klasse `TitleEditorComponent` het mogelijk om het veld "Title" van een geselecteerde `MusicRecord` te bewerken. De component bevat een invoerveld voor de nieuwe titel, samen met de knoppen "Opslaan" en "Annuleren".

Om de bewerkingscomponent te verbinden met de `Table`, wordt via een `VoidElementRenderer` een knop "Bewerken" aan de `Table` toegevoegd. Als gebruikers op deze knop klikken, wordt de methode `edit()` van `TitleEditorComponent` aangeroepen, zodat ze de waarde van "Title" kunnen wijzigen.

## Commitmethode {#commit-method}

Zodra de gebruiker de titel wijzigt en op de knop "Opslaan" klikt, roept `TitleEditorComponent` de methode `save()` aan. Deze methode werkt de titel van de bijbehorende `MusicRecord` bij en verstuurt een aangepaste `SaveEvent`.

De gegevens in de repository worden in realtime bijgewerkt via de methode `commit()`. Deze methode wordt gebruikt in de gebeurtenislistener `onSave`, zodat wijzigingen die via de bewerkingscomponent zijn aangebracht, worden doorgevoerd in de onderliggende dataset.

De methode `commit()` wordt aangeroepen om alle geïnteresseerde componenten te laten weten dat de gegevens zijn gewijzigd. De `Table` vangt de `RepositoryCommitEvent` op en werkt zichzelf bij op basis van de nieuwe gegevens.

:::tip Items bijwerken en maken
De methode `commit()` bijwerken van bestaande items en **voegt ook nieuwe items in die zijn gemaakt**.
:::
