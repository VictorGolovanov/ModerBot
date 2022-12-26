package org.golovanov.filter;

import junit.framework.TestCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class CensorTest extends TestCase {

    @SuppressWarnings("all")
    public void testFilterTextNullIsFalse() {
        String text = null;
        boolean result = Censor.filterText(text).getFirst();
        assertFalse(result);
    }


    /**
     * слова, похожие на мат, но не являющиеся таковым
     */
    @ParameterizedTest(name = "Значение => {arguments}")
    @ValueSource(strings =
            {
                    "Хулио",
                    "грабля",
                    "кребдышин",
                    "Подстрахуй братуха",
                    "Куй железо пока горячо",
                    "Курьер",
                    "Потреблять",
                    "Ипатьевский дом",
                    "СССР",
                    "бляха",
                    "бляшка",
                    "мандарин",
                    "скипидар",
            })
    public void testShouldNotTrigger(String text) {
        assertFalse(Censor.filterText(text).getFirst());
    }


    /**
     * простые варианты нецензурных слов без маскировок
     */
    @ParameterizedTest(name = "Значение => {arguments}")
    @ValueSource(strings =
            {
                    "хуй",
                    "хуепутало",
                    "вхуярить",
                    "прихуярить",
                    "нахуярить",
                    "перехуярить",
                    "хуйло",
                    "охуенно",
                    "ахуеть",
                    "хуёво",
                    "хуеплёт",
                    "хуесос",
                    "хуетряс",
                    "хуежмот",
                    "хуйлан",
                    "пизда",
                    "пиздолиз",
                    "пиздабол",
                    "пиздаболище",
                    "пиздец",
                    "отпиздить",
                    "допизделся до ледоруба",
                    "пиздануть",
                    "пиздануться",
                    "пиздострашище",
                    "пиздотряс",
                    "пиздато",
                    "ебатня",
                    "ебейший",
                    "еботня",
                    "съебался",
                    "по съёбам",
                    "хитровыебанный",
                    "съебись",
                    "невъебенный",
                    "еблан",
                    "ебантей",
                    "уёбище",
                    "уебище",
                    "ебальник",
                    "ебасос",
                    "ебобо",
                    "ебобо",
                    "разъебать",
                    "проебать",
                    "праебать",
                    "проёб",
                    "въебать",
                    "страхоёбище",
                    "блять",
                    "блядь",
                    "блядью",
                    "бля",
                    "страхоблядище",
                    "страхоблядина",
                    "потреблядь", // => не ловится пока todo => решить
                    "елда",
                    "елдак",
                    "елдой",
                    "манда",
                    "мандавошка",
            })
    public void shouldTriggerSimple(String text) {
        assertTrue(Censor.filterText(text).getFirst());
    }


    /**
     * простые варианты нецензурных слов с двойными буквами => боббёр
     */
    @ParameterizedTest(name = "Значение => {arguments}")
    @ValueSource(strings =
            {
                    "ххууйй",
                    "нахуууй",
                    "охххуенно",
                    "пиздаболиииище",
                    "пиииииздец",
                    "пиззздануться",
                    "пизддддато",
                    "ебааатня",
                    "ебботня",
                    "съеббался",
                    "ееблаан",
                    "уёёёбище",
                    "страхоёбииище",
                    "бблять",
                    "бляддь",
                    "бляяяяя",
                    "еллда",
                    "елдакк",
                    "еелдой",
                    "мманда",
            })
    public void shouldTriggerDoubleLetters(String text) {
        assertTrue(Censor.filterText(text).getFirst());
    }


    /**
     * варианты нецензурных слов с заменой одной или нескольких букв на латинские, цифры <p> => nирог, cамоваp
     */
    @ParameterizedTest(name = "Значение => {arguments}")
    @ValueSource(strings =
            {
                    "xyй",
                    "hyй",
                    "нaхyй",
                    "nиздабол",
                    "nизд@бол",
                    "пи3дос",
                    "пи3д0с",
                    "ebobo",
                    "bля",
                    "bля",
                    "ПИZZDEZ",
            })
    public void shouldTriggerLatinLetterInsideCyrillic(String text) {
        assertTrue(Censor.filterText(text).getFirst());
    }


    /**
     * варианты нецензурных слов со стандартной маскировкой б => м ; а => э
     */
    @ParameterizedTest(name = "Значение => {arguments}")
    @ValueSource(strings =
            {
                    "блэт",
                    "мля",
                    // слова, найденные потом
                    "бл@", // => не ловится пока todo => решить
                    "бл@дь", // => не ловится пока todo => решить
                    "бл@ть", // => не ловится пока todo => решить
                    "ёбацца",
                    "ПидоРАЗ",
                    "ПидорРАЗ",
                    "Пидорар",
                    "педрила",
                    "пидарюгу",
                    "пидорюка",
                    "мандализ",
            })
    public void shouldTriggerStandard(String text) {
        assertTrue(Censor.filterText(text).getFirst());
    }

}