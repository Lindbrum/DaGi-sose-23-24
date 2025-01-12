FROM maven:3.9.2-eclipse-temurin-17-alpine

WORKDIR /soap-proxy-prosumer-rest
COPY  . .

RUN mvn clean install -DskipTests

CMD mvn spring-boot:run