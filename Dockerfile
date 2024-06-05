FROM openjdk:20-oracle
ADD target/e-pharmacy.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","app.jar"]