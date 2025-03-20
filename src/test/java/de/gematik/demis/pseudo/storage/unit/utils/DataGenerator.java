package de.gematik.demis.pseudo.storage.unit.utils;

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
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.gematik.demis.pseudo.storage.data.Definitions;
import org.springframework.http.HttpHeaders;

public final class DataGenerator {
  public static final String DEFAULT_API_KEY =
      "505f45bf1319fdd83463e6ce246f6e9c5cda34c6799ad2d838c94fe35e8b5abd";

  private DataGenerator() {}

  public static JsonNode createValidPayload() {
    final var reqBody = JsonNodeFactory.instance.objectNode();
    reqBody.put(Definitions.KEY_TYPE, Definitions.TYPE_STORAGE_REQUEST);
    reqBody.put(Definitions.KEY_NOTIFICATION_BUNDLE_ID, "e56f32a8-fc8b-4177-bf84-29b4b9d281b3");
    reqBody
        .put(Definitions.KEY_PHO_CODE, "1.01.0.51.")
        .set(Definitions.KEY_PSEUDONYM, getValidPseudonym());
    return reqBody;
  }

  public static JsonNode createPayloadWithoutBundleId() {
    final var reqBody = JsonNodeFactory.instance.objectNode();
    reqBody.put(Definitions.KEY_TYPE, Definitions.TYPE_STORAGE_REQUEST);
    reqBody
        .put(Definitions.KEY_PHO_CODE, "1.01.0.51.")
        .set(Definitions.KEY_PSEUDONYM, getValidPseudonym());
    return reqBody;
  }

  public static JsonNode createPayloadWithEmptyValues() {
    final var emptyPseudonym = JsonNodeFactory.instance.objectNode();
    emptyPseudonym.put(Definitions.KEY_DISEASE_CODE, "covid19");
    emptyPseudonym.putArray(Definitions.KEY_FAMILYNAME);
    emptyPseudonym.putArray(Definitions.KEY_FIRSTNAME);
    emptyPseudonym.put(Definitions.KEY_DATE_OF_BIRTH, "");

    final var reqBody = JsonNodeFactory.instance.objectNode();
    reqBody.put(Definitions.KEY_TYPE, Definitions.TYPE_STORAGE_REQUEST);
    reqBody.put(Definitions.KEY_NOTIFICATION_BUNDLE_ID, "e56f32a8-fc8b-4177-bf84-29b4b9d281b3");
    reqBody
        .put(Definitions.KEY_PHO_CODE, "1.01.0.51.")
        .set(Definitions.KEY_PSEUDONYM, getValidPseudonym());

    return reqBody;
  }

  public static JsonNode createPayloadWithoutPseudonym() {
    final var reqBody = JsonNodeFactory.instance.objectNode();
    reqBody.put(Definitions.KEY_TYPE, Definitions.TYPE_STORAGE_REQUEST);
    reqBody.put(Definitions.KEY_NOTIFICATION_BUNDLE_ID, "e56f32a8-fc8b-4177-bf84-29b4b9d281b3");
    reqBody.put(Definitions.KEY_PHO_CODE, "1.01.0.51.");
    return reqBody;
  }

  public static JsonNode createPayloadWithoutDataInPseudonym() {
    final var reqBody = JsonNodeFactory.instance.objectNode();
    reqBody.put(Definitions.KEY_TYPE, Definitions.TYPE_STORAGE_REQUEST);
    reqBody
        .put(Definitions.KEY_PHO_CODE, "1.01.0.51.")
        .set(Definitions.KEY_PSEUDONYM, getPseudonymWithoutFields());
    return reqBody;
  }

