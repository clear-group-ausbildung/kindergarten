# TroubleShooting
Debug:  
 - in die "run" config von Gradle bei Arguments -> Programm Arguments "-D DEBUG=true" eintragen
 - Root-Verzeichnis vom Projekt -> Rechtsklick -> Debug as -> Debug Config -> Remote Java Application localhost:9099 -> debug
 
# Kindergarten Kassenverwaltung

## Neue Punkte 05.10.2017 - 17:00 Uhr - Erfahrung von MJ mit dem Tool am Basar-Tag
- [ ] 1. Wenn man neu Verkäufe anlegt, müssen alle drei Felder gefüllt sein!!!
- [ ] 2a. Die Anwdendung muss so umgebaut werden, dass man beim Verkäufer anlegen, wirklich einen Verkäufer anlegt. Also eine Person, der man dann nachträglich oder wann auch immer eine/mehrere Verkäufernummer zuordnen kann. Das hat den Vorteil, dass die Anwendung dann an vielen Stellen selbst entscheiden kann, welche Verkäufernummer gehört zur welcher Person. Man kann dann auch abfangen, damit keine Verkäufernummer doppelt vergeben wird.
- [ ] 2b. Wenn man das Programm so umgestellt hat, dass es selbst zuordnen kann, welche Verkäufernummern zu welchem Verkäufer (Person) gehört, dann soll es nur noch einen Abrechnungen erstellen Button geben. Die anderen sind unnötig, denn das Programm kann selbst entscheiden, welche Abrechnung für welchen Verkäufer erstellt wird.
- [ ] 2c. Wenn man diese Umstellung durch hat, dann müssen wir sehen wo man im Programm noch automatisch Entscheidungen treffen kann um die UX zu verbessern...
- [ ] 3. Bei mehrmaligen Abrechnungen, soll unten in der Liste der Einzelartikel dann nach jeder Verkäufernummer die Summe angezeigt werden. Also Gesamtsumme, Auszahlungsbetrag und Profit Kindergarten.
- [ ] 4. Es soll zusätlich noch eine weitere Abrechnung für die interne Abrechnung des Teams geben. Diese dient dann zur Beschriftung der Kuverts die dann an den Verkäufer mit dem Geld und der Abrechnung rausgegeben werden. In dieser Abrechnung sollen dann der Verkäufername, die Verkäufernummer(n) und der Auszahlungsbetrag stehen. Sollten es mehr Verkäufernummern sein, dann soll der Auszahlungsbetrag natürlich die Summe der Einzel-Auszahlungsbeträge.
- [ ] 5. Bei den Summenfeldern, werden anstatt 10,50 € -> 10,5 € angezeit. Hier bitte das Format ändern.
- [ ] 6. Die Abrechnungen sollen am besten direkt als PDF erstellt werden. Ich würde hier bei der XLSX Variante bleiben und im Programm einfach die Auswahl mit einbauen, ob PDF, XSLX oder Beides... Ich würde nach einer Möglichkeit suchen, dass man das XLSX nach PDF formatiert -> geht mit iText...Da wird dann das XLSX ausgelesen in einen FileInputStream und dann schreibt er das neu in das PDF...blöd an der Stelle. Bin damit nicht zufrieden, wollte von Anfang an iText nicht nutzen, weil das kostenpflichtig ist und ich finde, dass das Format XLSX besser für Abrechnungen geeignet ist. Auf der anderen Seite ist es schöner, wenn man PDF rausgeben kann. Mal überlegen...


## ARCHIV

