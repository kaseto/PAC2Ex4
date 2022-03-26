package edu.uoc.pac2;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
class DNATest {

    DNA dna;

    @Test
    void testConstructorGetterSetter() {
        try{
            dna = new DNA("TGAC");
            assertEquals("TGAC", dna.getBases());

            dna = new DNA("cCTAGGCTACGGCTACGctagcCTGAtcagt");
            assertEquals("CCTAGGCTACGGCTACGCTAGCCTGATCAGT", dna.getBases());

            dna.setBases("CGAGTAGTAa");
            assertEquals("CGAGTAGTAA", dna.getBases());

        }catch(Exception e) {
            fail("testConstructorGetterSetter failed");
        }
    }

    @Test
    void testConstructorGetterSetterException() {
        try{
            Exception ex = assertThrows(Exception.class, () -> new DNA("HHJ"));
            assertEquals("[ERROR] DNA's pattern is incorrect", ex.getMessage());

            ex = assertThrows(Exception.class, () -> dna.setBases("AGTCGGGAGFA"));
            assertEquals("[ERROR] DNA's pattern is incorrect", ex.getMessage());

        }catch(Exception e) {
            fail("testConstructorGetterSetter failed");
        }
    }

    @Test
    void testAddSequence() {
        try{
            dna = new DNA("CTAG");

            dna.addSequence("TTAA");
            assertEquals("CTAGTTAA", dna.getBases());

            dna.addSequence("tgggt", 2);
            assertEquals("CTTGGGTAGTTAA", dna.getBases());

            dna.addSequence("AAAAA", 7, 0);
            assertEquals("AAAAACTTGGGTAAAAAAGTTAA", dna.getBases());

            dna.addSequence("TT", 9, 10, 11);
            assertEquals("AAAAACTTGTTTTTTGGTAAAAAAGTTAA", dna.getBases());

            dna.addSequence("G", 29);
            assertEquals("AAAAACTTGTTTTTTGGTAAAAAAGTTAAG", dna.getBases());

            dna = new DNA("CTAG");
            dna.addSequence("CC", 1, 3);
            assertEquals("CCCCCTAG", dna.getBases());

            dna = new DNA("CTAG");
            dna.addSequence("CC", 3, 1);
            assertEquals("CCCTACCG", dna.getBases());

        }catch(Exception e) {
            fail("testAddSequence failed");
        }
    }


    @Test
    void testAddSequenceException() {
        try{
            dna = new DNA("AAAAACTTGTTTTTTGGTAAAAAAGTTAAG");

            Exception ex = assertThrows(Exception.class, () -> dna.addSequence("GACT", 31));
            assertEquals("[ERROR] position is greater than sequence length", ex.getMessage());
            assertEquals("AAAAACTTGTTTTTTGGTAAAAAAGTTAAG", dna.getBases());

           ex = assertThrows(Exception.class, () -> dna.addSequence("TTAH", 2));
            assertEquals("[ERROR] DNA's pattern is incorrect", ex.getMessage());
            assertEquals("AAAAACTTGTTTTTTGGTAAAAAAGTTAAG", dna.getBases());

            dna = new DNA("CTAG");

            ex = assertThrows(Exception.class, () -> dna.addSequence("CC", 5, 1));
            assertEquals("[ERROR] position is greater than sequence length", ex.getMessage());
            assertEquals("CTAG", dna.getBases());

            ex = assertThrows(Exception.class, () -> dna.addSequence("CC", 1, 7));
            assertEquals("[ERROR] position is greater than sequence length", ex.getMessage());
            assertEquals("CTAG", dna.getBases());

        }catch(Exception e) {
            fail("testAddSequenceException failed");
        }
    }


    @Test
    void testToRNA() {

        try{
            dna = new DNA("C");
            assertEquals("G", dna.toRNA());

            dna = new DNA("T");
            assertEquals("A", dna.toRNA());

            dna = new DNA("A");
            assertEquals("U", dna.toRNA());

            dna = new DNA("G");
            assertEquals("C", dna.toRNA());

            dna = new DNA("CT");
            assertEquals("AG", dna.toRNA());

            dna = new DNA("TC");
            assertEquals("GA", dna.toRNA());

            dna = new DNA("CTAG");
            assertEquals("CUAG", dna.toRNA());

            dna = new DNA("ACGGGCATCAGTATTAGCACAGT");
            assertEquals("ACUGUGCUAAUACUGAUGCCCGU", dna.toRNA());

        }catch(Exception e) {
            fail("testToRNA failed");
        }
    }

