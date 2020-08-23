# Description
Microservice for searching data. Depend on InfluxDB

Provides http endpoint POST /api/v1/search

'request.http' file contains example of search query

# Getting Started

1. Run InfluxDB on port 8086 
    ``` docker run -p 8086:8086 -e INFLUXDB_DB=db0 -v $PWD:/var/lib/influxdb influxdb```

2. Specify active profile as 'local' or 'docker' and you are ready to go.
   Example: -Dspring.profiles.active=profile