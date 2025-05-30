package de.gematik.demis.pseudo.storage.cucumbertest;

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

import static org.assertj.core.api.Assertions.assertThat;

import de.gematik.demis.pseudo.storage.rest.StorageResponse;
import java.util.Objects;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

/** Check response if it contains expected data */
@Slf4j
@Data
public class ResponseChecker {

  private ResponseEntity<StorageResponse> response;
  private ResponseEntity<StorageResponse> secondResponse;

  public void checkResultStatus(int expectedStatus) {
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode().value()).isEqualTo(expectedStatus);
  }

  public void checkErrorMessage(String expectedErrorMessage) {
    assertThat(response).isNotNull();
    assertThat(response.getBody()).isNotNull();
    if (Objects.nonNull(expectedErrorMessage) && !expectedErrorMessage.isBlank()) {
      assertThat(response.getBody().errors()).contains(expectedErrorMessage);
    }
  }
}
