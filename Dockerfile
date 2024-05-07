FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /workspace/app
COPY ./ ./

RUN --mount=type=cache,target=/root/.gradle ./gradlew clean bootJar
RUN mkdir build/extracted && (java -Djarmode=layertools -jar build/libs/*.jar extract --destination build/extracted)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG EXTRACTED=/workspace/app/build/extracted
COPY --from=builder ${EXTRACTED}/dependencies/ ./
COPY --from=builder ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=builder ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=builder ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]