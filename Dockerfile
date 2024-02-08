FROM maven:3.8.4-openjdk-17 AS build
COPY . /home/app/src
WORKDIR /home/app/src
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /home/app/src/target/*.jar ./
EXPOSE 7001
CMD ["java", "-jar", "customer-spring-boot-jenkins-0.0.1-SNAPSHOT.jar"]
