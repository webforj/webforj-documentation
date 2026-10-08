---
sidebar_position: 1
title: Navigatielevenscyclus
hide_giscus_comments: true
_i18n_hash: 14d81d1a9ff86af17370e0a7eb50608b
---
import DocCardList from '@theme/DocCardList';

Navigeren door verschillende weergaven in een webapp gaat gepaard met verschillende fasen, die mogelijkheden bieden om acties uit te voeren vóór, tijdens of na een overgang. De navigatielevenscyclus biedt een op evenementen gebaseerde systeem waarmee ontwikkelaars belangrijke aspecten van de navigatie kunnen beheren, zoals het valideren van gegevens, het controleren van toegang, het bijwerken van de UI en het afhandelen van opschoning.

Dit flexibele systeem stelt ontwikkelaars in staat om overgangen expliciet te beheren door in te haken op kritieke punten in het navigatieproces. Of je nu de navigatie wilt blokkeren, gegevens wilt ophalen wanneer een component wordt weergegeven, of niet-opgeslagen wijzigingen wilt beheren, je hebt volledige controle over de navigatiestroom via zijn levenscyclusgebeurtenissen en waarnemers.

## Overzicht van levenscyclusgebeurtenissen {#lifecycle-events-overview}

Het navigatieproces wordt beheerst door een reeks gebeurtenissen die worden geactiveerd tijdens routeovergangen. Deze gebeurtenissen stellen je in staat om te reageren op specifieke momenten in de levenscyclus:

1. **WillEnter**: Geactiveerd voordat je naar een route navigeert en voordat de component ervan aan de DOM wordt gekoppeld. Dit is ideaal voor taken zoals authenticatiecontroles of het blokkeren van de navigatie indien nodig.
2. **DidEnter**: Geactiveerd nadat de navigatie is voltooid en de component aan de DOM is gekoppeld. Deze gebeurtenis is geschikt voor acties zoals gegevens ophalen, animaties uitvoeren of focus instellen op UI-elementen.
3. **WillLeave**: Geactiveerd voordat je weg navigeert van de huidige route en voordat de component ervan uit de DOM wordt verwijderd. Het is nuttig voor het beheren van niet-opgeslagen gegevens, het vragen van bevestiging aan de gebruiker of het afhandelen van opschoontaken.
4. **DidLeave**: Geactiveerd nadat je naar een andere route bent overgestapt en nadat de component is verwijderd uit de DOM. Deze gebeurtenis is ideaal voor het opschonen van bronnen of het resetten van de UI voor toekomstig gebruik.
5. **Activate** (sinds `25.03`): Geactiveerd wanneer gecachte componenten worden geactiveerd in plaats van opnieuw te worden aangemaakt. Dit gebeurt wanneer je naar dezelfde route navigeert met verschillende parameters of wanneer je terugkeert naar een eerder bezochte route. De gebeurtenis wordt geactiveerd voor alle gecachte componenten in de routehiërarchie die in het huidige pad blijven, zodat zowel ouderlay-outs als kindcomponenten hun gegevens kunnen vernieuwen of de UI kunnen bijwerken op basis van nieuwe parameters, terwijl de componentstatus behouden blijft.

Deze gebeurtenissen bieden gedetailleerde controle over de navigatielevenscyclus, waardoor ontwikkelaars gegevensvalidatie, UI-updates en resourcebeheer tijdens routeovergangen kunnen coördineren.

## Ondwerpen {#topics}

<DocCardList className="topics-section" />
