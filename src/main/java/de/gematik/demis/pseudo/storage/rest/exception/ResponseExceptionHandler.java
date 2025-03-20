package de.gematik.demis.pseudo.storage.rest.exception;

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

import de.gematik.demis.pseudo.storage.rest.StorageResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Exception Handler for REST Operations. */
@ControllerAdvice
public class ResponseExceptionHandler {
  private static final String INTERNAL_ERROR_MESSAGE =
      "An Internal Server error has occurred. Please check the logs for more information about the issue.";

  @ExceptionHandler(IllegalStateException.class)
  protected ResponseEntity<StorageResponse> handleIllegalStateException(IllegalStateException ex) {
    return ResponseEntity.internalServerError()
        .body(new StorageResponse(List.of(INTERNAL_ERROR_MESSAGE)));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<StorageResponse> handleInvalidArgumentException(
      IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(new StorageResponse(List.of(ex.getLocalizedMessage())));
  }

  @ExceptionHandler(NullPointerException.class)
  protected ResponseEntity<StorageResponse> handleNullPointerException(NullPointerException ex) {
    return ResponseEntity.badRequest().body(new StorageResponse(List.of("Invalid input")));
  }
}
