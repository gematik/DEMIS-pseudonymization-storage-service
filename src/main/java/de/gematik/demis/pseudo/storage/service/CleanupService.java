package de.gematik.demis.pseudo.storage.service;

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

import de.gematik.demis.pseudo.storage.data.StorageRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Performs a recurrent deletion task to delete all the {@link
 * de.gematik.demis.pseudo.storage.data.StorageEntry} objects older than a given parametrized amount
 * of days.
 */
@Slf4j
@Service
@Validated
public class CleanupService {
  private final StorageRepository storageRepository;

  private final int cleanupLimitInDays;

  /**
   * Creates the Cleanup Service with a given cleanup limit.
   *
   * @param storageRepository a {@link StorageRepository} to be used to access the database.
   * @param cleanupLimitInDays the limit in days to be used to delete older entries.
   */
  public CleanupService(
      StorageRepository storageRepository,
      @Value("${storage.cleanup.after.days}") int cleanupLimitInDays) {
    this.storageRepository = storageRepository;
    if (cleanupLimitInDays <= 0 || cleanupLimitInDays > 365) {
      throw new IllegalArgumentException(
          "Invalid cleanup limit defined. It must be between 1 and 365.");
    }
    this.cleanupLimitInDays = cleanupLimitInDays;
  }

  /** Cleans up all the older entries in the Database. The task will be run every 6 Hours. */
  @Transactional(timeout = 120)
  @Scheduled(initialDelay = 5L, fixedDelay = 60 * 60 * 6, timeUnit = TimeUnit.SECONDS)
  @Observed(
      name = "delete",
      contextualName = "delete-pseudonyms",
      lowCardinalityKeyValues = {"pseudonyms", "fhir"})
  @Timed
  public void performCleanup() {
    log.debug(
        "Running cleanup task - deleting all the entries older than {} days", cleanupLimitInDays);
    storageRepository.deleteAllByCreationTimeOlderThan(cleanupLimitInDays);
    log.debug("Cleanup task completed successfully");
  }
}
