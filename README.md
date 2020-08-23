# Description



#Reference
Artemis console:
```http://localhost:8161/console```
Processor app actuator
```http://localhost:9080/actuator```
Device HeartRate actuator
```http://localhost:8081/actuator```
Search Service
```http://localhost:8080/actuator```

```POST http://localhost:8080/api/v1/search
   Content-Type: application/json
   
   
   {
       "deviceType": "HEART_RATE_METER"
   }
   
```