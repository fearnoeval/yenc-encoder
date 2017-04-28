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

  private static final String crc32Format                = "%08x";
  private static final String multiPartHeaderFormat      = "=ybegin part=%d total=%d line=%d size=%d name=%s\r\n=ypart begin=%d end=%d\r\n";
  private static final String multiPartTrailerFormat     = "=yend size=%d part=%d pcrc32=%s\r\n.\r\n";
  private static final String multiPartTrailerLastFormat = "=yend size=%d part=%d pcrc32=%s crc32=%s\r\n.\r\n";

  private static final String crc32ToString(final long crc32) {
    return String.format(crc32Format, crc32);
  }

  public static final byte[] multiPartHeader(final long partNumber, final long totalParts, final long lineSize, final long fileSize, final String name, final long beginByte, final long endByte) {
    return String.format(multiPartHeaderFormat, partNumber, totalParts, lineSize, fileSize, name, beginByte, endByte).getBytes(StandardCharsets.UTF_8);
  }
  public static final byte[] multiPartTrailer(final long partSize, final long partNumber, final long pcrc32) {
    return String.format(multiPartTrailerFormat, partSize, partNumber, crc32ToString(pcrc32)).getBytes(StandardCharsets.UTF_8);
  }
  public static final byte[] multiPartTrailerLast(final long partSize, final long partNumber, final long pcrc32, final long crc32) {
    return String.format(multiPartTrailerLastFormat, partSize, partNumber, crc32ToString(crc32), crc32ToString(crc32)).getBytes(StandardCharsets.UTF_8);
  }
}
