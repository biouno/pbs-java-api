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
package com.tupilabs.pbs.parser;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tupilabs.pbs.model.Job;
import com.tupilabs.pbs.model.Node;
import com.tupilabs.pbs.model.QueueState;

/**
 * Nodes (from qnodes) SAX XML handler.
 *
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public class NodeXmlHandler extends DefaultHandler {

    /**
     * List of nodes.
     */
    private List<Node> nodes;

    private Node node;

    private String name;

    private String state;

    private int np;

    private String properties;

    private String ntype;

    private String status;

    private String jobs;

    private boolean isName = false;

    private boolean isState = false;

    private boolean isNp = false;

    private boolean isProperties = false;

    private boolean isNtype = false;

    private boolean isStatus = false;

    private boolean isJobs = false;

    /*
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
        nodes = new LinkedList<Node>();
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
     * org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("name".equals(qName)) {
            isName = true;
        } else if ("state".equals(qName)) {
            isState = true;
        } else if ("np".equals(qName)) {
            isNp = true;
        } else if ("properties".equals(qName)) {
            isProperties = true;
        } else if ("ntype".equals(qName)) {
            isNtype = true;
        } else if ("status".equals(qName)) {
            isStatus = true;
        } else if ("jobs".equals(qName)) {
            isJobs = true;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
        if (this.isName) {
            this.name = text;
            this.isName = false;
        } else if (this.isState) {
            this.state = text;
            this.isState = false;
        } else if (this.isNp) {
            this.np = Integer.parseInt(text);
            this.isNp = false;
        } else if (this.isProperties) {
            this.properties = text;
            this.isProperties = false;
        } else if (this.isNtype) {
            this.ntype = text;
            this.isNtype = false;
        } else if (this.isStatus) {
            this.status = text;
            this.isStatus = false;
        } else if (this.isJobs) {
            jobs = text;
            this.isJobs = false;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("Node".equals(qName)) {
            node = new Node(this.name, this.np, this.ntype, QueueState.fromString(this.state));
            if (StringUtils.isNotBlank(properties)) {
                final String[] props = properties.split(",");
                for (final String prop : props) {
                    node.getProperties().add(prop);
                }
            }
            if (StringUtils.isNotBlank(status)) {
                final String[] statuses = status.split(",");
                for (final String statuss : statuses) {
                    if (statuss.indexOf('=') > 0) {
                        String[] temp = statuss.split("=");
                        if (temp.length == 2)
                            node.getStatus().put(temp[0].trim(), temp[1].trim());
                    }
                }
            }
            if (StringUtils.isNotBlank(jobs)) {
                final String[] jobses = jobs.split(",");
                for (String jobss : jobses) {
                    if (jobss.indexOf('/') > 0) {
                        String[] temp = jobss.split("/");
                        if (temp.length == 2) {
                            int index = Integer.parseInt(temp[0].trim());
                            String name = temp[1];
                            Job job = new Job();
                            job.setQueueIndex(index);
                            job.setName(name);
                            node.getJobs().add(job);
                        }
                    }
                }
            }
            this.nodes.add(node);
        }
    }

    /**
     * @return the nodes
     */
    public List<Node> getNodes() {
        return nodes;
    }

}
