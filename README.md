# PDF-to-Text

Spring Boot Application for converting PDF to raw text.

- - -

## Usage

Root path:
```shell
cd pdf-to-text/
```

Install jar:
```shell
mvn clean install
```

Install without running tests:
```shell
mvn clean install -DskipTests=true
```

Running:
```shell
java -jar target/pdf-to-text-${version}.jar
```

Curl:
```shell
curl --form file='@file.pdf' localhost:8081/pdf2text/api/1.0/convert
```

- - -

## Release Notes

 ### 0.0.1-SNAPSHOT
 - Support for provided file path and/or pdf.
 - Conversion to raw text.
 - Configurable logger, port and ip.
