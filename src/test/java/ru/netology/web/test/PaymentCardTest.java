package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.BdHelper;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.TravelPage;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentCardTest {

    @BeforeAll
    static void openPage() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    @Nested
    class shouldResponseDataBase {
        @Test
        void shouldSuccessWithValidDebitCard() {
            val validCardInformation = DataHelper.getValidCardInformation();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(validCardInformation);
            paymentPage.approved();
            assertEquals("APPROVED", new BdHelper().getPaymentStatus());
            assertEquals(45000, new BdHelper().getPaymentAmount());
        }


        @Test
        void shouldSuccessWithInvalidDebitCard() {
            val invalidCardInformation = DataHelper.getInvalidCardInformation();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(invalidCardInformation);
            paymentPage.approved();
            assertEquals("DECLINED", new BdHelper().getPaymentStatus());

        }
    }

    @Nested
    class shouldInvalidCardNumber {
        @Test
        void shouldGetNotificationEmptyFields() {
            val incorrectCardInfo = DataHelper.getInvalidCardDataIfEmptyAllFields();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldEmptyFieldNotification();
            paymentPage.shouldImproperFormatNotification();
            paymentPage.shouldValueFieldCodCVC();
            paymentPage.shouldValueFieldYear();
            paymentPage.shouldValueFieldMonth();
            paymentPage.shouldValueFieldNumberCard();
            paymentPage.shouldValueFieldHolder();
        }

        @Test
        public void shouldFailurePaymentIfEmptyNumberCard() {
            val incorrectCardInfo = DataHelper.getInvalidCardWithNumberEmpty();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldNumberCard();
        }

        @Test
        public void shouldIncorrectFieldNumberCardMiniNumber() {
            val incorrectCardInfo = DataHelper.getInvalidCardWithIncorrectNumber();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldNumberCard();
        }

        @Test
        public void shouldIncorrectFieldNumberCardOtherNumber() {
            val incorrectCardInfo = DataHelper.getInvalidCardNumberIfOutOfDatabase();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.declined();
        }
    }

    @Nested
    class shouldInvalidCardFieldMonth {
        @Test
        public void shouldInvalidMonthIfEmpty() {
            val incorrectCardInfo = DataHelper.getInvalidMonthIfEmpty();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldMonth();

        }

        @Test
        public void shouldInvalidNumberOfMonthIfMore12() {
            val incorrectCardInfo = DataHelper.getInvalidNumberOfMonthIfMore12();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldInvalidExpiredDateNotification();

        }

        @Test
        public void shouldInvalidNumberOfMonthIfOneDigit() {
            val incorrectCardInfo = DataHelper.getInvalidNumberOfMonthWhenOneDigit();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldMonth();

        }

        @Test
        public void shouldInvalidNumberOfMonthIfZero() {
            val incorrectCardInfo = DataHelper.getInvalidNumberOfMonthIfZero();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.declined();
            paymentPage.shouldValueFieldMonth();
        }
    }

    @Nested
    class shouldInvalidCardFieldYear {
        @Test
        public void shouldInvalidYearIfZero() {
            val incorrectCardInfo = DataHelper.getInvalidYearIfZero();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldExpiredDatePassNotification();
        }

        @Test
        public void shouldInvalidYearIfInTheFarFuture() {
            val incorrectCardInfo = DataHelper.getInvalidYearIfInTheFarFuture();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldInvalidExpiredDateNotification();

        }

        @Test
        public void shouldInvalidNumberOfYearIfOneDigit() {
            val incorrectCardInfo = DataHelper.getInvalidNumberOfYearIfOneDigit();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldYear();

        }

        @Test
        public void shouldInvalidYearIfEmpty() {
            val incorrectCardInfo = DataHelper.getInvalidYearIfEmpty();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldYear();
        }

        @Test
        public void shouldInvalidYearIfBeforeCurrentYear() {
            val incorrectCardInfo = DataHelper.getInvalidPastYear();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldExpiredDatePassNotification();

        }
    }

    @Nested
    class shouldInvalidCardFieldOwner {
        @Test
        public void shouldInvalidCardHolderNameIfEmpty() {
            val incorrectCardInfo = DataHelper.getInvalidCardHolderNameIfEmpty();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldHolder();
        }

        @Test
        public void shouldInvalidCardOwnerNameIfNumericAndSpecialCharacters() {
            val incorrectCardInfo = DataHelper.getInvalidCardOwnerNameIfNumericAndSpecialCharacters();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldHolder();
        }

        @Test
        public void shouldInvalidCardOwnerNameIfRussianLetters() {
            val incorrectCardInfo = DataHelper.getInvalidCardHolderNameIfRussianLetters();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldHolder();
        }
    }

    @Nested
    class shouldInvalidCardFieldCodeCVC {
        @Test
        public void shouldInvalidCvcIfEmpty() {
            val incorrectCardInfo = DataHelper.getInvalidCvcIfEmpty();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldCodCVC();
        }

        @Test
        public void shouldInvalidCvcIfOneDigit() {
            val incorrectCardInfo = DataHelper.getInvalidCvcIfOneDigit();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldCodCVC();
        }

        @Test
        public void shouldInvalidCvcIfTwoDigits() {
            val incorrectCardInfo = DataHelper.getInvalidCvcIfTwoDigits();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldCodCVC();
        }

        @Test
        public void shouldInvalidCvvIfThreeZero() {
            val incorrectCardInfo = DataHelper.getInvalidCvvIfThreeZero();
            val paymentPage = new TravelPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.declined();
            paymentPage.shouldValueFieldCodCVC();
        }
    }

    //Проверяем неверные подписи строчек после правильного заполнения
    @Test
    public void shouldInvalidCardDataIfEmptyAllFieldsAndAfterFullInformationCard() {
        val incorrectCardInfo = DataHelper.getInvalidCardDataIfEmptyAllFields();
        val paymentPage = new TravelPage().selectBuyByDebitCard();
        paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
        paymentPage.shouldEmptyFieldNotification();
        paymentPage.shouldImproperFormatNotification();
        paymentPage.shouldValueFieldCodCVC();
        paymentPage.shouldValueFieldYear();
        paymentPage.shouldValueFieldMonth();
        paymentPage.shouldValueFieldNumberCard();
        paymentPage.shouldValueFieldHolder();
        val invalidCardInfo = DataHelper.getInvalidCardNumberIfOutOfDatabase();
        paymentPage.fillCardInformationForSelectedWay(invalidCardInfo);
        paymentPage.declined();
        paymentPage.shouldValueFieldCodCVC();
        paymentPage.shouldValueFieldHolder();
        paymentPage.shouldValueFieldNumberCard();
        final SelenideElement declinedNotification = $(".notification_status_error");
        declinedNotification.click();
        paymentPage.approved();
    }

}
