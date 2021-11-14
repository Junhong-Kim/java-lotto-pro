package lotto.domain;

import java.util.Arrays;

public enum LottoPrize {

    FIRST_PLACE("1등", 6, 2_000_000_000)
    , SECOND_PLACE("2등", 5, 30_000_000)
    , THIRD_PLACE("3등", 5, 1_500_000)
    , FOURTH_PLACE("4등", 4, 50_000)
    , FIFTH_PLACE("5등", 3, 5_000)
    , MISS("낙첨", 0, 0);

    private final String name;
    private final int matchCount;
    private final int prizeMoney;

    LottoPrize(String name, int matchCount, int prizeMoney) {
        this.name = name;
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
    }

    public String getName() {
        return name;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public static LottoPrize valueOf(int matchCount, boolean matchBonus) {
        if (matchCount < FIFTH_PLACE.matchCount && matchCount >= 0) {
            return MISS;
        }

        if (SECOND_PLACE.isEqual(matchCount)) {
            return getSecondOrThirdPlace(matchBonus);
        }

        return Arrays.stream(values())
                .filter(lottoPrize -> lottoPrize.isEqual(matchCount))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static LottoPrize getSecondOrThirdPlace(boolean matchBonus) {
        return matchBonus ? SECOND_PLACE : THIRD_PLACE;
    }

    private boolean isEqual(int matchCount) {
        return this.matchCount == matchCount;
    }
}
