FROM openjdk:17-jdk-alpine
ADD build/libs/*.jar /app/app.jar
# copy all files to app directory
COPY . ./app
# specify work directory
WORKDIR /app
# Run Jar
ENTRYPOINT ["java", "-jar", "./app.jar"]
# specify port to run on
EXPOSE 8080
# run inside image
RUN ./gradlew dependency:go-offline
COPY src ./src
# run inside docker
RUN ./gradlew bootBuildImage