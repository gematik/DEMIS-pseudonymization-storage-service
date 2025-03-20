package de.gematik.demis.pseudo.storage.data;

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

import com.fasterxml.jackson.databind.JsonNode;
import io.micrometer.core.annotation.Timed;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/** Utility Class to convert Entity from/to JSON. */
@Slf4j
public final class EntityConverter {

  private static final String WRONG_FIELD_BUNDLE_LOG_MESSAGE = "Wrong or missing '{}' field";

  private EntityConverter() {}

  /**
   * Converts a {@link JsonNode} object to a {@link StorageEntry} one
   *
   * @param json the JSON Structure with the information
   * @return a new instance of {@link StorageEntry}
   */
  @Timed
  public static StorageEntry fromJson(final JsonNode json) {
    log.debug("Extracting information from JSON");
    validateJson(json);

    final JsonNode pseudonym = json.get(Definitions.KEY_PSEUDONYM);
    return StorageEntry.builder()
        .demisId(json.get(Definitions.KEY_NOTIFICATION_BUNDLE_ID).asText())
        .phoCode(
            json.has(Definitions.KEY_PHO_CODE)
                ? json.get(Definitions.KEY_PHO_CODE).asText()
                : Definitions.KEY_PHO_CODE_UNKNOWN)
        .familyName(toArray(pseudonym.get(Definitions.KEY_FAMILYNAME)))
        .firstName(toArray(pseudonym.get(Definitions.KEY_FIRSTNAME)))
        .diseaseCode(pseudonym.get(Definitions.KEY_DISEASE_CODE).asText())
        .dateOfBirth(pseudonym.get(Definitions.KEY_DATE_OF_BIRTH).asText())
        .creationTime(new Timestamp(System.currentTimeMillis()))
        .build();
  }

  private static void validateJson(final JsonNode json) {
    log.debug("Validating JSON");

    if (!json.hasNonNull(Definitions.KEY_TYPE)
        || !Definitions.TYPE_STORAGE_REQUEST.contentEquals(
            json.get(Definitions.KEY_TYPE).textValue())) {
      log.error(WRONG_FIELD_BUNDLE_LOG_MESSAGE, Definitions.KEY_TYPE);
      throw new IllegalArgumentException(Definitions.WRONG_TYPE_BUNDLE_MESSAGE);
    }

    if (!json.hasNonNull(Definitions.KEY_NOTIFICATION_BUNDLE_ID)) {
      log.error(WRONG_FIELD_BUNDLE_LOG_MESSAGE, Definitions.KEY_NOTIFICATION_BUNDLE_ID);
      throw new IllegalArgumentException(
          String.format(
              Definitions.MISSING_FIELD_BUNDLE_MESSAGE, Definitions.KEY_NOTIFICATION_BUNDLE_ID));
    }

    if (!json.hasNonNull(Definitions.KEY_PSEUDONYM)) {
      log.error(WRONG_FIELD_BUNDLE_LOG_MESSAGE, Definitions.KEY_PSEUDONYM);
      throw new IllegalArgumentException(
          String.format(Definitions.MISSING_FIELD_BUNDLE_MESSAGE, Definitions.KEY_PSEUDONYM));
    }

    final JsonNode pseudonym = json.get(Definitions.KEY_PSEUDONYM);
    if (!pseudonym.hasNonNull(Definitions.KEY_FIRSTNAME)) {
      throw new IllegalArgumentException(
          String.format(Definitions.INCOMPLETE_PSEUDONYM_MESSAGE, Definitions.KEY_FIRSTNAME));
    }

    if (!pseudonym.hasNonNull(Definitions.KEY_FAMILYNAME)) {
      throw new IllegalArgumentException(
          String.format(Definitions.INCOMPLETE_PSEUDONYM_MESSAGE, Definitions.KEY_FAMILYNAME));
    }

    if (!pseudonym.hasNonNull(Definitions.KEY_DISEASE_CODE)) {
      throw new IllegalArgumentException(
          String.format(Definitions.INCOMPLETE_PSEUDONYM_MESSAGE, Definitions.KEY_DISEASE_CODE));
    }

    if (!pseudonym.hasNonNull(Definitions.KEY_DATE_OF_BIRTH)) {
      throw new IllegalArgumentException(
          String.format(Definitions.INCOMPLETE_PSEUDONYM_MESSAGE, Definitions.KEY_DATE_OF_BIRTH));
    }

    log.debug("Validation successful");
  }

  private static List<String> toArray(JsonNode entry) {
    if (!entry.isArray()) {
      throw new IllegalArgumentException("Could not extract Pseudonym values");
    }

    List<String> elements = new ArrayList<>();
    entry.forEach(element -> elements.add(element.textValue()));

    return elements;
  }
}
