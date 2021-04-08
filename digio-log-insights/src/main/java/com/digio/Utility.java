package com.digio;

import com.digio.models.ApacheLogAttributes;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utility {
  public final static int IP_ADDRESS_REGEX_GROUP = 1;
  public final static int GROUP_2 = 2;
  public final static int USER_REGEX_GROUP = 3;
  public final static int DATE_REGEX_GROUP = 4;
  public final static int METHOD_REGEX_GROUP = 5;
  public final static int URL_REGEX_GROUP = 6;
  public final static int PROTOCOL_REGEX_GROUP = 7;
  public final static int RESPONSE_CODE_REGEX_GROUP = 8;
  public final static int SIZE_REGEX_GROUP = 9;
  public final static int GROUP_10 = 10;
  public final static int USER_AGENT_REGEX_GROUP = 11;
  public final static int JUNK_REGEX_GROUP = 12;
  public static final String LOG_REGEX = "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] "
        + "\"(\\S+)\\s?(\\S+)?\\s?(\\S+)?\" "
        + "(\\d{3}|-) (\\d+|-)\\s?\"?([^\"]*)\"?\\s?\"?([^\"]*)?\"?(\\S.+)?$";
  public static final Pattern PATTERN = Pattern.compile(LOG_REGEX, Pattern.MULTILINE);
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
  public static final Comparator<ApacheLogAttributes> ATTRIBUTES_COMPARATOR = Comparator
        .comparing(ApacheLogAttributes::getCount)
        .thenComparing(ApacheLogAttributes::getDate);
}
