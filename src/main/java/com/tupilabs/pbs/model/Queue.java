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
 * A PBS queue.
 *
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public class Queue implements Serializable {

    private static final long serialVersionUID = -577795360134584431L;

    private String name;

    private String queueType;

    private int priority = -1;

    private int totalJobs = -1;

    private String stateCount;

    private String mtime;

    private final Map<String, String> resourcesMax;

    private final Map<String, String> resourcesMin;

    private final Map<String, String> resourcesAssigned;

    private final Map<String, String> resourcesDefault;

    private int maxUserRun = -1;

    private boolean enabled;

    private boolean started;

    /**
     * Default constructor.
     */
    public Queue() {
        super();

        this.resourcesMax = new HashMap<String, String>();
        this.resourcesMin = new HashMap<String, String>();
        this.resourcesAssigned = new HashMap<String, String>();
        this.resourcesDefault = new HashMap<String, String>();
    }

    /**
     * Constructor with args.
     *
     * @param name name
     * @param queueType queye type
     * @param priority queue priority
     * @param totalJobs total of jobs
     * @param stateCount state count
     * @param mtime mtime
     * @param maxUserRun maximum user run
     * @param enabled enabled flag
     * @param started started flag
     */
    public Queue(String name, String queueType, int priority, int totalJobs, String mtime, String stateCount,
            int maxUserRun, boolean enabled, boolean started) {
        super();
        this.name = name;
        this.queueType = queueType;
        this.priority = priority;
        this.totalJobs = totalJobs;
        this.mtime = mtime;
        this.stateCount = stateCount;
        this.maxUserRun = maxUserRun;
        this.enabled = enabled;
        this.started = started;
        this.resourcesMax = new HashMap<String, String>();
        this.resourcesMin = new HashMap<String, String>();
        this.resourcesAssigned = new HashMap<String, String>();
        this.resourcesDefault = new HashMap<String, String>();
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
     * @return the totalJobs
     */
    public int getTotalJobs() {
        return totalJobs;
    }

    /**
     * @param totalJobs the totalJobs to set
     */
    public void setTotalJobs(int totalJobs) {
        this.totalJobs = totalJobs;
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
     * @return the stateCount
     */
    public String getStateCount() {
        return stateCount;
    }

    /**
     * @param stateCount the stateCount to set
     */
    public void setStateCount(String stateCount) {
        this.stateCount = stateCount;
    }

    /**
     * @return the maxUserRun
     */
    public int getMaxUserRun() {
        return maxUserRun;
    }

    /**
     * @param maxUserRun the maxUserRun to set
     */
    public void setMaxUserRun(int maxUserRun) {
        this.maxUserRun = maxUserRun;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * @param started the started to set
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * @return the resourcesMax
     */
    public Map<String, String> getResourcesMax() {
        return resourcesMax;
    }

    /**
     * @return the resourcesMin
     */
    public Map<String, String> getResourcesMin() {
        return resourcesMin;
    }

    /**
     * @return the resourcesAssigned
     */
    public Map<String, String> getResourcesAssigned() {
        return resourcesAssigned;
    }

    /**
     * @return the resourcesDefault
     */
    public Map<String, String> getResourcesDefault() {
        return resourcesDefault;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
