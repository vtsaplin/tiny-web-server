package com.tsaplin.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 * Represents HTTP protocol version.
 */
public class HttpProtocolVersion implements Comparable<HttpProtocolVersion>{

    public static HttpProtocolVersion HTTP_1_0 = new HttpProtocolVersion(1, 0);
    public static HttpProtocolVersion HTTP_1_1 = new HttpProtocolVersion(1, 1);

    private static final Pattern HTTP_VERSION = Pattern.compile("HTTP/(\\d+).(\\d+)");

    private final int majorVersion;
    private final int minorVersion;

    private HttpProtocolVersion(int majorVersion, int minorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public String asVersionString() {
        return "HTTP/" + majorVersion + "." + minorVersion;
    }

    public static HttpProtocolVersion parse(String value) {
        Matcher matcher = HTTP_VERSION.matcher(value);
        if (matcher.find()) {
            return new HttpProtocolVersion(parseInt(matcher.group(1)), parseInt(matcher.group(2)));
        }
        throw new RuntimeException("Error while parsing protocol version " + value);
    }

    @Override
    public int compareTo(HttpProtocolVersion httpProtocolVersion) {
        return ComparisonChain.start()
                .compare(majorVersion, httpProtocolVersion.majorVersion)
                .compare(minorVersion, httpProtocolVersion.minorVersion)
                .result();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpProtocolVersion that = (HttpProtocolVersion) o;
        return majorVersion == that.majorVersion && minorVersion == that.minorVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(majorVersion, minorVersion);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("majorVersion", majorVersion)
                .add("minorVersion", minorVersion)
                .toString();
    }
}
