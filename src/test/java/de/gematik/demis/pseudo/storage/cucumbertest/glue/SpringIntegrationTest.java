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

import de.gematik.demis.pseudo.storage.Application;
import de.gematik.demis.pseudo.storage.data.Definitions;
import de.gematik.demis.pseudo.storage.data.StorageEntry;
import de.gematik.demis.pseudo.storage.integration.PostgresStarter;
import de.gematik.demis.pseudo.storage.rest.StorageResponse;
import de.gematik.demis.pseudo.storage.unit.utils.DataGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureObservability
@Slf4j
public abstract class SpringIntegrationTest {

  private static final PostgreSQLContainer<PostgresStarter> postgreSQLContainer =
      PostgresStarter.getInstance();

  protected final DatabaseConnector databaseConnector =
      new DatabaseConnector(
          postgreSQLContainer.getJdbcUrl(),
          postgreSQLContainer.getUsername(),
          postgreSQLContainer.getPassword());
  private final TestRestTemplate restTemplate = new TestRestTemplate();
  protected List<StorageEntry> foundEntries = new ArrayList<>();
  protected boolean existApiKey;
  @LocalServerPort private int port;

  /**
   * Injects Properties dynamically into the application context.
   *
   * @param registry the registry used by the application context
   */
  @DynamicPropertySource
  protected static void postgresProperties(DynamicPropertyRegistry registry) {
    postgreSQLContainer.withReuse(true);
    postgreSQLContainer.start();
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  @BeforeEach
  public void beforeEach() {}

  protected ResponseEntity<StorageResponse> sendPostRequest(String body) {
    HttpEntity<String> entity;
    if (existApiKey) {
      entity =
          new HttpEntity<>(body, DataGenerator.getDefaultHeader(DataGenerator.DEFAULT_API_KEY));
    } else {
      entity = new HttpEntity<>(body, DataGenerator.getDefaultHeaderWithoutApiKey());
    }

    return restTemplate.exchange(
        createURLWithPort(Definitions.STORAGE_PATH),
        HttpMethod.POST,
        entity,
        StorageResponse.class);
  }

  protected ResponseEntity<StorageResponse> sendRequestWithWrongContentTypeAndAccept(
      String contentType, String accept) throws Exception {
    HttpEntity<String> entity =
        new HttpEntity<>(
            "",
            DataGenerator.getDefaultHeaderWithContentType(
                DataGenerator.DEFAULT_API_KEY, contentType, accept));
    ;

    return restTemplate.exchange(
        createURLWithPort(Definitions.STORAGE_PATH),
        HttpMethod.POST,
        entity,
        StorageResponse.class);
  }

  protected String createBodyFromFile(String filePath) throws FileNotFoundException {
    ClassLoader classloader = getClass().getClassLoader();
    if (classloader.getResource(filePath) == null) {
      throw new FileNotFoundException("The file: " + filePath + " was not found.");
    }
    File dataFile = new File(Objects.requireNonNull(classloader.getResource(filePath)).getFile());
    try {
      return Files.readString(dataFile.toPath(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.error("Could not read content from " + dataFile.toPath() + "\n" + e.getMessage());
    }
    return null;
  }

  @Before
  public void setupTest() {
    foundEntries.clear();
  }

  @After
  public void afterScenarios() {}

  private String createURLWithPort(final String path) {
    return "http://localhost:" + port + path;
  }
}
