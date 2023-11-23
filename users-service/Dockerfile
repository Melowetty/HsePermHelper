FROM gradle:8.4.0-jdk17-alpine AS TEMP_BUILD_IMAGE

# build container
WORKDIR /home/gradle/project

COPY users-service/build.gradle.kts .
COPY users-service/src ./src
COPY settings.gradle.kts .
COPY shared ./shared

RUN gradle clean build --no-daemon -x test


# actual container
FROM openjdk:17-jdk-slim
ENV ARTIFACT_NAME=users-service-standalone.jar
ENV APP_HOME=/home/gradle/project

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

ENTRYPOINT ["java", "-jar", "users-service-standalone.jar"]