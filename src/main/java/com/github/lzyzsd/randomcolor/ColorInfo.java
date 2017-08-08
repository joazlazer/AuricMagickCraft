package com.github.lzyzsd.randomcolor;

import java.util.List;

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
public class ColorInfo {
    Range hueRange;
    Range saturationRange;
    Range brightnessRange;
    List<Range> lowerBounds;

    public ColorInfo(Range hueRange, Range saturationRange, Range brightnessRange, List<Range> lowerBounds) {
        this.hueRange = hueRange;
        this.saturationRange = saturationRange;
        this.brightnessRange = brightnessRange;
        this.lowerBounds = lowerBounds;
    }

    public Range getHueRange() {
        return hueRange;
    }

    public void setHueRange(Range hueRange) {
        this.hueRange = hueRange;
    }

    public Range getSaturationRange() {
        return saturationRange;
    }

    public void setSaturationRange(Range saturationRange) {
        this.saturationRange = saturationRange;
    }

    public Range getBrightnessRange() {
        return brightnessRange;
    }

    public void setBrightnessRange(Range brightnessRange) {
        this.brightnessRange = brightnessRange;
    }

    public List<Range> getLowerBounds() {
        return lowerBounds;
    }

    public void setLowerBounds(List<Range> lowerBounds) {
        this.lowerBounds = lowerBounds;
    }
}