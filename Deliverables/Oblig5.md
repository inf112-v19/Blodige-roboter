# Obligatorisk oppgave 5

## Deloppgave 1

- Vi har en byråkratisk måte som krever litt github tid, men vi har alltid god kontroll på hvilke oppgaver som gjøres og har sammen med codacy holdt kodekvaliteten på et relativt høyt nivå. Pull requests har endt opp med å bli litt store, dette har vi løst med at flere reviewer. Pull requests har også blitt ligget litt lenge til tider, et par dager. Men det skal som regel ikke noe mer til enn en purring.
- God, vi er alle motiverte for å nå det samme målet.
- Vi er gode til å være tilgjengelige på slack, tar mer formelle diskusjoner på github. Vi innså etter hvert at vi hadde litt for mange diskusjoner to og to, og ble enige om å bli flinkere på å ta disse i et felles forum.
- Referater ligger på  [wiki](https://github.com/inf112-v19/Blodige-roboter/wiki)
- Vi har hatt en konstant rotasjon, alle har jobbet med UI og spillLogikk. Vi har ikke tid til å rotere slik at absolutt alle får involvere seg på nettverksbiten.
- Prosjektmetodikk er i utganspunktet slik som i oblig 2. Største endringer er testrolle per issue, og en todo kolonne på project boardet.

- Ift til klassediagram anbefales det å finne GameGraphics klassen og se på ting i forhold til denne, for dette er klassen som binder ting sammen.

### Retroperspektiv  prosjekt

- For å se prosjektmetodogikken vi har valgt kan man se på Oblig 2 task 3

- Justeringer
  - Vi måtte skjerpe oss litt på pull requests og bli flinkere å reviewe disse raskere
  - Vi innførte en dedikert testrolle for hver issue, slik at det alltid var noen andre enn deg selv som testet din egen kode
  - Vi jobbet for å lage issues og pr's så lite omfattende som mulig.
  - Vi endret project board til at vi la oppgaver vi har hatt per iterasjon inn i en todo kolonne og resten i en backlog, dette syntes vi var det letteste for å holde oversikten.
  - Vi synes alt ovenfor har fungert bra, fordi vi har kontroll på hva som skjer og hvem som gjør hva. Vi har så og si aldri gjort noenting dobbelt arbeid. Dette stammer fra god struktur på arbeidsoppgaver og god git kunnskap, også enkelt å vite hvem som har skrevet hva for å spørre om detaljer.
  - Vi måtte sette opp noen egne oppgaver til å teste ting som var litt forglemt.
    - Det betydde at testrollen vår kanskje ikke fungerte optimalt. Det ble mange oppgaver som gjorde det vanskelig for individet å ha kontroll og oversikt over alle, og tungt å sette seg inn i veldig spesifikke detaljer i anndres kode for å kunne teste det og det ble enda vanskeligere å bedømme om det er testene eller implementasjonen som var problemet.
  - Vi fant også ut at vi måtte fokusere enda mer på skrive spesifikasjon på issues slik at vi var forente om hva som dekket en spesifikk oppgave
    - Men dette fikk vi merke at er vanskelig å gjøre helt definivt.
- Fremtidige justeringer
  - Vi har ikke noe som vi føler strekker seg frem, kanskje litt mer fokus på dokumentasjon på wiki'en for eksempel med en ansvarlig person for dette.
- Hva vi har lært
  - Kontroll på egne arbeidsoppgaver
  - Selv om god struktur er litt "pain in the ass" er det verdt det i det lange løp.

## Deloppgave 2

- Man må kunne spille en komplett runde
- Man må kunne vinne spillet spillet ved å besøke siste flagg (fullføre et spill)
- Det skal være lasere på brettet
- Det skal være hull på brettet
- Skademekanismer (spilleren får færre kort ved skade)
- Spillmekanismer for å skyte andre spillere innen rekkevidde med laser som peker rett frem
- fungerende samlebånd på brettet som flytter robotene
  - Kan oppstå fiffige situasjoner hvor en robot dytter en annen mens de dyttes av samlebånd. 
- fungerende gyroer på brettet som flytter robotene
- game over etter 3 tapte liv
- multiplayer over LAN eller Internet (trenger ikke gjøre noe fancy her, men må kunne spille på ulike maskiner mot hverandre)
- Feilhåndtering ved disconnect. (Spillet skal i hvertfall ikke kræsje)
  - Hvis hosten disconnecter sendes de andre spillerene til en error skjerm, mens hvis andre disconnecter velges det tilfeldige kort for dem.
- power down
- samlebånd som går i dobbelt tempo
- spille mot AI (single-player-mode), evt spill mot random-roboter

Vi har fullført alle krav for mvp og "dyttere", ingen andre spesielle tillegg utenom kart navigasjon.

## Deloppgave 3

- Vi har trege tester
- UI henger og lyden spiller ikke av
- Power down kan kun gjøres før man får kort, det betyr i praksis at man kun kan sette power down i runden før den runden man ønsker å være powered down i. Dette kommer av tolkningen fra orginalspillet hvor man ikke skal få powere down etter man har trukket kort.
- Det kan skje rare ting, når to spillere er ved hveandre på samlebåndet, den ene kan dytte den andre når den flyttes av beltet og da vil den andre først bli flyttet av dytten for så av samlebåndet
