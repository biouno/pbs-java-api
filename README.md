# PBS Java API

A simple Java API for interfacing with a PBS cluster.

## Examples

Given the following PBS script from the agaveapi/torque repository.

```
#!/bin/bash
#PBS -l walltime=00:1:00
#PBS -l nice=19
#PBS -q debug

date
hostname
sleep 5
date
```

### qsub

You can submit to a PBS server with the following code.

```
    String scriptLocation = PBS001.class.getResource("/torque.submit").getFile();
    String jobId = PBS.qsub(scriptLocation);
    System.out.println(String.format("Job ID %s", jobId));
```

Which would be equivalent to `qsub /${HOME}/workspace/project/torque.submit`. The returned output is probably similar to "0.localhost", with the job id and the node name.

### tracejob

Or you could query the server for information about the job.

```
    CommandOutput co = PBS.traceJob("0", 30);
    System.out.println(co.getOutput());
```

Which is similar to tracejob 0, and will probably return something similar to:

```
[root@localhost 5.0.0]# tracejob 0
/var/spool/torque/server_logs/20150920: No such file or directory
/var/spool/torque/mom_logs/20150920: No such file or directory
/var/spool/torque/sched_logs/20150920: No such file or directory

Job: 0.localhost

09/20/2015 10:11:55  A    queue=debug
09/20/2015 10:11:55  A    user=testuser group=testuser jobname=torque.submit
                          queue=debug ctime=1442740315 qtime=1442740315
                          etime=1442740315 start=1442740315
                          owner=testuser@localhost exec_host=localhost/0
                          Resource_List.nice=19 Resource_List.walltime=00:01:00
                          
09/20/2015 10:12:00  A    user=testuser group=testuser jobname=torque.submit
                          queue=debug ctime=1442740315 qtime=1442740315
                          etime=1442740315 start=1442740315
                          owner=testuser@localhost exec_host=localhost/0
                          Resource_List.nice=19 Resource_List.walltime=00:01:00
                          session=235 end=1442740320 Exit_status=0
                          resources_used.cput=00:00:00 resources_used.mem=0kb
                          resources_used.vmem=0kb
                          resources_used.walltime=00:00:05
```

## API JavaDocs

The API JavaDocs are published in the gh-pages of this GitHub repository, and are publicly available at [http://biouno.org/pbs-java-api/](http://biouno.org/pbs-java-api/)

## Developers guide

Contributions as pull requests, issues or comments and suggestions are welcome. Please use the [BioUno mailing lists](http://biouno.org/contact.html) to send us your feedback. For pull requests and issues, use the project repository in GitHub, please. 

### Testing PBS servers with Docker

You can use Docker to create a container running Linux and a Torque PBS server with the following commands.

```
docker run -d -h docker.example.com \
-p 10022:22 \
--privileged \
--name torque \
agaveapi/torque
```

Or to expose the PBS ports.

```
docker run -d -h docker.example.com \
-p 10022:22 \
-p 15001:15001 \
-p 15002:15002 \
-p 15003:15003 \
-p 15004:15004 \
--privileged \
--name torque \
agaveapi/torque
```

And later start it with `docker start torque` when necessary.

We are using the [agaveapi/torque](https://hub.docker.com/r/agaveapi/torque/) image.

### Publishing the JavaDocs to GitHub

We use ghp-import to publish the API JavaDocs to GitHub, with the following commands:

`mvn clean javadoc:javadoc && ghp-import -n -m "Publish JavaDocs to gh-pages branch" -p target/site/apidocs/`

The first statement cleans the existing reports, and creates the JavaDocs reports using the Maven plug-in. The second statement calls ghp-import passing the location of the API JavaDocs, which is used as working directory to commit to gh-pages branch.