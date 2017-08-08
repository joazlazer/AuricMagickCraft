package com.github.lzyzsd.randomcolor;

/**
 * MIT license:
 * Copyright 2015 lzyzsd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * Created by bruce on 15/2/9.
 */
public class Range {
    int start;
    int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean contain(int value) {
        return value >= start && value <= end;
    }

    @Override
    public String toString() {
        return "start: " + start + " end: " + end;
    }
}
