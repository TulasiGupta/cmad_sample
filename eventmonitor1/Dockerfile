FROM maven

WORKDIR /opt/eventmonitor1

COPY . .

RUN mvn package 

EXPOSE 8443

CMD  java -jar target/EventsMonitor*jar

