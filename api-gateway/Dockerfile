FROM gradle:8.4.0-jdk17-alpine AS TEMP_BUILD_IMAGE

# build container
WORKDIR /home/gradle/project

COPY api-gateway/build.gradle.kts .
COPY api-gateway/src ./src
COPY settings.gradle.kts .
COPY shared ./shared

RUN gradle clean build --no-daemon -x test


# actual container
FROM openjdk:17-jdk-slim
ENV ARTIFACT_NAME=api-gateway-standalone.jar
ENV APP_HOME=/home/gradle/project

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

ENTRYPOINT ["java", "-jar", "api-gateway-standalone.jar"]