package com.fearnoeval.yenc;

import java.nio.ByteBuffer;

public final class YencEncoder {

  // Disallow instantiation

  private YencEncoder() {}

  // Critical characters

  private static final byte null_           = '\0';
  private static final byte carriageReturn  = '\r';
  private static final byte lineFeed        = '\n';
  private static final byte escapeCharacter = '=';

  // Semi-critical characters

  private static final byte tab             = '\t';
  private static final byte space           = ' ';
  private static final byte dot             = '.';

  // Helper for lookup tables

  private static final boolean[] makeLookupTable(final byte... criticalCharacters) {
    boolean[] lookupTable = new boolean[256];

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
}
