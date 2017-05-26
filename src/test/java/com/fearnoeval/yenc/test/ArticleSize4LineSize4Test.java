package com.fearnoeval.yenc.test;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import static com.fearnoeval.yenc.test.Util.encodeOutputHelper;
import static com.fearnoeval.yenc.test.Util.shouldNotHaveThrown;
import static com.fearnoeval.yenc.test.Util.utf8;

import java.io.ByteArrayInputStream;

import java.io.IOException;

public class ArticleSize4LineSize4Test {

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
    final byte[] input  = { -42, -42, -42, -42 };
    final byte[] expect = "=@=@\r\n=@=@\r\n".getBytes(utf8);


    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNonCriticalEquallySized() {
    final byte[] input  = { 55, 55, 55, 55 };
    final byte[] expect = "aaaa\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Oversized input

  @Test
  public void encodeToCriticalNullOversized() {
    final byte[] input  = { -42, -42, -42, -42, 0 };
    final byte[] expect = "=@=@\r\n=@=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNonCriticalOversized() {
    final byte[] input  = { 55, 55, 55, 55, 0 };
    final byte[] expect = "aaaa\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Permutations

  @Test
  public void encodeCCCC() {
    final byte[] input  = { -42, -42, -42, -42, 0 };
    final byte[] expect = "=@=@\r\n=@=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCCCN() {
    final byte[] input  = { -42, -42, -42, 55, 0 };
    final byte[] expect = "=@=@\r\n=@a\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCCNC() {
    final byte[] input  = { -42, -42, 55, -42, 0 };
    final byte[] expect = "=@=@\r\na=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCCNN() {
    final byte[] input  = { -42, -42, 55, 55, 0 };
    final byte[] expect = "=@=@\r\naa\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCNCC() {
    final byte[] input  = { -42, 55, -42, -42, 0 };
    final byte[] expect = "=@a=@\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCNCN() {
    final byte[] input  = { -42, 55, -42, 55, 0 };
    final byte[] expect = "=@a=@\r\na\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCNNC() {
    final byte[] input  = { -42, 55, 55, -42, 0 };
    final byte[] expect = "=@aa\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeCNNN() {
    final byte[] input  = { -42, 55, 55, 55, 0 };
    final byte[] expect = "=@aa\r\na\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNCCC() {
    final byte[] input  = { 55, -42, -42, -42, 0 };
    final byte[] expect = "a=@=@\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNCCN() {
    final byte[] input  = { 55, -42, -42, 55, 0 };
    final byte[] expect = "a=@=@\r\na\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNCNC() {
    final byte[] input  = { 55, -42, 55, -42, 0 };
    final byte[] expect = "a=@a\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNCNN() {
    final byte[] input  = { 55, -42, 55, 55, 0 };
    final byte[] expect = "a=@a\r\na\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNNCC() {
    final byte[] input  = { 55, 55, -42, -42, 0 };
    final byte[] expect = "aa=@\r\n=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNNCN() {
    final byte[] input  = { 55, 55, -42, 55, 0 };
    final byte[] expect = "aa=@\r\na\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNNNC() {
    final byte[] input  = { 55, 55, 55, -42, 0 };
    final byte[] expect = "aaa=@\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  @Test
  public void encodeNNNN() {
    final byte[] input  = { 55, 55, 55, 55, 0 };
    final byte[] expect = "aaaa\r\n".getBytes(utf8);

    try { assertArrayEquals(expect, helper(input)); }
    catch (Exception e) { fail(shouldNotHaveThrown); }
  }

  // Helpers

  private static byte[] helper(byte[] input) throws IOException {
    return encodeOutputHelper(new ByteArrayInputStream(input), 4, 4).toByteArray();
  }
}
