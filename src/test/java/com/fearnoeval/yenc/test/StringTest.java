package com.fearnoeval.yenc.test;

import com.fearnoeval.yenc.YencEncoder;
import static com.fearnoeval.yenc.test.Util.utf8;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class StringTest {

  @Test
  public void multiPartSubject() {
    final byte[] result = YencEncoder.multiPartSubject("test", 0, 0, "test", 0, 0);
    final byte[] expected = "test [0/0] - \"test\" yEnc (0/0)".getBytes(utf8);

    assertArrayEquals(result, expected);
  }

  @Test
  public void multiPartHeader() {
    final byte[] result   = YencEncoder.multiPartHeader(0, 0, 0, 0, "test", 0, 0);
    final byte[] expected = "=ybegin part=0 total=0 line=0 size=0 name=test\r\n=ypart begin=0 end=0\r\n".getBytes(utf8);

    assertArrayEquals(result, expected);
  }

  @Test
  public void multiPartTrailer() {
    final byte[] result   = YencEncoder.multiPartTrailer(0, 0, 0);
    final byte[] expected = "=yend size=0 part=0 pcrc32=00000000\r\n.\r\n".getBytes(utf8);;

    assertArrayEquals(result, expected);
  }

  @Test
  public void multiPartTrailerLast() {
    final byte[] result   = YencEncoder.multiPartTrailerLast(0, 0, 0, 0);
    final byte[] expected = "=yend size=0 part=0 pcrc32=00000000 crc32=00000000\r\n.\r\n".getBytes(utf8);;

    assertArrayEquals(result, expected);
  }
}
