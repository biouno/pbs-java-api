package com.tupilabs.pbs.model;

import com.tupilabs.pbs.PBS;
import com.tupilabs.pbs.util.CommandOutput;

public class PBS001 {

    public static void main(String[] args) {
        CommandOutput co = PBS.traceJob("0", 30);
        System.out.println(co.getOutput());
    }

}
