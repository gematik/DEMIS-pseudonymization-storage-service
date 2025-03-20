#language: de
Funktionalität: Überpruefung regelmässiges Löschens der Einträge aus der Datenbank
  #Story: https://service.gematik.de/browse/DSC2-2382

  Szenario: Direkte SQL-Anfrage für Einträg in der Datenbank die älter als 90 Tage sind
    Gegeben sei Datenbankverbindung wurde hergestellt
    Wenn Eine Anfrage für Einträge die älter als 90 Tage sind an die Datenbank gesendet wird
    Dann Wird kein Eintrag gefunden

  Szenario: Direkte SQL-Anfrage für einen Eintrag in der Datenbank die 88 Tage alt ist
    Gegeben sei Datenbankverbindung wurde hergestellt
    Wenn Eine Anfrage für einen Eintrag die 88 Tage alt ist an die Datenbank gesendet wird
    Dann Kann der Eintrag gefunden werden