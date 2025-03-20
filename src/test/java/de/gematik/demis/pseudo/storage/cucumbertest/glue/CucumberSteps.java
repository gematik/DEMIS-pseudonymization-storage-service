package de.gematik.demis.pseudo.storage.cucumbertest.glue;

/*-
 * #%L
 * pseudonymization-storage-service
 * %%
 * Copyright (C) 2025 gematik GmbH
 * %%
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by the
 * European Commission – subsequent versions of the EUPL (the "Licence").
 * You may not use this work except in compliance with the Licence.
 *
 * You find a copy of the Licence in the "Licence" file or at
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.
 * In case of changes by gematik find details in the "Readme" file.
 *
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * #L%
 */

import de.gematik.demis.pseudo.storage.cucumbertest.ResponseChecker;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Gegebensei;
import io.cucumber.java.de.Wenn;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@Data
@CucumberContextConfiguration
public class CucumberSteps extends SpringIntegrationTest {

  private ResponseChecker responseChecker = new ResponseChecker();

  @Gegebensei("ApiKey für den Storageservice wurde geholt")
  public void retrieveApiKey() {
    existApiKey = true;
  }

  @Gegebensei("Kein ApiKey für den Storageservice wurde geholt")
  public void dontRetrieveApiKey() {
    existApiKey = false;
  }

  @Wenn("Eine Anfrage aus der Datei {string} an den Storageservice gesendet wird")
  public void sendRequestFromFile(String filename) throws Exception {
    String jsonBody = createBodyFromFile(filename);
    var response = sendPostRequest(jsonBody);
    responseChecker.setResponse(response);
  }

  @Wenn("Eine Anfrage mit content-typ {string} and accept {string} gesendet wird")
  public void sendRequestWithContentTypAndAccept(String contentType, String accept)
      throws Exception {
    var response = sendRequestWithWrongContentTypeAndAccept(contentType, accept);
    responseChecker.setResponse(response);
  }

  @Dann("Wird eine Antwort mit Http Status {int} erwartet")
  public void expectHttpStatus(int expectedHttpStatus) {
    getResponseChecker().checkResultStatus(expectedHttpStatus);
  }

  @Dann("Wird eine Antwort mit Http Status {int} und der Fehlermeldung {string} erwartet")
  public void expectHttpStatusWithErrorMessage(
      int expectedHttpStatus, String expectedErrorMessage) {
    responseChecker.checkResultStatus(expectedHttpStatus);
    responseChecker.checkErrorMessage(expectedErrorMessage);
  }

  @SneakyThrows
  @Gegebensei("Datenbankverbindung wurde hergestellt")
  public void accessDatabaseConnection() {
    Assertions.assertTrue(databaseConnector.isConnected());
    // Wait 10 Seconds so the task can be performed
    Thread.sleep(10000L); // NOSONAR
  }

  @Wenn("Ein Eintrag wird in der Datenbank gesucht")
  public void checkIfNoDataExistOlderThan() {
    foundEntries = Assertions.assertDoesNotThrow(databaseConnector::readData);
  }

  @Wenn("Eine Anfrage für Einträge die älter als {int} Tage sind an die Datenbank gesendet wird")
  public void performRequestToCheckEntriesOlderThanDays(int days) {
    foundEntries = databaseConnector.readDataOlderThan(days);
  }

  @Dann("Wird kein Eintrag gefunden")
  public void expectAnEmptyResponse() {
    Assertions.assertNotNull(foundEntries);
    Assertions.assertTrue(foundEntries.isEmpty());
  }

  @Wenn("Eine Anfrage für einen Eintrag die {int} Tage alt ist an die Datenbank gesendet wird")
  public void performRequestToCheckEntriesAsOldAsDays(int days) {
    foundEntries = databaseConnector.readDataAsOldAs(days);
  }

  @Dann("Kann der Eintrag gefunden werden")
  public void expectNotEmptyResponse() {
    Assertions.assertNotNull(foundEntries);
    Assertions.assertFalse(foundEntries.isEmpty());
    foundEntries.forEach(storageEntry -> Assertions.assertNotNull(storageEntry.getDemisId()));
  }
}
