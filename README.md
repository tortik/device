# Architecture
Basic pub-sub (topic per device type) with time series DB as a data store.

Every app is based on hexagon/onion architecture

## Scale
1. New device type? - add a topic.
2. Publishers scale? - we can have robin-bobin with multiple topics
3. Subscribers scale? - add more subscribers, with different subscription group
4. Broker can't handle load? - use two brokers  
5. DB can't handle writes? - make a cluster
6. DB can't handle read? - have another DB (CQRS)

## Security 
There is only network level security inside docker and basic authentication for broker and DB

For having secure WebService I would require more requirements.

What authentication type? Do we need basic per each call or OAuth with tokens

How we gonna authorize clients? Per device owners or set of roles, attributes

# How to Run
Requirements: Docker

Run ```run.sh``` script that should bring up multiple devices and consumers with broker and database


# Reference
Artemis console (admin/admin):
```http://localhost:8161/console```

Processor app actuator
```http://localhost:9080/actuator```

Device HeartRate actuator
```http://localhost:8081/actuator```

Search Service
```http://localhost:8080/actuator```

Search Call Example
```POST http://localhost:8080/api/v1/search
   Content-Type: application/json
   
   
   {
       "deviceType": "HEART_RATE_METER"
   }
   
```