package com.simplaex.bedrock;

import com.greghaskins.spectrum.Spectrum;
import lombok.val;
import org.junit.runner.RunWith;

import static com.greghaskins.spectrum.Spectrum.describe;
import static com.greghaskins.spectrum.Spectrum.it;
import static com.mscharhag.oleaster.matcher.Matchers.expect;

@SuppressWarnings({"ClassInitializerMayBeStatic", "CodeBlock2Expr"})
@RunWith(Spectrum.class)
public class SeqSimpleSortedTest {

  {
    describe("a simple seq", () -> {
      val seq = Seq.of(1, 2, 2, 4, 3).sorted();
      SeqPropertyChecks.checks(seq);
    });

    describe("sorted() + union()", () -> {
      val seq1 = Seq.of(1, 2, 5, 4, 3, 3, 2).sorted();
      val seq2 = Seq.of(4, 2, 6, 7, 7, 9, 10, 12, -1, 3, 0).sorted();
      it("should return a sorted set", () -> {
        expect(seq1).toBeInstanceOf(SeqSimpleSorted.class);
        expect(seq2).toBeInstanceOf(SeqSimpleSorted.class);
      });
      it("should produce a sorted seq when union", () -> {
        expect(seq1.union(seq2)).toBeInstanceOf(SeqSimpleSorted.class);
      });
      it("result of union() should be sorted", () -> {
        val seq = seq1.union(seq2);
        expect(Seq.all(seq.zipWith(Operators::lte, seq.tail()))).toBeTrue();
      });
      it("should return the union with duplicates removed", () -> {
        expect(seq1.union(seq2)).toEqual(Seq.of(-1, 0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 12));
      });
    });

    describe("minimum()", () -> {
      it("should find minimum in sorted seq", () -> {
        expect(Seq.of(1, 2, 3).sorted().minimum()).toEqual(1);
      });
    });

    describe("maximum()", () -> {
      it("should find maximum in sorted seq", () -> {
        expect(Seq.of(1, 2, 3).sorted().maximum()).toEqual(3);
      });
    });
  }

}
