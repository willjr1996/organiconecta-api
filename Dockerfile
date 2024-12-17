FROM maven:3.8.4-openjdk-17 AS build 

WORKDIR /app 

COPY pom.xml . 

RUN mvn dependency:go-offline 

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/organiconecta-api-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/organiconecta-api-0.0.1-SNAPSHOT.jar" ]

# ./mvnw clean package  
# ./mvnw package 
# docker login 
# docker build -t organiconecta .
# docker tag organiconecta willjunior1996/organiconecta
# docker push organiconecta

# https://organiconecta.onrender.com/