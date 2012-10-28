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
package com.tupilabs.pbs.util;

import java.util.Vector;

/**
 * Utils class.
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public final class Utils {

    /**
     * Hidden constructor of the utility class.
     */
    private Utils() {};
    
    /**
     * Split the source into two strings at the first occurrence of the splitter
     * Subsequent occurrences are not treated specially, and may be part of the
     * second string.
     * 
     * @author http://www.java2s.com/Code/Java/Data-Type/
     *         SplitthesourceintotwostringsatthefirstoccurrenceofthesplitterSubsequentoccurrencesarenottreatedspeciallyandmaybepartofthesecondstring
     *         .htm
     * @param source The string to split
     * @param splitter The string that forms the boundary between the two
     *        strings returned.
     * @return An array of two strings split from source by splitter.
     */
    public static String[] splitFirst(String source, String splitter) {
        // hold the results as we find them
        Vector<String> rv = new Vector<String>();
        int last = 0;
        int next = 0;

        // find first splitter in source
        next = source.indexOf(splitter, last);
        if (next != -1) {
            // isolate from last thru before next
            rv.add(source.substring(last, next));
            last = next + splitter.length();
        }

        if (last < source.length()) {
            rv.add(source.substring(last, source.length()));
        }

        // convert to array
        return (String[]) rv.toArray(new String[rv.size()]);
    }

}
