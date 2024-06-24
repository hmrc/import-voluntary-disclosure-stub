# import-voluntary-disclosure-stub

## Purpose
This stub is for local development and staging support. It provides endpoints to stub out the off platform calls that the service makes.

## Running the service
### Service manager
`sm2 --start IMPORT_VOLUNTARY_DISCLOSURE_STUB`
### Locally
`sbt run` or `sbt 'run 7952'`

## Endpoints
The following endpoints are implemented:


* **GET         /subscriptions/subscriptiondisplay/v1**        
  MDG SUB09 endpoint for retrieving Known EORI Details

* **POST        /cpr/caserequest/c18/create/v1**              
  EIS endpoint for creating a new submission

* **POST        /cpr/caserequest/c18/update/v1**              
  EIS endpoint for updating an existing submission

* **POST        /api/v2/init**              
  Address Lookup Frontend endpoint to initiate the journey

* **GET         /api/confirmed**              
  Address Lookup Frontend endpoint to retrieve and address

### Scalafmt
This repository uses [Scalafmt](https://scalameta.org/scalafmt/), a code formatter for Scala. The formatting rules configured for this repository are defined within [.scalafmt.conf](.scalafmt.conf).

To apply formatting to this repository using the configured rules in [.scalafmt.conf](.scalafmt.conf) execute:

 ```
 sbt scalafmtAll
 ```

To check files have been formatted as expected execute:

 ```
 sbt scalafmtCheckAll scalafmtSbtCheck
 ```