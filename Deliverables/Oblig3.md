# Obligatorisk øvelse 3

* Alt går fint
  * Kundekontakt:
    * Min rolle innebærer å finne ut av regler og spillogikk hvis det er noe vi lurer på, dette utøfres ved å lese manualen nøye hvis det er noe som er uklart. Rollen fungerer fint og jeg finner svaret på det gruppen lurer på.
  * Teamlead:
    * Har en god dialog med resten av teamet, folk velger oppgaver selv eller får kommentere på tildelte oppgaver. Jeg tar ansvar for at møtene holder en noenlunde struktur og at vi ikke bruker tiden på diskutere uvesentlige ting.
    * Jeg tar ansvar for at project board, wiki og annet materiale utenom kode er i avtalt struktur.
* Ingen andre roller, passer at alle gjør litt av alt
* Vi bruker ikke boardet kanskje vi skal gjøre det om til per iterasjon.
* Vi er alle involverte takket være test/utvikler delegasjon og pullreviews.
* Forskjellige tider vi arbeider på, noen av oss liker å gjøre alt på en gang mens andre foretrekker å trekke det litt ut over tiden.
* Gått ned til ett møte i uken, vi diskuterer litt på slack, og tar det mer formelle på github.
* Vi fortsetter med bruk av et kanban board uten begrensninger her legger vi også inn arbeidsoppgaver som ikke omhandler koding. 
* Vi ser at dette kan bli rotete og velger derfor å lage et ekstra board som følger iterasjonene hvor kun "kode" relaterte oppgaver skrives.
  * Dette gjør vi fordi vi ikke er fornøyde med det grafiske og hvor enkelt det er å følge opp progresjonen i hver enkelt iterasjon.
* Når vi ser hva de andre har gjort, tenker vi at vi ikke har all funksjonalitet på plass. Men vi er veldig fornøyde med grunnleggende struktur og tror vi vil bli belønnet for dette i de kommende ukene.
* Vi er fleksible og har ikke for rigide regler, vi ser hver oppgave ann men beholder grunnleggende prinsipper.
* Problemer med å registrere rikke i github da hennes arbeid ikke kobles til githubkontoen hennes. Dette har vi fått fikset, problemet var at hennes git credentials email var en mail som ikke eksisterer.
* Elg har mye involvering, vi ser at han commiter "mindre" blokker enn oss andre. I tillegg har han hatt mye grafikk, som han kjenner fra før, som betyr at tid per kodelinje er mindre.
* Men vi ser at dette kan forbedres og jobber mot dette.

## Kunnskapsoverføring

* Vi jobber med noe parprogrammering, spesielt i situasjoner hvor en skal gjøre mye i noe en annen har laget. Dette gjøres for å slippe at en annen må sette seg inn i andres kode
* Vi spør spørsmål om andres antakelser når vi endrer kode og spør etter spillinformasjon fra de andre.
* Elg er vår LibGdx sjef, og vi spør han om mye tekniske spørsmål spesielt under planlegging av iterasjoner og implementasjoner.

## Punkter for forbedring

1. Jobbe mot bedre commit fordeling.
2. Sette opp nytt board som følger Iterasjonen og se hvordan dette går
3. Øke code coverage ved testing, vi håper at en refaktoreringsjobb skal gjøre det lettere å teste ref (#50)
   * Vi ligger foreløpig på 60% som vi synes er litt lavt for automatiske tester, sammen med manuelle er vi oppe i 85% som er bra, men om vi egentlig tester alle 85% av linjene ved de manuelle testene er diskutabelt.
4. Populere wiki'en enda mer.
5. At alle har god sinnstemning

* [Møtenotater](https://github.com/inf112-v19/Blodige-roboter/wiki)
* Se [Workflow](https://github.com/inf112-v19/Blodige-roboter/wiki/Workflow) for mer beskrivende arbeidsmetoder
* Se [Run with IntelliJ](https://github.com/inf112-v19/Blodige-roboter/wiki/Other_Run-with-IntelliJ) for hvordan kjøre spillet og kjøre tester

Kristian lager Klassediagram

## Presisering av krav

* Kunne få alle typene bevegelseskort
  * Gjort tidligere i issue #5 og #47
* Dele ut 9 kort
  * Gjort i issue #5
* Velge 5 kort (godkjenne valg/si “nå er jeg klar”)
  * Gjort i issue #47
  * Trykk enter for å gjøre deg "klar"
* Eksekvere program utfra valgte kort
  * Gjort i issue #5, #47 og #14
* Besøke flagg
  * Her hadde vi lagt grunnleggende funksjonalitet for mapInteraction fra forrige iterasjon.
  * Dermed ble oppgaven #21 laget for denne og forrige iterasjon.
* Hvis robot går av brettet blir den ødelagt og går tilbake til siste backup
  * #44 ble laget for denne iterasjonen
* Oppdatere backup hvis robot blir stående på skiftenøkkelrute i slutten av en fase
  * #20 laget klar for denne iterasjonen
* Flytte backup ved besøk på flagg
  * Gjøres i #20
* Kunne spille en fullverdig runde med alle faser
  * #56 laget for denne iterasjonen
* Få nye kort til ny runde
  * #5 og #47

I fra forrige uke var vi "heldige" på det vi valgte å fokusere på. Derfor var mye gjort i forrige iterasjon mot denne uken. For eksempel #5, #47, #14, #20. 

## Akseptkriterier

* Kunne få alle typene bevegelseskort
  * Skal følge orginalspillets regler og være like kort mtp prioritering. Alle kort skal ha hjelpetekst
* Dele ut 9 kort
  * Deles ut fra et deck, i forhold til fordeling fra orginalspillet. Spillere i samme spill kan ikke få samme kort (les samme prioritering)
* Velge 5 kort (godkjenne valg/si “nå er jeg klar”)
  * Click and drag funksjon
  * Trykk 'enter' for å gjøre deg "klar"
  * Velg random kort hvis ikke alle er valgt
  * rekkefølge fra venstre til høyre
* Eksekvere program utfra valgte kort
  * Kun for main robot. Vi sikter mot multiplayer only i denne applikasjonen for øyeblikket.
  * Kun selve bevegelsen ikke interaksjon med brett o.l.
    * Selv om vi implementerer litt under andre punkter
* Besøke flagg
  * Skjer kun etter samlebånd,  lasere o.l.
  * Når roboten står på et flagg skal dette registreres i spilleren. Kun når flagg besøkes i riktig rekkefølge skal de registreres. Det skal tilrettelegges for en enkel sjekk om en spiller har vunnet til fremtidig "Win condition".
* Hvis robot går av brettet blir den ødelagt og går tilbake til siste backup
  * Backup er en verdi styrt av player klassen. Å bli ødelagt gjør at den mister et liv men at helsepoengene blir satt til maks.
* Oppdatere backup hvis robot blir stående på skiftenøkkelrute i slutten av en fase
  * Styres av player klassen. Skjer kun etter samlebånd,  lasere o.l.
* Flytte backup ved besøk på flagg
  * Kun ved godkjent flagg besøk. samme som over ift backup.
* Kunne spille en fullverdig runde med alle faser
  * Fasene går sin gang, etter 5 faser venter spillet på input fra spilleren.
  * Legge tilrette for kart interaksjon på brukeren men ikke implementere det.
* Få nye kort til ny runde
  * Deles ut fra et deck. Hele dekket shufflet og alle kort er med i starten(hentet fra orginalspillet)
