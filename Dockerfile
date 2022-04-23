FROM openjdk:11

WORKDIR /home/java/app

COPY /target/articles*.jar app.jar

SHELL [ "/bin/sh", "-c" ]

EXPOSE 8080

CMD java -jar app.jar