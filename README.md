<img align="right" width="250" height="47" src="/media/Gematik_Logo_Flag.png"/> <br/>

# Pseudonymization Storage Service

## Description

This service stores a _pseudonym_ together with a _demisId_ (that references the NotificationBundleId) in a (Postgres-)Database.
Every 6 hours a cleanup task will delete all the _pseudonym_ information that are older than a certain amount of days (by default 90, configurable with the environment variable **STORAGE_CLEANUP_AFTER_DAYS**).
It represents a new development of its predecessor, the `espri-storage-service`.

## Quality Gate
![Alert Status](https://sonar.prod.ccs.gematik.solutions/api/project_badges/measure?branch=main&project=de.gematik.demis%3Apseudonymization-storage-service&metric=alert_status&token=7df02901452371d0d612ae5ad38a3e539b3f1d11) ![Bugs](https://sonar.prod.ccs.gematik.solutions/api/project_badges/measure?branch=main&project=de.gematik.demis%3Apseudonymization-storage-service&metric=bugs&token=7df02901452371d0d612ae5ad38a3e539b3f1d11) ![Coverage](https://sonar.prod.ccs.gematik.solutions/api/project_badges/measure?branch=main&project=de.gematik.demis%3Apseudonymization-storage-service&metric=coverage&token=7df02901452371d0d612ae5ad38a3e539b3f1d11) ![NCLOC](https://sonar.prod.ccs.gematik.solutions/api/project_badges/measure?branch=main&project=de.gematik.demis%3Apseudonymization-storage-service&metric=ncloc&token=7df02901452371d0d612ae5ad38a3e539b3f1d11) ![Vulnerabilities](https://sonar.prod.ccs.gematik.solutions/api/project_badges/measure?branch=main&project=de.gematik.demis%3Apseudonymization-storage-service&metric=vulnerabilities&token=7df02901452371d0d612ae5ad38a3e539b3f1d11) ![SQALE Index](https://sonar.prod.ccs.gematik.solutions/api/project_badges/measure?branch=main&project=de.gematik.demis%3Apseudonymization-storage-service&metric=sqale_index&token=7df02901452371d0d612ae5ad38a3e539b3f1d11)

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#release-notes">Release Notes</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#security-policy">Security Policy</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


### Release Notes

See [ReleaseNotes](ReleaseNotes.md) for all information regarding the (newest) releases.

## Getting Started

### Prerequisites

The Project requires Java 17 and Maven 3.8+, for integration tests also Docker is required (due the usage of Testcontainers).

### Installation

The Project can be built with the following command:

```sh
mvn clean install
```

The Docker Image associated to the service can be built with the extra profile `docker`:

```sh
mvn clean install -Pdocker
```

## Usage

The application can be executed from a JAR file or a Docker Image:

```sh
# As JAR Application
java -jar target/pseudonymization-storage-service.jar
# As Docker Image
docker run --rm -it -p 8080:8080 pseudonymization-storage-service:latest
```

It requires a valid configuration for the Database (Postgres) and the configuration can be done overriding the `application.properties` file or defining environment variables.

It can also be deployed on Kubernetes by using the Helm Chart defined in the folder `deployment/helm/pseudonymization-storage-service`:

```ssh
helm install pseudonymization-storage-service ./deployment/helm/pseudonymization-storage-service
```

### Endpoints
Subsequently we list the endpoints provided by the IGS-Service.

| HTTP Method | Endpoint       | Parameters    | Description                                              |
|-------------|----------------|---------------|----------------------------------------------------------|
| POST        | /demis/storage | -             | Endpoint for storing the Pseudonym in the database table |

## Contributing

If you want to contribute, please check our [CONTRIBUTING.md](.github/CONTRIBUTING.md).

## Security Policy

If you want to see the security policy, please check our [SECURITY.md](.github/SECURITY.md).

## License
EUROPEAN UNION PUBLIC LICENCE v. 1.2

EUPL © the European Union 2007, 2016

Following terms apply:

1. Copyright notice: Each published work result is accompanied by an explicit statement of the license conditions for use. These are regularly typical conditions in connection with open source or free software. Programs described/provided/linked here are free software, unless otherwise stated.

2. Permission notice: Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions::

    1. The copyright notice (Item 1) and the permission notice (Item 2) shall be included in all copies or substantial portions of the Software.

    2. The software is provided "as is" without warranty of any kind, either express or implied, including, but not limited to, the warranties of fitness for a particular purpose, merchantability, and/or non-infringement. The authors or copyright holders shall not be liable in any manner whatsoever for any damages or other claims arising from, out of or in connection with the software or the use or other dealings with the software, whether in an action of contract, tort, or otherwise.

    3. The software is the result of research and development activities, therefore not necessarily quality assured and without the character of a liable product. For this reason, gematik does not provide any support or other user assistance (unless otherwise stated in individual cases and without justification of a legal obligation). Furthermore, there is no claim to further development and adaptation of the results to a more current state of the art.

3. Gematik may remove published results temporarily or permanently from the place of publication at any time without prior notice or justification.

4. Please note: Parts of this code may have been generated using AI-supported technology.’ Please take this into account, especially when troubleshooting, for security analyses and possible adjustments.

See [LICENSE](LICENSE.md).

## Contact

E-Mail to [DEMIS Entwicklung](mailto:demis-entwicklung@gematik.de?subject=[GitHub]%20pseudonymization-storage-service)