package com.fearnoeval.yenc.test;

import com.fearnoeval.yenc.YencEncoder;
import static com.fearnoeval.yenc.test.Util.utf8;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class StringTest {

  @Test
  public void multiPartSubject() {
    final byte[] result = YencEncoder.multiPartSubject("test", 0, 1, "test", 2, 3);
    final byte[] expected = "test [0/1] - \"test\" yEnc (2/3)".getBytes(utf8);

    assertArrayEquals(result, expected);
  }

  @Test
  public void multiPartHeader() {
    final byte[] result   = YencEncoder.multiPartHeader(0, 1, 2, 3, "test", 4, 5);
    final byte[] expected = "=ybegin part=0 total=1 line=2 size=3 name=test\r\n=ypart begin=4 end=5\r\n".getBytes(utf8);

    assertArrayEquals(result, expected);
  }

  @Test
  public void multiPartTrailer() {
    final byte[] result   = YencEncoder.multiPartTrailer(0, 1, 2);
    final byte[] expected = "=yend size=0 part=1 pcrc32=00000002\r\n.\r\n".getBytes(utf8);;

    assertArrayEquals(result, expected);
  }

  @Test
  public void multiPartTrailerLast() {
    final byte[] result   = YencEncoder.multiPartTrailerLast(0, 1, 2, 3);
    final byte[] expected = "=yend size=0 part=1 pcrc32=00000002 crc32=00000003\r\n.\r\n".getBytes(utf8);;

    assertArrayEquals(result, expected);
  }
}
