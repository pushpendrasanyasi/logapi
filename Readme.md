Assumption:
--------------------
S3 integration is not made, but localdisk has been used for storing raw logs and processed logs
Get logs API triggers log processing and will repond with a status of in_progress till the processing
of log for the given dates is completed.
Once the processing is completed, from next call onwards the api will return the server file paths for the logs files.

Configurations to be made prior to running the server
-----------------------------------------------------
Edit the application.properties file
configure the log directory in the local disk(local-disk.log.path)

Compile:
---------------
mvn clean install

Run
----------------
java -jar path\to\jar\file\LogApi-1.0.jar com.solution.Application



Sample CURL:
------------
API for retrieving regexPatterns
curl --location --request GET 'localhost:8080/log/api/1.0/regex'

API for getting raw logs between dates
curl --location --request GET 'localhost:8080/log/api/1.0/raw?from_date=2023-01-01&to_date=2023-01-26'

API for retrieving logs for a regex pattern between dates
curl --location --request GET 'localhost:8080/log/api/1.0/sensitive?from_date=2023-01-21&to_date=2023-01-26'

