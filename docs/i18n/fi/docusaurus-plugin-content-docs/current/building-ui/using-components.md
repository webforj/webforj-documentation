---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 97722c8e3bf6c3129c078d8ae23cf2a4
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Komponentit ovat webforJ-sovellusten rakennuspalikoita. Käytitpä sitten sisäänrakennettuja komponentteja, kuten `Button` ja `TextField`, tai työsi tarjoamia mukautettuja komponentteja, vuorovaikutus niiden kanssa seuraa samaa johdonmukaista mallia: määrität ominaisuuksia, hallitset tilaa ja koostet komponentteja asetteluiksi.

Tässä oppaassa keskitytään näihin päivittäisiin toimintoihin: ei siihen, miten komponentit toimivat sisäisesti, vaan siihen, miten niitä käytetään käytännössä.

## Komponenttien ominaisuudet {#component-properties}

Jokainen komponentti tarjoaa ominaisuuksia, jotka hallitsevat sen sisältöä, ulkoasua ja käyttäytymistä. Suurin osa näistä pitää sisällään erillisiä, tyypitettyjä Java-menetelmiä (`setText()`, `setTheme()`, `setExpanse()`, ja niin edelleen), ja tämä on pääasiallinen tapa, jolla määrität komponentit webforJ:ssä. Alla olevat osiot käsittelevät ominaisuuksia ja menetelmiä, jotka soveltuvat laajasti eri komponenttityypeille.

### Tekstisisältö {#text-content}

`setText()`-menetelmä asettaa komponentin näkyvän tekstin kirjaimellisesti, kuten painikkeen kuvauksen tai labelin sisällön. Syötekomponenteille, kuten `TextField`, käytä sen sijaan `setValue()` asettaaksesi kentän nykyisen arvon.

```java
Button button = new Button();
button.setText("Napsauta minua");

Label label = new Label();
label.setText("Tila: valmis");

TextField field = new TextField();
field.setValue("Alkuarvo");
```

`setText()`-menetelmällä kirjoitettu markup näkyy kirjaimellisesti ja sitä ei koskaan ajetaan, mikä estää käyttäjän syöttämistä tai ulkoista dataa tulkitsemasta live-markupina.

```java
// Näkyy kirjaimellisesti "<b>Tila: valmis</b>"
component.setText("<b>Tila: valmis</b>");
```

:::note Käyttämällä `<html>`-tagia
WebforJ:n aikaisemmat versiot käsittelivät `setText()`-menetelmään käärittyä arvoa `<html>`-tagissa HTML:nä. Tämä käyttäytyminen on poistettu käytöstä ja poistuu webforJ 27.00:ssa.

Ensimmäisen kerran, kun `<html>`-kääritty arvo saavuttaa `setText()`, lokitetaan varoitus, jossa mainitaan komponentti ja kutsupaikka, jotta kutsu voidaan siirtää `setHtml()`-menetelmään.

Ottaaksesi webforJ 27.00:n oletusasetuksen käyttöön etukäteen, aseta `webforj.legacyHtmlInText` arvoon `false`. Spring-sovelluksessa sama arvo asetetaan `webforj.legacy-html-in-text` kautta.

```java
// webforj.legacyHtmlInText = true (oletus)
component.setText("<html><b>Tila: valmis</b></html>"); // renderoi lihavoidun

// webforj.legacyHtmlInText = false
component.setText("<html><b>Tila: valmis</b></html>"); // näyttää merkit <b>Tila: valmis</b>
```
:::

### HTML:n renderöinti {#rendering-html}

Jotkut komponentit tukevat myös `setHtml()`-menetelmää, kun tarvitset inline HTML-markupin renderöimistä sisällössä:

```java
Div container = new Div();
container.setHtml("<strong>Lihavoitu teksti</strong> ja <em>kursivoitu teksti</em>");
```

