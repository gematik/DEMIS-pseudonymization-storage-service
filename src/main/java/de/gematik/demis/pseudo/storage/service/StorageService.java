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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gematik.demis.pseudo.storage.data.EntityConverter;
import de.gematik.demis.pseudo.storage.data.StorageRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Executes the operations with the Database. */
@Slf4j
@Service
public class StorageService {
  private final StorageRepository storageRepository;

  private final ObjectMapper mapper;

  public StorageService(StorageRepository storageRepository, ObjectMapper mapper) {
    this.storageRepository = storageRepository;
    this.mapper = mapper;
  }

  /**
   * Converts the Body String in JSON and then persist it as Entity in the Database.
   *
   * @param data the Request Body payload
   * @return the ID of the persisted data, if successful
   * @throws JsonProcessingException in case of parsing failure
   */
  @Transactional(timeout = 120)
  @Observed(
      name = "store",
      contextualName = "store-pseudonyms",
      lowCardinalityKeyValues = {"pseudonyms", "fhir"})
  @Timed
  public String storeData(final String data) throws Exception {
    final JsonNode jsonNode = mapper.readTree(data);
    log.debug("Body Request converted to JSON successfully");
    final var storageEntity = EntityConverter.fromJson(jsonNode);
    log.debug("JSON Payload converted successfully");
    final var persistedEntity = storageRepository.save(storageEntity);
    log.debug("Entity persisted in DB successfully");
    return persistedEntity.getDemisId();
  }
}
