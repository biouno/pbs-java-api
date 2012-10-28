/*
 * The MIT License
 *
 * Copyright (c) <2012> <Bruno P. Kinoshita>
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * A node of a PBS cluster.
 * 
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class Node
    implements Serializable {

    private static final long serialVersionUID = 2980172720826485748L;

    private final String name;

    private final int numberOfProcessors;

    private final String nodeType;

    private final QueueState state;

    private final Map<String, String> status;

    private final List<String> properties;

    private final List<Job> jobs;

    /**
     * @param name
     * @param numberOfProcessors
     * @param nodeType
     * @param state
     */
    public Node(String name, int numberOfProcessors, String nodeType, QueueState state) {
        super();
        this.name = name;
        this.numberOfProcessors = numberOfProcessors;
        this.nodeType = nodeType;
        this.state = state;
        this.status = new HashMap<String, String>();
        this.properties = new ArrayList<String>();
        this.jobs = new LinkedList<Job>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the numberOfProcessors
     */
    public int getNumberOfProcessors() {
        return numberOfProcessors;
    }

    /**
     * @return the nodeType
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * @return the state
     */
    public QueueState getState() {
        return state;
    }

    /**
     * @return the status
     */
    public Map<String, String> getStatus() {
        return status;
    }

    /**
     * @return the properties
     */
    public List<String> getProperties() {
        return properties;
    }

    /**
     * @return the jobs
     */
    public List<Job> getJobs() {
        return jobs;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