:::danger Cross-site Scripting (XSS)
Varotoimenpiteenä [cross-site scripting (XSS) hyökkäyksiltä](/docs/security/application-security/common-threats#cross-site-scripting-xss), käytä `setHtml()`-menetelmää vain sisällössä, jota hallitset suoraan.
:::

### HTML-ominaisuudet {#html-attributes}

Suurin osa määrittelystä webforJ:ssä tehdään tyypitetyllä Java-menetelmillä eikä raakatextillä HTML-ominaisuuksilla. Kuitenkin, `setAttribute()`-menetelmä on hyödyllinen, kun haluat siirtää saavutettavuusominaisuuksia, joille ei ole omistettua API:a:

```java
Button button = new Button("Lähetä");
button.setAttribute("aria-label", "Lähetä lomake");
button.setAttribute("aria-describedby", "lomake-vihje");
```

:::note Tarkista komponentin tuki
Kaikki komponentit eivät tue satunnaisia ominaisuuksia. Tämä riippuu taustalla olevan komponentin toteutuksesta.
:::

### Komponentti-ID:t {#component-ids}

Voit määrittää ID:n komponentin HTML-elementille käyttämällä `setAttribute()`-menetelmää:

```java
Button submitButton = new Button("Lähetä");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Sähköposti");
emailField.setAttribute("id", "email-input");
```

DOM-ID:t ovat yleisesti käytössä testivalitsijoiden ja CSS:n kohdistamiseen tyylitiedostoissasi.

:::tip Suosi luokkia monikomponenttien kohdistamiseen
Erilaisista CSS-luokista poiketen, ID:t tulisi olla ainutlaatuisia sovelluksessasi. Jos tarvitset useiden komponenttien kohdistamista, käytä `addClassName()`-menetelmää sen sijaan.
:::

:::info Kehyksen hallinnoimat ID:t
WebforJ määrittää myös automaattisia tunnisteita komponentteille sisäisesti. Palvelinpuolen ID (jota käytetään `getComponentId()`-menetelmän avulla) on käytössä kehysseurantaan, kun taas asiakaspuolen ID (jota käytetään `getClientComponentId()`-menetelmän avulla) on käytössä asiakas-palvelin-viestintään. Nämä ovat erillisiä DOM `id`-ominaisuudesta, jonka asetat `setAttribute()`-menetelmän avulla.
:::

### Tyylit {#styling}

Kolme menetelmää kattaa suurimman osan tyylitarpeista: `setStyle()` yksittäisille CSS-ominaisuusarvoille, ja `addClassName()` ja `removeClassName()` CSS-luokkien lisäämiseksi tai poistamiseksi, jotka on määritelty tyylitiedostoissasi. Käytä `setStyle()`-menetelmää pienille tai kertaluonteisille tyylimuutoksille, ja käytä CSS-luokkia isompien tai uudelleenkäytettävien tyylien soveltamiseen.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Vaihda");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

:::note Perinteinen lähestymistapa
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) on perinteinen lähestymistapa ja sitä ei yleisesti suositella uusiin projekteihin. Useimmissa tapauksissa pidä tyylisi erillisissä CSS-tiedostoissa.
:::

## Komponentin tila {#component-state}

Sisällön ja ulkoasun lisäksi komponentilla on tilan ominaisuuksia, jotka määrittävät, ovatko ne näkyviä ja reagoivatko ne käyttäjän vuorovaikutukseen. Kaksi yleisimmin käytettyä ovat `setVisible()` ja `setEnabled()`.

`setVisible()` hallitsee, näkyykö komponentti lainkaan käyttöliittymässä. `setEnabled()` hallitsee sitä, hyväksyykö se syötteen tai vuorovaikutuksen pysyessään näkyvänä. Useimmissa tapauksissa häivyttäminen on suositeltavampaa kuin piilottaminen: poistettu painike kertoo silti, että toiminto on olemassa, mutta ei ole vielä saatavilla, mikä on vähemmän häiritsevää kuin sen ilmestyminen ja katoaminen.

