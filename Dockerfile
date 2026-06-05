# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# copy pom first (better caching)
COPY pom.xml .

# copy your actual project folder
COPY Dream_shop ./Dream_shop

WORKDIR /app/Dream_shop

RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# copy jar from build stage
COPY --from=build /app/Dream_shop/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]