    @Test
    void testIsProtein() {
        try{

            dna = new DNA("ATGCGATACTGA");
            assertTrue(dna.isProtein());

            dna = new DNA("ATGCGATACTAG");
            assertFalse(dna.isProtein());

            dna = new DNA("TGACGATACATG");
            assertFalse(dna.isProtein());

            dna = new DNA("TAATGCGATACTGACGTC");
            assertFalse(dna.isProtein());

            dna = new DNA("ATGCGTACTGA");
            assertFalse(dna.isProtein());

            dna = new DNA("ATGTGA");
            assertTrue(dna.isProtein());

            dna = new DNA("ATGGTGA");
            assertFalse(dna.isProtein());

        }catch(Exception e) {
            fail("testIsProtein failed");
        }
    }

    @Test
    void testSequencesRepeatedLength() {
        try{

            dna = new DNA("TTTTTCCCCCTTTTTCCCCCCTTTTTGGGAAA");
            MatcherAssert.assertThat(Arrays.asList("TTTTTCCCCC", "CCCCCTTTTT"), Matchers.containsInAnyOrder(dna.sequencesRepeatedLength(10).split(" ")));
            MatcherAssert.assertThat(Arrays.asList("TTTTTCCC", "TTTTCCCC", "TTTCCCCC", "CCCCCTTT", "CCCCTTTT", "CCCTTTTT"), Matchers.containsInAnyOrder(dna.sequencesRepeatedLength(8).split(" ")));
            MatcherAssert.assertThat(Arrays.asList("TTTTT", "TTTTC", "TTTCC", "TTCCC", "TCCCC", "CCCCC", "CCCCT", "CCCTT", "CCTTT", "CTTTT"), Matchers.containsInAnyOrder(dna.sequencesRepeatedLength(5).split(" ")));

            dna = new DNA("TTTTTCCCCCTTTTTCCCCCCTTTTTAAATTAGGGGGGCCCCCCAATTGGGGGGCCCCCC");
            MatcherAssert.assertThat(Arrays.asList("GGGGGGCCCCCC"), Matchers.containsInAnyOrder(dna.sequencesRepeatedLength(12).split(" ")));
            MatcherAssert.assertThat(Arrays.asList("GGGGGGCCCCC", "GGGGGCCCCCC"), Matchers.containsInAnyOrder(dna.sequencesRepeatedLength(11).split(" ")));
            MatcherAssert.assertThat(Arrays.asList("TTTTTCCCC", "TTTTCCCCC", "CCCCCTTTT", "CCCCTTTTT", "GGGGGGCCC", "GGGGGCCCC", "GGGGCCCCC", "GGGCCCCCC"), Matchers.containsInAnyOrder(dna.sequencesRepeatedLength(9).split(" ")));


            dna = new DNA("TGATGGCGTAGCTTGGACCACCAGTAC");
            MatcherAssert.assertThat(Arrays.asList("TGG", "ACC", "CCA", "GTA"), Matchers.containsInAnyOrder(dna.sequencesRepeatedLength(3).split(" ")));

            dna = new DNA("ATATAT");
            MatcherAssert.assertThat(Arrays.asList("ATA", "TAT"), Matchers.containsInAnyOrder(dna.sequencesRepeatedLength(3).split(" ")));
            assertTrue(dna.sequencesRepeatedLength(3).contains("ATA"));
            assertTrue(dna.sequencesRepeatedLength(3).contains("TAT"));

            dna = new DNA("ACGTACGTACGTACGT");
            MatcherAssert.assertThat(Arrays.asList("ACGT", "CGTA", "GTAC", "TACG"), Matchers.containsInAnyOrder(dna.sequencesRepeatedLength(4).split(" ")));

        }catch(Exception e) {
            fail("testSequencesRepeatedLength failed");
        }
    }
}