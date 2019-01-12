# VoteManager

[![Build Status](https://travis-ci.org/HSZemi/VoteManager.svg?branch=master)](https://travis-ci.org/HSZemi/VoteManager)

## English summary
This is a rather simple vote counting/tracking application which has been written to support the vote counting during the student parliament elections at a German university.

## Beschreibung

"VoteManager" ist ein relativ einfach gehaltenes Programm, das bei der Auszählung einer Wahl zur Erfassung der Stimmergebnisse eingesetzt werden kann.
Das Programm wurde ursprünglich für die Wahlen zum Studierendenparlament an der Universität Bonn entwickelt, sollte aber auch bei anderen Wahlen einsetzbar sein.

## Funktionen

- Definition der zur Wahl antretenden Listen und Kandidaten
. Definition der Wahlurnen/-Bezirke/-Lokale
- Eingabe der Stimmergebnisse für jede einzelne Urne mit automatischer Fehlerüberprüfung
- Anzeige der Ergebnisse einzelner Urnen und des Gesamtergebnis aller Urnen
- Export der Ergebnisse als Textdatei
- Laden/Speichern der Wahldaten als XML-Datei

## Installation und Benutzung

Die jeweils aktuelle Version von VoteManager kann als .jar-Datei unter dem Menüpunkt "Releases" heruntergeladen werden. Zum Ausführen der Datei wird Java 8 benötigt.

Nach Programmstart öffnet sich das Hauptfenster, in dem alle Programmfunktionen verfügbar sind. Zu Beginn wird das Programm mit einer leeren Datei initialisiert. Nun können die Urnen, Listen und Kandidaten über die entsprechenden Menüpunkte erstellt und bearbeitet werden. Nachdem diese Definitionen erstellt sind, kann die Eingabe der Wahlergebnisse durchgeführt werden.

Das Laden und Speichern ist jederzeit über den Menüpunkt "Datei" möglich.

## Entwicklung

Das Programm basiert auf der Programmiersprache Java und kann mit Gradle kompiliert werden. (`./gradlew build`)
Die GUI des Programms nutzt das GUI-Toolkit "JavaFX". Zur Bearbeitung der JavaFX-Definitionen kann der JavaFX Scene Builder benutzt werden.

## Lizenz

Das Programm und alle enthaltenen Dateien werden unter MIT-Lizenz veröffentlicht. Für weitere Informationen siehe die Datei "LICENSE".
