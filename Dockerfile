# ====================================================================
# ============================ HOW TO USE ============================
# ====================================================================
# 1. Build an image from this Dockerfile using the following command:
#       >_ docker build -t com.switchfully/order-backend-app .
#          (where . stands for the current directory)
#          (where we tag (-t) our image giving it the unique name com.switchfully/order-backend-app (with version 'latest'))
#               https://docs.docker.com/engine/reference/commandline/build/#tag-an-image--t
# 2. Run one (or more) docker container(s) from the created image, using command:
#       >_ docker run -d -p 9500:9000 com.switchfully/order-backend-app
#          (option -d will run the container in detatched mode, thus you can reuse your terminal)
#               https://docs.docker.com/engine/reference/run/#detached--d
#          (option -p 9500:9000 will map internal port 9000 on which our Order application runs in the container to the outside docker host port 9500)
#               https://docs.docker.com/config/containers/container-networking/
# 3. Navigate in your browser to http://localhost:9500/swagger-ui.html to validate that the container is correctly running your application
# 4. (As of here, optional) Create a new app on Heroku
# 5. Install the Heroku CLI
# 6. Run the following commands in order:
#       >_ heroku login
#       >_ heroku container:login
#       >_ heroku container:push web -a order-jpa
#          (where -a order-jpa specifies the name of the app on heroku)
#          (where web is the process-type)
#       >_ heroku container:release web -a order-jpa
#       Runs on: https://order-jpa.herokuapp.com/swagger-ui.html

# Start with a base image containing Java runtime
FROM openjdk:11-jre-slim

# The EXPOSE instruction informs Docker that the container listens on the specified network ports at runtime.
# The EXPOSE instruction does not actually publish the port. It functions as a type of documentation between
# the person who builds the image and the person who runs the container, about which ports are intended to be published.
EXPOSE 9000

# Store the application's jar file in a temporary argument
ARG JAR_FILE=war/target/war-1.0-SNAPSHOT.jar

# Copy the application's jar (using the temporary argument) to the docker container (in the root)
COPY ${JAR_FILE} order-backend-app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/order-backend-app.jar"]

# Replace ENTRYPOINT for the command below when pushing (& releasing) to Heroku
# CMD ["sh", "-c", "java -Dserver.port=$PORT -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -jar /order-backend-app.jar"]
# Read this article on R14 and R10 Errors on Heroku: https://blog.codecentric.de/en/2019/08/spring-boot-heroku-docker-jdk11/
