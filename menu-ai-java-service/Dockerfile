<<<<<<< HEAD

FROM maven:3.9.6-eclipse-temurin-21 AS build_base
WORKDIR /app

COPY pom.xml ./

RUN mvn dependency:go-offline -B

FROM build_base AS build_prod

COPY . .

RUN mvn clean package

CMD ["mvn", "clean", "package"]
=======

FROM maven:3.9.6-eclipse-temurin-21 AS build_base
WORKDIR /app

COPY pom.xml ./

RUN mvn dependency:go-offline -B

FROM build_base AS build_prod

COPY . .

RUN mvn clean package

CMD ["mvn", "clean", "package"]
>>>>>>> fccaf06 (первый вариант настройки продюсера для кафки)
