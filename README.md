# PBS Java API

A simple Java API for interfacing with a PBS cluster.

## Testing PBS servers with Docker

You can use Docker to create a container running Linux and a Torque PBS server with the following commands.

```
docker run -d -h docker.example.com \
-p 10022:22 \ # SSHD, SFTP
--privileged \ # run in privileged mode
--name torque \
agaveapi/torque
```

We are using the [agaveapi/torque](https://hub.docker.com/r/agaveapi/torque/) image.