FROM openjdk:15-jdk-slim AS JAVA_BUILD
COPY . /usr/src/xhtml-validator
WORKDIR /usr/src/xhtml-validator
RUN ./gradlew jar


FROM validator/validator:20.3.16
COPY --from=JAVA_BUILD /usr/src/xhtml-validator/build/libs/xhtml-validator.jar /.
ENTRYPOINT ["java", "-cp", "xhtml-validator.jar", "org.openstax.xml.Main"]