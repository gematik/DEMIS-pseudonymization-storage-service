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

import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/** Entity to store pseudonymised data. */
@Builder
@Getter
@Setter
@AllArgsConstructor
@Table(name = "demis_table")
public class StorageEntry {

  @Id
  @Column(value = "record_id")
  private int recordId;

  @Column(value = "technical_id")
  private String technicalId;

  @Column(value = "demis_id")
  private String demisId;

  @Column(value = "pho_code")
  private String phoCode;

  @Column(value = "family_name")
  private List<String> familyName;

  @Column(value = "first_name")
  private List<String> firstName;

  @Column(value = "disease_code")
  private String diseaseCode;

  @Column(value = "date_of_birth")
  private String dateOfBirth;

  @Column(value = "creation_time")
  private Timestamp creationTime;
}
