FROM maven:3.9.2-eclipse-temurin-17-alpine

WORKDIR /merchandising-rest
COPY  . .

RUN mvn clean install -DskipTests

# Expose ports
#EXPOSE 8085

CMD mvn spring-boot:run