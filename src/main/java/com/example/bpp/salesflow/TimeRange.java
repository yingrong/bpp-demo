package com.example.bpp.salesflow;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class TimeRange {
    private final Instant startTime;
    private final Instant endTime;
    private final boolean startInclusive;
    private final boolean endInclusive;

    // 构造方法（Instant 版本）
    public TimeRange(Instant startTime, Instant endTime,
                     boolean startInclusive, boolean endInclusive) {

        if (startTime == null && endTime == null) {
            throw new IllegalArgumentException("开始时间和结束时间不能同时为 null");
        }
        // 验证逻辑与之前相同...
        if (startTime != null && endTime != null) {
            if (startInclusive && endInclusive && startTime.isAfter(endTime)) {
                throw new IllegalArgumentException("开始时间不能晚于结束时间（两个闭区间）");
            }
            if (!startInclusive && !endInclusive && !startTime.isBefore(endTime)) {
                throw new IllegalArgumentException("开始时间必须早于结束时间（两个开区间）");
            }
            if ((startInclusive ^ endInclusive) && startTime.equals(endTime)) {
                throw new IllegalArgumentException("当且仅当两个边界均为闭区间时，允许相等");
            }
        }

        this.startTime = startTime;
        this.endTime = endTime;
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }

    // 判断指定时间是否在区间内（Instant 版本）
    public boolean contains(Instant time) {
        Objects.requireNonNull(time, "待检查的时间不能为 null");

        boolean afterStart = true;
        if (startTime != null) {
            afterStart = startInclusive ?
                    !time.isBefore(startTime) :
                    time.isAfter(startTime);
        }

        boolean beforeEnd = true;
        if (endTime != null) {
            beforeEnd = endInclusive ?
                    !time.isAfter(endTime) :
                    time.isBefore(endTime);
        }

        return afterStart && beforeEnd;
    }

    // 判断指定时间是否在区间内（Date 版本）
    public boolean contains(Date time) {
        Objects.requireNonNull(time, "待检查的时间不能为 null");
        return contains(time.toInstant());
    }

    public static TimeRange byDate(Date start, Date end, boolean startInclusive, boolean endInclusive) {
        return new TimeRange(
                start != null ? start.toInstant() : null,
                end != null ? end.toInstant() : null,
                startInclusive,
                endInclusive
        );
    }

    // 静态工厂方法（Instant 版本）
    public static TimeRange closed(Instant start, Instant end) {
        return new TimeRange(start, end, true, true);
    }

    public static TimeRange open(Instant start, Instant end) {
        return new TimeRange(start, end, false, false);
    }

    // 静态工厂方法（Date 版本）
    public static TimeRange closedByDate(Date start, Date end) {
        return new TimeRange(
                start != null ? start.toInstant() : null,
                end != null ? end.toInstant() : null,
                true,
                true
        );
    }

    public static TimeRange open(Date start, Date end) {
        return new TimeRange(
                start != null ? start.toInstant() : null,
                end != null ? end.toInstant() : null,
                false,
                false
        );
    }

    // Getters（返回 Instant）
    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    // Getters（返回 Date）
    public Date getStartTimeAsDate() {
        return startTime != null ? Date.from(startTime) : null;
    }

    public Date getEndTimeAsDate() {
        return endTime != null ? Date.from(endTime) : null;
    }

    // 其他方法保持不变...
    public boolean isStartInclusive() { return startInclusive; }
    public boolean isEndInclusive() { return endInclusive; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeRange that = (TimeRange) o;
        return startInclusive == that.startInclusive &&
                endInclusive == that.endInclusive &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, startInclusive, endInclusive);
    }

    @Override
    public String toString() {
        String startSymbol = startInclusive ? "[" : "(";
        String endSymbol = endInclusive ? "]" : ")";
        return String.format("%s%s, %s%s",
                startSymbol,
                startTime != null ? startTime : "-∞",
                endTime != null ? endTime : "+∞",
                endSymbol);
    }
}