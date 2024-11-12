import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import edu.princeton.cs.algs4.StdRandom;
import main.AutograderBuddy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Tests the case where the list of words is length greater than 1, but k is still zero. */
public class TestMultiWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    // ngrams files
    public static final String VERY_SHORT_WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    private static final String SMALL_WORDS_FILE = "data/ngrams/top_14377_words.csv";
    private static final String WORDS_FILE = "data/ngrams/top_49887_words.csv";

    // wordnet Files
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";
    private static final String HYPONYMS_FILE_SUBSET = "data/wordnet/hyponyms1000-subgraph.txt";
    private static final String SYNSETS_FILE_SUBSET = "data/wordnet/synsets1000-subgraph.txt";

    // EECS files
    private static final String FREQUENCY_EECS_FILE = "data/ngrams/frequency-EECS.csv";
    private static final String HYPONYMS_EECS_FILE = "data/wordnet/hyponyms-EECS.txt";
    private static final String SYNSETS_EECS_FILE = "data/wordnet/synsets-EECS.txt";


    /** This is an example from the spec.*/
    @Test
    public void testOccurrenceAndChangeK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                VERY_SHORT_WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = new ArrayList<>();
        words.add("occurrence");
        words.add("change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testKGreaterThanActual() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                VERY_SHORT_WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = new ArrayList<>();
        words.add("occurrence");
        words.add("change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 10);
        String actual = studentHandler.handle(nq);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                SMALL_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);

//        List<String> words = new ArrayList<>();
//        words.add("crude");
//        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 0);
//        String actual = studentHandler.handle(nq);
//        String expected = "[crude, crude_oil, fossil_oil, oil, petroleum, resid, residual_oil, rock_oil]";
//        assertThat(actual).isEqualTo(expected);

        List<String> words1 = new ArrayList<>();
        words1.add("food");
        words1.add("cake");
        NgordnetQuery nq1 = new NgordnetQuery(words1, 1950, 1990, 5);
        String actual1 = studentHandler.handle(nq1);
        String expected1 = "[cake, cookie, kiss, snap, wafer]";
        assertThat(actual1).isEqualTo(expected1);
    }

    @Test
    public void EECStest() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                FREQUENCY_EECS_FILE, TOTAL_COUNTS_FILE, SYNSETS_EECS_FILE, HYPONYMS_EECS_FILE);
        List<String> words = new ArrayList<>();
        words.add("CS61A");

        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 4);
        String actual = studentHandler.handle(nq);
        System.out.println("CS61A");
        System.out.println(actual);
        System.out.println("[CS170, CS61A, CS61B, CS61C]");

        List<String> words1 = new ArrayList<>();
        words1.add("CS61B");
        NgordnetQuery nq1 = new NgordnetQuery(words1, 1470, 2019, 0);
        String actual1 = studentHandler.handle(nq1);
        System.out.println("CS61B");
        System.out.println(actual1);
        System.out.println("[CS160, CS162, CS164, CS168, CS169, CS170, CS172, CS174, CS176, CS184, CS186, CS188, CS189, CS191, CS61B, bean, bee]");

        List<String> words2 = new ArrayList<>();
        words2.add("CS61A");
        words2.add("CS61B");
        words2.add("CS70");
        words2.add("CS800");
        NgordnetQuery nq2 = new NgordnetQuery(words2, 1470, 2019, 0);
        String actual2 = studentHandler.handle(nq2);
        System.out.println("CS61A, CS61B, CS70, CS800");
        System.out.println(actual2);
        System.out.println("[]");
    }

    @Test
    public void test2() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                SMALL_WORDS_FILE, TOTAL_COUNTS_FILE, SYNSETS_FILE_SUBSET, HYPONYMS_FILE_SUBSET);

        List<String> words = new ArrayList<>();
        words.add("crude");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[crude, crude_oil, fossil_oil, oil, petroleum, resid, residual_oil, rock_oil]";
        assertThat(actual).isEqualTo(expected);

        List<String> words1 = new ArrayList<>();
        words1.add("os");
        NgordnetQuery nq1 = new NgordnetQuery(words1, 1470, 2019, 0);
        String actual1 = studentHandler.handle(nq1);
        String expected1 = "[Wormian_bone, adult_tooth, anklebone, anterior, anvil, arcus_zygomaticus, arm_bone, " +
                "astragal, astragalus, atlas, atlas_vertebra, auditory_ossicle, axis, axis_vertebra, baby_tooth, " +
                "back_tooth, bare_bone, bicuspid, bone, bonelet, braincase, brainpan, breastbone, bucktooth, " +
                "calcaneus, calf_bone, calvaria, canine, canine_tooth, cannon_bone, capitate, capitate_bone, " +
                "carnassial_tooth, carpal, carpal_bone, cartilage_bone, caudal_vertebra, centrum, cervical_vertebra, " +
                "cheekbone, chop, chopper, clavicle, coccygeal_vertebra, coccyx, collarbone, conodont, corpus_sternum, " +
                "costa, cranium, cuboid_bone, cuneiform_bone, cuspid, deciduous_tooth, dentin, dentine, diaphysis, " +
                "dogtooth, dorsal_vertebra, elbow_bone, epiphysis, ethmoid, ethmoid_bone, eye_tooth, eyetooth, fang, " +
                "femoris, femur, fetter_bone, fibula, fishbone, forehead, front_tooth, frontal_bone, furcula, gladiolus, " +
                "grinder, hamate, hamate_bone, hammer, heelbone, hipbone, humerus, hyoid, hyoid_bone, ilium, incisor, " +
                "incus, innominate_bone, ischial_bone, ischium, jaw, jawbone, jowl, jugal_bone, kneecap, kneepan, " +
                "lacrimal_bone, lantern_jaw, leg_bone, long_bone, lower_jaw, lower_jawbone, lumbar_vertebra, lunate_bone, " +
                "malar, malar_bone, malleus, malposed_tooth, mandible, mandibula, mandibular_bone, manubrium, marrowbone, " +
                "maxilla, maxillary, membrane_bone, metacarpal, metacarpal_bone, metatarsal, milk_tooth, modiolus, molar, " +
                "nasal, nasal_bone, nasal_concha, navicular, neck_bone, occipital_bone, occiput, os, os_breve, os_capitatum, " +
                "os_frontale, os_hamatum, os_hyoideum, os_ischii, os_longum, os_lunatum, os_nasale, os_palatinum, os_pisiforme, " +
                "os_pubis, os_scaphoideum, os_sesamoideum, os_sphenoidale, os_tarsi_fibulare, os_temporale, os_trapezium, " +
                "os_trapezoideum, os_triquetrum, os_zygomaticum, ossicle, ossiculum, palatine, palatine_bone, parietal_bone, " +
                "pastern, patella, pearly, permanent_tooth, phalanx, pisiform, pisiform_bone, posterior, premolar, " +
                "primary_tooth, pubic_bone, pubis, pyramidal_bone, radius, ramus, rib, round_bone, sacral_vertebra, " +
                "sacrum, scaphoid_bone, scapula, semilunar_bone, sesamoid, sesamoid_bone, shaft, shin, shin_bone, shinbone, " +
                "short_bone, shoulder_blade, shoulder_bone, sinciput, skull, skullcap, sphenoid, sphenoid_bone, splint_bone, " +
                "stapes, sternum, stirrup, submaxilla, sutural_bone, tail_bone, talus, tarsal, tarsal_bone, temporal_bone, " +
                "thighbone, thoracic_vertebra, tibia, tooth, trapezium, trapezium_bone, trapezoid, trapezoid_bone, triquetral, " +
                "triquetral_bone, true_rib, turbinal, turbinate, turbinate_bone, tusk, tympanic_bone, ulna, unciform_bone, " +
                "upper_jaw, upper_jawbone, vertebra, vomer, wisdom_tooth, wishbone, wishing_bone, wrist_bone, xiphoid_process, " +
                "zygoma, zygomatic, zygomatic_arch, zygomatic_bone]";
        assertThat(actual1).isEqualTo(expected1);

        List<String> words2 = new ArrayList<>();
        words2.add("canine");
        words2.add("canine_tooth");
        words2.add("dogtooth");
        NgordnetQuery nq2 = new NgordnetQuery(words2, 1470, 2019, 0);
        String actual2 = studentHandler.handle(nq2);
        String expected2 = "[canine, canine_tooth, cuspid, dogtooth, eye_tooth, eyetooth, fang]";
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    public void test3() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                SMALL_WORDS_FILE, TOTAL_COUNTS_FILE, SYNSETS_FILE_SUBSET, HYPONYMS_FILE_SUBSET);

        List<String> words2 = new ArrayList<>();
        words2.add("tube");
        NgordnetQuery nq2 = new NgordnetQuery(words2, 1470, 2019, 6);
        String actual2 = studentHandler.handle(nq2);
        String expected2 = "[portal, tube, vein, vessel]";
        assertThat(actual2).isEqualTo(expected2);

        List<String> words = new ArrayList<>();
        words.add("thing");
        words.add("connective_tissue");
        NgordnetQuery nq = new NgordnetQuery(words, 1920, 1980, 10);
        String actual = studentHandler.handle(nq);
        String expected = "[anterior, axis, bone, investment, posterior, radius, shaft, skin, skull, tooth]";
        assertThat(actual).isEqualTo(expected);

        List<String> words1 = new ArrayList<>();
        words1.add("crude");
        NgordnetQuery nq1 = new NgordnetQuery(words1, 1450, 1451, 3);
        String actual1 = studentHandler.handle(nq1);
        String expected1 = "[]";
        assertThat(actual1).isEqualTo(expected1);

    }
}
