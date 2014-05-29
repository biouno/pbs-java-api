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
package com.tupilabs.pbs.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.tupilabs.pbs.model.Job;
import com.tupilabs.pbs.util.Utils;


/**
 * Parser for qstat -f [job_id] command.
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class QstatJobsParser implements Parser<String, List<Job>> {

    private final static Logger LOGGER = Logger.getLogger(QstatJobsParser.class.getName());
    
    /*
     * Regex.
     */
    private final static String REGEX_JOB = "(?i)job\\s+(?i)i(?i)d:(.*)";
    private final static Pattern PATTERN_JOB = Pattern.compile(REGEX_JOB);
    
    /*
     * Constants.
     */
    private final static String CHAR_EQUALS = "=";
    
    /* (non-Javadoc)
     * @see com.tupilabs.pbs.parser.Parser#parse(java.lang.Object)
     */
    public List<Job> parse(String text) throws ParseException {
        final List<Job> jobs;
        if(StringUtils.isNotBlank(text)) {
        	text = StringUtils.replace(text, "\n\t", "");
            jobs = new LinkedList<Job>();
            String separator = "\n";
            if(text.indexOf("\r\n") > 0) {
                separator = "\r\n";
            }
            final String[] lines = text.split(separator);
            Job job = null;
            for(final String line : lines) {
                Matcher matcher = PATTERN_JOB.matcher(line);
                if(matcher.matches()) {
                    if(job != null) {
                        jobs.add(job);
                    } 
                    job = new Job();
                    final String id = matcher.group(1).trim();
                    job.setId(id);
                } else if(StringUtils.isNotBlank(line)) {
                    String[] temp = Utils.splitFirst(line, CHAR_EQUALS);
                    if(temp.length == 2) {
                        final String key = temp[0].trim().toLowerCase();
                        final String value = temp[1].trim();
                        if("job_name".equals(key)) {
                            job.setName(value);
                        } else if("job_owner".equals(key)) {
                            job.setOwner(value);
                        } else if(key.startsWith("resources_used.")) {
                            job.getResourcesUsed().put(key, value);
                        } else if("job_state".equals(key)) {
                            job.setState(value);
                        } else if("queue".equals(key)) {
                            job.setQueue(value);
                        } else if("server".equals(key)) {
                            job.setServer(value);
                        } else if("checkpoint".equals(key)) {
                            job.setCheckpoint(value);
                        } else if("ctime".equals(key)) {
                            job.setCtime(value);
                        } else if("error_path".equals(key)) {
                            job.setErrorPath(value);
                        } else if("exec_host".equals(key)) {
                            job.setExecHost(value);
                        } else if("exec_port".equals(key)) {
                            try {
                                job.setExecPort(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing job exec port: " + nfe.getMessage(), nfe);
                                job.setExecPort(-1);
                            }
                        } else if("hold_types".equals(key)) {
                            job.setHoldTypes(value);
                        } else if("join_path".equals(key)) {
                            job.setJoinPath(value);
                        } else if("keep_files".equals(key)) {
                            job.setKeepFiles(value);
                        } else if("mail_points".equals(key)) {
                            job.setMailPoints(value);
                        } else if("mail_users".equals(key)) {
                            job.setMailUsers(value);
                        } else if("mtime".equals(key)) {
                            job.setMtime(value);
                        } else if("output_path".equals(key)) {
                            job.setOutputPath(value);
                        } else if("priority".equals(key)) {
                            try {
                                job.setPriority(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing job priority: " + nfe.getMessage(), nfe);
                                job.setPriority(-1);
                            }
                        } else if("qtime".equals(key)) {
                            job.setQtime(value);
                        } else if("rerunable".equals(key)) {
                            job.setRerunable(Boolean.parseBoolean(value));
                        } else if(key.startsWith("resource_list.")) {
                            job.getResourceList().put(key, value);
                        } else if("session_id".equals(key)) {
                            try {
                                job.setSessionId(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing job session id: " + nfe.getMessage(), nfe);
                                job.setSessionId(-1);
                            }
                        } else if("substate".equals(key)) {
                            try {
                                job.setSubstate(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing job substate: " + nfe.getMessage(), nfe);
                                job.setSubstate(-1);
                            }
                        } else if(key.startsWith("variable_list")) {
                           job.getVariableList().put(key, value);
                        } else if("etime".equals(key)) {
                            job.setEtime(value);
                        } else if("euser".equals(key)) {
                            job.setEuser(value);
                        } else if("egroup".equals(key)) {
                            job.setEgroup(value);
                        } else if("hashname".equals(key)) {
                            job.setHashName(value);
                        } else if("queue_rank".equals(key)) {
                            try {
                                job.setQueueRank(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing job queue rank: " + nfe.getMessage(), nfe);
                                job.setQueueRank(-1);
                            }
                        } else if("queue_type".equals(key)) {
                            job.setQueueType(value);
                        } else if("comment".equals(key)) {
                            job.setComment(value);
                        } else if("submit_args".equals(key)) {
                            job.setSubmitArgs(value);
                        } else if("submit_host".equals(key)) {
                            job.setSubmitHost(value);
                        } else if("start_time".equals(key)) {
                            job.setStartTime(value);
                        } else if("start_count".equals(key)) {
                            try {
                                job.setStartCount(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing job start count: " + nfe.getMessage(), nfe);
                                job.setStartCount(-1);
                            }
                        } else if("fault_tolerant".equals(key)) {
                            job.setFaultTolerant(Boolean.parseBoolean(value));
                        } else if("job_radix".equals(key)) {
                            try {
                                job.setRadix(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing job radix: " + nfe.getMessage(), nfe);
                                job.setRadix(-1);
                            }
                        } else if("walltime.remaining".equals(key)) { 
                            try {
                                job.setWalltimeRemaining(Long.parseLong(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing job walltime remaining: " + nfe.getMessage(), nfe);
                                job.setWalltimeRemaining(-1L);
                            }
                        }
                    }
                }
            }
            if(job != null) {
                jobs.add(job);
            }
            return jobs;
        } else {
            return Collections.emptyList();
        }
    }

}
