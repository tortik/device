# Description
Microservice for processing messages from IoT devices

Receive jms messages from specific topic.

# Getting Started

1. Run Artemis on port 61616 
    ```
    docker run -it --rm -p 8161:8161 -p 61616:61616 -e ARTEMIS_USERNAME=admin -e ARTEMIS_PASSWORD=admin vromero/activemq-artemis
    ```

2. Specify active profile as 'local' or 'docker' and you are ready to go.
   Example: `-Dspring.profiles.active=profile`
   