package de.gematik.demis.pseudo.storage.cucumbertest.glue;

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
import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/** JDBC Connector to test if data can be read from DB. */
@Slf4j
public class DatabaseConnector implements Closeable {
  private final Connection connection;

  public DatabaseConnector(final String jdbcUrl, final String username, final String password) {
    this.connection = initConnection(jdbcUrl, username, password);
  }

  @SneakyThrows
  private Connection initConnection(
      final String jdbcUrl, final String username, final String password) {
    return DriverManager.getConnection(jdbcUrl, username, password);
  }

  @SneakyThrows
  public boolean isConnected() {
    return !connection.getClientInfo().isEmpty();
  }

  @SneakyThrows
  @Override
  public void close() {
    connection.close();
  }

  @SneakyThrows
  public List<StorageEntry> readData() {
    final var storageEntries = new ArrayList<StorageEntry>();
    try (PreparedStatement statement =
        connection.prepareStatement(
            """
                    SELECT *
                    FROM demis_table
                """)) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          StorageEntry storageEntry =
              StorageEntry.builder()
                  .recordId(resultSet.getInt("record_id"))
                  .demisId(resultSet.getString("demis_id"))
                  .phoCode(resultSet.getString("pho_code"))
                  .creationTime(resultSet.getTimestamp("creation_time"))
                  .build();
          log.info("Retrieved StorageEntry with Id {}", storageEntry.getDemisId());
          storageEntries.add(storageEntry);
        }
      }
    }

    log.info("Got {} results", storageEntries.size());
    return storageEntries;
  }

  @SneakyThrows
  public List<StorageEntry> readDataOlderThan(final int days) {
    final var storageEntries = new ArrayList<StorageEntry>();
    try (PreparedStatement statement =
        connection.prepareStatement(
            String.format(
                """
                   SELECT *
                   FROM demis_table dt WHERE dt.creation_time <= current_date at time zone 'Europe/Berlin' - interval '%d days'
                """,
                days))) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          StorageEntry storageEntry =
              StorageEntry.builder()
                  .recordId(resultSet.getInt("record_id"))
                  .demisId(resultSet.getString("demis_id"))
                  .phoCode(resultSet.getString("pho_code"))
                  .creationTime(resultSet.getTimestamp("creation_time"))
                  .build();
          log.info(
              "Retrieved StorageEntry with Id {} and creation time {}",
              storageEntry.getDemisId(),
              storageEntry.getCreationTime());
          storageEntries.add(storageEntry);
        }
      }
    }

    log.info("Got {} results", storageEntries.size());
    return storageEntries;
  }

  @SneakyThrows
  public List<StorageEntry> readDataAsOldAs(final int days) {
    final var storageEntries = new ArrayList<StorageEntry>();
    try (PreparedStatement statement =
        connection.prepareStatement(
            String.format(
                """
                        SELECT *
                        FROM demis_table dt WHERE dt.creation_time = current_date at time zone 'Europe/Berlin' - interval '%d days'
                     """,
                days))) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          StorageEntry storageEntry =
              StorageEntry.builder()
                  .recordId(resultSet.getInt("record_id"))
                  .demisId(resultSet.getString("demis_id"))
                  .phoCode(resultSet.getString("pho_code"))
                  .creationTime(resultSet.getTimestamp("creation_time"))
                  .build();
          log.info(
              "Retrieved StorageEntry with Id {} and creation time {}",
              storageEntry.getDemisId(),
              storageEntry.getCreationTime());
          storageEntries.add(storageEntry);
        }
      }
    }

    log.info("Got {} results", storageEntries.size());
    return storageEntries;
  }
}
