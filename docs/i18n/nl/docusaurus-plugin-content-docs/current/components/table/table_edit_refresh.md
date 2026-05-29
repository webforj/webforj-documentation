---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 86d395b36fe0cdec90b5a29497a8b0d3
---
Het bewerken van gegevens binnen de `Table` gebeurt via interactie met de `Repository` die de gegevens voor de `Table` bevat. De `Repository` fungeert als een brug tussen de `Table` en de onderliggende dataset, en biedt methoden voor gegevensophaling, wijziging en vernieuwing. Hieronder volgt een voorbeeld dat gedrag implementeert om de "Titel" van een gewenste rij te bewerken op basis.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

In het bovenstaande voorbeeld faciliteert de `TitleEditorComponent` klasse het bewerken van het "Titel" veld voor een geselecteerd `MusicRecord`. De component bevat een invoerveld voor de nieuwe titel, samen met "Opslaan" en "Annuleren" knoppen.

Om de bewerkingscomponent te verbinden met de `Table`, wordt er een "Bewerken" knop toegevoegd aan de `Table` via een `VoidElementRenderer`. Wanneer op deze knop wordt geklikt, wordt de `edit()` methode van de `TitleEditorComponent` geactiveerd, waardoor gebruikers de "Titel" waarde kunnen aanpassen.

## Commit methode {#commit-method}

Zodra de gebruiker de titel wijzigt en op de "Opslaan" knop klikt, activeert de `TitleEditorComponent` de `save()` methode. Deze methode werkt de titel bij van het overeenkomstige `MusicRecord` en stuurt een aangepaste `SaveEvent`.

De real-time update van de gegevens in de repository wordt bereikt via de `commit()` methode. Deze methode wordt gebruikt binnen de `onSave` event listener, waardoor ervoor wordt gezorgd dat wijzigingen die via de bewerkingscomponent zijn aangebracht, worden weerspiegeld in de onderliggende dataset.

De `commit()` methode wordt aangeroepen om alle geïnteresseerde componenten te informeren dat de gegevens zijn gewijzigd. De `Table` vangt het `RepositoryCommitEvent` op en werkt bij op basis van de nieuwe gegevens.

:::tip Bijwerken en creëren van invoeren
Het aanroepen van de `commit()` methode werkt zowel bestaande invoeren bij als **voegt eventuele nieuwe invoeren die zijn aangemaakt toe**.
:::
