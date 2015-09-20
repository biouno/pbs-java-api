# PBS Java API

A simple Java API for interfacing with a PBS cluster.

## API JavaDocs

The API JavaDocs are published in the gh-pages of this GitHub repository, and are publicly available at [http://biouno.org/pbs-java-api/](http://biouno.org/pbs-java-api/)

## Development guide

### Testing PBS servers with Docker

You can use Docker to create a container running Linux and a Torque PBS server with the following commands.

```
docker run -d -h docker.example.com \
-p 10022:22 \ # SSHD, SFTP
--privileged \ # run in privileged mode
--name torque \
agaveapi/torque
```

And later start it with `docker start torque` when necessary.

We are using the [agaveapi/torque](https://hub.docker.com/r/agaveapi/torque/) image.

### Publishing the JavaDocs to GitHub

We use ghp-import to publish the API JavaDocs to GitHub, with the following commands:

`mvn clean javadoc:javadoc && ghp-import -n -m "Publish JavaDocs to gh-pages branch" -p target/site/apidocs/`

The first statement cleans the existing reports, and creates the JavaDocs reports using the Maven plug-in. The second statement calls ghp-import passing the location of the API JavaDocs, which is used as working directory to commit to gh-pages branch.