  public static ObjectNode getValidPseudonym() {
    final var pseudonym = JsonNodeFactory.instance.objectNode();

    pseudonym.put(Definitions.KEY_DISEASE_CODE, "covid19");
    pseudonym
        .putArray(Definitions.KEY_FAMILYNAME)
        .add(
            "AABAQEQCAAAAAABAIAAAAAAABgAAAAAAABEmAAQABAAEAAwCBAAEAAQFDCAEAERgAAAAAAIAAAABAAAAAAAAAAQAABABQEAQMAAAAAAgACRAAAAAAJAIQAAFAAAQSIAAAAQAAAQEAAgAEAAAQAIQgABAAADAEQAACAABBIIBAAA=")
        .add(
            "AAEABAAIQAAEYAAAAAgAAAQACAABAECAAAUQAAMQAAEQAAEQAAEYAAkAgEAgAAQAAAAAAIAAAECAIAAAAAAEAAEAAAAAAJEKEAAAAAABAABAAAAQQIAQAAAAAQAAAAAQAEIAFAiAAAAIAIAAAAAAAAAABAAERIAAAEAECEAAIAA=")
        .add(
            "AAAIAIQAIAAhARQBARAABQAAEAgIIAAIBAiACAAAAAAAAQAAKRAAAIIAGQAIAAAAAgBEABAIAECIgAAgECAAAAEQAACAAQCgAEiABEEEDMBAAQAEAAAAQAghGAAACAEIAAIAGABAAEIAAhAAAADIABACAIAACQCAAAASAQBEAAg=");

    pseudonym
        .putArray(Definitions.KEY_FIRSTNAME)
        .add(
            "AQAAAQABAIAAIIAIAAIIAAAAIAAQgQAAEAAAAQAAAAAACAABCAACDAAACAAACAAACAEACoAAKAGACASACAAgAAAAAAAAAAABAAAAQAQBAAAQIAAAAAAAAwAAAAAIACgAAsAAAUACAAIACQAAEAAAAAKQCAAAAAAEAEAAACAAAQE=")
        .add(
            "ACAgAAkAAAQAAAYEEMAAAAIAIEACAAAIEoQAEhMRAAACAABAAgBABAJAAAAGCARABgAAAAIFCAAAQAAgAAAgQAAAAAQAwAAAAAMAAAAAiIAEACQQAAAAAAAAAEBETAAQBEIAAEAAAABBAEIQAAAEABBAAAAAAQFAAgAAAAQAAAI=")
        .add(
            "AAAAAEgAAKAAAAAAIAAAEAIABAACAAAAAgAAEBMRAAACAAABggAAAAIAIIACAEAAAgCAAAIAAAAAiAAAAAACAIAAAAABQAAAAAAAAAAgAAAAIAAAAJAAAAAEAEBETAAAEAAAAAAEAAAIAAACQAIACAAAAAAIAQgAAAAAAIAJAAA=");

    pseudonym.put(
        Definitions.KEY_DATE_OF_BIRTH,
        "JXnehJIS4ACEMQAlCFyAQDQAAECAAIByADIgAEIoYINpEgImQEI4CCIAACgAQQCAAEBCAAFAAIgIAYo2AEhKgA==");
    return pseudonym;
  }

  public static ObjectNode getPseudonymWithoutFields() {
    final var pseudonym = JsonNodeFactory.instance.objectNode();

    pseudonym.put(Definitions.KEY_DISEASE_CODE, "covid19");
    pseudonym.putArray(Definitions.KEY_FAMILYNAME);

    pseudonym.putArray(Definitions.KEY_FIRSTNAME);

    pseudonym.put(
        Definitions.KEY_DATE_OF_BIRTH,
        "JXnehJIS4ACEMQAlCFyAQDQAAECAAIByADIgAEIoYINpEgImQEI4CCIAACgAQQCAAEBCAAFAAIgIAYo2AEhKgA==");
    return pseudonym;
  }

  public static HttpHeaders getDefaultHeader(final String apiKey) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(Definitions.API_KEY_HEADER, apiKey);
    headers.set(HttpHeaders.CONTENT_TYPE, Definitions.STORAGE_CONTENT_TYPE);
    headers.set(HttpHeaders.ACCEPT, Definitions.STORAGE_CONTENT_TYPE);
    return headers;
  }

  public static HttpHeaders getDefaultHeaderWithContentType(
      final String apiKey, final String contentType, final String acceptContent) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(Definitions.API_KEY_HEADER, apiKey);
    headers.set(HttpHeaders.CONTENT_TYPE, contentType);
    headers.set(HttpHeaders.ACCEPT, acceptContent);
    return headers;
  }

  public static HttpHeaders getDefaultHeaderWithoutApiKey() {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, Definitions.STORAGE_CONTENT_TYPE);
    headers.set(HttpHeaders.ACCEPT, Definitions.STORAGE_CONTENT_TYPE);
    return headers;
  }
}
