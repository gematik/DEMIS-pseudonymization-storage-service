package de.gematik.demis.pseudo.storage.unit.data;

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

import de.gematik.demis.pseudo.storage.data.EntityConverter;
import de.gematik.demis.pseudo.storage.unit.utils.DataGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntityConverterTest {

  @Test
  void expectConversionSuccessful() {
    Assertions.assertDoesNotThrow(
        () -> EntityConverter.fromJson(DataGenerator.createValidPayload()));
  }

  @Test
  void expectHandlesEmptyPseudonymWithoutError() {
    Assertions.assertDoesNotThrow(
        () -> EntityConverter.fromJson(DataGenerator.createPayloadWithEmptyValues()));
  }

  @Test
  void expectErrorOnPayloadWithoutBundleId() {
    Assertions.assertThrowsExactly(
        IllegalArgumentException.class,
        () -> EntityConverter.fromJson(DataGenerator.createPayloadWithoutBundleId()));
  }

  @Test
  void expectErrorOnPayloadWithoutPseudonym() {
    Assertions.assertThrowsExactly(
        IllegalArgumentException.class,
        () -> EntityConverter.fromJson(DataGenerator.createPayloadWithoutPseudonym()));
  }

  @Test
  void expectErrorOnPayloadWithEmptyPseudonym() {
    Assertions.assertThrowsExactly(
        IllegalArgumentException.class,
        () -> EntityConverter.fromJson(DataGenerator.createPayloadWithoutDataInPseudonym()));
  }
}
