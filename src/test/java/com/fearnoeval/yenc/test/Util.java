package com.fearnoeval.yenc.test;

import com.fearnoeval.yenc.YencEncoder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

import java.io.IOException;

class Util {

  static final Charset utf8 = StandardCharsets.UTF_8;

  static final String shouldNotHaveThrown = "Exception shouldn't have been thrown";

  static final ByteArrayOutputStream encodeOutputHelper(final InputStream source, final int articleSize, final int lineSize) throws IOException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    encodeOutputHelper(source, baos, articleSize, lineSize);
    return baos;
  }

  static final OutputStream encodeOutputHelper(final InputStream source, final OutputStream destination, final int articleSize, final int lineSize) throws IOException {
    final CRC32 crc32  = new CRC32();
    final CRC32 pcrc32 = new CRC32();

    YencEncoder.encode(source, destination, articleSize, lineSize, crc32, pcrc32);

    return destination;
  }
}
