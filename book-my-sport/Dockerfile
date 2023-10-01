# Use the official SBT image as the base image
FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1 as builder

# Set the working directory inside the container
WORKDIR /app

COPY build.sbt .
ADD project ./project
RUN sbt update
COPY . .
ARG BUILD_VERSION

# Build your Play application using sbt
RUN sbt compile stage

# Expose the port your application will run on (9001 in this case)
EXPOSE 9001

# Start your Play application
CMD ["target/universal/stage/bin/book-my-sport", "-Dhttp.port=9001"]
