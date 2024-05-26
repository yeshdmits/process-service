mvn clean install -f ../pom.xml
mvn clean compile jib:dockerBuild -f ../process-service-api/pom.xml

mvn clean compile jib:dockerBuild -f ../process-service-ui/pom.xml
