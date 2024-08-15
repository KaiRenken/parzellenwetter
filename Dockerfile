FROM eclipse-temurin:21-jdk-alpine as app-build

COPY mvnw .
COPY .mvn .mvn/
COPY pom.xml .

# Maven should be executable
RUN chmod +x ./mvnw

# Copy application sources
COPY src src

# Copy git folder so that git info can be displayed
COPY .git .git

RUN \
    apk update && \
#dos2unix fixes ./mvnw: not found error on windows builds
    apk add dos2unix && \
    dos2unix mvnw && \
# libstdc++ is needed for npm but does not come by default with alpine
    apk add libstdc++

# Build artifact
RUN ./mvnw clean package -DskipTests -Pui
RUN mv target/*.jar target/app.jar


FROM eclipse-temurin:21-jre-alpine

COPY --from=app-build /target/app.jar .

# For Security reasons it is best practice to have container not run as root
# Alpine comnes with a default non-root guest user
USER guest

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]