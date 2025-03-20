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

import de.gematik.demis.pseudo.storage.data.StorageEntry;
import java.sql.Timestamp;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StorageEntryTest {

  @Test
  void expectGetRecordIdWorks() {
    final var entry = StorageEntry.builder().recordId(0).build();
    Assertions.assertEquals(0, entry.getRecordId());
  }

  @Test
  void expectGetTechnicalIdWorks() {
    final String id = "an ID";
    final var entry = StorageEntry.builder().technicalId(id).build();
    Assertions.assertEquals(id, entry.getTechnicalId());
  }

  @Test
  void expectGetDemisIdWorks() {
    final String id = "an ID";
    final var entry = StorageEntry.builder().demisId(id).build();
    Assertions.assertEquals(id, entry.getDemisId());
  }

  @Test
  void expectGetPhoCodeWorks() {
    final String code = "a code";
    final var entry = StorageEntry.builder().phoCode(code).build();
    Assertions.assertEquals(code, entry.getPhoCode());
  }

  @Test
  void expectGetFamilyNameWorks() {
    final var entry = StorageEntry.builder().familyName(List.of("A", "B", "C")).build();
    Assertions.assertNotNull(entry.getFamilyName());
    Assertions.assertSame(3, entry.getFamilyName().size());
  }

  @Test
  void expectGetFirstNameWorks() {
    final var entry = StorageEntry.builder().firstName(List.of("A", "B", "C")).build();
    Assertions.assertNotNull(entry.getFirstName());
    Assertions.assertSame(3, entry.getFirstName().size());
  }

  @Test
  void expectGetDiseaseCodeWorks() {
    final String code = "a code";
    final var entry = StorageEntry.builder().diseaseCode(code).build();
    Assertions.assertEquals(code, entry.getDiseaseCode());
  }

  @Test
  void expectGetDateOfBirthWorks() {
    final String date = "2023-01-01";
    final var entry = StorageEntry.builder().dateOfBirth(date).build();
    Assertions.assertEquals(date, entry.getDateOfBirth());
  }

  @Test
  void expectGetCreationTimeWorks() {
    final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    final var entry = StorageEntry.builder().creationTime(timestamp).build();
    Assertions.assertEquals(timestamp, entry.getCreationTime());
  }

  @Test
  void expectSetRecordIdWorks() {
    Assertions.assertDoesNotThrow(() -> StorageEntry.builder().build().setRecordId(0));
  }

  @Test
  void expectSetTechnicalIdWorks() {
    Assertions.assertDoesNotThrow(() -> StorageEntry.builder().build().setTechnicalId("0"));
  }

  @Test
  void expectSetDemisIdWorks() {
    Assertions.assertDoesNotThrow(() -> StorageEntry.builder().build().setDemisId("0"));
  }

  @Test
  void expectSetPhoCodeWorks() {
    Assertions.assertDoesNotThrow(() -> StorageEntry.builder().build().setPhoCode("0"));
  }

  @Test
  void expectSetFamilyNameWorks() {
    Assertions.assertDoesNotThrow(
        () -> StorageEntry.builder().build().setFamilyName(List.of("Name")));
  }

  @Test
  void expectSetFirstNameWorks() {
    Assertions.assertDoesNotThrow(
        () -> StorageEntry.builder().build().setFirstName(List.of("Name")));
  }

  @Test
  void expectSetDiseaseCodeWorks() {
    Assertions.assertDoesNotThrow(() -> StorageEntry.builder().build().setDiseaseCode("code"));
  }

  @Test
  void expectSetDateOfBirthWorks() {
    Assertions.assertDoesNotThrow(
        () -> StorageEntry.builder().build().setDateOfBirth("2023-01-01"));
  }

  @Test
  void expectSetCreationTimeWorks() {
    Assertions.assertDoesNotThrow(
        () ->
            StorageEntry.builder()
                .build()
                .setCreationTime(new Timestamp(System.currentTimeMillis())));
  }
}
