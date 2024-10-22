FROM maven:3.9.9-sapmachine-21 AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app/pom.xml
RUN mvn clean package -DskipTests

RUN ls -la /app/target

CMD ["sh", "-c", "java -jar /app/target/*.jar"]