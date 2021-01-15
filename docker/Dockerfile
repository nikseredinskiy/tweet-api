FROM openjdk:10-jre-slim as BUILD

# Env variables
ENV SBT_VERSION 1.4.6
ENV SCALA_VERSION 2.12.10

# Install Scala
RUN \
  apt-get update && \
  apt-get install -y --no-install-recommends apt-utils && \
  apt-get install -y curl && \
  curl -fsL https://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C /root/ && \
  echo >> /root/.bashrc && \
  echo "export PATH=~/scala-$SCALA_VERSION/bin:$PATH" >> /root/.bashrc

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get install sbt

COPY . ./project

WORKDIR /project

RUN sbt clean
RUN sbt assembly

FROM openjdk:10-jre-slim
RUN \
  apt-get update && \
  apt-get install -y --no-install-recommends apt-utils && \
  apt-get install -y wget

EXPOSE 8080

COPY --from=BUILD /project/target/scala-*/*.jar /opt/app/app.jar

WORKDIR /opt/app

RUN wget -O dd-java-agent.jar 'https://search.maven.org/classic/remote_content?g=com.datadoghq&a=dd-java-agent&v=LATEST'



CMD ["java", "-javaagent:dd-java-agent.jar", "-jar", "app.jar"]


