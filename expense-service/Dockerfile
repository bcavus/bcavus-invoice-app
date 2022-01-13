FROM adoptopenjdk/openjdk11:alpine-slim as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

ARG ENV_PROFILE
ENV ACTIVE_PROFILE=$ENV_PROFILE

RUN echo 'Active Profile: -> ' $ACTIVE_PROFILE

RUN ./mvnw install -Dspring.profiles.active=$ACTIVE_PROFILE -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM adoptopenjdk/openjdk11:alpine-jre

VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.bcavus.invoiceapp.expenseservice.ExpenseServiceApplication"]