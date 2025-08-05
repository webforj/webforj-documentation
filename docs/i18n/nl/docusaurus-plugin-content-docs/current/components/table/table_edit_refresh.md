---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 0c57bd3fd3a9adb9cb7275e23efa725f
---
Het bewerken van gegevens binnen de `Table` werkt via interactie met de `Repository` die de gegevens voor de `Table` bevat. De `Repository` fungeert als een brug tussen de `Table` en de onderliggende dataset en biedt methoden voor gegevensophaling, -wijziging en -vernieuwing. Hieronder staat een voorbeeld dat gedrag implementeert om de "Titel" van een gewenste rij te bewerken.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

In het bovenstaande voorbeeld faciliteert de `TitleEditorComponent` klasse het bewerken van het "Titel" veld voor een geselecteerd `MusicRecord`. De component bevat een invoerveld voor de nieuwe titel, samen met "Opslaan" en "Annuleren" knoppen.

Om de bewerkcomponent te verbinden met de `Table`, is er een "Bewerken" knop toegevoegd aan de `Table` via een `VoidElementRenderer`. Op het klikken van deze knop wordt de `edit()` methode van de `TitleEditorComponent` geactiveerd, waardoor gebruikers de "Titel" kunnen aanpassen.

## Commit-methode {#commit-method}

Zodra de gebruiker de titel aanpast en op de "Opslaan" knop klikt, activeert de `TitleEditorComponent` de `save()` methode. Deze methode werkt de titel van het bijbehorende `MusicRecord` bij en verzendt een aangepaste `SaveEvent`.

De realtime-update van gegevens in de repository wordt bereikt via de `commit()` methode. Deze methode wordt toegepast binnen de `onSave` gebeurtenislistener, waardoor ervoor wordt gezorgd dat wijzigingen die via de bewerkcomponent zijn aangebracht in de onderliggende dataset worden weerspiegeld.

De `commit()` methode wordt aangeroepen om alle betrokken componenten te informeren dat de gegevens zijn gewijzigd. De `Table` vangt de `RepositoryCommitEvent` op en werkt bij op basis van de nieuwe gegevens.

:::tip Bijwerken en aanmaken van records
Het aanroepen van de `commit()` methode werkt zowel bestaande records bij als **voegt nieuwe records toe die zijn aangemaakt**.
:::
