FROM openjdk:15-jdk-slim AS JAVA_BUILD
COPY . /usr/src/xhtml-validator
WORKDIR /usr/src/xhtml-validator
RUN ./gradlew jar

RUN cp /usr/src/xhtml-validator/build/libs/xhtml-validator.jar /.
WORKDIR /
ENTRYPOINT ["java", "-cp", "xhtml-validator.jar", "org.openstax.xml.Main"]
