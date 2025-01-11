FROM maven:3.9.2-eclipse-temurin-17-alpine

WORKDIR /config-server
COPY  . .

RUN mvn clean install -DskipTests

# Expose ports
#EXPOSE 8888

CMD mvn spring-boot:run