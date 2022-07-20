package ru.netology.web.test;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;

import lombok.val;
import org.junit.jupiter.api.*;

import ru.netology.web.data.BdHelper;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.CreditCardPage;
import ru.netology.web.page.TravelPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.BdHelper.cleanDataBase;


public class CreditCardTest {

    @BeforeEach
    void openPage() {
        cleanDataBase();
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Nested
    class shouldResponseDataBase {
        @Test
        void shouldSuccessWithValidCreditCard() {
            val validCardInformation = DataHelper.getValidCardInformation();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditCardPage = new CreditCardPage();
            creditCardPage.creditCardFullInformation(validCardInformation);
            creditCardPage.approved();
            assertEquals("APPROVED", new BdHelper().getPurchaseOnCreditCard());
        }

        @Test
        void shouldSuccessWithInvalidCreditCard() {
            val invalidCardInformation = DataHelper.getInvalidCardInformation();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditCardPage = new CreditCardPage();
            creditCardPage.creditCardFullInformation(invalidCardInformation);
            creditCardPage.declined();
            assertEquals("DECLINED", new BdHelper().getPurchaseOnCreditCard());
        }
    }

    @Nested
    class shouldInvalidCardNumber {
        @Test
        void shouldGetNotificationEmptyFields() {
            val invalidCardInformation = DataHelper.getInvalidCardDataIfEmptyAllFields();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardInformation);
            creditPage.shouldEmptyFieldNotification();
            creditPage.shouldImproperFormatNotification();
            creditPage.shouldValueFieldCodeCVC();
            creditPage.shouldValueFieldHolder();
            creditPage.shouldValueFieldMonth();
            creditPage.shouldValueFieldYear();
            creditPage.shouldValueFieldNumberCard();
        }

        @Test
        public void shouldInvalidCardNumberIfEmpty() {
            val invalidCardInfo = DataHelper.getInvalidCardWithNumberEmpty();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardInfo);
            creditPage.shouldValueFieldNumberCard();
        }

        @Test
        public void shouldIncorrectFieldNumberCardMiniNumber() {
            val invalidCardInfo = DataHelper.getInvalidCardWithIncorrectNumber();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardInfo);
            creditPage.shouldValueFieldNumberCard();
        }

        @Test
        public void shouldIncorrectFieldNumberCardOtherNumber() {
            val invalidCardInfo = DataHelper.getInvalidCardNumberIfOutOfDatabase();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardInfo);
            creditPage.declined();
        }
    }

    @Nested
    class shouldInvalidCardFieldMonth {
        @Test
        public void shouldInvalidMonthIfEmpty() {
            val invalidCardNumber = DataHelper.getInvalidMonthIfEmpty();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldMonth();
        }

        @Test
        public void shouldInvalidNumberOfMonthIfMore12() {
            val invalidCardNumber = DataHelper.getInvalidNumberOfMonthIfMore12();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldInvalidExpiredDateNotification();
        }

        @Test
        public void shouldInvalidNumberOfMonthIfOneDigit() {
            val invalidCardNumber = DataHelper.getInvalidNumberOfMonthWhenOneDigit();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldMonth();
        }

        @Test
        public void shouldInvalidNumberOfMonthIfZero() {
            val invalidCardNumber = DataHelper.getInvalidNumberOfMonthIfZero();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.declined();
        }
    }

    @Nested
    class shouldInvalidCardFieldYear {
        @Test
        public void shouldInvalidYearIfZero() {
            val invalidCardNumber = DataHelper.getInvalidYearIfZero();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldExpiredDatePassNotification();
        }

        @Test
        public void shouldInvalidYearIfInTheFarFuture() {
            val invalidCardNumber = DataHelper.getInvalidYearIfInTheFarFuture();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldInvalidExpiredDateNotification();
        }

        @Test
        public void shouldInvalidNumberOfYearIfOneDigit() {
            val invalidCardNumber = DataHelper.getInvalidNumberOfYearIfOneDigit();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldYear();
        }

        @Test
        public void shouldInvalidYearIfEmpty() {
            val invalidCardNumber = DataHelper.getInvalidYearIfEmpty();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldYear();
        }

        @Test
        public void shouldInvalidYearIfBeforeCurrentYear() {
            val invalidCardInfo = DataHelper.getInvalidPastYear();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardInfo);
            creditPage.shouldExpiredDatePassNotification();
        }
    }

    @Nested
    class shouldInvalidCardFieldOwner {
        @Test
        public void shouldInvalidCardOwnerNameIfEmpty() {
            val invalidCardInfo = DataHelper.getInvalidCardHolderNameIfEmpty();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardInfo);
            creditPage.shouldValueFieldHolder();
        }

        @Test
        public void shouldInvalidCardOwnerNameIfNumericAndSpecialCharacters() {
            val invalidCardInfo = DataHelper.getInvalidCardOwnerNameIfNumericAndSpecialCharacters();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardInfo);
            creditPage.shouldValueFieldHolder2();
        }

        @Test
        public void shouldInvalidCardOwnerNameIfRussianLetters() {
            val invalidCardInfo = DataHelper.getInvalidCardHolderNameIfRussianLetters();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardInfo);
            creditPage.shouldValueFieldHolder2();
        }
    }

    @Nested
    class shouldInvalidCardFieldCodeCVC {
        @Test
        public void shouldInvalidCvcIfEmpty() {
            val invalidCardInfo = DataHelper.getInvalidCvcIfEmpty();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardInfo);
            creditPage.shouldValueFieldCodeCVC();
        }

        @Test
        public void shouldInvalidCvcIfOneDigit() {
            val invalidCardNumber = DataHelper.getInvalidCvcIfOneDigit();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldCodeCVC();
        }

        @Test
        public void shouldInvalidCvcIfTwoDigits() {
            val invalidCardNumber = DataHelper.getInvalidCvcIfTwoDigits();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldCodeCVC();
        }

        @Test
        public void shouldInvalidCvvIfThreeZero() {
            val invalidCardNumber = DataHelper.getInvalidCvvIfThreeZero();
            val paymentPage = new TravelPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditCardPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldCodeCVC();
        }
    }
}





