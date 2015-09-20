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


/**
 * Queue state. Valid values are:
 * <ul>
 *  <li>free</li>
 *  <li>offline</li>
 *  <li>down</li>
 *  <li>reserve</li>
 *  <li>job-exclusive</li>
 *  <li>job-sharing</li>
 *  <li>busy</li>
 *  <li>time-shared</li>
 *  <li>state-unknown</li>
 *  <li>unknown</li>
 * </ul>
 * @author Bruno P. Kinoshita
 * @see Queue
 * @since 0.1
 */
public enum QueueState {

    FREE("free"),
    OFFLINE("offline"),
    DOWN("down"),
    RESERVE("reserve"),
    JOB_EXCLUSIVE("job-exclusive"),
    JOB_SHARING("job-sharing"),
    BUSY("busy"),
    TIME_SHARED("time-shared"),
    STATE_UNKNOWN("state-unknown"),
    UNKNOWN("unknown");
    
    private final String state;
    
    QueueState(String state) {
        this.state = state;
    }
    
    public String getState() {
        return this.state;
    }
    
    public static QueueState fromString(String state) {
        if("free".equals(state)) 
            return FREE;
        if("down".equals(state)) 
            return DOWN;
        return UNKNOWN;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return this.state;
    }
    
}
