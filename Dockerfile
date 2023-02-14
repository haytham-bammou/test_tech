FROM openjdk:17-jdk-slim
EXPOSE 8080
RUN mkdir /usr/app && mkdir /usr/app/data
COPY ./target/demo-*.jar /usr/app
WORKDIR /usr/app
CMD java -jar demo-*.jar