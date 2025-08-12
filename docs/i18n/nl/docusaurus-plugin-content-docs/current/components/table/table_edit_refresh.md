---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 39816123675d62a6dda185187e8d13e2
---
Het bewerken van gegevens binnen de `Table` werkt via interactie met de `Repository` die de gegevens voor de `Table` bevat. De `Repository` dient als een brug tussen de `Table` en de onderliggende dataset, en biedt methoden voor gegevensophaling, wijziging en vernieuwing. Hieronder volgt een voorbeeld dat gedrag implementeert om de "Titel" van een gewenste rij te bewerken.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

In het bovenstaande voorbeeld faciliteert de `TitleEditorComponent` klasse het bewerken van het "Titel" veld voor een geselecteerde `MusicRecord`. De component bevat een invoerveld voor de nieuwe titel, samen met "Opslaan" en "Annuleren" knoppen.

Om de bewerkingscomponent met de `Table` te verbinden, wordt er een "Bewerken" knop toegevoegd aan de `Table` via een `VoidElementRenderer`. Het klikken op deze knop activeert de `edit()` methode van de `TitleEditorComponent`, waardoor gebruikers de "Titel" kunnen wijzigen.

## Commit-methode {#commit-method}

Wanneer de gebruiker de titel wijzigt en op de "Opslaan" knop klikt, activeert de `TitleEditorComponent` de `save()` methode. Deze methode werkt de titel van de overeenkomstige `MusicRecord` bij en verstuurt een aangepaste `SaveEvent`.

De realtime-update van gegevens in de repository wordt bereikt via de `commit()` methode. Deze methode wordt gebruikt binnen de `onSave` gebeurtenislistener, zodat wijzigingen die zijn aangebracht via de bewerkingscomponent worden weerspiegeld in de onderliggende dataset.

De `commit()` methode wordt aangeroepen om alle geïnteresseerde componenten te informeren dat de gegevens zijn gewijzigd. De `Table` vangt de `RepositoryCommitEvent` op en werkt bij op basis van de nieuwe gegevens.

:::tip Bijwerken en creëren van items
Het aanroepen van de `commit()` methode werkt zowel bestaande items bij als **voegt nieuwe items toe die zijn aangemaakt**.
:::