```java
// Paljasta lisäkenttä, kun valintaruutu on valittu
TextField advancedField = new TextField("Lisäasetukset");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Näytä lisäasetukset");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Ota painike käyttöön vain, kun vaadittu kenttä on täytetty
Button submitButton = new Button("Lähetä");
submitButton.setEnabled(false);

TextField nameField = new TextField("Nimi");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

Seuraava kirjautumislomake esittelee `setEnabled()`-menetelmää käytännössä. Kirjautumispainike pysyy pois käytöstä, kunnes molemmissa kentissä on sisältöä, mikä tekee käyttäjälle selväksi, että syöte on pakollista ennen etenemistä:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## Työskentely säiliöiden kanssa {#working-with-containers}

WebforJ:ssä asettelu hoidetaan säiliöiden avulla, jotka ovat komponentteja, jotka sisältävät muita komponentteja ja hallitsevat niiden asettelua. Älä sijoita lapsikomponentteja manuaalisesti; sen sijaan, lisää ne säiliöön ja määritä sen asetteluominaisuudet.

### Komponenttien lisääminen {#adding-components}

Kaikki säiliöt tarjoavat `add()`-menetelmän. Voit siirtää komponentteja yksitellen tai kaikki kerralla:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Napsauta minua"));

TextField nameField = new TextField("Nimi");
TextField emailField = new TextField("Sähköposti");
Button submitButton = new Button("Lähetä");

container.add(nameField, emailField, submitButton);
```

### Asettelu vaihtoehdot {#layout-options}

`FlexLayout` on webforJ:n pääasiallinen asettelu säiliö ja kattaa suurimman osan käyttötapauksista: rivit, sarakkeet, kohdistus, väli ja kierrätys. Monimutkaisemmille järjestelyille, kuten CSS Gridille tai mukautetuille sijoituksille, voit soveltaa CSS:ää suoraan `setStyle()` tai `addClassName()` avulla mihin tahansa säiliökomponenttiin. Katso [FlexLayout](/docs/components/flex-layout) -dokumentaatio täydellisten asettelu vaihtoehtojen listalle.

### Osioiden näyttäminen ja piilottaminen {#showing-hiding-sections}

Yksi yleinen käytto `setVisible()` -säiliöissä on paljastaa lisää UI:ta vain silloin, kun se on merkityksellistä. Tämä pitää käyttöliittymän keskittyneenä ja vähentää visuaalista hälinää. Sen sijaan, että siirtyisit uuteen näkymään, voit näyttää osan nykyisestä asettelusta suoraan käyttäjän syötteeseen reagoinnin avulla.

