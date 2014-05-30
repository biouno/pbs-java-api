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
package com.tupilabs.pbs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang.StringUtils;

import com.tupilabs.pbs.model.Job;
import com.tupilabs.pbs.model.Node;
import com.tupilabs.pbs.model.Queue;
import com.tupilabs.pbs.parser.NodeXmlParser;
import com.tupilabs.pbs.parser.ParseException;
import com.tupilabs.pbs.parser.QstatJobsParser;
import com.tupilabs.pbs.parser.QstatQueuesParser;
import com.tupilabs.pbs.util.CommandOutput;
import com.tupilabs.pbs.util.PBSException;


/**
 * PBS Java API.
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class PBS {
    
    /**
     * PBS qnodes command.
     * <p>
     * Get information about the cluster nodes.
     * @return list of nodes
     * @throws PBSException
     */
    public static List<Node> qnodes() {
        return qnodes(null);
    }
    
    /**
     * PBS qnodes command.
     * <p>
     * Get information about the cluster nodes.
     * @param name node name
     * @return list of nodes
     * @throws PBSException
     */
    public static List<Node> qnodes(String name) {
        final List<Node> nodes;
        
        final CommandLine cmdLine = new CommandLine(COMMAND_QNODES);
        cmdLine.addArgument(PARAMETER_XML);
        if(StringUtils.isNotBlank(name)) {
            cmdLine.addArgument(name);
        }
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qnodes command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qnodes command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qnodes command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qnodes exit value: " + exitValue);
        
        try {
            nodes = NODE_XML_PARSER.parse(out.toString());
        } catch (ParseException pe) {
            throw new PBSException("Failed to parse node XML: " + pe.getMessage(), pe);
        }
        
        return nodes;
    }
    
    /**
     * PBS qstat command. 
     * <p>
     * Equivalent to qstat -Q -f [name]
     * @param name queue name
     * @return list of queues
     */
    public static List<Queue> qstatQueues() {
        return qstatQueues(null);
    }

    /**
     * PBS qstat command. 
     * <p>
     * Equivalent to qstat -Q -f [name]
     * @param name queue name
     * @return list of queues
     */
    public static List<Queue> qstatQueues(String name) {
        final CommandLine cmdLine = new CommandLine(COMMAND_QSTAT);
        cmdLine.addArgument(PARAMETER_FULL_STATUS);
        cmdLine.addArgument(PARAMETER_QUEUE);
        if(StringUtils.isNotBlank(name)) {
            cmdLine.addArgument(name);
        }
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qstat command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qstat command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qstat command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qstat exit value: " + exitValue);
        
        final List<Queue> queues;
        try {
            queues = QSTAT_QUEUES_PARSER.parse(out.toString());
        } catch (ParseException pe) {
            throw new PBSException("Failed to parse qstat queues output: " + pe.getMessage(), pe);
        }
        
        return (queues == null ? new ArrayList<Queue>(0) : queues);
    }
    
    /**
     * PBS qstat command. 
     * <p>
     * Equivalent to qstat -f
     * @return list of jobs
     */
    public static List<Job> qstat() {
        return qstat((String)null);
    }
    
    /**
     * PBS qstat command. 
     * <p>
     * Equivalent to qstat -f [queue_name]
     * @return list of jobs
     */
    public static List<Job> qstat(Queue queue) {
        return qstat(queue.getName());
    }
    
    /**
     * PBS qstat command. 
     * <p>
     * Equivalent to qstat -f [job_name]
     * @return list of jobs
     */
    public static List<Job> qstat(Job job) {
        return qstat(job.getName());
    }
    
    /**
     * PBS qstat command. 
     * <p>
     * Equivalent to qstat -f [param]
     * @param name job name
     * @return list of jobs
     */
    public static List<Job> qstat(String name) {
        final CommandLine cmdLine = new CommandLine(COMMAND_QSTAT);
        cmdLine.addArgument(PARAMETER_FULL_STATUS);
        if(StringUtils.isNotBlank(name)) {
            cmdLine.addArgument(name);
        }
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qstat command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qstat command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qstat command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qstat exit value: " + exitValue);
        
        final List<Job> jobs;
        try {
            jobs = QSTAT_JOBS_PARSER.parse(out.toString());
        } catch (ParseException pe) {
            throw new PBSException("Failed to parse qstat jobs output: " + pe.getMessage(), pe);
        }
        
        return (jobs == null ? new ArrayList<Job>(0) : jobs);
    }

    /**
     * PBS qstat command for Array Jobs 
     * <p>
     * Equivalent to qstat -f -t [param]
     * @param name job name
     * @return list of jobs
     */
    public static List<Job> qstatArrayJob(String name) {
        final CommandLine cmdLine = new CommandLine(COMMAND_QSTAT);
        cmdLine.addArgument(PARAMETER_FULL_STATUS);
	cmdLine.addArgument(PARAMETER_ARRAY_JOB_STATUS);
        if(StringUtils.isNotBlank(name)) {
            cmdLine.addArgument(name);
        }
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qstat command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qstat command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qstat command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qstat exit value: " + exitValue);
        
        final List<Job> jobs;
        try {
            jobs = QSTAT_JOBS_PARSER.parse(out.toString());
        } catch (ParseException pe) {
            throw new PBSException("Failed to parse qstat jobs output: " + pe.getMessage(), pe);
        }
        
        return (jobs == null ? new ArrayList<Job>(0) : jobs);
    }

    /**
     * PBS qdel command. 
     * <p>
     * Equivalent to qdel [param]
     * @param name jobId job id
     */
    public static void qdel(String jobId) {
    	final CommandLine cmdLine = new CommandLine(COMMAND_QDEL);
        cmdLine.addArgument(jobId);
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qdel command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qdel command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qdel command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qdel exit value: " + exitValue);
        
        if (exitValue != 0)
        	throw new PBSException("Failed to delete job " + jobId + ". Error output: " + err.toString());
    }
    
    /**
     * PBS qsub command. 
     * <p>
     * Equivalent to qsub [param]
     * @param input job input file
     * @return job id
     */
    public static String qsub(String input) {
    	final CommandLine cmdLine = new CommandLine(COMMAND_QSUB);
        cmdLine.addArgument(input);
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qsub exit value: " + exitValue);
        LOGGER.fine("qsub output: " + out.toString());
        
        if (exitValue != 0)
        	throw new PBSException("Failed to submit job script " + input + ". Error output: " + err.toString());
        
        String jobId = out.toString();
        return jobId.trim();
    }
    
