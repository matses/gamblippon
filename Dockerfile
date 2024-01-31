FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/*-all.jar /app/gamblippon.jar
ENTRYPOINT ["java","-jar","/app/gamblippon.jar"]