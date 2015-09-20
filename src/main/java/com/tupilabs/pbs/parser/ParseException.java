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

/**
 * Exception threw by PBS parsers.
 *
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public class ParseException extends Exception {

    private static final long serialVersionUID = 1832085151390299726L;

    /**
     * Default constructor.
     */
    public ParseException() {
    }

    /**
     * Constructor with message.
     *
     * @param message exception message
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Constructor with case.
     *
     * @param cause cause
     */
    public ParseException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message message
     * @param cause cause
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
