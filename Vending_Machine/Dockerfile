FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY src ./src

RUN javac -encoding UTF-8 src/*.java

CMD ["java","-cp","src","Main"]
