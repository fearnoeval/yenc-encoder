package com.fearnoeval.yenc.test;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import static com.fearnoeval.yenc.test.Util.encodeOutputHelper;
import static com.fearnoeval.yenc.test.Util.shouldNotHaveThrown;
import static com.fearnoeval.yenc.test.Util.utf8;

import java.io.ByteArrayInputStream;

import java.io.IOException;

public class ArticleSize3LineSize3Test {

  // Undersized input

  @Test
  public void encodeToCriticalUndersized() {
    final byte[] input  = { -42 };
    final byte[] expect = "=@\r\n".getBytes(utf8);

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
  public void encodeToCriticalEquallySized() {
    final byte[] input  = { -42, -42, -42 };
    final byte[] expect = "=@=@\r\n=@\r\n".getBytes(utf8);


    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNonCriticalEquallySized() {
    final byte[] input  = { 55, 55, 55 };
    final byte[] expect = "aaa\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Oversized input

  @Test
  public void encodeToCriticalNullOversized() {
    final byte[] input  = { -42, -42, -42, 0 };
    final byte[] expect = "=@=@\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNonCriticalOversized() {
    final byte[] input  = { 55, 55, 55, 0 };
    final byte[] expect = "aaa\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Permutations

  @Test
  public void encodeCCC() {
    final byte[] input  = { -42, -42, -42, 0 };
    final byte[] expect = "=@=@\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCCN() {
    final byte[] input  = { -42, -42, 55, 0 };
    final byte[] expect = "=@=@\r\na\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCNC() {
    final byte[] input  = { -42, 55, -42, 0 };
    final byte[] expect = "=@a\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCNN() {
    final byte[] input  = { -42, 55, 55, 0 };
    final byte[] expect = "=@a\r\na\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNCC() {
    final byte[] input  = { 55, -42, -42, 0 };
    final byte[] expect = "a=@\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNCN() {
    final byte[] input  = { 55, -42, 55, 0 };
    final byte[] expect = "a=@\r\na\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNNC() {
    final byte[] input  = { 55, 55, -42, 0 };
    final byte[] expect = "aa=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNNN() {
    final byte[] input  = { 55, 55, 55, 0 };
    final byte[] expect = "aaa\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Helpers

  private static byte[] helper(byte[] input) throws IOException {
    return encodeOutputHelper(new ByteArrayInputStream(input), 3, 3).toByteArray();
  }
}
