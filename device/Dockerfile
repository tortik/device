FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/device*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java $JAVA_OPTS  -jar /app.jar

#FROM openjdk:8-jdk-alpine
#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
#COPY pom.xml /tmp/
#COPY src /tmp/src/
#WORKDIR /tmp/
#RUN mvn package
#
#FROM openjdk:8-jdk-alpine
#COPY --from=MAVEN_TOOL_CHAIN /tmp/target/device*.jar app.jar
#ENTRYPOINT exec java FROM openjdk:8-jdk-alpine
                      #FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
                      #COPY pom.xml /tmp/
                      #COPY src /tmp/src/
                      #WORKDIR /tmp/
                      #RUN mvn package
                      #
                      #FROM openjdk:8-jdk-alpine
                      #COPY --from=MAVEN_TOOL_CHAIN /tmp/target/device*.jar app.jar
                      #ENTRYPOINT exec java $JAVA_OPTS  -jar /app.jar$JAVA_OPTS  -jar /app.jar