FROM openjdk:15-jdk-slim AS JAVA_BUILD
COPY ./src/ /usr/src/data-validator
WORKDIR /usr/src/data-validator
RUN javac org/openstax/xml/*.java


FROM validator/validator:20.3.16
COPY --from=JAVA_BUILD /usr/src/data-validator /dynamic-checks
ENTRYPOINT ["java", "-cp", "/dynamic-checks/", "org.openstax.xml.Main"]