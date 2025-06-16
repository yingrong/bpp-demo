package com.example.bpp.salesflow;

import static com.example.bpp.salesflow.TimeRange.byDate;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Date;

class TimeRangeTest {

    // ==================== 构造函数测试 ====================
    @Test
    void constructor_WithBothNull_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new TimeRange(null, null, true, true));
    }

    @Test
    void staticFactory_WithBothNullInstants_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> TimeRange.closed(null, null));
    }

    @Test
    void staticFactory_WithBothNullDates_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> TimeRange.closedByDate((Date) null, (Date) null));
    }

    @Test
    void constructor_WithValidInstants_CreatesInterval() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");

        TimeRange interval = new TimeRange(start, end, true, true);

        assertEquals(start, interval.getStartTime());
        assertEquals(end, interval.getEndTime());
        assertTrue(interval.isStartInclusive());
        assertTrue(interval.isEndInclusive());
    }

    @Test
    void constructor_WithValidDates_CreatesInterval() {
        Date start = Date.from(Instant.parse("2020-01-01T00:00:00Z"));
        Date end = Date.from(Instant.parse("2025-01-01T00:00:00Z"));

        TimeRange interval = byDate(start, end, false, false);

        assertEquals(start.toInstant(), interval.getStartTime());
        assertEquals(end.toInstant(), interval.getEndTime());
        assertFalse(interval.isStartInclusive());
        assertFalse(interval.isEndInclusive());
    }

    @Test
    void constructor_WithNullStart_CreatesValidInterval() {
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(null, end, true, false);

        assertNull(interval.getStartTime());
        assertEquals(end, interval.getEndTime());
        assertTrue(interval.isStartInclusive()); // 虽然 start 为 null，但标志仍保留
        assertFalse(interval.isEndInclusive());
    }

    @Test
    void constructor_WithNullEnd_CreatesValidInterval() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, null, false, true);

        assertEquals(start, interval.getStartTime());
        assertNull(interval.getEndTime());
        assertFalse(interval.isStartInclusive());
        assertTrue(interval.isEndInclusive());
    }

    @Test
    void constructor_WithStartAfterEnd_ThrowsException_ClosedClosed() {
        Instant start = Instant.parse("2025-01-01T00:00:00Z");
        Instant end = Instant.parse("2020-01-01T00:00:00Z");

        assertThrows(IllegalArgumentException.class,
                () -> new TimeRange(start, end, true, true));
    }

    @Test
    void constructor_WithStartEqualsEnd_ThrowsException_OpenOpen() {
        Instant sameTime = Instant.parse("2020-01-01T00:00:00Z");

        assertThrows(IllegalArgumentException.class,
                () -> new TimeRange(sameTime, sameTime, false, false));
    }

    @Test
    void constructor_WithStartEqualsEnd_ThrowsException_Mixed() {
        Instant sameTime = Instant.parse("2020-01-01T00:00:00Z");

        assertThrows(IllegalArgumentException.class,
                () -> new TimeRange(sameTime, sameTime, true, false));

        assertThrows(IllegalArgumentException.class,
                () -> new TimeRange(sameTime, sameTime, false, true));
    }

    @Test
    void constructor_WithStartEqualsEnd_AllowsClosedClosed() {
        Instant sameTime = Instant.parse("2020-01-01T00:00:00Z");

        assertDoesNotThrow(() -> new TimeRange(sameTime, sameTime, true, true));
    }

    @Test
    void constructor_WithStartBeforeEnd_AllowsOpenOpen() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2020-01-01T00:00:01Z");

        assertDoesNotThrow(() -> new TimeRange(start, end, false, false));
    }

    // ==================== contains() 方法测试 ====================

    @Test
    void contains_WithInstant_WithinClosedInterval_ReturnsTrue() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, end, true, true);

        Instant within = Instant.parse("2022-01-01T00:00:00Z");
        assertTrue(interval.contains(within));
    }

    @Test
    void contains_WithInstant_AtStartClosed_ReturnsTrue() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, end, true, false);

        assertTrue(interval.contains(start));
    }

    @Test
    void contains_WithInstant_AtStartOpen_ReturnsFalse() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, end, false, true);

        assertFalse(interval.contains(start));
    }

    @Test
    void contains_WithInstant_AtEndClosed_ReturnsTrue() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, end, false, true);

        assertTrue(interval.contains(end));
    }

    @Test
    void contains_WithInstant_AtEndOpen_ReturnsFalse() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, end, true, false);

        assertFalse(interval.contains(end));
    }

    @Test
    void contains_WithInstant_BeforeStart_ReturnsFalse() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, end, true, true);

        Instant before = Instant.parse("2019-12-31T23:59:59Z");
        assertFalse(interval.contains(before));
    }

    @Test
    void contains_WithInstant_AfterEnd_ReturnsFalse() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, end, true, true);

        Instant after = Instant.parse("2025-01-01T00:00:01Z");
        assertFalse(interval.contains(after));
    }

    @Test
    void contains_WithInstant_NullStart_ReturnsTrueIfBeforeEnd() {
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(null, end, true, true);

        Instant beforeEnd = Instant.parse("2024-12-31T23:59:59Z");
        assertTrue(interval.contains(beforeEnd));
    }

    @Test
    void contains_WithInstant_NullEnd_ReturnsTrueIfAfterStart() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, null, true, true);

        Instant afterStart = Instant.parse("2020-01-01T00:00:01Z");
        assertTrue(interval.contains(afterStart));
    }

    @Test
    void contains_WithDate_WithinInterval_ReturnsTrue() {
        Date start = Date.from(Instant.parse("2020-01-01T00:00:00Z"));
        Date end = Date.from(Instant.parse("2025-01-01T00:00:00Z"));
        TimeRange interval = byDate(start, end, true, true);

        Date within = Date.from(Instant.parse("2022-01-01T00:00:00Z"));
        assertTrue(interval.contains(within));
    }

    // ==================== 静态工厂方法测试 ====================

    @Test
    void closed_WithInstants_CreatesClosedInterval() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");

        TimeRange interval = TimeRange.closed(start, end);

        assertEquals(start, interval.getStartTime());
        assertEquals(end, interval.getEndTime());
        assertTrue(interval.isStartInclusive());
        assertTrue(interval.isEndInclusive());
    }

    @Test
    void open_WithInstants_CreatesOpenInterval() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");

        TimeRange interval = TimeRange.open(start, end);

        assertEquals(start, interval.getStartTime());
        assertEquals(end, interval.getEndTime());
        assertFalse(interval.isStartInclusive());
        assertFalse(interval.isEndInclusive());
    }

    @Test
    void closed_WithDates_CreatesClosedInterval() {
        Date start = Date.from(Instant.parse("2020-01-01T00:00:00Z"));
        Date end = Date.from(Instant.parse("2025-01-01T00:00:00Z"));

        TimeRange interval = TimeRange.closedByDate(start, end);

        assertEquals(start.toInstant(), interval.getStartTime());
        assertEquals(end.toInstant(), interval.getEndTime());
        assertTrue(interval.isStartInclusive());
        assertTrue(interval.isEndInclusive());
    }

    @Test
    void open_WithDates_CreatesOpenInterval() {
        Date start = Date.from(Instant.parse("2020-01-01T00:00:00Z"));
        Date end = Date.from(Instant.parse("2025-01-01T00:00:00Z"));

        TimeRange interval = TimeRange.open(start, end);

        assertEquals(start.toInstant(), interval.getStartTime());
        assertEquals(end.toInstant(), interval.getEndTime());
        assertFalse(interval.isStartInclusive());
        assertFalse(interval.isEndInclusive());
    }

    // ==================== Getter 方法测试 ====================

    @Test
    void getStartTimeAsDate_ReturnsCorrectDate() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(start, null, true, true);

        assertEquals(Date.from(start), interval.getStartTimeAsDate());
    }

    @Test
    void getEndTimeAsDate_ReturnsCorrectDate() {
        Instant end = Instant.parse("2025-01-01T00:00:00Z");
        TimeRange interval = new TimeRange(null, end, true, true);

        assertEquals(Date.from(end), interval.getEndTimeAsDate());
    }

     // ==================== 其他方法测试 ====================

    @Test
    void equals_WithSameInterval_ReturnsTrue() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");

        TimeRange interval1 = new TimeRange(start, end, true, false);
        TimeRange interval2 = new TimeRange(start, end, true, false);

        assertEquals(interval1, interval2);
    }

    @Test
    void equals_WithDifferentStart_ReturnsFalse() {
        Instant start1 = Instant.parse("2020-01-01T00:00:00Z");
        Instant start2 = Instant.parse("2021-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");

        TimeRange interval1 = new TimeRange(start1, end, true, true);
        TimeRange interval2 = new TimeRange(start2, end, true, true);

        assertNotEquals(interval1, interval2);
    }

    @Test
    void equals_WithDifferentEnd_ReturnsFalse() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end1 = Instant.parse("2025-01-01T00:00:00Z");
        Instant end2 = Instant.parse("2026-01-01T00:00:00Z");

        TimeRange interval1 = new TimeRange(start, end1, true, true);
        TimeRange interval2 = new TimeRange(start, end2, true, true);

        assertNotEquals(interval1, interval2);
    }

    @Test
    void equals_WithDifferentInclusivity_ReturnsFalse() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");

        TimeRange interval1 = new TimeRange(start, end, true, true);
        TimeRange interval2 = new TimeRange(start, end, false, true);

        assertNotEquals(interval1, interval2);
    }

    @Test
    void hashCode_WithEqualIntervals_ReturnsSameCode() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");

        TimeRange interval1 = new TimeRange(start, end, true, false);
        TimeRange interval2 = new TimeRange(start, end, true, false);

        assertEquals(interval1.hashCode(), interval2.hashCode());
    }

    @Test
    void toString_RendersCorrectly() {
        Instant start = Instant.parse("2020-01-01T00:00:00Z");
        Instant end = Instant.parse("2025-01-01T00:00:00Z");

        TimeRange closed = new TimeRange(start, end, true, true);
        assertEquals("[2020-01-01T00:00:00Z, 2025-01-01T00:00:00Z]", closed.toString());

        TimeRange open = new TimeRange(start, end, false, false);
        assertEquals("(2020-01-01T00:00:00Z, 2025-01-01T00:00:00Z)", open.toString());

    }
}