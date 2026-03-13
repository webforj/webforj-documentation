---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 1a8c3b1ab877d759906737431d8601e1
---
Het bewerken van gegevens binnen de `Table` werkt via interactie met de `Repository` die de gegevens voor de `Table` bevat. De `Repository` fungeert als een brug tussen de `Table` en de onderliggende dataset, en biedt methoden voor het ophalen, wijzigen en vernieuwen van gegevens. Hieronder volgt een voorbeeld dat de functionaliteit implementeert om de "Titel" van een gewenste rij te bewerken.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

In het bovenstaande voorbeeld faciliteert de `TitleEditorComponent`-klasse het bewerken van het "Titel"-veld voor een geselecteerde `MusicRecord`. De component bevat een invoerveld voor de nieuwe titel, samen met "Opslaan" en "Annuleren" knoppen.

Om de bewerkingscomponent met de `Table` te verbinden, wordt een "Bewerk" knop toegevoegd aan de `Table` via een `VoidElementRenderer`. Het klikken op deze knop activeert de `edit()`-methode van de `TitleEditorComponent`, waardoor gebruikers de "Titel" waarde kunnen wijzigen.

## Commit-methode {#commit-method}

Zodra de gebruiker de titel heeft aangepast en op de knop "Opslaan" klikt, activeert de `TitleEditorComponent` de `save()`-methode. Deze methode werkt de titel van de overeenkomstige `MusicRecord` bij en verzendt een aangepaste `SaveEvent`.

De realtime-update van gegevens in de repository wordt bereikt via de `commit()`-methode. Deze methode wordt gebruikt binnen de `onSave`-evenementlistener, zodat wijzigingen die via de bewerkingscomponent zijn aangebracht, worden weerspiegeld in de onderliggende dataset.

De `commit()`-methode wordt aangeroepen om alle geïnteresseerde componenten te informeren dat de gegevens zijn gewijzigd. De `Table` vangt de `RepositoryCommitEvent` en update op basis van de nieuwe gegevens.

:::tip Bijwerken en aanmaken van vermeldingen
Het aanroepen van de `commit()`-methode werkt zowel bestaande vermeldingen bij, als **voegt nieuwe vermeldingen toe die zijn aangemaakt**.
:::
