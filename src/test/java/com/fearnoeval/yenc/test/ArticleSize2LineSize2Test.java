package com.fearnoeval.yenc.test;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import static com.fearnoeval.yenc.test.Util.encodeOutputHelper;
import static com.fearnoeval.yenc.test.Util.shouldNotHaveThrown;
import static com.fearnoeval.yenc.test.Util.utf8;

import java.io.ByteArrayInputStream;

import java.io.IOException;

public class ArticleSize2LineSize2Test {

  // Undersized input

  @Test
  public void encodeToCriticalNullUndersized() {
    final byte[] input  = { -42 };
    final byte[] expect = "=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalCarriageReturnUndersized() {
    final byte[] input  = { -29 };
    final byte[] expect = "=M\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalLineFeedUndersized() {
    final byte[] input  = { -32 };
    final byte[] expect = "=J\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalEscapeCharacterUndersized() {
    final byte[] input  = { 19 };
    final byte[] expect = "=}\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalTabUndersized() {
    final byte[] input  = { -33 };
    final byte[] expect = "=I\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalSpaceUndersized() {
    final byte[] input  = { -10 };
    final byte[] expect = "=`\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalDotUndersized() {
    final byte[] input  = { 4 };
    final byte[] expect = "=n\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNonCriticalUndersized() {
    final byte[] input  = { 55 };
    final byte[] expect = "a\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Equally-sized input

  @Test
  public void encodeToCriticalNullEquallySized() {
    final byte[] input  = { -42, -42 };
    final byte[] expect = "=@\r\n=@\r\n".getBytes(utf8);


    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalCarriageReturnEquallySized() {
    final byte[] input  = { -29, -29 };
    final byte[] expect = "=M\r\n=M\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalLineFeedEquallySized() {
    final byte[] input  = { -32, -32 };
    final byte[] expect = "=J\r\n=J\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalEscapeCharacterEquallySized() {
    final byte[] input  = { 19, 19 };
    final byte[] expect = "=}\r\n=}\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalTabEquallySized() {
    final byte[] input  = { -33, -33 };
    final byte[] expect = "=I\r\n=I\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalSpaceEquallySized() {
    final byte[] input  = { -10, -10 };
    final byte[] expect = "=`\r\n=`\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalDotEquallySized() {
    final byte[] input  = { 4, 4 };
    final byte[] expect = "=n\r\n=n\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNonCriticalEquallySized() {
    final byte[] input  = { 55, 55 };
    final byte[] expect = "aa\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Oversized input

  @Test
  public void encodeToCriticalNullOversized() {
    final byte[] input  = { -42, -42, 0 };
    final byte[] expect = "=@\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalCarriageReturnOversized() {
    final byte[] input  = { -29, -29, 0 };
    final byte[] expect = "=M\r\n=M\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalLineFeedOversized() {
    final byte[] input  = { -32, -32, 0 };
    final byte[] expect = "=J\r\n=J\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalEscapeCharacterOversized() {
    final byte[] input  = { 19, 19, 0 };
    final byte[] expect = "=}\r\n=}\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalTabOversized() {
    final byte[] input  = { -33, -33, 0 };
    final byte[] expect = "=I\r\n=I\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalSpaceOversized() {
    final byte[] input  = { -10, -10, 0 };
    final byte[] expect = "=`\r\n=`\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeToCriticalDotOversized() {
    final byte[] input  = { 4, 4, 0 };
    final byte[] expect = "=n\r\n=n\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNonCriticalOversized() {
    final byte[] input  = { 55, 55, 0 };
    final byte[] expect = "aa\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Permutations

  @Test
  public void encodeCC() {
    final byte[] input  = { -42, -42, 0 };
    final byte[] expect = "=@\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCN() {
    final byte[] input  = { -42, 55, 0 };
    final byte[] expect = "=@\r\na\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNC() {
    final byte[] input  = { 55, -42, 0 };
    final byte[] expect = "a=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Helpers

  private static byte[] helper(byte[] input) throws IOException {
    return encodeOutputHelper(new ByteArrayInputStream(input), 2, 2).toByteArray();
  }
}
