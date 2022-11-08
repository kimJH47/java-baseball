package baseball;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseballGame {
    private final View view;
    private final BaseballService baseballService;

    public BaseballGame(View view, BaseballService baseballService) {
        this.view = view;
        this.baseballService = baseballService;
    }

    public void start() {
        while (true) {
            List<Integer> baseballNumberList = createBaseballNumberList();
            BaseBallDto result = baseballService.getResult(baseballNumberList);
            view.printBaseBallResult(result);
            //3스트라이크 체크 후 다음 동작
            if (result.isStrikeOut()) {
                GameOption gameOption = createGameOption();
                if (gameOption.equals(GameOption.EXIT)) {
                    break;
                }
                baseballService.resetNumberList();
            }
        }
    }


    private GameOption createGameOption() {
        String input = view.inputExitOrRestart();
        validDateSelectOption(input);
        return GameOption.of(input);
    }

    private void validDateSelectOption(String selectOption) {
        if (!(selectOption.equals("1") || selectOption.equals("2"))) {
            throw new IllegalArgumentException("1 또는 2를 선택해야 합니다");
        }
    }

    private List<Integer> createBaseballNumberList() {
        String baseballNumber = view.inputBaseballNumber();
        validateBaseballNumber(baseballNumber);
        return Stream.of(baseballNumber.split(""))
                     .map(Integer::parseInt)
                     .collect(Collectors.toCollection(ArrayList::new));
    }

    private void validateBaseballNumber(String baseballNumber) {
        String pattern = "^[0-9]*$"; // 숫자만 등장하는지
        if (baseballNumber.length() > 3) {
            throw new IllegalArgumentException("3자리 정수를 입력해야합니다.");
        }
        if (!Pattern.matches(pattern, baseballNumber)) {
            throw new IllegalArgumentException("정수만 포함되어야 합니다.");
        }
        hasDuplicateNumbers(baseballNumber);

    }

    private void hasDuplicateNumbers(String baseballNumber) {
        long count = Arrays.stream(baseballNumber.split(""))
                           .distinct()
                           .count();
        if (count != 3) {
            throw new IllegalArgumentException("서로 다른 정수를 입력해야 합니다.");
        }

    }
}
