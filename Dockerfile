FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw

COPY src src
RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/event-booking-*.jar app.jar

EXPOSE 8084

#ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]

#Host
#ep-damp-haze-apc1y1y2.c-7.us-east-1.aws.neon.tech
#Database
#neondb
#Role
#neondb_owner
#Password
#************
#Pooler host
#ep-damp-haze-apc1y1y2-pooler.c-7.us-east-1.aws.neon.tech