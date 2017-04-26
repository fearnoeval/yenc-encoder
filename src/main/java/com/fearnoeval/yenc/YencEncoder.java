package com.fearnoeval.yenc;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class YencEncoder {

  // Disallow instantiation

  private YencEncoder() {}

  // Critical characters

  private static final byte null_           = 0;
  private static final byte carriageReturn  = 13;
  private static final byte lineFeed        = 10;
  private static final byte escapeCharacter = 61;

  // Semi-critical characters

  private static final byte tab             = 9;
  private static final byte space           = 32;
  private static final byte dot             = 46;

  // Helper for lookup tables

  private static final boolean[] makeLookupTable(final byte... criticalCharacters) {
    final boolean[] lookupTable = new boolean[256];

    for (int i = 0; i < lookupTable.length; ++i) {
      lookupTable[i] = false;
    }

    for (int i = 0; i < criticalCharacters.length; ++i) {
      lookupTable[criticalCharacters[i]] = true;
    }

    return lookupTable;
  }

  // Lookup tables

  private static final boolean[] isCritical         = makeLookupTable(null_, carriageReturn, lineFeed, escapeCharacter);
  private static final boolean[] isLeadingCritical  = makeLookupTable(null_, carriageReturn, lineFeed, escapeCharacter, tab, space, dot);
  private static final boolean[] isTrailingCritical = makeLookupTable(null_, carriageReturn, lineFeed, escapeCharacter, tab, space);

  // Encoding

  public static int encode(final ByteBuffer source, final ByteBuffer destination, final int articleSize, final int lineSize) {
    final int startingPosition = destination.position();
    final int lastColumn = lineSize - 1;
    byte b;
    int  i;

    for (int column = 0; source.hasRemaining();) {
      b = (byte) (source.get() + 42);
      i = b & 0xFF;

      if (isCritical[i] || (column == 0 && isLeadingCritical[i]) || (column == lastColumn && isTrailingCritical[i])) {
        destination.put(escapeCharacter);
        destination.put((byte) (i + 64));
        column += 2;
      }
      else {
        destination.put((byte) i);
        column += 1;
      }

      if (column > lastColumn) {
        destination.put(carriageReturn);
        destination.put(lineFeed);
        column = 0;
      }
    }

    destination.put(carriageReturn);
    destination.put(lineFeed);

    return destination.position() - startingPosition;
  }

  // Header and trailer methods

  private static final String multiPartHeader      = "=ybegin part=%d total=%d line=%d size=%d name=%s\r\n=ypart begin=%d end=%d\r\n";
  private static final String multiPartTrailer     = "=yend size=%d part=%d pcrc32=%s\r\n.\r\n";
  private static final String multiPartTrailerLast = "=yend size=%d part=%d pcrc32=%s crc32=%s\r\n.\r\n";

  public static final byte[] multiPartHeader(final long part, final long total, final long line, final long size, final String name, final long begin, final long end) {
    return String.format(multiPartHeader, part, total, line, size, name, begin, end).getBytes(StandardCharsets.UTF_8);
  }
  public static final byte[] multiPartTrailer(final long size, final long part, final long pcrc32) {
    return String.format(multiPartTrailer, size, part, Long.toHexString(pcrc32)).getBytes(StandardCharsets.UTF_8);
  }
  public static final byte[] multiPartTrailerLast(final long size, final long part, final long pcrc32, final long crc32) {
    return String.format(multiPartTrailerLast, size, part, Long.toHexString(pcrc32), Long.toHexString(crc32)).getBytes(StandardCharsets.UTF_8);
  }
}
