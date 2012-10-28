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

import com.tupilabs.pbs.model.Queue;
import com.tupilabs.pbs.util.Utils;


/**
 * Parser for qstat -Q -f [queue_name] command.
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class QstatQueuesParser implements Parser<String, List<Queue>> {

    private final static Logger LOGGER = Logger.getLogger(QstatQueuesParser.class.getName());
    
    /*
     * Regex.
     */
    private final static String REGEX_QUEUE = "(?i)queue:(.*)";
    private final static Pattern PATTERN_QUEUE = Pattern.compile(REGEX_QUEUE);
    
    /*
     * Constants.
     */
    private final static String CHAR_EQUALS = "=";
    
    /* (non-Javadoc)
     * @see com.tupilabs.pbs.parser.Parser#parse(java.lang.Object)
     */
    public List<Queue> parse(String text) throws ParseException {
        final List<Queue> queues;
        if(StringUtils.isNotBlank(text)) {
            queues = new LinkedList<Queue>();
            String separator = "\n";
            if(text.indexOf("\r\n") > 0) {
                separator = "\r\n";
            }
            final String[] lines = text.split(separator);
            Queue queue = null;
            for(final String line : lines) {
                Matcher matcher = PATTERN_QUEUE.matcher(line);
                if(matcher.matches()) {
                    if(queue != null) {
                        queues.add(queue);
                    } 
                    queue = new Queue();
                    final String name = matcher.group(1);
                    queue.setName(name);
                } else if(StringUtils.isNotBlank(line)) {
                    String[] temp = Utils.splitFirst(line, CHAR_EQUALS);
                    if(temp.length == 2) {
                        final String key = temp[0].trim().toLowerCase();
                        final String value = temp[1].trim();
                        if("queue_type".equals(key)) {
                            queue.setQueueType(value);
                        } else if("priority".equals(key)) {
                            try {
                                queue.setPriority(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing queue priority: " + nfe.getMessage(), nfe);
                                queue.setPriority(-1);
                            }
                        } else if("total_jobs".equals(key)) {
                            try {
                                queue.setTotalJobs(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing queue total jobs: " + nfe.getMessage(), nfe);
                                queue.setPriority(-1);
                            }
                        } else if("state_count".equals(key)) {
                            queue.setStateCount(value);
                        } else if("mtime".equals(key)) {
                            queue.setMtime(value);
                        } else if("max_user_run".equals(key)) {
                            try {
                                queue.setMaxUserRun(Integer.parseInt(value));
                            } catch (NumberFormatException nfe) {
                                LOGGER.log(Level.WARNING, "Failed parsing queue max user run: " + nfe.getMessage(), nfe);
                                queue.setPriority(-1);
                            }
                        } else if("enabled".equals(key)) {
                            queue.setEnabled(Boolean.parseBoolean(value));
                        } else if("started".equals(key)) {
                            queue.setStarted(Boolean.parseBoolean(value));
                        } else if(key.startsWith("resources_max.")) {
                            queue.getResourcesMax().put(key, value);
                        } else if(key.startsWith("resources_min.")) {
                            queue.getResourcesMin().put(key, value);
                        } else if(key.startsWith("resources_assigned.")) {
                            queue.getResourcesAssigned().put(key, value);
                        } else if(key.startsWith("resources_default.")) {
                            queue.getResourcesDefault().put(key, value);
                        } else {
                            LOGGER.info("Unmmaped key, value: " + key + ", " + value);
                        }
                    }
                }
            }
            if(queue != null) {
                queues.add(queue);
            }
            return queues;
        } else {
            return Collections.emptyList();
        }
    }

}
