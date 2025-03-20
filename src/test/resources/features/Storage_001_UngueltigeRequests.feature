#language: de

Funktionalität: Versand von ungültigen Storage Anfragen
  #Story: https://service.gematik.de/browse/DSC2-2382

  Szenariogrundriss: ungültigen Storage Anfrage
    Gegeben sei ApiKey für den Storageservice wurde geholt
    Wenn Eine Anfrage aus der Datei "<datei>" an den Storageservice gesendet wird
    Dann Wird eine Antwort mit Http Status <status> und der Fehlermeldung "<errorMessage>" erwartet
    Beispiele:
      | datei                                                    | status | errorMessage                               |
      | testdata/pseudomeldung_without_birthday.json             | 400    | Incomplete Pseudonym (missing dateOfBirth) |
      | testdata/pseudomeldung_without_familyname.json           | 400    | Incomplete Pseudonym (missing familyName)  |
      | testdata/pseudomeldung_without_firstname.json            | 400    | Incomplete Pseudonym (missing firstName)   |
      | testdata/pseudomeldung_without_notificationBundleId.json | 400    | Missing notificationBundleId               |
      | testdata/pseudomeldung_without_pseudonym.json            | 400    | Missing pseudonym                          |
      | testdata/pseudomeldung_wrong_type.json                   | 400    | wrong type                                 |
      | testdata/pseudomeldung_without_pho.json                  | 201    |                                            |


  Szenario: Storage Anfrage ohne ApiKey
    Gegeben sei Kein ApiKey für den Storageservice wurde geholt
    Wenn Eine Anfrage aus der Datei "testdata/pseudomeldung.json" an den Storageservice gesendet wird
    Dann Wird eine Antwort mit Http Status 201 erwartet


  Szenario: Storage Anfrage mit falschem content-type und accept-type
    Gegeben sei ApiKey für den Storageservice wurde geholt
    Wenn Eine Anfrage mit content-typ 'application/vnd.demis_XXstorage+json' and accept 'application/vnd.demis_XXstorage+json' gesendet wird
    Dann Wird eine Antwort mit Http Status 415 erwartet







