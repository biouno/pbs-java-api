/*
 * The MIT License
 *
 * Copyright (c) 2012-2015 Bruno P. Kinoshita, BioUno
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.tupilabs.pbs.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * A PBS job. This job can be submitted to the PBS cluster, but its state is always detached. The information in a Job
 * object is updated as you call service methods.
 *
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public class Job implements Serializable {

    private static final long serialVersionUID = -8705011705872768446L;

    /**
     * Number used to index a job in a queue (used by qnodes).
     */
    private int queueIndex;

    /**
     * Job ID. Usually a sequential identification number followed by a . (dot) and the computer name (eg:
     * 23434.thunder.mackenzie.br).
     */
    private String id;

    /**
     * Job name. Assigned by the user.
     */
    private String name;

    /**
     * Job owner.
     */
    private String owner;

    /**
     * Resources used by the job.
     */
    private Map<String, String> resourcesUsed;

    /**
     * State of the job.
     */
    private String state;

    /**
     * Job queue name.
     */
    private String queue;

    /**
     * PBS server name.
     */
    private String server;

    private String checkpoint;

    private String ctime;

    private String errorPath;

    private String execHost;

    private String execPort;

    private String holdTypes;

    private String joinPath;

    private String keepFiles;

    private String mailPoints;

    private String mailUsers;

    private String mtime;

    /**
     * Job output path.
     */
    private String outputPath;

    /**
     * Job priority.
     */
    private int priority;

    private String qtime;

    /**
     * Whether a job can be rescheduled.
     */
    private boolean rerunable;

    /**
     * List of resources used by the job.
     */
    private Map<String, String> resourceList;

    private int sessionId;

    private int substate;

    private Map<String, String> variableList;

    private String euser;

    private String egroup;

    private String hashName;

    private int queueRank;

    /**
     * Job queue type.
     */
    private String queueType;

    /**
     * Job comment.
     */
    private String comment;

    private String etime;

    /**
     * Job exit status.
     */
    private int exitStatus;

    /**
     * Submit args. Usually a shell script, but can include too other command line parameters.
     */
    private String submitArgs;

    /**
     * Start time.
     */
    private String startTime;

    private int startCount;

    private int jobArrayId;

    /**
     * Whether the job is fault-tolerant or not.
     */
    private boolean faultTolerant;

    private String compTime;

    private int radix;

    /**
     * Job total runtime.
     */
    private double totalRuntime;

    /**
     * Host that submitted the job.
     */
    private String submitHost;

    private long walltimeRemaining;

    /**
     * Default constructor.
     */
    public Job() {
        super();

        this.resourceList = new HashMap<String, String>();
        this.resourcesUsed = new HashMap<String, String>();
        this.variableList = new HashMap<String, String>();
    }

    /**
     * @return the queueIndex
     */
    public int getQueueIndex() {
        return queueIndex;
    }

    /**
     * @param queueIndex the queueIndex to set
     */
    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the resourcesUsed
     */
    public Map<String, String> getResourcesUsed() {
        return resourcesUsed;
    }

    /**
     * @param resourcesUsed the resourcesUsed to set
     */
    public void setResourcesUsed(Map<String, String> resourcesUsed) {
        this.resourcesUsed = resourcesUsed;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the queue
     */
    public String getQueue() {
        return queue;
    }

    /**
     * @param queue the queue to set
     */
    public void setQueue(String queue) {
        this.queue = queue;
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the checkpoint
     */
    public String getCheckpoint() {
        return checkpoint;
    }

    /**
     * @param checkpoint the checkpoint to set
     */
    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }

    /**
     * @return the ctime
     */
    public String getCtime() {
        return ctime;
    }

    /**
     * @param ctime the ctime to set
     */
    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    /**
     * @return the errorPath
     */
    public String getErrorPath() {
        return errorPath;
    }

    /**
     * @param errorPath the errorPath to set
     */
    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }

    /**
     * @return the execHost
     */
    public String getExecHost() {
        return execHost;
    }

    /**
     * @param execHost the execHost to set
     */
    public void setExecHost(String execHost) {
        this.execHost = execHost;
    }

    /**
     * @return the execPort
     */
    public String getExecPort() {
        return execPort;
    }

    /**
     * @param execPort the execPort to set
     */
    public void setExecPort(String execPort) {
        this.execPort = execPort;
    }

    /**
     * @return the holdTypes
     */
    public String getHoldTypes() {
        return holdTypes;
    }

    /**
     * @param holdTypes the holdTypes to set
     */
    public void setHoldTypes(String holdTypes) {
        this.holdTypes = holdTypes;
    }

    /**
     * @return the joinPath
     */
    public String getJoinPath() {
        return joinPath;
    }

    /**
     * @param joinPath the joinPath to set
     */
    public void setJoinPath(String joinPath) {
        this.joinPath = joinPath;
    }

    /**
     * @return the keepFiles
     */
    public String getKeepFiles() {
        return keepFiles;
    }

    /**
     * @param keepFiles the keepFiles to set
     */
    public void setKeepFiles(String keepFiles) {
        this.keepFiles = keepFiles;
    }

    /**
     * @return the mailPoints
     */
    public String getMailPoints() {
        return mailPoints;
    }

    /**
     * @param mailPoints the mailPoints to set
     */
    public void setMailPoints(String mailPoints) {
        this.mailPoints = mailPoints;
    }

    /**
     * @return the mailUsers
     */
    public String getMailUsers() {
        return mailUsers;
    }

    /**
     * @param mailUsers the mailUsers to set
     */
    public void setMailUsers(String mailUsers) {
        this.mailUsers = mailUsers;
    }

    /**
     * @return the mtime
     */
    public String getMtime() {
        return mtime;
    }

    /**
     * @param mtime the mtime to set
     */
    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    /**
     * @return the outputPath
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * @param outputPath the outputPath to set
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return the qtime
     */
    public String getQtime() {
        return qtime;
    }

    /**
     * @param qtime the qtime to set
     */
    public void setQtime(String qtime) {
        this.qtime = qtime;
    }

    /**
     * @return the rerunable
     */
    public boolean isRerunable() {
        return rerunable;
    }

    /**
     * @param rerunable the rerunable to set
     */
    public void setRerunable(boolean rerunable) {
        this.rerunable = rerunable;
    }

    /**
     * @return the resourceList
     */
    public Map<String, String> getResourceList() {
        return resourceList;
    }

    /**
     * @param resourceList the resourceList to set
     */
    public void setResourceList(Map<String, String> resourceList) {
        this.resourceList = resourceList;
    }

    /**
     * @return the sessionId
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the substate
     */
    public int getSubstate() {
        return substate;
    }

    /**
     * @param substate the substate to set
     */
    public void setSubstate(int substate) {
        this.substate = substate;
    }

    /**
     * @return the variableList
     */
    public Map<String, String> getVariableList() {
        return variableList;
    }

    /**
     * @param variableList the variableList to set
     */
    public void setVariableList(Map<String, String> variableList) {
        this.variableList = variableList;
    }

    /**
     * @return the euser
     */
    public String getEuser() {
        return euser;
    }

    /**
     * @param euser the euser to set
     */
    public void setEuser(String euser) {
        this.euser = euser;
    }

    /**
     * @return the egroup
     */
    public String getEgroup() {
        return egroup;
    }

    /**
     * @param egroup the egroup to set
     */
    public void setEgroup(String egroup) {
        this.egroup = egroup;
    }

    /**
     * @return the hashName
     */
    public String getHashName() {
        return hashName;
    }

    /**
     * @param hashName the hashName to set
     */
    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    /**
     * @return the queueRank
     */
    public int getQueueRank() {
        return queueRank;
    }

    /**
     * @param queueRank the queueRank to set
     */
    public void setQueueRank(int queueRank) {
        this.queueRank = queueRank;
    }

    /**
     * @return the queueType
     */
    public String getQueueType() {
        return queueType;
    }

    /**
     * @param queueType the queueType to set
     */
    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the etime
     */
    public String getEtime() {
        return etime;
    }

    /**
     * @param etime the etime to set
     */
    public void setEtime(String etime) {
        this.etime = etime;
    }

    /**
     * @return the exitStatus
     */
    public int getExitStatus() {
        return exitStatus;
    }

    /**
     * @param exitStatus the exitStatus to set
     */
    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    /**
     * @return the submitArgs
     */
    public String getSubmitArgs() {
        return submitArgs;
    }

    /**
     * @param submitArgs the submitArgs to set
     */
    public void setSubmitArgs(String submitArgs) {
        this.submitArgs = submitArgs;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the startCount
     */
    public int getStartCount() {
        return startCount;
    }

    /**
     * @param startCount the startCount to set
     */
    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    /**
     * @return the faultTolerant
     */
    public boolean isFaultTolerant() {
        return faultTolerant;
    }

    /**
     * @param faultTolerant the faultTolerant to set
     */
    public void setFaultTolerant(boolean faultTolerant) {
        this.faultTolerant = faultTolerant;
    }

    /**
     * @return the compTime
     */
    public String getCompTime() {
        return compTime;
    }

    /**
     * @param compTime the compTime to set
     */
    public void setCompTime(String compTime) {
        this.compTime = compTime;
    }

    /**
     * @return the radix
     */
    public int getRadix() {
        return radix;
    }

    /**
     * @param radix the radix to set
     */
    public void setRadix(int radix) {
        this.radix = radix;
    }

    /**
     * @return the totalRuntime
     */
    public double getTotalRuntime() {
        return totalRuntime;
    }

    /**
     * @param totalRuntime the totalRuntime to set
     */
    public void setTotalRuntime(double totalRuntime) {
        this.totalRuntime = totalRuntime;
    }

    /**
     * @return the submitHost
     */
    public String getSubmitHost() {
        return submitHost;
    }

    /**
     * @param submitHost the submitHost to set
     */
    public void setSubmitHost(String submitHost) {
        this.submitHost = submitHost;
    }

    /**
     * @return the walltimeRemaining
     */
    public long getWalltimeRemaining() {
        return this.walltimeRemaining;
    }

    /**
     * @param walltimeRemaining the walltimeRemaining
     */
    public void setWalltimeRemaining(long walltimeRemaining) {
        this.walltimeRemaining = walltimeRemaining;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public int getJobArrayId() {
        return jobArrayId;
    }

    public void setJobArrayId(int jobArrayId) {
        this.jobArrayId = jobArrayId;
    }

}
