package de.gematik.demis.pseudo.storage.rest;

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

import de.gematik.demis.pseudo.storage.data.Definitions;
import de.gematik.demis.pseudo.storage.service.StorageService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** Storage REST Controller. */
@Slf4j
@RestController
@Validated
public class StorageController {

  private final StorageService storageService;

  public StorageController(final StorageService storageService) {
    this.storageService = storageService;
  }

  /**
   * Converts the incoming JSON data and store them in the database.
   *
   * @return an instance of {@link StorageResponse}
   */
  @Timed
  @Counted
  @PostMapping(
      path = Definitions.STORAGE_PATH,
      produces = Definitions.STORAGE_CONTENT_TYPE,
      consumes = Definitions.STORAGE_CONTENT_TYPE)
  public ResponseEntity<StorageResponse> store(@Nonnull @RequestBody final String requestBody) {
    log.debug("Storing new request");
    try {
      final var storedId = storageService.storeData(requestBody);
      log.info("Request with id {} stored successfully", storedId);
      return new ResponseEntity<>(new StorageResponse(storedId), HttpStatus.CREATED);
    } catch (IllegalArgumentException | NullPointerException exception) {
      // Triggers 4xx Error Codes
      log.error("Got: {}", exception.getLocalizedMessage(), exception);
      throw new IllegalArgumentException(exception.getLocalizedMessage());
    } catch (Exception exception) {
      // Triggers 5xx Error Codes
      log.error("Operation failed: {}", exception.getLocalizedMessage(), exception);
      throw new IllegalStateException(exception.getLocalizedMessage());
    }
  }
}