/**
     * PBS qsub command with arguments resource overrides 
     * <p>
     * Equivalent to qsub [param] -l [resource_name=value,resource_name=value]]
     * @param input job input file
     * @param variable number of resources to override
     * @return job id
     */
    public static String qsub(String input, String... resourceOverrides) {
    	final CommandLine cmdLine = new CommandLine(COMMAND_QSUB);
    	cmdLine.addArgument(PARAMETER_RESOURCE_OVERRIDE_STATUS);
    	String resourceOverrideArgument = StringUtils.join(resourceOverrides, ",");
    	cmdLine.addArgument(resourceOverrideArgument);
        cmdLine.addArgument(input);
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qsub exit value: " + exitValue);
        LOGGER.fine("qsub output: " + out.toString());
        
        if (exitValue != 0)
        	throw new PBSException("Failed to submit job script " + input + ". Error output: " + err.toString());
        
        String jobId = out.toString();
        return jobId.trim();
    }

    /**
     * PBS qsub command for an Array Job with Specific PBS_ARRAY_IDs to submit 
     * <p>
     * Equivalent to qsub -t 1,2,3 [param]
     * @param input job input file
     * @param list of specified PBS indices
     * @return job id of array job
     */
    public static String qsubArrayJob(String input, List<Integer> pbsArrayIDs) {
    	final CommandLine cmdLine = new CommandLine(COMMAND_QSUB);
    	cmdLine.addArgument(PARAMETER_ARRAY_JOB_STATUS);
    	String listArgument = StringUtils.join(pbsArrayIDs, ",");
    	cmdLine.addArgument(listArgument);
        cmdLine.addArgument(input);
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qsub exit value: " + exitValue);
        LOGGER.fine("qsub output: " + out.toString());
        
        if (exitValue != 0)
        	throw new PBSException("Failed to submit job script " + input + ". Error output: " + err.toString());
        
        String jobId = out.toString();
        return jobId.trim();
    }
    
    /**
     * PBS qsub command for an Array Job with Specific PBS_ARRAY_IDs to submit, and resource overrides
     * <p>
     * Equivalent to qsub -t 1,2,3 -l [resource_name=value,resource_name=value] [param]
     * @param input job input file
     * @param list of specified PBS indices
     * @param list of resource overrides
     * @return job id of array job
     */
    public static String qsubArrayJob(String input, List<Integer> pbsArrayIDs, String... resourceOverrides) {
    	final CommandLine cmdLine = new CommandLine(COMMAND_QSUB);
    	cmdLine.addArgument(PARAMETER_ARRAY_JOB_STATUS);
    	String listArgument = StringUtils.join(pbsArrayIDs, ",");
    	cmdLine.addArgument(listArgument);
    	cmdLine.addArgument(PARAMETER_RESOURCE_OVERRIDE_STATUS);
    	String resourceOverrideArgument = StringUtils.join(resourceOverrides, ",");
    	cmdLine.addArgument(resourceOverrideArgument);
        cmdLine.addArgument(input);
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qsub exit value: " + exitValue);
        LOGGER.fine("qsub output: " + out.toString());
        
        if (exitValue != 0)
        	throw new PBSException("Failed to submit job script " + input + ". Error output: " + err.toString());
        
        String jobId = out.toString();
        return jobId.trim();
    }
    
    /**
     * PBS qsub command for an Array Job with Specific PBS_ARRAY_IDs to submit 
     * <p>
     * Equivalent to qsub -t 5-20 [param]
     * @param input job input file
     * @param beginning of index range
     * @param end of index range
     * @return job id of array job
     */
    public static String qsubArrayJob(String input, int beginIndex, int endIndex) {
    	final CommandLine cmdLine = new CommandLine(COMMAND_QSUB);
    	cmdLine.addArgument(PARAMETER_ARRAY_JOB_STATUS);
    	String rangeArgument = beginIndex + "-" + endIndex;
    	cmdLine.addArgument(rangeArgument);
        cmdLine.addArgument(input);
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qsub exit value: " + exitValue);
        LOGGER.fine("qsub output: " + out.toString());
        
        if (exitValue != 0)
        	throw new PBSException("Failed to submit job script " + input + ". Error output: " + err.toString());
        
        String jobId = out.toString();
        return jobId.trim();
    }
 
    /**
     * PBS qsub command for an Array Job with Specific PBS_ARRAY_IDs to submit 
     * AND a range to submit
     * <p>
     * Equivalent to qsub -t 1,2,3,5-20 [param]
     * @param input job input file
     * @param list of specified indices
     * @param beginning of index range
     * @param end of index range
     * @return job id of array job
     */
    public static String qsubArrayJob(String input, List<Integer> pbsArrayIDs, int beginIndex, int endIndex) {
    	final CommandLine cmdLine = new CommandLine(COMMAND_QSUB);
    	cmdLine.addArgument(PARAMETER_ARRAY_JOB_STATUS);
    	String rangeArgument = beginIndex + "-" + endIndex;
    	String listArgument = StringUtils.join(pbsArrayIDs, ",");
    	String combinedArgument = listArgument + "," + rangeArgument;
    	cmdLine.addArgument(combinedArgument);
        cmdLine.addArgument(input);
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute qsub command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("qsub exit value: " + exitValue);
        LOGGER.fine("qsub output: " + out.toString());
        
        if (exitValue != 0)
        	throw new PBSException("Failed to submit job script " + input + ". Error output: " + err.toString());
        
        String jobId = out.toString();
        return jobId.trim();
    }

	/**
     * PBS tracejob command.
     * <p>
     * Equivalent to tracejob -n [numberOfDays] [jobId]
     * @param jobId
     * @param numberOfDays
     * @return tracejob output
     */
    public static CommandOutput traceJob(String jobId, int numberOfDays) {
    	final CommandLine cmdLine = new CommandLine(COMMAND_TRACEJOB);
    	cmdLine.addArgument(PARAMETER_NUMBER_OF_DAYS);
    	cmdLine.addArgument(Integer.toString(numberOfDays));
        cmdLine.addArgument(jobId);
        
        final OutputStream out = new ByteArrayOutputStream();
        final OutputStream err = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler;
        try {
            resultHandler = execute(cmdLine, out, err);
            resultHandler.waitFor(DEFAULT_TIMEOUT);
        } catch (ExecuteException e) {
            throw new PBSException("Failed to execute tracejob command: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PBSException("Failed to execute tracejob command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new PBSException("Failed to execute tracejob command: " + e.getMessage(), e);
        }
        
        final int exitValue = resultHandler.getExitValue();
        LOGGER.info("tracejob exit value: " + exitValue);
        LOGGER.fine("tracejob output: " + out.toString());
        
        return new CommandOutput(out.toString(), err.toString());
    }
    
    /*
     * ------------------------------
     * Utility methods
     * ------------------------------
     */
    /**
     * Executes a PBS command.
     * @param cmdLine command
     * @param out output stream
     * @param err err stream
     * @return execute handler
     * @throws ExecuteException
     * @throws IOException
     */
    static DefaultExecuteResultHandler execute(CommandLine cmdLine, OutputStream out, OutputStream err) throws ExecuteException, IOException {
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        ExecuteStreamHandler streamHandler = new PumpStreamHandler(out, err);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(0);
        executor.setStreamHandler(streamHandler);
        executor.execute(cmdLine, resultHandler);
        return resultHandler;
    }
    
   private static final Logger LOGGER = Logger.getLogger(PBS.class.getName());

    private static final String COMMAND_QNODES = "qnodes";
    private static final String COMMAND_QSTAT = "qstat";
    private static final String COMMAND_QDEL = "qdel";
    private static final String COMMAND_QSUB = "qsub";
    private static final String COMMAND_TRACEJOB = "tracejob";
    // qstat
    private static final String PARAMETER_XML = "-x";
    private static final String PARAMETER_FULL_STATUS = "-f";
    private static final String PARAMETER_ARRAY_JOB_STATUS = "-t";
    private static final String PARAMETER_RESOURCE_OVERRIDE_STATUS = "-l";
    private static final String PARAMETER_QUEUE = "-Q";
    // tracejob
    private static final String PARAMETER_NUMBER_OF_DAYS = "-n";
    
    private static final NodeXmlParser NODE_XML_PARSER = new NodeXmlParser();
    private static final QstatQueuesParser QSTAT_QUEUES_PARSER = new QstatQueuesParser();
    private static final QstatJobsParser QSTAT_JOBS_PARSER = new QstatJobsParser();
    
    /**
     * Default time-out for process execution.
     */
    private static final int DEFAULT_TIMEOUT = 60000;
    
//    public static void main(String[] args) throws Exception {
//        List<Node> nodes = PBS.qnodes("chuva");
//        for(Node node : nodes) {
//            System.out.println(node);
//        }
//        System.out.println("------");
//        List<Queue> queues = PBS.qstatQueues("batch");
//        for(Queue queue : queues) {
//            System.out.println(queue);
//        }
//        System.out.println("------");
//        List<Job> jobs = PBS.qstatJobs();
//        for(Job job : jobs) {
//            System.out.println(job);
//        }
//    }
    
}
