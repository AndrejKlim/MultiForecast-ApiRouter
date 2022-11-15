FROM maven:3-amazoncorretto-17 as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM amazoncorretto:17
COPY --from=build /home/app/target/apirouter-0.0.1-SNAPSHOT.jar /usr/local/lib/apirouter.jar
EXPOSE 9095
EXPOSE 6570
ENTRYPOINT ["java","-jar","/usr/local/lib/apirouter.jar"]