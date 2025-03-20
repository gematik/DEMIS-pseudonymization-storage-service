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

/** Class with definitions used across the application. */
public final class Definitions {

  public static final String API_KEY_HEADER = "x-api-key";
  public static final String INVALID_API_KEY_MESSAGE = "Invalid API Key in Request";
  public static final String WRONG_TYPE_BUNDLE_MESSAGE = "wrong type";
  public static final String MISSING_FIELD_BUNDLE_MESSAGE = "Missing %s";
  public static final String INCOMPLETE_PSEUDONYM_MESSAGE = "Incomplete Pseudonym (missing %s)";
  public static final String STORAGE_CONTENT_TYPE = "application/vnd.demis_storage+json";
  public static final String STORAGE_PATH = "/demis/storage"; // NOSONAR
  public static final String TYPE_STORAGE_RESPONSE = "demisStorageResponse";
  public static final String KEY_TYPE = "type";
  public static final String TYPE_STORAGE_REQUEST = "demisStorageRequest";
  public static final String KEY_NOTIFICATION_BUNDLE_ID = "notificationBundleId";
  public static final String KEY_PHO_CODE = "pho";
  public static final String KEY_PHO_CODE_UNKNOWN = "unbekannt";
  public static final String KEY_PSEUDONYM = "pseudonym";
  public static final String KEY_FAMILYNAME = "familyName";
  public static final String KEY_FIRSTNAME = "firstName";
  public static final String KEY_DISEASE_CODE = "diseaseCode";
  public static final String KEY_DATE_OF_BIRTH = "dateOfBirth";

  private Definitions() {}
}
