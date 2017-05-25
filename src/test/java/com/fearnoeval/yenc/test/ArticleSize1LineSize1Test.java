package com.fearnoeval.yenc.test;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import static com.fearnoeval.yenc.test.Util.encodeOutputHelper;
import static com.fearnoeval.yenc.test.Util.shouldNotHaveThrown;
import static com.fearnoeval.yenc.test.Util.utf8;

import java.io.ByteArrayInputStream;

import java.io.IOException;

public class ArticleSize1LineSize1Test {

  // Equally-sized input

  @Test
  public void encodeToCriticalNullEquallySized() {
    final byte[] input  = { -42 };
    final byte[] expect = "=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalCarriageReturnEquallySized() {
    final byte[] input  = { -29 };
    final byte[] expect = "=M\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalLineFeedEquallySized() {
    final byte[] input  = { -32 };
    final byte[] expect = "=J\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalEscapeCharacterEquallySized() {
    final byte[] input  = { 19 };
    final byte[] expect = "=}\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalTabEquallySized() {
    final byte[] input  = { -33 };
    final byte[] expect = "=I\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalSpaceEquallySized() {
    final byte[] input  = { -10 };
    final byte[] expect = "=`\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalDotEquallySized() {
    final byte[] input  = { 4 };
    final byte[] expect = "=n\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNonCriticalEquallySized() {
    final byte[] input  = { 55 };
    final byte[] expect = "a\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Oversized input

  @Test
  public void encodeToCriticalNullOversized() {
    final byte[] input  = { -42, 0 };
    final byte[] expect = "=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalCarriageReturnOversized() {
    final byte[] input  = { -29, 0 };
    final byte[] expect = "=M\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalLineFeedOversized() {
    final byte[] input  = { -32 , 0 };
    final byte[] expect = "=J\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalEscapeCharacterOversized() {
    final byte[] input  = { 19 , 0 };
    final byte[] expect = "=}\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalTabOversized() {
    final byte[] input  = { -33, 0 };
    final byte[] expect = "=I\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalSpaceOversized() {
    final byte[] input  = { -10, 0 };
    final byte[] expect = "=`\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalDotOversized() {
    final byte[] input  = { 4, 0 };
    final byte[] expect = "=n\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNonCriticalLowersOversized() {
    final byte[] input  = { 55, 0 };
    final byte[] expect = "a\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Helpers

  private static byte[] helper(byte[] input) throws IOException {
    return encodeOutputHelper(new ByteArrayInputStream(input), 1, 1).toByteArray();
  }
}
