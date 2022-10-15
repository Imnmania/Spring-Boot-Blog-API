package me.niloybiswas.spblog.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

// gets the actual date with actual timezone of Dhaka
public class DateUtil {
    public static OffsetDateTime getCurrentDate() {
        return ZonedDateTime.now(ZoneId.of("Asia/Dhaka")).toOffsetDateTime();
    }
}