## Offene Punkte
- [ ] Verkäuferübersicht, die Suche oben
- [X] Verkäuferübersicht, werden die Felder "Geliefert", Dreckig und "Abgeholt" in einer anderen Schriftart dargestellt
- [X] Verkäuferübersicht, das Feld "Geld erhalten" passt gar nicht mehr mit drauf
- [X] Verkäufer neu: Verkkäufernummer nicht mit 0 vorbelegen
- [X] Verkäufer neu: Tabreihenfolge ändern, erst alle Eingabefelder, dann die Checkboxen *ODER* den Aufbau der Seite umstellen, dass die Checkboxen einfach darunter sind
- [X] Verkäufer neu: Vorname soll kein Mussfeld mehr sein
- [X] Verkäufe: Die Suche über der Liste kann aus meiner Sicht raus, bzw. soll hier ein Dropdown rein, damit man nach einer VerkäuferNummer filtern kann - Anstatt Suchfeld bitte eine Dropdown rein, die gleiche wie bei Belege drucken.
- [X] Verkäufe:  Die Anzeigefelder unten müssen noch sauber formatiert werden... € etc...
- [X] Neuer Verkauf: Titel oben stimmt nicht - "Neuer Verkäuf"...
- [X] Neuer Verkauf: würde den Button direkt neben das letzte Eingabefeld oben ziehen
- [X] Neuer Verkauf: Values unten müssen nach hinzufügen und entfernen IMMER neu berechnet werden und sollten auch definitiv FETT Formatiert sein, bzw. Deutlich besser erkennbar. Das sind die Felder die für die Kassierer dann ja letztendlich entscheidend sind.
- [X] Gestylte Aufsummierungskomponente
- [X] Verkäuferübersicht: Preview aktualisiert sich nicht bei Tabellenzeilenselektion
- [X] Verkäufer bearbeiten: Bearbeitete Werte werden auf der GUI aktualisiert, nach Neustart sind alte Daten wieder vorhanden

## Neue Punkte 22.09.2017 - 21:10 Uhr
- [X] Neuer Verkauf: Feld Artikelpreis wäre als Euro Feld mit Euro Zeichen dahinter und Betrag nicht mit . sondern mit , eingegeben schöner
- [X] **Neuer Verkauf: Rechts oben die Feler, beim Aufsummieren "Artikelanzahl" zeigt nur eine Stelle an und je größer der Betrag in "Artikelsumme" wird, desto mehr wird abgeschnitten! Wenn der Betrag über 1.000 Euro hat, sieht man die STellen nach dem Komma nicht mehr!!!**
- [X] Neuer Verkauf: Wenn ein Artikel entfernt wird, bitte noch die Verkäufernummer mit in die Meldung "Verkauf löschen" mit aufnehmen. Ist ja immer das entscheidende Paar, Verkäufernummer und Artikelnummer.
- [X] Verkäufe: Hier werden rechts oben auch teilweise Werte abgeschnitten. Ich würde sagen du solltest mindestens eine MEnge von 99.999,99 € bezüglich der Stellen anzeigen können, vom Platz her!
- [X]  **Buisy-Dialog: Bevor du dieses Ladezeile unten einblendest, trotzdem eine Infofenster anzeigen, dass noch einen kleinen Text anzeigt, wie "Datensätze werden importiert" oder "Abrechnungen werden erstellt" oder sowas? Weil im Moment sitzt der User da und weiß nicht was passiert...**

## Neue Punkte 26.09.2017 - 22:30 Uhr - Anne
**ESSENTIELL:**
- [X] Hinweis, dass Preis mit Punkt statt Komma eingegeben werden muss, sollte immer ersichtlich sein.
- [X] Bei Return/Enter nach der Eingabe wird der Einkauf abgeschlossen. Das ist schlecht. Es wäre schön, wenn man bei Return/Enter den nächsten Artikel eingeben kann.
- [X] Verkauf abschließen sollte nur aktiv durch Klicken des Buttons möglich sein.
- [X] Abrechnungen drucken: Button Abrechnungen drucken erst aktiv, sobald eine Auswahl in der Liste ist, und zwar mehr als eine Verkäufernummer!

**Nice to have:**
- [X] Beim Drucken der Belege wäre es schön, wenn angezeigt wird, wo genau sie hin gespeichert werden.
- [X] Button Hinzufügen hat Rechtschreibfehler.
- [X] Bei dem Menüpunkt Anwendungen würde ich den "Verkäufer" in Abrechnung umbenennen.
- [X] Bei der Abrechnung in Excel erscheint: "Verk?ufer Nummer: 7" bei der Übersicht über die verkauften Artikel (also wenn ich mehrere Nummern habe)
- [X] Zeile mit Eingabefeldern bei Verkäufe größer.
- [X] Fenster "Der Verkaufsexport wird gestartet" ist überflüssig.
