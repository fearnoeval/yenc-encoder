package com.fearnoeval.yenc;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

import java.io.IOException;

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

  private static final byte[] crlf = {carriageReturn, lineFeed};

  public static void encode(final InputStream source, final OutputStream destination, final int articleSize, final int lineSize, final CRC32 crc32, final CRC32 pcrc32) throws IOException {
    final int    lastColumn = lineSize - 1;
    final byte[] escaped    = {escapeCharacter, 0};

    int     bytesRead      = 0;
    int     column         = 0;
    boolean shouldContinue = true;

    int b;

    while (shouldContinue) {
      b = source.read();
      ++bytesRead;

      if (b == -1) {
        break;
      }
      else {
        if (bytesRead >= articleSize) {
          shouldContinue = false;
        }

        pcrc32.update(b);
        crc32.update(b);
        b = (b + 42) & 0xFF;

        if (isCritical[b] || ((column == 0) && isLeadingCritical[b]) || (((column == lastColumn) || !shouldContinue) && isTrailingCritical[b])) {
          escaped[1] = (byte) (b + 64);
          destination.write(escaped);
          column += 2;
        }
        else {
          destination.write((byte) b);
          column += 1;
        }

        if ((column > lastColumn) && shouldContinue) {
          destination.write(crlf);
          column = 0;
        }
      }
    }

    if (column > 0) {
      destination.write(crlf);
    }
  }
}
