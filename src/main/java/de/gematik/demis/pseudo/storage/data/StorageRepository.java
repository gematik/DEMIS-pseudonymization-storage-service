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

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

/**
 * Repository exposing DB operations. Defines strictly the exposed operations for the Database,
 * therefore {@link Repository} will be used instead of {@link
 * org.springframework.data.repository.CrudRepository}.
 */
public interface StorageRepository extends Repository<StorageEntry, String> {

  /**
   * Implements the store operation.
   *
   * @param storageInfo the data structure holding the information to be persisted
   * @return the persisted entity
   */
  StorageEntry save(StorageEntry storageInfo);

  /**
   * Implements the delete operation after a configured amount of days.
   *
   * @param days the number of days after an entry was created
   */
  @Query(
      value =
          "DELETE FROM demis_table dt WHERE dt.creation_time <= current_date at time zone 'Europe/Berlin' - make_interval(days => :days)")
  @Modifying
  void deleteAllByCreationTimeOlderThan(final int days);
}
