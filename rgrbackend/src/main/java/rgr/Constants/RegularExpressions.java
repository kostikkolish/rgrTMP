package rgr.Constants;

public interface RegularExpressions {

    String RUSSIAN_CHARACTERS = "(.*)[а-яА-я](.*)";
    String USERDATA_FORBIDDEN_CHARACTERS = "(.*)\\W(.*)";
    String TEST_FORBIDDEN_CHARACTERS = "(.*)[~`@#$%^&/](.*)";
}
