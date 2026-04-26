FROM maven:3.9.9-eclipse-temurin-17-bookworm

WORKDIR /workspace

RUN apt-get update \
    && apt-get install -y --no-install-recommends chromium \
    && rm -rf /var/lib/apt/lists/*

ENV CHROME_BIN=/usr/bin/chromium
ENV JAVA_TOOL_OPTIONS="-Dheadless=true"

COPY selenium-tests-java/ ./selenium-tests-java/
COPY student-form-app/ ./student-form-app/

WORKDIR /workspace/selenium-tests-java

CMD ["mvn", "-B", "test", "-Dheadless=true"]