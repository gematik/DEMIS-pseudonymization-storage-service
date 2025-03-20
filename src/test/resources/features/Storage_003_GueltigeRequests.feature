#language: de

Funktionalität: Versand von gültigen Storage Anfragen
  #Story: https://service.gematik.de/browse/DSC2-2382

  Szenariogrundriss: Gültige Storage Anfrage
    Gegeben sei ApiKey für den Storageservice wurde geholt
    Wenn Eine Anfrage aus der Datei "<datei>" an den Storageservice gesendet wird
    Dann Wird eine Antwort mit Http Status <status> erwartet
    Wenn Ein Eintrag wird in der Datenbank gesucht
    Dann Kann der Eintrag gefunden werden
    Beispiele:
      | datei                                       | status |
      | testdata/pseudomeldung.json                 | 201    |
      | testdata/pseudomeldung_only_birthday.json   | 201    |
      | testdata/pseudomeldung_only_familyname.json | 201    |
      | testdata/pseudomeldung_only_firstname.json  | 201    |
