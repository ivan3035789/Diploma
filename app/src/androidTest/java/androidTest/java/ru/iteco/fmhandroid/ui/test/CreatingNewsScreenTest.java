package androidTest.java.ru.iteco.fmhandroid.ui.test;

import static androidTest.java.ru.iteco.fmhandroid.ui.data.Helper.Text.textSymbol;
import static androidTest.java.ru.iteco.fmhandroid.ui.data.Helper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidTest.java.ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.ControlPanelScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.CreatingNewsScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.MainScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.WatchScreenStep;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class CreatingNewsScreenTest {

    MainScreenStep mainScreenStep = new MainScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    ControlPanelScreenStep controlPanelScreenStep = new ControlPanelScreenStep();
    CreatingNewsScreenStep creatingNewsScreenStep = new CreatingNewsScreenStep();
    WatchScreenStep watchScreenStep = new WatchScreenStep();

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenStep.checkNameMainScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.validLoginPassword(authInfo());
            SystemClock.sleep(5000);
        }
        mainScreenStep.randomTransitionToCreatingNews();
    }

    @After
    public void setUp() {
        SystemClock.sleep(5000);
    }

    @Test
    @DisplayName("?? ???????????? ???????????? ???????? ????????????????")
    @Description("?? ???????? ???????? ?????????? ???? ?????????????????? ???????????????? ???????????? Creating News")
    public void theScreenShouldHaveName() {
        creatingNewsScreenStep.checkingTheNameOfTheCreatingNewsScreen();
    }

    @Test
    @DisplayName("the fields must have names")
    @Description("?? ???????? ???????? ?????????? ???? ?????????????????? ???????????????? ??????????")
    public void FieldsMustHaveNames() {
        creatingNewsScreenStep.checkNameFieldInCreatingNews();
    }

    @Test
    @DisplayName("A drop-down list with categories should appear")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????? ???? ???????? \"Category\" ???????????????????? ???????????????????? ???????????? ?? ???????????????????? ?????????????????????? ")
    public void aDropDownListWithCategoriesShouldAppear() {
        creatingNewsScreenStep.clickingOnTheCategoryField();
        SystemClock.sleep(2000);
        creatingNewsScreenStep.checkingTheAppearanceOfTheDropDownList(ActivityTestRule.getActivity());
    }

    @Test
    @DisplayName("A calendar should appear")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????? \"Publication date\" ???????????????????? ?????????????????? ")
    public void aCalendarShouldAppear() {
        creatingNewsScreenStep.clickingOnTheDateField();
        creatingNewsScreenStep.checkingTheCalendarAppearance(ActivityTestRule.getActivity());
    }

    @Test
    @DisplayName("A clock of the arrow type should appear")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????? \"Time\" ???????????????????? ???????? ?????????????????????? ????????")
    public void aClockOfTheArrowTypeShouldAppear() {
        creatingNewsScreenStep.clickingOnTheTimeField();
        creatingNewsScreenStep.checkingTheAppearanceOfClockOfTheArrowType(ActivityTestRule.getActivity());
    }

    @Test
    @DisplayName("The type of watch should change")
    @Description("?? ???????? ???????? ?????????? ???? ?????????????????? ?????????????????????? ???????????? ???????? ??????????. ?????? ?????? ?????????????? ???? ???????????? ?? ?????????????? \"????????????????????\" ???????????? ???????????????????? ?????? ??????????")
    public void theTypeOfWatchShouldChange() {
        SystemClock.sleep(3000);
        creatingNewsScreenStep.clickingOnTheTimeField();
        watchScreenStep.pressingTheButtonToChangeTheWatchType();
        watchScreenStep.checkingTheTypeOfDigitalClock();
    }

    @Test
    @DisplayName("News should be created")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????????? \"SAVE\" ?????????? ???????????????????? ?????????? \"Title, Time, Description, Publicftion date\" ?????????????????? ????????????????????, ???????????? ?????????????????? ?????????????? (?????????????????? ?? ?????????? ???????????????? ???? ???????????????? \"News, Main\", ?? ?????? ???? ?? \"Control panel\"")
    public void newsShouldBeCreated() {
        int position = 0;
        String text = textSymbol(5);

        creatingNewsScreenStep.clickingOnTheExitButtonFromNewsCreation();
        creatingNewsScreenStep.clickingOnTheConfirmationButtonToExitTheNewsCreation();
        creatingNewsScreenStep.choosingNews(position);

        String nameNewsItWas = creatingNewsScreenStep.nameNews();
        String publicationDateNewsItWas = creatingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWas = creatingNewsScreenStep.creationDateNews();
        String authorNewsItWas = creatingNewsScreenStep.authorNews();
        String descriptionNewsItWas = creatingNewsScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.fillingInFieldsWithValidData(text);
        creatingNewsScreenStep.clickingOnTheSaveNewsButton();
        creatingNewsScreenStep.choosingNews(position);

        String nameNewsItWasHasBecomes = creatingNewsScreenStep.nameNews();
        String publicationDateNewsItWasHasBecomes = creatingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWasHasBecomes = creatingNewsScreenStep.creationDateNews();
        String authorNewsItWasHasBecomes = creatingNewsScreenStep.authorNews();
        String descriptionNewsItWasHasBecomes = creatingNewsScreenStep.descriptionNews();

        creatingNewsScreenStep.comparingTheDataOfTheCreatedNewsWithTheDataOfTheFirstNewsFromTheList(
                nameNewsItWas, nameNewsItWasHasBecomes, publicationDateNewsItWas, publicationDateNewsItWasHasBecomes,
                creationDateNewsItWas, creationDateNewsItWasHasBecomes, authorNewsItWas, authorNewsItWasHasBecomes,
                descriptionNewsItWas, descriptionNewsItWasHasBecomes);
    }

    @Test
    @DisplayName("Canceling news creation")
    @Description("?? ???????? ???????? ?????????? ???? ?????????????????? ???????????? ???????????????? ?????????????? ?????? ?????????????? ???? ???????????? \"CANCEL\"")
    public void cancelingNewsCreation() {
        int position = 0;
        String text = textSymbol(5);

        creatingNewsScreenStep.clickingOnTheExitButtonFromNewsCreation();
        creatingNewsScreenStep.clickingOnTheConfirmationButtonToExitTheNewsCreation();
        creatingNewsScreenStep.choosingNews(position);

        String nameNewsItWas = creatingNewsScreenStep.nameNews();
        String publicationDateNewsItWas = creatingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWas = creatingNewsScreenStep.creationDateNews();
        String authorNewsItWas = creatingNewsScreenStep.authorNews();
        String descriptionNewsItWas = creatingNewsScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.fillingInFieldsWithValidData(text);
        creatingNewsScreenStep.clickingOnTheExitButtonFromNewsCreation();
        creatingNewsScreenStep.clickingOnTheConfirmationButtonToCancelTheCreationOfTheNews();

        creatingNewsScreenStep.choosingNews(position);

        String nameNewsItWasHasBecomes = creatingNewsScreenStep.nameNews();
        String publicationDateNewsItWasHasBecomes = creatingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWasHasBecomes = creatingNewsScreenStep.creationDateNews();
        String authorNewsItWasHasBecomes = creatingNewsScreenStep.authorNews();
        String descriptionNewsItWasHasBecomes = creatingNewsScreenStep.descriptionNews();

        creatingNewsScreenStep.checkingTheDataOfTheFirstNewsFromTheListMustMatchAfterCancelingTheCreationOfTheNews(
                nameNewsItWas, nameNewsItWasHasBecomes, publicationDateNewsItWas, publicationDateNewsItWasHasBecomes,
                creationDateNewsItWas, creationDateNewsItWasHasBecomes, authorNewsItWas, authorNewsItWasHasBecomes,
                descriptionNewsItWas, descriptionNewsItWasHasBecomes);
    }

    @Test
    @DisplayName("A warning message should appear if the fields are empty when you click on the SAVE button")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ??????????????????????????, ?????????????????????????? ?????????? ???????????????????? ?????????????????????????????? ??????????????????, ?????????? ?????????????? ???? ???????????? \"SAVE\"  \"fill empty fields\" ")
    public void aWarningMessageShouldAppearIfTheFieldsAreEmptyWhenYouClickOnTheSaveButton() {
        creatingNewsScreenStep.clickingOnTheSaveNewsButton();
        SystemClock.sleep(2000);
        creatingNewsScreenStep.checkingTheFillEmptyFields(ActivityTestRule.getActivity(), R.string.empty_fields);
    }

    @Test
    @DisplayName("A warning message should appear when filling in the Category field")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ???????????????????? ???????? Category ?? ?????????????????????? ???????????????? ???? ???????????? ???????????????????? ???????????????????? ?????????????????????????????? ??????????????")
    public void aWarningMessageShouldAppearWhenFillingInTheCategoryField() {
        String text = textSymbol(5);

        creatingNewsScreenStep.fillingInTheCategoryField(text);
        creatingNewsScreenStep.clickingOnTheSaveNewsButton();
        creatingNewsScreenStep.checkingTheSavingFailedTryAgainLater(ActivityTestRule.getActivity(), R.string.error_saving);
    }

    @Test
    @DisplayName("The fields must be filled in with English letters")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ???????? ?????????????????????? ???????????????????? ??????????????")
    public void theFieldsMustBeFilledInWithEnglishLetters() {
        String validLanguageText = "hello world";

        creatingNewsScreenStep.validLanguage(validLanguageText, validLanguageText, validLanguageText);
        creatingNewsScreenStep.checkingForThePresenceOfWordsFromEnglishLettersInTheFields(validLanguageText);
    }

    @Test
    @DisplayName("Fields should not be filled in with Russian letters")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ???????? ?????????????????????????? ???????????????????????? ??????????????")
    public void fieldsShouldNotBeFilledInWithRussianLetters() {
        String invalidLanguageText = "???????????? ??????";
        try {
            creatingNewsScreenStep.invalidLanguage(invalidLanguageText, invalidLanguageText);
        } catch (RuntimeException expected) {
        } finally {
            creatingNewsScreenStep.checkingForTheAbsenceOfWordsFromRussianLettersInTheFields();
        }
    }
}
