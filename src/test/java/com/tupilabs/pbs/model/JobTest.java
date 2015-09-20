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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link Job}.
 *
 * @author Bruno P. Kinoshita
 * @since 0.11
 */
public class JobTest {

    private static Job job;

    @BeforeClass
    public static void setUp() {
        job = new Job();
        job.setCheckpoint("checkpoint");
        job.setComment("comment");
        job.setCompTime("compTime");
        job.setCtime("ctime");
        job.setEgroup("egroup");
        job.setErrorPath("errorPath");
        job.setEtime("etime");
        job.setEuser("euser");
        job.setExecHost("execHost");
        job.setExecPort("execPort");
        job.setExitStatus(0);
        job.setFaultTolerant(true);
        job.setHashName("hashName");
        job.setHoldTypes("holdTypes");
        job.setId("id");
        job.setJobArrayId(1);
        job.setJoinPath("joinPath");
        job.setKeepFiles("keepFiles");
        job.setMailPoints("mailPoints");
        job.setMailUsers("mailUsers");
        job.setMtime("mtime");
        job.setName("name");
        job.setOutputPath("outputPath");
        job.setOwner("owner");
        job.setPriority(2);
        job.setQtime("qtime");
        job.setQueue("queue");
        job.setQueueIndex(3);
        job.setQueueRank(4);
        job.setQueueType("queueType");
        job.setRadix(5);
        job.setRerunable(true);
        Map<String, String> resourceList = new HashMap<String, String>();
        resourceList.put("key", "value");
        job.setResourceList(resourceList);
        Map<String, String> resourcesUsed = new HashMap<String, String>();
        resourcesUsed.put("key", "value");
        job.setResourcesUsed(resourcesUsed);
        job.setServer("server");
        job.setSessionId(6);
        job.setStartCount(7);
        job.setStartTime("startTime");
        job.setState("state");
        job.setSubmitArgs("submitArgs");
        job.setSubmitHost("submitHost");
        job.setSubstate(8);
        job.setTotalRuntime(0.1);
        Map<String, String> variableList = new HashMap<String, String>();
        variableList.put("key", "value");
        job.setVariableList(variableList);
        job.setWalltimeRemaining(100L);
    }

    /**
     * Test method for object getters.
     */
    @Test
    public void testGetters() {
        assertEquals("checkpoint", job.getCheckpoint());
        assertEquals("comment", job.getComment());
        assertEquals("compTime", job.getCompTime());
        assertEquals("ctime", job.getCtime());
        assertEquals("egroup", job.getEgroup());
        assertEquals("errorPath", job.getErrorPath());
        assertEquals("etime", job.getEtime());
        assertEquals("euser", job.getEuser());
        assertEquals("execHost", job.getExecHost());
        assertEquals("execPort", job.getExecPort());
        assertEquals(0, job.getExitStatus());
        assertEquals(true, job.isFaultTolerant());
        assertEquals("hashName", job.getHashName());
        assertEquals("holdTypes", job.getHoldTypes());
        assertEquals("id", job.getId());
        assertEquals(1, job.getJobArrayId());
        assertEquals("joinPath", job.getJoinPath());
        assertEquals("keepFiles", job.getKeepFiles());
        assertEquals("mailPoints", job.getMailPoints());
        assertEquals("mailUsers", job.getMailUsers());
        assertEquals("mtime", job.getMtime());
        assertEquals("name", job.getName());
        assertEquals("outputPath", job.getOutputPath());
        assertEquals("owner", job.getOwner());
        assertEquals(2, job.getPriority());
        assertEquals("qtime", job.getQtime());
        assertEquals("queue", job.getQueue());
        assertEquals(3, job.getQueueIndex());
        assertEquals(4, job.getQueueRank());
        assertEquals("queueType", job.getQueueType());
        assertEquals(5, job.getRadix());
        assertEquals(true, job.isRerunable());
        assertFalse(job.getResourceList().isEmpty());
        assertEquals("key", job.getResourceList().keySet().iterator().next());
        assertEquals("value", job.getResourceList().values().iterator().next());
        assertFalse(job.getResourcesUsed().isEmpty());
        assertEquals("key", job.getResourcesUsed().keySet().iterator().next());
        assertEquals("value", job.getResourcesUsed().values().iterator().next());
        assertEquals("server", job.getServer());
        assertEquals(6, job.getSessionId());
        assertEquals(7, job.getStartCount());
        assertEquals("startTime", job.getStartTime());
        assertEquals("state", job.getState());
        assertEquals("submitArgs", job.getSubmitArgs());
        assertEquals("submitHost", job.getSubmitHost());
        assertEquals(8, job.getSubstate());
        assertEquals(Double.valueOf(0.1), Double.valueOf(job.getTotalRuntime()));
        assertFalse(job.getVariableList().isEmpty());
        assertEquals("key", job.getVariableList().keySet().iterator().next());
        assertEquals("value", job.getVariableList().values().iterator().next());
        assertEquals(Long.valueOf(100), Long.valueOf(job.getWalltimeRemaining()));
    }

}
