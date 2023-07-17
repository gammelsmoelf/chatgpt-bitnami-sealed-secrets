FROM registry.access.redhat.com/ubi9:latest as builder

ENV VERSION=0.17.3
ENV CHECKSUM='5270b926c5689ba1587cdc8ea8dcc1283fc3c5863d663f5fe2b31372ed60c19e  kubeseal-0.17.3-linux-amd64.tar.gz'

RUN curl --insecure  -L https://github.com/bitnami-labs/sealed-secrets/releases/download/v${VERSION}/kubeseal-${VERSION}-linux-amd64.tar.gz -o kubeseal-${VERSION}-linux-amd64.tar.gz && \
    echo "${CHECKSUM}" > kubeseal-${VERSION}-linux-amd64.tar.gz_SHA256SUMS && \
    sha256sum -c kubeseal-${VERSION}-linux-amd64.tar.gz_SHA256SUMS &&\
    tar -xzvf kubeseal-${VERSION}-linux-amd64.tar.gz kubeseal && \
    mv kubeseal /tmp/ && \
    chmod 755 /tmp/kubeseal

FROM registry.access.redhat.com/ubi9/openjdk-17-runtime@sha256:5ce6423557629f5ecca005c2084b4afa8629ea87c93a7bdc530b4a650e48b436
USER root

RUN useradd kubeseal

USER kubeseal

COPY --from=builder --chown=kubeseal:kubeseal /tmp/kubeseal /usr/bin/kubeseal
COPY --chown=kubeseal:kubeseal build/libs/application-entrypoint-0.0.1.jar /application-entrypoint.jar
COPY --chown=kubeseal:kubeseal files/mycert.pem /mycert.pem

ENTRYPOINT ["java", "-jar","/application-entrypoint.jar"]

