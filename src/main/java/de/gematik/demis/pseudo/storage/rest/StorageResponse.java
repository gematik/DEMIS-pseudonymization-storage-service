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
import java.time.Instant;
import java.util.List;

/** Storage Response Object. */
public record StorageResponse(String demisId, String type, String timestamp, List<String> errors) {

  public StorageResponse(String demisId) {
    this(demisId, Definitions.TYPE_STORAGE_RESPONSE, Instant.now().toString(), List.of());
  }

  public StorageResponse(List<String> errors) {
    this(null, Definitions.TYPE_STORAGE_RESPONSE, Instant.now().toString(), errors);
  }
}
