# Obligatorisk oppgave 4

## Deloppgave 1

- Vi setter Kristian opp med en test rolle. Vi mener at vi har en god struktur for testing. Våre manuelle tester ligger i Manual Testing Instructions.md i root.
- Det ble unødvendig mye project boards, så vi går ned til ett med kolonnene backlog/todo/in progress/needs review/done.
  - Det betyr altså at vi har endret fra å ha to project boards hvor ett følger iterasjonen og ett ikke, til å ha en hvor vi fører over fra backlog til to do når vi planlegger iterasjonene.
- Ellers er vi fornøyd med metodikken.
- Gruppedynamikken er bra vi kommuniserer bra og blir ferdige med det vi ønsker
- Kommunikasjon: Fungerer bra, møtes i virkeligheten(uavtalt, og avtalt), slack og noe på github
- Vi har akkurat passe med byråkrati slik at vi ikke gjør ting to ganger og vi har kontroll. Uten at vi bruker mer tid på byråkratiet enn koden. Vi har vært litt dårlige den siste perioden med at pull request er blitt liggende, vi lager også pr med engang vi begynner for at de andre på teamet letter skal kunne gå inn og se hva noen andre gjør.
  1. Pull reviews må skje raskere
- Møtenotater ligger under meeting notes i sidemenyen på [wiki'en](https://github.com/inf112-v19/Blodige-roboter/wiki)
- Kunnskapsoverføring:
  - Vi er tilgjengelige for hverandre når vi jobber med noe og kan spørre enten på lesesalen, i møter eller på slack.
  - Vi har en "mini" standup på hvert møte hvor folk sier hva de har jobbet med og om de har eller har hatt eventuelle utfordringer.
  - Hvis det gjøres store endringer i strukturen, som f.eks med [#108](https://github.com/inf112-v19/Blodige-roboter/issues/108) setter vi oss sammen og ser på det i møtet slik at vi vet at alle har forstått det nye. Dette gjør vi for å kunne utnytte strukturen mest mulig.
  - Dette har vi også gjort tidligere i f.eks. [#50](https://github.com/inf112-v19/Blodige-roboter/issues/50). Men da ved at alle på teamet utførte pull review.
- Vi ser at det i oppgaven står at det forventes at alle deltar i kodebasen: det har alle gjort. I forhold til retteskjema forventes det lik fordeling av commits: det har vi ikke. Det kommer nok fra at vi har valgt forskjellige oppgaver. Kristian, Rikke og Elg som har flest den siste perioden har hatt refaktorering og nye funksjoner som oppgaver og har generert flere commits. Rune og Daniel har hatt forholdsvis bug fixing og ny funksjonalitet. Buggen viste seg vanskelig å få bort og det ble brukt mye tid per commit. I forhold til ny funksjonaliteten var også denne vanskelig å få inn i flow'en vi hadde. Dette gjaldt altså [#24](https://github.com/inf112-v19/Blodige-roboter/issues/24). Dette gjelder kun siste uke mens siste måned ligger vi relativt jevnt.

## Deloppgave 2

Oppgavene vi har valgt å gjøre ligger i [iterasjon 4](https://github.com/inf112-v19/Blodige-roboter/milestone/5)
Under kommer kravene samlet i tre kategorier, de vi mener at vi er ferdige, de vi har valgt og de vi vil gjøre senere. Akseptansekriterier for oppgavene ligger under issuet på github.

### Ferdige med

- Man må kunne spille en komplett runde
- Man må kunne vinne spillet spillet ved å besøke siste flagg (fullføre et spill)
- Skademekanismer (spilleren får færre kort ved skade)

### Ønsker å gjøre denne iterasjonen

- Det skal være lasere på brettet
  - [#19](https://github.com/inf112-v19/Blodige-roboter/issues/19)
- Det skal være hull på brettet
  - [#73](https://github.com/inf112-v19/Blodige-roboter/issues/73)
- Spillmekanismer for å skyte andre spillere innen rekkevidde med laser som peker rett frem.
  - Her har vi tatt en forutsetning om at robotene skyter "evig" langt men ikke gjennom andre roboter, vegger eller "dyttere", dette samsvarer også med spillmanualen
  - [#19](https://github.com/inf112-v19/Blodige-roboter/issues/19)
- fungerende samlebånd på brettet som flytter robotene
  - [#17](https://github.com/inf112-v19/Blodige-roboter/issues/17)
- fungerende gyroer på brettet som flytter robotene
  - [#18](https://github.com/inf112-v19/Blodige-roboter/issues/18)
- power down
  - [#24](https://github.com/inf112-v19/Blodige-roboter/issues/24)
- samlebånd som går i dobbelt tempo
  - [#17](https://github.com/inf112-v19/Blodige-roboter/issues/17)

Dette er mange punkter men mye av det bygger på allerede eksiserende funksjonalitet. Mye av arbeidet for denne iterasjone er en endring av hvordan kartet intagerer med spilleren, og med dette på plass vil alle disse funksjonene fort komme på plass.

### Ting vi har igjen som vi ikke har fokusert på denne iterasjonen

- Game over etter 3 tapte liv
  - Vi stopper ikke spillet, men roboten eksisterer ikke hvis den mister alle tre livene
- multiplayer over LAN eller Internet (trenger ikke gjøre noe fancy her, men må kunne spille på ulike maskiner mot hverandre)
  - Vi utsetter dette på bakgrunn av et intrykk at spillets funksjoner er viktigere enn flerspiller og dermed nedprioriteres dette punktet
- options-kort
  - Dette mener vi krever noen grunnfunksjoner som vi må få på plass først før vi kan begynne å lage funksjonelle options kort
- plassere flagg selv før spillet starter
  - Dette har vi ikke sett for oss å gjøre, og siden disse kravene kom 6(+3) dager før innlevering har vi ikke "kastet oss rundt" for å gjennomføre det, det er i tillegg ikke noe vi anser som særdeles viktig for grunnspillet.
- sette sammen ulike brett til større spillflate
  - Her har vi implementert muligheten for flere kart, men ikke i forhold til brukerinteraksjon, sammensetting av kart har vi heller ikke sett på.
  - Også dette punktet anser vi som ikke en viktig funksjon for grunnspillet
- spille mot AI (single-player-mode), evt spill mot random-roboter
  - Nedprioritert for å fokusere på grunnleggende funksjoner.
  - Men strukturen vår er noenlunde tilrettelagt for dette.
