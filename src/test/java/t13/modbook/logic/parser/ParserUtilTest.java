package t13.modbook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static t13.modbook.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import t13.modbook.logic.parser.exceptions.ParseException;
import t13.modbook.model.module.Day;
import t13.modbook.model.module.Link;
import t13.modbook.model.module.ModBookDate;
import t13.modbook.model.module.ModBookTime;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.ModuleCode;
import t13.modbook.model.module.ModuleName;
import t13.modbook.model.module.Timeslot;
import t13.modbook.model.module.Venue;
import t13.modbook.model.module.exam.Exam;
import t13.modbook.model.module.exam.ExamName;
import t13.modbook.model.module.lesson.Lesson;
import t13.modbook.model.module.lesson.LessonName;
import seedu.modbook.model.person.Address;
import seedu.modbook.model.person.Email;
import seedu.modbook.model.person.Name;
import seedu.modbook.model.person.Phone;
import seedu.modbook.model.tag.Tag;
import t13.modbook.testutil.Assert;
import t13.modbook.testutil.TypicalIndexes;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_LINK = " ";
    private static final String INVALID_TIME = "33:00PM";
    private static final String INVALID_DATE = "20/15/2022";
    private static final String INVALID_VENUE = " ";
    private static final String INVALID_EXAM_NAME = " ";
    private static final String INVALID_LESSON_NAME = " ";
    private static final String INVALID_MODULE_CODE = "C2030S";
    private static final String INVALID_MODULE_NAME = " ";
    private static final String INVALID_FIRST_ARG = "shez";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_LINK = "https://www.google.com/";
    private static final String VALID_TIME_1 = "09:00";
    private static final String VALID_TIME_2 = "11am";
    private static final ModBookTime VALID_MBTIME_1 = new ModBookTime(VALID_TIME_1);
    private static final ModBookTime VALID_MBTIME_2 = new ModBookTime(VALID_TIME_2);
    private static final String VALID_DATE = "13/12/2021";
    private static final String VALID_VENUE = "University Sports Centre";
    private static final String VALID_EXAM_NAME = "Midterms";
    private static final String VALID_LESSON_NAME = "Lecture";
    private static final String VALID_MODULE_CODE = "CS2103T";
    private static final String VALID_MODULE_NAME = "Software Engineering";
    private static final String VALID_FIRST_ARG_MOD = "mod";
    private static final String VALID_FIRST_ARG_EXAM = "exam";
    private static final String VALID_FIRST_ARG_LESSON = "lesson";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseLink_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLink(null));
    }

    @Test
    public void parseLink_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseLink(INVALID_LINK));
    }

    @Test
    public void parseLink_validValueWithoutWhitespace_returnsLink() throws Exception {
        Link expectedLink = new Link(VALID_LINK);
        assertEquals(expectedLink, ParserUtil.parseLink(VALID_LINK));
    }

    @Test
    public void parseLink_validValueWithWhitespace_returnsLink() throws Exception {
        String linkWithWhiteSpace = WHITESPACE + VALID_LINK + WHITESPACE;
        Link expectedLink = new Link(VALID_LINK);
        assertEquals(expectedLink, ParserUtil.parseLink(linkWithWhiteSpace));
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTime(null));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        ModBookTime expectedTime = new ModBookTime(VALID_TIME_1);
        assertEquals(expectedTime, ParserUtil.parseTime(VALID_TIME_1));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_TIME_1 + WHITESPACE;
        ModBookTime expectedTime = new ModBookTime(VALID_TIME_1);
        assertEquals(expectedTime, ParserUtil.parseTime(timeWithWhitespace));
    }

    @Test
    public void parseTimeslot_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTimeslot(VALID_TIME_1, null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTimeslot(null, VALID_TIME_2));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTimeslot(null, null));
    }

    @Test
    public void parseTimeSlot_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTimeslot(VALID_TIME_1, VALID_TIME_1));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTimeslot(VALID_TIME_2, VALID_TIME_1));
    }

    @Test
    public void parseTimeslot_validValueWithoutWhitespace_returnsTimeslot() throws Exception {
        Timeslot expectedTimeslot = new Timeslot(VALID_MBTIME_1, VALID_MBTIME_2);
        assertEquals(expectedTimeslot, ParserUtil.parseTimeslot(VALID_TIME_1, VALID_TIME_2));
    }

    @Test
    public void parseTimeslot_validValueWithWhitespace_returnsTrimmedTimeslot() throws Exception {
        String timeWithWhitespaceOne = WHITESPACE + VALID_TIME_1 + WHITESPACE;
        String timeWithWhitespaceTwo = WHITESPACE + VALID_TIME_2 + WHITESPACE;
        Timeslot expectedTimeslot = new Timeslot(VALID_MBTIME_1, VALID_MBTIME_2);
        assertEquals(expectedTimeslot, ParserUtil.parseTimeslot(timeWithWhitespaceOne, timeWithWhitespaceTwo));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        ModBookDate expectedDate = new ModBookDate(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        ModBookDate expectedDate = new ModBookDate(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseVenue_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseVenue(null));
    }

    @Test
    public void parseVenue_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseVenue(INVALID_VENUE));
    }

    @Test
    public void parseVenue_validValueWithoutWhitespace_returnsVenue() throws Exception {
        Venue expectedVenue = new Venue(VALID_VENUE);
        assertEquals(expectedVenue, ParserUtil.parseVenue(VALID_VENUE));
    }

    @Test
    public void parseVenue_validValueWithWhitespace_returnsVenue() throws Exception {
        String venueWithWhiteSpace = WHITESPACE + VALID_VENUE + WHITESPACE;
        Venue expectedVenue = new Venue(VALID_VENUE);
        assertEquals(expectedVenue, ParserUtil.parseVenue(venueWithWhiteSpace));
    }

    @Test
    public void parseExamName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseExamName(null));
    }

    @Test
    public void parseExamName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseExamName(INVALID_EXAM_NAME));
    }

    @Test
    public void parseExamName_validValueWithoutWhitespace_returnsExamName() throws Exception {
        ExamName expectedExamName = new ExamName(VALID_EXAM_NAME);
        assertEquals(expectedExamName, ParserUtil.parseExamName(VALID_EXAM_NAME));
    }

    @Test
    public void parseExamName_validValueWithWhitespace_returnsExamName() throws Exception {
        String examNameWithWhiteSpace = WHITESPACE + VALID_EXAM_NAME + WHITESPACE;
        ExamName expectedExamName = new ExamName(VALID_EXAM_NAME);
        assertEquals(expectedExamName, ParserUtil.parseExamName(examNameWithWhiteSpace));
    }

    @Test
    public void parseExam_null_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new Exam(
                new ExamName(VALID_EXAM_NAME), new ModBookDate(VALID_DATE), null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> new Exam(
                new ExamName(VALID_EXAM_NAME), null, null,
                Optional.of(new Venue(VALID_VENUE)), Optional.of(new Link(VALID_LINK))));
        Assertions.assertThrows(NullPointerException.class, () -> new Exam(
                null, null, null, null, null));
    }

    @Test
    public void parseLessonName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLessonName(null));
    }

    @Test
    public void parseLessonName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseLessonName(INVALID_LESSON_NAME));
    }

    @Test
    public void parseLessonName_validValueWithoutWhitespace_returnsLessonName() throws Exception {
        LessonName expectedLessonName = new LessonName(VALID_LESSON_NAME);
        assertEquals(expectedLessonName, ParserUtil.parseLessonName(VALID_LESSON_NAME));
    }

    @Test
    public void parseLessonName_validValueWithWhitespace_returnsLessonName() throws Exception {
        String lessonNameWithWhiteSpace = WHITESPACE + VALID_LESSON_NAME + WHITESPACE;
        LessonName expectedLessonName = new LessonName(VALID_LESSON_NAME);
        assertEquals(expectedLessonName, ParserUtil.parseLessonName(lessonNameWithWhiteSpace));
    }

    @Test
    public void parseLesson_null_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new Lesson(
                new LessonName(VALID_LESSON_NAME), Day.MONDAY, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> new Lesson(
                new LessonName(VALID_LESSON_NAME), null, null,
                Optional.of(new Venue(VALID_VENUE)), Optional.of(new Link(VALID_LINK))));
        Assertions.assertThrows(NullPointerException.class, () -> new Lesson(
                null, null, null, null, null));
    }

    @Test
    public void parseLesson_validValues_returnsLesson() throws Exception {
        LessonName lessonName = new LessonName("Lecture");
        Day day = Day.MONDAY;
        Timeslot timeslot = new Timeslot(VALID_MBTIME_1, VALID_MBTIME_2);
        Lesson expectedLesson = new Lesson(lessonName, day, timeslot, Optional.of(new Venue(VALID_VENUE)),
                Optional.of(new Link(VALID_LINK)));
        assertEquals(expectedLesson, ParserUtil.parseLesson(
                "Lecture", "Monday", "09:00", "11:00",
                Optional.of(VALID_VENUE), Optional.of(VALID_LINK)));
    }

    @Test
    public void parseModuleCode_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleCode(null));
    }

    @Test
    public void parseModuleCode_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseModuleCode(INVALID_MODULE_CODE));
    }

    @Test
    public void parseModuleCode_validValueWithoutWhitespace_returnsModuleCode() throws Exception {
        ModuleCode expectedModuleCode = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(VALID_MODULE_CODE));
    }

    @Test
    public void parseModuleCode_validValueWithWhitespace_returnsTrimmedModuleCode() throws Exception {
        String moduleCodeWithWhitespace = WHITESPACE + VALID_MODULE_CODE + WHITESPACE;
        ModuleCode expectedModuleCode = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(moduleCodeWithWhitespace));
    }

    @Test
    public void parseModuleName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleName(null));
    }

    @Test
    public void parseModuleName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseModuleName(INVALID_MODULE_NAME));
    }

    @Test
    public void parseModuleName_validValueWithoutWhitespace_returnsModuleName() throws Exception {
        ModuleName expectedModuleName = new ModuleName(VALID_MODULE_NAME);
        assertEquals(expectedModuleName, ParserUtil.parseModuleName(VALID_MODULE_NAME));
    }

    @Test
    public void parseModuleName_validValueWithWhitespace_returnsTrimmedModuleName() throws Exception {
        String moduleNameWithWhitespace = WHITESPACE + VALID_MODULE_NAME + WHITESPACE;
        ModuleName expectedModuleName = new ModuleName(VALID_MODULE_NAME);
        assertEquals(expectedModuleName, ParserUtil.parseModuleName(moduleNameWithWhitespace));
    }

    @Test
    public void parseModule_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseModule(null, null));
    }

    @Test
    public void parseModule_validValues_returnsModule() throws Exception {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE);
        ModuleName moduleName = new ModuleName(VALID_MODULE_NAME);
        Module expectedModule = new Module(moduleCode, Optional.of(moduleName));
        assertEquals(expectedModule, ParserUtil.parseModule(VALID_MODULE_CODE, Optional.of(VALID_MODULE_NAME)));
    }

    @Test
    public void parseFirstArg_null_throwsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseFirstArg(null, null));
    }

    @Test
    public void parseFirstArg_validValues_returnsValidResults() throws Exception {
        assertEquals(Type.MOD, ParserUtil.parseFirstArg(VALID_FIRST_ARG_MOD, null));
        assertEquals(Type.LESSON, ParserUtil.parseFirstArg(VALID_FIRST_ARG_LESSON, null));
        assertEquals(Type.EXAM, ParserUtil.parseFirstArg(VALID_FIRST_ARG_EXAM, null));
    }

    @Test
    public void parseFirstArg_invalidValue_throwsParseException() throws Exception {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseFirstArg(INVALID_FIRST_ARG, null));
    }
}