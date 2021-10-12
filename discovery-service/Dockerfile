# Install the image of the Maven – JDK version.
FROM maven:3.8.2-jdk-11

# Path of the working directory.
WORKDIR /

# Copy all the files inside the project directory to the container.
COPY . .

# Execute a command-line inside the container: 
# mvn clean install to install the dependencies in pom.xml.
RUN mvn clean install

# run script mvn spring-boot:run after the image is built
CMD mvn spring-boot:run