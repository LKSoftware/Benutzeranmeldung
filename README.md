# Benutzeranmeldung

Benutzeranmeldung Projekt

### Aufgabe

Entwickeln Sie eine Bibliothek, mit der Benutzeranmeldungen bei Websites verwaltet werden können.

Neue Benutzer registrieren sich zunächst. Sie müssen mindestens ihre Email-Adresse angeben. Wenn kein Passwort gewählt wurde, generiert die Registrierung eines, das der Benutzer später ändern kann.

Wer sich registriert, bekommt eine Registrierungsemail geschickt mit einem Link zur Bestätigung, in dem eine Registrierungsnummer enthalten ist. Erst wenn die Registrierung mit dieser Nummer bestätigt ist, ist der Benutzer permanent im System (Benutzer.Bestätigt ist dann true).

Die Anmeldung erfolgt mit Email-Adresse oder Nickname und Passwort nach Bestätigung. Ist sie erfolgreich, liefert sie ein Token zurück. Das kann später immer wieder bei Bedarf zur Prüfung vorgelegt werden, ob Anfragen von einem Client gültig sind. Zu diesem Zweck sollte das Token so beschaffen sein, dass Clients sie nicht fälschen können. Für den Rest der Welt ist das Token opaque.

Wer sein Passwort vergessen hat, kann eine Zurücksetzung beantragen. Es wird dann eine Nachricht an die Email-Adresse gesendet, in der ein Link steht, über den man ein neues Passwort anfordern kann. Wird der angeklickt, wird ein Passwort generiert und an die beantragende Email-Adresse versandt.

Benutzer können ihre Anmeldedaten verändern. Sie greifen darauf über ihr Token zu, das sie bei der Anmeldung bekommen. Anschließend werden die Benutzerdaten über eine interne Id identifiziert; Benutzer sind mithin Entitäten, deren Daten bis auf die Id veränderbar sind.

Wie die Benutzerdaten gespeichert werden, soll nicht vorgeschrieben werden. In jedem Fall jedoch dürfen Passworte nicht im Klartext in der Benutzerdatenbank stehen.

Unbestätigte Registrierungen sollten ein Verfallsdatum bekommen und danach automatisch gelöscht werden.

Die Bibliothek ist mit einigen Angaben zu konfigurieren, z.B.

Verbindungsdaten für die Benutzerdatenbank
URLs für Seiten, auf die in Emails verwiesen wird
Wie die Konfigurationsdaten gespeichert werden, wird ebenfalls nicht vorgegeben.