Seuraava asetuspaneeli havainnollistaa tätä: perusilmoitusasetukset ovat aina näkyvissä ja lisäasetusten osio ilmestyy vain, kun käyttäjä pyytää sitä. Tallenna-painike aktivoituu heti, kun jokin asetus muuttuu:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Säiliöhallinta {#container-management}

Käytä `remove()` ja `removeAll()` ottaaksesi komponentit pois säiliöstä ajonaikana:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Väliaikainen");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Tämä on hyödyllistä, kun sinun täytyy vaihtaa sisältö kokonaan, kuten vaihtaa latausindikaattori ladattuun dataan.

## Lomakevarmennus {#form-validation}

Useiden komponenttien koordinoiminen lähetys toimintamalliin on yksi yleisimmistä kaavoista webforJ:n UI:issa. Ydinidea on yksinkertainen: jokainen syötekenttä rekisteröi kuuntelijan, ja aina kun jokin arvo muuttuu, lomake arvioi uudelleen, ovatko kaikki kriteerit täyttyneet ja päivittää lähetyspainiketta vastaavasti.

Tämä on suositeltavampaa kuin näyttää varmistusvirheitä vasta sen jälkeen, kun käyttäjä on napsauttanut lähetyspainiketta, koska se antaa jatkuvaa palautetta ja estää tarpeettomia lähetyksiä. Lähetyspainike toimii indikaattorina: pois käytöstä tarkoittaa, että lomake ei ole valmis, päällä tarkoittaa, että se on valmis.

Tässä yhteydenottolomakkeessa nimen kenttä ei saa olla tyhjää, sähköpostin on sisällettävä `@`-merkki ja viestin on oltava vähintään 10 merkkiä pitkä:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynaamiset sisältöpäivitykset {#dynamic-content-updates}

Komponenttien ei tarvitse pysyä kiinteässä tilassa niiden luomisen jälkeen. Voit päivittää tekstiä, vaihtaa CSS-luokkia ja vaihtaa käytettävyyden tilaa mihin tahansa kohtaan sovellustapahtumien reagoimisen takia. Yksi yleinen esimerkki on palautteen antaminen pitkään kestäessä tehtävässä:

```java
Label statusLabel = new Label("Valmis");
Button startButton = new Button("Aloita prosessi");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Käsitellään...");
    statusLabel.addClassName("processing");
    
    performTask(() -> {
        statusLabel.setText("Valmis");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Painikkeen poistaminen käytöstä samalla, kun tehtävä suoritetaan, estää kaksoislähetyksiä, ja etikettiä päivittämällä pidät käyttäjän tietoisena siitä, mitä tapahtuu.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver`-rajapinta antaa sinun tarkkailla komponentin elinkaaritapahtumia komponentin ulkopuolelta. Tämä on hyödyllistä, kun sinun tarvitsee reagoida komponentin luomiseen tai tuhoamiseen ilman, että sinun tarvitsee muuttaa sen toteutusta. Esimerkiksi voit käyttää sitä ylläpitämään aktiivisten komponenttien rekisteriä tai vapauttamaan ulkoisia resursseja, kun komponentti poistetaan.

### Peruskäyttö {#basic-usage}

Kutsu `addLifecycleObserver()`-menetelmää mihin tahansa komponenttiin rekisteröidäksesi palautteen. Palautteen saa komponentti ja elinkaaritapahtuma:

```java
Button button = new Button("Käytä minua");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Painike luotiin");
            break;
        case DESTROY:
            System.out.println("Painike tuhottiin");
            break;
    }
});
```

### Malli: Resurssirekisteri {#pattern-resource-registry}

DESTROY-tapahtuma on erityisen hyödyllinen pitämään rekisteri automaattisesti synkronoituna. Sen sijaan, että poistat komponentteja manuaalisesti, kun niitä ei enää tarvita, annat komponentin ilmoittaa rekisterille itselleen:

```java
public class ResourceRegistry {
    private final Map<String, Component> activeComponents = new ConcurrentHashMap<>();
    
    public void track(Component component, String name) {
        activeComponents.put(name, component);
        
        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                activeComponents.remove(name);
            }
        });
    }
}
```

### Malli: Komponenttikoordinaatio {#pattern-component-coordination}

Koordinoija-luokka, joka hallitsee joukkoa liittyviä komponentteja, voi käyttää samaa lähestymistapaa pitääkseen sisäisen luettelonsa tarkkana:

```java
public class FormCoordinator {
    private final List<DwcComponent<?>> managedComponents = new ArrayList<>();
    
    public void manage(DwcComponent<?> component) {
        managedComponents.add(component);
        
        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                managedComponents.remove(comp);
            }
        });
    }
    
    public void disableAll() {
        managedComponents.forEach(c -> c.setEnabled(false));
    }
}
```

### Milloin käyttää {#when-to-use}

Käytä `ComponentLifecycleObserver`-menetelmää:
- Rakentaaksesi komponenttirekistereitä
- Toteuttaaksesi lokitusta tai seurantaa
- Koordinoidaksesi useita komponentteja
- Siivoaksesi ulkoisia resursseja

Koodin suorittamiseksi sen jälkeen, kun komponentti on kiinnitetty DOM:iin, katso [`whenAttached()`](/docs/building-ui/composite-components) Composite Components -oppaasta.

## Käyttäjätiedot {#user-data}

Komponentit voivat kuljettaa satunnaisia palvelinpuolen tietoja `setUserData()` ja `getUserData()`-menetelmien avulla. Molemmat menetelmät ottavat avaimen tunnistaakseen tiedot. Tämä on hyödyllistä, kun sinun täytyy liittää domainelementtejä tai konteksteja komponenttiin ilman, että hallitset erillistä hakurakennetta.

```java
Button button = new Button("Käsittele");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Koska käyttäjätietoja ei koskaan lähetetä asiakkaalle, voit turvallisesti tallentaa arkaluontoisia tietoja tai suuria objekteja ilman, että verkkoliikenne vaikuttaa.
